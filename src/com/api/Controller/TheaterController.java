package com.api.Controller;

import com.Crawler.entity.City;
import com.Crawler.entity.Movie;
import com.Crawler.entity.Showtime;
import com.Crawler.entity.Theater;
import com.googlecode.objectify.ObjectifyService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.googlecode.objectify.ObjectifyService.ofy;

public class TheaterController extends HttpServlet {

    static {
        ObjectifyService.register(City.class);
        ObjectifyService.register(Movie.class);
        ObjectifyService.register(Theater.class);
        ObjectifyService.register(Showtime.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       // String cityName = req.getParameter("cityName");
        String cityName = "Hà Nội";
        req.setAttribute("city", cityName);
        List<City> listCity = ofy().load().type(City.class).filter("cityName", cityName).list();
        //System.out.println("city" + listCity.size());
        req.setAttribute("listCity", listCity);
        List<Movie> listMovie = new ArrayList<>();
        for (City city : listCity){
            List<Theater> listTheater = ofy().load().type(Theater.class).filter("cityId", city.getId()).list();
            req.setAttribute("listTheater", listTheater);
            System.out.println("theater" + listTheater.size());
            for (Theater theater : listTheater){
                List<Showtime> listShowtime = ofy().load().type(Showtime.class).filter("theaterId", theater.getTheaterId()).list();
                //System.out.println("showtime" + listShowtime.size());
                req.setAttribute("listShowtime", listShowtime);
                for (Showtime showtime : listShowtime){
                    Movie movie = ofy().load().type(Movie.class).id(showtime.getMovieId()).now();
                    listMovie.add(movie);
                    //System.out.println("movie " + listMovie.size());
                    req.setAttribute("listMovie", listMovie);
                }
            }
        }
        req.getRequestDispatcher("/cum-rap.jsp").forward(req, resp);
    }
}
