package com.api.Endpoint;

import com.Crawler.entity.Movie;
import com.Crawler.entity.Showtime;
import com.Crawler.entity.gson.ResponseMessage;
import com.google.gson.JsonSyntaxException;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.cmd.Query;
import com.untility.FullTextSearchHandle;
import com.untility.RestfulHelper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.googlecode.objectify.ObjectifyService.ofy;

public class MovieEndpoint extends HttpServlet {

    static {
        ObjectifyService.register(Movie.class);
        ObjectifyService.register(Showtime.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/gson");
        resp.setCharacterEncoding("UTF-8");

        int action = 1;
        String id = "";
        String[] uriSplit = req.getRequestURI().split("/");
        if (uriSplit.length == 9) {
            action = 2;
            id = req.getRequestURI().split("movie/")[1];
        }
        switch (action) {
            case 1:
                getList(req, resp);
                // resp.getWriter().println("get list");
                break;
            case 2:
                getDetail(req, resp, id);
                //resp.getWriter().println("get detail");
                break;
            default:
                break;
        }
    }

    public void getList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/gson");
        resp.setCharacterEncoding("UTF-8");

        int page = 1;
        int limit = 8;

        try {
            page = Integer.parseInt(req.getParameter("page"));
            limit = Integer.parseInt(req.getParameter("limit"));
        } catch (Exception e) {
            page = 1;
            limit = 8;
        }
        Query<Movie> movieQuery = ofy().load().type(Movie.class);
        int totalItem = movieQuery.count();
        int totalPage = totalItem / limit + ((totalItem % limit) > 0 ? 1 : 0);
        List<Movie> listMovie = ofy().load().type(Movie.class).limit(limit).offset((page - 1) * limit).filter("status !=", 0).list();

        HashMap<String, Object> responseMovie = new HashMap<>();
        responseMovie.put("dataMovie", listMovie);
        responseMovie.put("page", page);
        responseMovie.put("limit", limit);
        responseMovie.put("totalPage", totalPage);
        responseMovie.put("totalItem", totalItem);


        RestfulHelper.gson.toJson(responseMovie);
        resp.getWriter().println(RestfulHelper.gson.toJson(responseMovie));
    }

    public void getDetail(HttpServletRequest req, HttpServletResponse resp, String id) throws ServletException, IOException {
        resp.setContentType("application/gson");
        resp.setCharacterEncoding("UTF-8");

        Movie movie = ofy().load().type(Movie.class).id(id).now();
        if (movie == null || movie.getStatus() == 0) {
            resp.setStatus(404);
            ResponseMessage responseMessage = new ResponseMessage(404, "Not found", "Movie is not exist or has been deleted!");
            resp.getWriter().println(RestfulHelper.gson.toJson(responseMessage));
            return;
        }
        RestfulHelper.gson.toJson(movie);
        resp.getWriter().println(RestfulHelper.gson.toJson(movie));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/gson");
        resp.setCharacterEncoding("UTF-8");

        try {
            String content = RestfulHelper.parseStringInputStream(req.getInputStream());
            Movie movie = RestfulHelper.gson.fromJson(content, Movie.class);
            movie.setStatus(1);
            HashMap<String, String> errors = movie.validate();
            if (errors.size() > 0) {
                resp.setStatus(400);
                ResponseMessage responseMessage = new ResponseMessage(400, "Bad request", RestfulHelper.gson.toJson(errors));
                resp.getWriter().println(RestfulHelper.gson.toJson(responseMessage));
                return;
            }
            if (ofy().save().entity(movie).now() == null) {
                resp.setStatus(500);
                ResponseMessage responseMessage = new ResponseMessage(500, "Server error", "Please try again later!");
                resp.getWriter().println(RestfulHelper.gson.toJson(responseMessage));
                return;
            }
            FullTextSearchHandle.add(movie.toSearchDocument());
            resp.setStatus(201);
            resp.getWriter().println(RestfulHelper.gson.toJson(movie));
        } catch (JsonSyntaxException e) {
            resp.setStatus(400);
            ResponseMessage responseMessage = new ResponseMessage(400, "Bad request", "Invalid parameter!");
            resp.getWriter().println(RestfulHelper.gson.toJson(responseMessage));
            return;
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/gson");
        resp.setCharacterEncoding("UTF-8");

        // Kiểm tra định dạng url.
        String strId = null;
        String[] uriSplit = req.getRequestURI().split("/");
        if (uriSplit.length != 9) {
            resp.setStatus(400);
            ResponseMessage responseMessage = new ResponseMessage(400, "Bad request", "Invalid parameter!");
            resp.getWriter().println(RestfulHelper.gson.toJson(responseMessage));
            return;
        }
        strId = req.getRequestURI().split("movie/")[1];

        Movie movie = ofy().load().type(Movie.class).id(strId).now();
        if (movie == null) {
            resp.setStatus(404);
            ResponseMessage responseMessage = new ResponseMessage(404, "Not found", "Movie is not exist or has been deleted!");
            resp.getWriter().println(RestfulHelper.gson.toJson(responseMessage));
            return;
        }
        try {
            String content = RestfulHelper.parseStringInputStream(req.getInputStream());
            Movie movieUpdate = RestfulHelper.gson.fromJson(content, Movie.class);
            HashMap<String, String> errors = movieUpdate.validate();
            if (errors.size() > 0) {
                resp.setStatus(400);
                ResponseMessage responseMessage = new ResponseMessage(400, "Bad request", RestfulHelper.gson.toJson(errors));
                resp.getWriter().println(RestfulHelper.gson.toJson(responseMessage));
                return;
            }
            movie.setName(movieUpdate.getName());
            movie.setType(movieUpdate.getType());
            movie.setTrailer(movieUpdate.getTrailer());
            movie.setPoster(movieUpdate.getPoster());
            movie.setOpenAt(movieUpdate.getOpenAt());
            movie.setDuration(movieUpdate.getDuration());
            movie.setLanguage(movieUpdate.getLanguage());
            movie.setDirector(movieUpdate.getDirector());
            movie.setDescription(movieUpdate.getDescription());
            movie.setActor(movieUpdate.getActor());
            movie.setMinAge(movieUpdate.getMinAge());
            //movie.setMovieId(movieUpdate.getMovieId());
            movie.setStatus(movieUpdate.getStatus());
            if (ofy().save().entity(movie).now() == null) {
                resp.setStatus(500);
                ResponseMessage responseMessage = new ResponseMessage(500, "Server error", "Please try again later!");
                resp.getWriter().println(RestfulHelper.gson.toJson(responseMessage));
                return;
            }
            FullTextSearchHandle.add(movie.toSearchDocument());
            resp.setStatus(200);
            resp.getWriter().println(RestfulHelper.gson.toJson(movie));

        } catch (JsonSyntaxException e) {
            resp.setStatus(400);
            ResponseMessage responseMessage = new ResponseMessage(400, "Bad request", "Invalid parameter!");
            resp.getWriter().println(RestfulHelper.gson.toJson(responseMessage));
            return;
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/gson");
        resp.setCharacterEncoding("UTF-8");

        String strId = null;
        String[] uriSplit = req.getRequestURI().split("/");
        if (uriSplit.length != 9) {
            resp.setStatus(400);
            ResponseMessage responseMessage = new ResponseMessage(400, "Bad request", "Invalid parameter!");
            resp.getWriter().println(RestfulHelper.gson.toJson(responseMessage));
            return;
        }
        strId = req.getRequestURI().split("movie/")[1];

        Movie movie = ofy().load().type(Movie.class).id(strId).now();
        if (movie == null || movie.getStatus() == 0) {
            resp.setStatus(404);
            ResponseMessage responseMessage = new ResponseMessage(404, "Not found", "Movie is not exist or has been deleted!");
            resp.getWriter().println(RestfulHelper.gson.toJson(responseMessage));
            return;
        }
        movie.setStatus(0);
        List<Showtime> listShowtime = ofy().load().type(Showtime.class).filter("movieId", strId).list();
        List<Key> listKey = new ArrayList<>();
        for (Showtime showtime : listShowtime) {
            showtime.setStatus(0);
            Key key = ofy().save().entity(showtime).now();
            listKey.add(key);
        }

        if (ofy().save().entity(movie).now() == null || listKey.size() < listShowtime.size()) {
            resp.setStatus(500);
            ResponseMessage responseMessage = new ResponseMessage(500, "Server error", "Please try again later!");
            resp.getWriter().println(RestfulHelper.gson.toJson(responseMessage));
            return;
        }
        ResponseMessage responseMessage = new ResponseMessage(200, "Ok", "Movie has been deleted!");
        resp.getWriter().println(RestfulHelper.gson.toJson(responseMessage));
    }
}
