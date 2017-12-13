package com.api.Controller;

import com.Crawler.entity.*;
import com.google.gson.JsonSyntaxException;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.cmd.Query;
import com.untility.RestfulHelper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import static com.googlecode.objectify.ObjectifyService.ofy;

public class HomeController extends HttpServlet {


    static {
        ObjectifyService.register(City.class);
        ObjectifyService.register(Movie.class);
        ObjectifyService.register(Theater.class);
        ObjectifyService.register(Showtime.class);
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //resp.setContentType("application/json");
        //resp.setCharacterEncoding("UTF-8");

        int action = 1; // 1.show tất cả phim. 2.show lịch chiếu theo phim hoặc theo rạp
        String id = null;
        String[] uriSplit = req.getRequestURI().split("/");
        if (uriSplit.length == 5) {
            action = 2;
            id = uriSplit[uriSplit.length - 1];
        }
        switch (action) {
            case 1:
                getlist(req,resp);
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
        //resp.getWriter().println(RestfulHelper.gson.toJson(responseCity));
        req.setAttribute("listCity", RestfulHelper.gson.toJson(responseCity));


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
        Query<Movie> movieQuery = ofy().load().type(Movie.class).filter("status", 2);
        int totalItem = movieQuery.count();
        int totalPage = totalItem / limit + ((totalItem % limit) > 0 ? 1 : 0);
        List<Movie> list = ofy().load().type(Movie.class).limit(limit).offset((page - 1) * limit).filter("status", 2).list();
        List<Movie> listMovie = ofy().load().type(Movie.class).filter("status", 2).list();

        HashMap<String, Object> responseMovie = new HashMap<>();
        responseMovie.put("dataMovie", listMovie);
        responseMovie.put("page", page);
        responseMovie.put("limit", limit);
        responseMovie.put("totalPage", totalPage);
        responseMovie.put("totalItem", totalItem);
        RestfulHelper.gson.toJson(responseMovie);
        req.setAttribute("listMovie", RestfulHelper.gson.toJson(responseMovie));

        req.getRequestDispatcher("/index.jsp").forward(req, resp);


    }

    private void getDetail(HttpServletRequest req, HttpServletResponse resp, String id) throws IOException, ServletException {
        //Tìm theo tên phim


        List<Movie> listMovieSearch = ofy().load().type(Movie.class).filter("movieName", id).filter("status", 2).list();
        List<Theater> listTheaterSearch = ofy().load().type(Theater.class).filter("theaterName", id).list();
        List<City> listCity = ofy().load().type(City.class).list();

        if (listMovieSearch.size() == 0 && listTheaterSearch.size() == 0) {
            resp.setStatus(404);
            ResponseMessage responseMessage = new ResponseMessage(404, "Not found", "Object is not exist or has been deleted!");
            resp.getWriter().println(RestfulHelper.gson.toJson(responseMessage));
            return;

        } else if (listMovieSearch.size() != 0) {

            for (Movie movie : listMovieSearch) {
                for (City city : listCity) {
                    List<Theater> theaterList = ofy().load().type(Theater.class).filter("cityId", city.getCityId()).list();
                    for (Theater theater : theaterList) {
                        List<Showtime> listShowtime = ofy().load().type(Showtime.class).filter("movieId", movie.getMovieId()).filter("theaterId", theater.getTheaterId()).list();
                        HashMap<String, Object> responseShowtime = new HashMap<>();
                        responseShowtime.put("dataShowtime", listShowtime);
                        RestfulHelper.gson.toJson(responseShowtime);
                        req.setAttribute("listShowtime", RestfulHelper.gson.toJson(responseShowtime));

                        resp.getWriter().println(RestfulHelper.gson.toJson(responseShowtime));
                    }

                }

            }

            req.getRequestDispatcher("lich-chieu.jsp").forward(req, resp);

        } else if (listTheaterSearch.size() != 0) {
            for (Theater theater : listTheaterSearch) {

                List<Showtime> listShowtiem = ofy().load().type(Showtime.class).filter("theaterId", theater.getTheaterId()).list();

                HashMap<String, Object> responseShowtime = new HashMap<>();
                responseShowtime.put("dataShowtime", listShowtiem);
                RestfulHelper.gson.toJson(responseShowtime);
                req.setAttribute("listShowtime", RestfulHelper.gson.toJson(responseShowtime));
                resp.getWriter().println(RestfulHelper.gson.toJson(responseShowtime));
            }

        }

//        req.getRequestDispatcher("showtime.jsp").forward(req, resp);
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        try {
            String content = RestfulHelper.parseStringInputStream(req.getInputStream());
            Showtime showtime = RestfulHelper.gson.fromJson(content, Showtime.class);
            Movie movie = RestfulHelper.gson.fromJson(content, Movie.class);
            City city = RestfulHelper.gson.fromJson(content, City.class);
            Theater theater = RestfulHelper.gson.fromJson(content, Theater.class);
            Category category = RestfulHelper.gson.fromJson(content, Category.class);


            if (ofy().save().entity(showtime).now() == null || ofy().save().entity(movie).now() == null || ofy().save().entity(city).now() == null || ofy().save().entity(category).now() == null || ofy().save().entity(theater).now() == null) {
                resp.setStatus(500);
                ResponseMessage responseMessage = new ResponseMessage(500, "Server error", "Please try again later!");
                resp.getWriter().println(RestfulHelper.gson.toJson(responseMessage));
                return;
            }
            resp.setStatus(201);
            ofy().save().entity(showtime).now();
            ofy().save().entity(movie).now();
            ofy().save().entity(city).now();
            ofy().save().entity(theater).now();
            ofy().save().entity(category).now();

            resp.getWriter().println(RestfulHelper.gson.toJson(showtime));
            resp.getWriter().println(RestfulHelper.gson.toJson(city));
            resp.getWriter().println(RestfulHelper.gson.toJson(movie));
            resp.getWriter().println(RestfulHelper.gson.toJson(theater));
            resp.getWriter().println(RestfulHelper.gson.toJson(category));
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
        if (uriSplit.length != 5) {
            resp.setStatus(400);
            ResponseMessage responseMessage = new ResponseMessage(400, "Bad request", "Invalid parameter!");
            resp.getWriter().println(RestfulHelper.gson.toJson(responseMessage));
            return;
        }
        strId = uriSplit[uriSplit.length - 1];

        Movie movie = ofy().load().type(Movie.class).id(strId).now();

    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        String strId = null;
        String[] uriSplit = req.getRequestURI().split("/");
        if (uriSplit.length != 5) {
            resp.setStatus(400);
            ResponseMessage responseMessage = new ResponseMessage(400, "Bad request", "Invalid parameter!");
            resp.getWriter().println(RestfulHelper.gson.toJson(responseMessage));
            return;
        }
        strId = uriSplit[uriSplit.length - 1];

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
        ofy().save().entity(movie).now();
        ResponseMessage responseMessage = new ResponseMessage(200, "Ok", "Object has been deleted!");
        resp.getWriter().println(RestfulHelper.gson.toJson(responseMessage));
    }

}
