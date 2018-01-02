package com.api.Controller;

import com.Crawler.entity.*;
import com.Crawler.entity.gson.ResponseMessage;
import com.googlecode.objectify.ObjectifyService;
import com.untility.RestfulHelper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static com.googlecode.objectify.ObjectifyService.ofy;

public class ShowtimeController extends HttpServlet {

    static {
        ObjectifyService.register(City.class);
        ObjectifyService.register(Movie.class);
        ObjectifyService.register(Theater.class);
        ObjectifyService.register(Showtime.class);
    }

   @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding("UTF-8");

        String idMovie = req.getParameter("movieId");
        String cityName = req.getParameter("cityName");

        req.setAttribute("city", cityName);
        Movie movie = ofy().load().type(Movie.class).id(idMovie).now();
        req.setAttribute("movie", movie);
       System.out.println(idMovie + "--" + movie.getName());

        if (movie == null || movie.getStatus() == 0) {
            resp.setStatus(404);
            ResponseMessage responseMessage = new ResponseMessage(404, "Not found", "Object is not exist or has been deleted!");
            resp.getWriter().println(RestfulHelper.gson.toJson(responseMessage));
            return;

        }
        List<City> listCity = ofy().load().type(City.class).list();
        req.setAttribute("listCity", listCity);
        List<City> cityList = ofy().load().type(City.class).filter("cityName", cityName).list();

        for (City city : cityList) {
            List<Theater> listTheater = ofy().load().type(Theater.class).filter("cityId", city.getId()).list();
            req.setAttribute("listTheater", listTheater);
            for (Theater theater : listTheater) {
                List<Showtime> listShowtime = ofy().load().type(Showtime.class).filter("movieId", movie.getId()).filter("theaterId", theater.getTheaterId()).list();
                req.setAttribute("listShowtime", listShowtime);
            }
        }
        req.getRequestDispatcher("/showtime.jsp").forward(req, resp);
    }



}
