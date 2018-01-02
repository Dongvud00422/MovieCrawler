package com.api.Endpoint;

import com.Crawler.entity.Theater;
import com.Crawler.entity.gson.ResponseMessage;
import com.google.appengine.repackaged.com.google.gson.JsonSyntaxException;
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

public class TheaterEndpoint extends HttpServlet {
    static {
        ObjectifyService.register(Theater.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/gson");
        resp.setCharacterEncoding("UTF-8");

        int action = 1;
        String id = null;
        String[] uriSplit = req.getRequestURI().split("/");
        if (uriSplit.length == 5) {
            action = 2;
            id = uriSplit[uriSplit.length - 1];
        }
        switch (action) {
            case 1:
                getListTheater(req, resp);
            case 2:
                getDetailTheater(req, resp, id);
            default:
                break;
        }
    }

    public void getListTheater(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/gson");
        resp.setCharacterEncoding("UTF-8");


        int page = 1;
        int limit = 8;

        try {
            page = Integer.parseInt(req.getParameter("page"));
            limit = Integer.parseInt(req.getParameter("limit"));
        } catch (Exception e) {
            page = 1;
            limit = 8;
        }
        Query<Theater> theaterQuery = ofy().load().type(Theater.class);
        int totalItem = theaterQuery.count();
        int totalPage = totalItem / limit + ((totalItem % limit) > 0 ? 1 : 0);
        List<Theater> listTheater = ofy().load().type(Theater.class).limit(limit).offset((page - 1) * limit).list();
        HashMap<String, Object> responseTheater = new HashMap<>();
        responseTheater.put("dataTheater", listTheater);
        responseTheater.put("page", page);
        responseTheater.put("limit", limit);
        responseTheater.put("totalPage", totalPage);
        responseTheater.put("totalItem", totalItem);

        resp.getWriter().println(RestfulHelper.gson.toJson(responseTheater));

    }

    public void getDetailTheater(HttpServletRequest req, HttpServletResponse resp, String id) throws ServletException, IOException {
        Theater theater = ofy().load().type(Theater.class).id(id).now();
        if (theater == null) {
            resp.setStatus(404);
            ResponseMessage responseMessage = new ResponseMessage(404, "Not found", "Object is not exist or has been deleted!");
            resp.getWriter().println(RestfulHelper.gson.toJson(responseMessage));
            return;
        }
        resp.getWriter().println(RestfulHelper.gson.toJson(theater));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/gson");
        resp.setCharacterEncoding("UTF-8");

        try {
            String content = RestfulHelper.parseStringInputStream(req.getInputStream());
            Theater theater = RestfulHelper.gson.fromJson(content, Theater.class);
            HashMap<String, String> errors = theater.validate();
            if (errors.size() > 0) {
                resp.setStatus(400);
                ResponseMessage responseMessage = new ResponseMessage(400, "Bad request", RestfulHelper.gson.toJson(errors));
                resp.getWriter().println(RestfulHelper.gson.toJson(responseMessage));
                return;
            }
            if (ofy().save().entity(theater).now() == null) {
                resp.setStatus(500);
                ResponseMessage responseMessage = new ResponseMessage(500, "Server error", "Please try again later!");
                resp.getWriter().println(RestfulHelper.gson.toJson(responseMessage));
                return;
            }
            resp.setStatus(201);
            resp.getWriter().println(RestfulHelper.gson.toJson(theater));

        } catch (JsonSyntaxException e) {
            resp.setStatus(400);
            ResponseMessage responseMessage = new ResponseMessage(400, "Bad request", "Invalid parameter!");
            resp.getWriter().println(RestfulHelper.gson.toJson(responseMessage));
            return;
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/gson");
        resp.setCharacterEncoding("UTF-8");

        String strId = null;
        String[] uriSplit = req.getRequestURI().split("/");
        if (uriSplit.length != 5) {
            resp.setStatus(400);
            ResponseMessage responseMessage = new ResponseMessage(400, "Bad request", "Invalid parameter!");
            resp.getWriter().println(RestfulHelper.gson.toJson(responseMessage));
            return;
        }
        strId = uriSplit[uriSplit.length - 1];

        Theater theater = ofy().load().type(Theater.class).id(strId).now();
        if (theater == null) {
            resp.setStatus(404);
            ResponseMessage responseMessage = new ResponseMessage(404, "Not found", "Object is not exist or has been deleted!");
            resp.getWriter().println(RestfulHelper.gson.toJson(responseMessage));
            return;
        }
        try {
            String content = RestfulHelper.parseStringInputStream(req.getInputStream());
            Theater theaterUpdate = RestfulHelper.gson.fromJson(content, Theater.class);
            HashMap<String, String> errors = theaterUpdate.validate();
            if (errors.size() > 0) {
                resp.setStatus(400);
                ResponseMessage responseMessage = new ResponseMessage(400, "Bad request", RestfulHelper.gson.toJson(errors));
                resp.getWriter().println(RestfulHelper.gson.toJson(responseMessage));
                return;
            }
            theater.setTheaterName(theaterUpdate.getTheaterName());
            theater.setHotline(theaterUpdate.getHotline());
            theater.setFax(theaterUpdate.getFax());
            theater.setAddress(theaterUpdate.getAddress());
            theater.setCityId(theaterUpdate.getCityId());
            if (ofy().save().entity(theater).now() == null) {
                resp.setStatus(500);
                ResponseMessage responseMessage = new ResponseMessage(500, "Server error", "Please try again later!");
                resp.getWriter().println(RestfulHelper.gson.toJson(responseMessage));
                return;
            }
            resp.setStatus(200);
            resp.getWriter().println(RestfulHelper.gson.toJson(theater));

        } catch (JsonSyntaxException e) {
            resp.setStatus(400);
            ResponseMessage responseMessage = new ResponseMessage(400, "Bad request", "Invalid parameter!");
            resp.getWriter().println(RestfulHelper.gson.toJson(responseMessage));
            return;
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}
