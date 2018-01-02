package com.api.Controller;

import com.googlecode.objectify.ObjectifyService;
import com.Crawler.entity.*;

import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static com.googlecode.objectify.ObjectifyService.ofy;

//@WebServlet(name = "CheckOutController")
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
