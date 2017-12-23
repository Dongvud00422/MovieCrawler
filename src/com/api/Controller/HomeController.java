package com.api.Controller;

import com.Crawler.entity.City;
import com.Crawler.entity.Movie;
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

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");


        //req.getParameter("")


        List<City> listCity = ofy().load().type(City.class).list();
        HashMap<String, Object> responseCity = new HashMap<>();
        responseCity.put("dataCity", listCity);
        RestfulHelper.gson.toJson(responseCity);
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
        req.setAttribute("listMovie", RestfulHelper.gson.toJson(responseMovie));
        req.getRequestDispatcher("/index.jsp").forward(req, resp);
    }
}

