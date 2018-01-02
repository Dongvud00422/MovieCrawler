package com.Crawler.support;

import com.Crawler.entity.City;
import com.Crawler.entity.gson.ResponseMessage;
import com.googlecode.objectify.ObjectifyService;
import com.untility.RestfulHelper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.googlecode.objectify.ObjectifyService.ofy;

public class addCityFromCrawler extends HttpServlet {
    static {
        ObjectifyService.register(City.class);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/gson");
        resp.setCharacterEncoding("UTF-8");

            String content = RestfulHelper.parseStringInputStream(req.getInputStream());
            City city = RestfulHelper.gson.fromJson(content, City.class);

            if (ofy().save().entity(city).now() == null) {
                resp.setStatus(500);
                ResponseMessage responseMessage = new ResponseMessage(500, "Server error", "Please try again later!");
                resp.getWriter().println(RestfulHelper.gson.toJson(responseMessage));
                return;
            }
            resp.setStatus(201);
            resp.getWriter().println(RestfulHelper.gson.toJson(city));
    }
}

