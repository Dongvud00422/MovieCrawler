package com.Crawler.support;

import com.Crawler.entity.Showtime;
import com.Crawler.entity.gson.ResponseMessage;
import com.google.appengine.repackaged.com.google.gson.JsonSyntaxException;
import com.googlecode.objectify.ObjectifyService;
import com.untility.RestfulHelper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

import static com.googlecode.objectify.ObjectifyService.ofy;

public class addShowtimeFromCrawler extends HttpServlet {
    static {
        ObjectifyService.register(Showtime.class);
    }



    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/gson");
        resp.setCharacterEncoding("UTF-8");

        try{
            String content = RestfulHelper.parseStringInputStream(req.getInputStream());
            Showtime showtime = RestfulHelper.gson.fromJson(content, Showtime.class);
            HashMap<String, String> errors = showtime.validate();
            if (errors.size() > 0){
                resp.setStatus(400);
                ResponseMessage responseMessage = new ResponseMessage(400, "Bad request", RestfulHelper.gson.toJson(errors));
                resp.getWriter().println(RestfulHelper.gson.toJson(responseMessage));
                return;
            }
            if (ofy().save().entity(showtime).now() == null){
                resp.setStatus(500);
                ResponseMessage responseMessage = new ResponseMessage(500, "Server error", "Please try again later!");
                resp.getWriter().println(RestfulHelper.gson.toJson(responseMessage));
                return;
            }
            resp.setStatus(201);
            resp.getWriter().println(RestfulHelper.gson.toJson(showtime));
        }catch (JsonSyntaxException e){
            resp.setStatus(400);
            ResponseMessage responseMessage = new ResponseMessage(400, "Bad request", "Invalid parameter!");
            resp.getWriter().println(RestfulHelper.gson.toJson(responseMessage));
            return;
        }
    }
}
