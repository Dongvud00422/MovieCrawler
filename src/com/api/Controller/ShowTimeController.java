package com.api.Controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import com.Crawler.entity.Movie;
import com.Crawler.entity.Showtime;
import com.googlecode.objectify.ObjectifyService;
import static com.googlecode.objectify.ObjectifyService.ofy;

public class ShowTimeController extends HttpServlet {
    static {
        ObjectifyService.register(Showtime.class);
        ObjectifyService.register(Movie.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String movieId = req.getParameter("movieId");
        String cityId = req.getParameter("cityId");
       // Showtime showtime = ofy().load().type(Showtime.class).id(movieId).now();
        List<Showtime> showtimeList = ofy().load().type(Showtime.class).filter("movieId ==", movieId).list();
        req.setAttribute("showtime", showtimeList);
        for (Showtime obj : showtimeList){
            System.out.println(obj.getDate());
        }
        req.getRequestDispatcher("showtime.jsp").forward(req, resp);

    }
}