package com.api.Endpoint;

import com.Crawler.entity.*;
import com.google.gson.JsonSyntaxException;
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

public class HomeEndpoint extends HttpServlet {
    static {
        ObjectifyService.register(City.class);
        ObjectifyService.register(Movie.class);
        ObjectifyService.register(Theater.class);
        ObjectifyService.register(Showtime.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        int action = 1; // 1.show tất cả phim. 2.show lịch chiếu theo phim hoặc theo rạp
        String id = null;
        String[] uriSplit = req.getRequestURI().split("/");
        if (uriSplit.length == 9) {
            action = 2;
            id = req.getRequestURI().split("movie/")[1];
        }
        switch (action) {
            case 1:
                getlist(req, resp);
                break;
            case 2:
                getDetail(req, resp, id);
                break;
            default:
                break;
        }
    }

    private void getlist(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        //Lấy danh sách City

        List<City> listCity = ofy().load().type(City.class).list();
        HashMap<String, Object> responseCity = new HashMap<>();
        responseCity.put("dataCity", listCity);
        RestfulHelper.gson.toJson(responseCity);
        resp.getWriter().println(RestfulHelper.gson.toJson(responseCity));

        //Lấy danh sách phim,có tham số page và limit
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
        List<Movie> list = ofy().load().type(Movie.class).limit(limit).offset((page - 1) * limit).filter("status !=", 0).list();

        HashMap<String, Object> responseMovie = new HashMap<>();
        responseMovie.put("dataMovie", list);
        responseMovie.put("page", page);
        responseMovie.put("limit", limit);
        responseMovie.put("totalPage", totalPage);
        responseMovie.put("totalItem", totalItem);


        RestfulHelper.gson.toJson(responseMovie);
        resp.getWriter().println(RestfulHelper.gson.toJson(responseMovie));
    }

    private void getDetail(HttpServletRequest req, HttpServletResponse resp, String id) throws IOException, ServletException {
        //Tìm theo tên phim
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        Movie movie = ofy().load().type(Movie.class).id(id).now();
        HashMap<String, Object> responseMovie = new HashMap<>();
        responseMovie.put("dataMovie", movie);
        RestfulHelper.gson.toJson(responseMovie);

        resp.getWriter().println(RestfulHelper.gson.toJson(responseMovie));


        if (movie == null || movie.getStatus() == 0) {
            resp.setStatus(404);
            ResponseMessage responseMessage = new ResponseMessage(404, "Not found", "Object is not exist or has been deleted!");
            resp.getWriter().println(RestfulHelper.gson.toJson(responseMessage));
            return;
        }
        List<City> listCity = ofy().load().type(City.class).list();
        HashMap<String, Object> responseCity = new HashMap<>();
        responseCity.put("dataCity", listCity);
        RestfulHelper.gson.toJson(responseCity);
        resp.getWriter().println(RestfulHelper.gson.toJson(responseCity));

        HashMap<String, Object> responseShowtime = new HashMap<>();
        HashMap<String, Object> responseTheater = new HashMap<>();
        for (City city : listCity) {
            List<Theater> listTheater = ofy().load().type(Theater.class).filter("cityId", city.getCityId()).list();

            responseTheater.put("dataTheater", listTheater);
            RestfulHelper.gson.toJson(responseTheater);
            resp.getWriter().println(RestfulHelper.gson.toJson(responseTheater));
            for (Theater theater : listTheater) {
                List<Showtime> listShowtime = ofy().load().type(Showtime.class).filter("movieId", movie.getMovieId()).filter("theaterId", theater.getTheaterId()).list();

                responseShowtime.put("dataShowtime", listShowtime);
                RestfulHelper.gson.toJson(responseShowtime);
                resp.getWriter().println(RestfulHelper.gson.toJson(responseShowtime));

            }
        }
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        try {
            String content = RestfulHelper.parseStringInputStream(req.getInputStream());
            ListResponsePostSW listData = RestfulHelper.gson.fromJson(content, ListResponsePostSW.class);
            Movie movie = listData.getMovie();
            movie.setStatus(1);
            Theater theater = listData.getTheater();
            List<Showtime> listShowtime = listData.getDataShowtime();


            HashMap<String, String> errorsMovie = movie.validate();
            HashMap<String, Object> errorsShowtime = new HashMap<>();
            for (int i = 0; i < listShowtime.size(); i++) {

                errorsShowtime.put("errors", listShowtime.get(i).validate());
            }
            System.out.println(errorsShowtime.size());

            if (errorsMovie.size() > 0 || errorsShowtime.size() > 1) {
                resp.setStatus(400);
                HashMap<String, Object> errors = new HashMap<>();
                errors.put("errorsMovie", errorsMovie);
                errors.put("errorsShowtime", errorsShowtime);
                ResponseMessage responseMessage = new ResponseMessage(400, "Bad request", RestfulHelper.gson.toJson(errors));
                resp.getWriter().println(RestfulHelper.gson.toJson(responseMessage));
                return;
            }
            if (ofy().save().entity(movie).now() == null || ofy().save().entity(theater).now() == null) {
                resp.setStatus(500);
                ResponseMessage responseMessage = new ResponseMessage(500, "Server error", "Please try again later!");
                resp.getWriter().println(RestfulHelper.gson.toJson(responseMessage));
                return;
            }
            for (int i = 0; i < listShowtime.size(); i++) {
                ofy().save().entity(listShowtime.get(i)).now();
            }
            FullTextSearchHandle.add(movie.toSearchDocument());

            resp.setStatus(201);
            ListResponsePostSW data = new ListResponsePostSW();
            data.setDataShowtime((ArrayList<Showtime>) listShowtime);
            data.setMovie(movie);
            data.setTheater(theater);

            resp.getWriter().println(RestfulHelper.gson.toJson(data));

        } catch (IOException | JsonSyntaxException e) {
            resp.setStatus(400);
            ResponseMessage responseMessage = new ResponseMessage(400, "Bad request", "Invalid parameter!");
            resp.getWriter().println(RestfulHelper.gson.toJson(responseMessage));
            return;
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
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
        if (movie == null || movie.getStatus() == 0) {
            resp.setStatus(404);
            ResponseMessage responseMessage = new ResponseMessage(404, "Not found", "Object is not exist or has been deleted!");
            resp.getWriter().println(RestfulHelper.gson.toJson(responseMessage));
            return;
        }
        List<City> listCity = ofy().load().type(City.class).list();
        List<Theater> listTheater = ofy().load().type(Theater.class).list();
        List<Showtime> listShowtime = ofy().load().type(Showtime.class).filter("movieId", movie.getMovieId()).list();
        if (listShowtime.size() == 0) {
            resp.setStatus(404);
            ResponseMessage responseMessage = new ResponseMessage(404, "Not found", "Object is not exist or has been deleted!");
            resp.getWriter().println(RestfulHelper.gson.toJson(responseMessage));
            return;
        }

        try {
            String content = RestfulHelper.parseStringInputStream(req.getInputStream());


        } catch (IOException | JsonSyntaxException ex) {
            resp.setStatus(400);
            ResponseMessage responseMessage = new ResponseMessage(400, "Bad request", "Invalid parameter!");
            resp.getWriter().println(RestfulHelper.gson.toJson(responseMessage));
            return;
        }
        FullTextSearchHandle.add(movie.toSearchDocument());

        ResponseMessage responseMessage = new ResponseMessage(200, "Ok", "Edit success");
        resp.getWriter().println(RestfulHelper.gson.toJson(responseMessage));

    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
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
            ResponseMessage responseMessage = new ResponseMessage(404, "Not found", "Object is not exist or has been deleted!");
            resp.getWriter().println(RestfulHelper.gson.toJson(responseMessage));
            return;
        }
        movie.setStatus(0);
        if (ofy().save().entity(movie).now() == null) {
            resp.setStatus(500);
            ResponseMessage responseMessage = new ResponseMessage(500, "Server error", "Please try again later!");
            resp.getWriter().println(RestfulHelper.gson.toJson(responseMessage));
            return;
        }

        ResponseMessage responseMessage = new ResponseMessage(200, "Ok", "Object has been deleted!");
        resp.getWriter().println(RestfulHelper.gson.toJson(responseMessage));
    }

}
