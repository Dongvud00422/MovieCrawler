package com.api.Controller;

import com.Crawler.entity.City;
import com.Crawler.entity.Movie;
import com.Crawler.entity.Showtime;
import com.Crawler.entity.Theater;

import com.googlecode.objectify.ObjectifyService;
import com.untility.FullTextSearchHandle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.googlecode.objectify.ObjectifyService.ofy;

public class SearchController extends HttpServlet {
    static {
        ObjectifyService.register(City.class);
        ObjectifyService.register(Movie.class);
        ObjectifyService.register(Theater.class);
        ObjectifyService.register(Showtime.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/gson");
        resp.setCharacterEncoding("UTF-8");

        String keyword = req.getParameter("keyword");


        List<Movie> listMovie = new ArrayList<>();
        String notification = null;
        // List<Theater> listTheater = new ArrayList<>();
        if (keyword != null && keyword.length() > 0) {
            listMovie = FullTextSearchHandle.getInstance().searchMovie(keyword);
            req.setAttribute("listMovieSearch", listMovie);
        }

        req.getRequestDispatcher("/searchMovie.jsp").forward(req, resp);
    }
}
