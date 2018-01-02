package com.Crawler.support;

import com.Crawler.entity.Movie;
import com.Crawler.entity.Showtime;
import com.Crawler.entity.gson.ResponseMessage;
import com.google.gson.JsonSyntaxException;
import com.googlecode.objectify.ObjectifyService;
import com.untility.FullTextSearchHandle;
import com.untility.RestfulHelper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.googlecode.objectify.ObjectifyService.ofy;

public class addMovieFromCrawler extends HttpServlet {

    static {
        ObjectifyService.register(Movie.class);
        ObjectifyService.register(Showtime.class);
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/gson");
        resp.setCharacterEncoding("UTF-8");

        try {
            String content = RestfulHelper.parseStringInputStream(req.getInputStream());
            Movie movie = RestfulHelper.gson.fromJson(content, Movie.class);

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
}
