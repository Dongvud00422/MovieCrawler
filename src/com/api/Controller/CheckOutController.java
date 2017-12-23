package com.api.Controller;

import com.Crawler.entity.City;
import com.Crawler.entity.Movie;
import com.Crawler.entity.Showtime;
import com.Crawler.entity.Theater;
import com.googlecode.objectify.ObjectifyService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "CheckOutController")
public class CheckOutController extends HttpServlet {

    static {
        ObjectifyService.register(City.class);
        ObjectifyService.register(Movie.class);
        ObjectifyService.register(Theater.class);
        ObjectifyService.register(Showtime.class);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");


    }
}
