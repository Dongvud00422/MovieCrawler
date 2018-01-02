package com.api.Endpoint;

import com.Crawler.entity.Showtime;
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

public class ShowtimeEndpoint extends HttpServlet {
    static {
        ObjectifyService.register(Showtime.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/gson");
        resp.setCharacterEncoding("UTF-8");

        int action = 1;
        String id = null;
        String[] uriSplit = req.getRequestURI().split("/");
        if (uriSplit.length == 8) {
            action = 2;
            id = req.getRequestURI().split("showtime/")[1];
        }
        switch (action) {
            case 1:
                getListShowtime(req, resp);
            case 2:
                getDetailShowtime(req, resp, id);
            default:
                break;
        }
    }

    public void getListShowtime(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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
        Query<Showtime> showtimeQuery = ofy().load().type(Showtime.class);
        int totalItem = showtimeQuery.count();
        int totalPage = totalItem / limit + ((totalItem % limit) > 0 ? 1 : 0);
        List<Showtime> listShowtime = ofy().load().type(Showtime.class).limit(limit).offset((page - 1) * limit).list();
        HashMap<String, Object> responseShowtime = new HashMap<>();
        responseShowtime.put("dataShowtime", listShowtime);
        responseShowtime.put("page", page);
        responseShowtime.put("limit", limit);
        responseShowtime.put("totalPage", totalPage);
        responseShowtime.put("totalItem", totalItem);

        resp.getWriter().println(RestfulHelper.gson.toJson(responseShowtime));
    }

    public void getDetailShowtime(HttpServletRequest req, HttpServletResponse resp, String id) throws ServletException, IOException {
        Showtime showtime = ofy().load().type(Showtime.class).id(id).now();
        if (showtime == null) {
            resp.setStatus(404);
            ResponseMessage responseMessage = new ResponseMessage(404, "Not found", "Object is not exist or has been deleted!");
            resp.getWriter().println(RestfulHelper.gson.toJson(responseMessage));
            return;
        }
        resp.getWriter().println(RestfulHelper.gson.toJson(showtime));
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

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/gson");
        resp.setCharacterEncoding("UTF-8");

        String strId = null;
        String[] uriSplit = req.getRequestURI().split("/");
        if (uriSplit.length != 8) {
            resp.setStatus(400);
            ResponseMessage responseMessage = new ResponseMessage(400, "Bad request", "Invalid parameter!");
            resp.getWriter().println(RestfulHelper.gson.toJson(responseMessage));
            return;
        }
        strId = uriSplit[uriSplit.length - 1];

        Showtime showtime = ofy().load().type(Showtime.class).id(strId).now();
        if (showtime == null) {
            resp.setStatus(404);
            ResponseMessage responseMessage = new ResponseMessage(404, "Not found", "Object is not exist or has been deleted!");
            resp.getWriter().println(RestfulHelper.gson.toJson(responseMessage));
            return;
        }
        try{
            String content = RestfulHelper.parseStringInputStream(req.getInputStream());
            Showtime showtimeUpdate = RestfulHelper.gson.fromJson(content, Showtime.class);
            HashMap<String, String> errors = showtimeUpdate.validate();
            if (errors.size() > 0){
                resp.setStatus(400);
                ResponseMessage responseMessage = new ResponseMessage(400, "Bad request", RestfulHelper.gson.toJson(errors));
                resp.getWriter().println(RestfulHelper.gson.toJson(responseMessage));
                return;
            }
            showtime.setMovieId(showtimeUpdate.getMovieId());
            showtime.setSlotLeft(showtimeUpdate.getSlotLeft());
            showtime.setEndTimeMLS(showtimeUpdate.getEndTimeMLS());
            showtime.setStartTimeMLS(showtimeUpdate.getStartTimeMLS());
            showtime.setTheaterId(showtimeUpdate.getTheaterId());
            showtime.setStatus(showtimeUpdate.getStatus());
            if (ofy().save().entity(showtime).now() == null){
                resp.setStatus(500);
                ResponseMessage responseMessage = new ResponseMessage(500, "Server error", "Please try again later!");
                resp.getWriter().println(RestfulHelper.gson.toJson(responseMessage));
                return;
            }
            resp.setStatus(200);
            resp.getWriter().println(RestfulHelper.gson.toJson(showtime));

        }catch (JsonSyntaxException e){
            resp.setStatus(400);
            ResponseMessage responseMessage = new ResponseMessage(400, "Bad request", "Invalid parameter!");
            resp.getWriter().println(RestfulHelper.gson.toJson(responseMessage));
            return;
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/gson");
        resp.setCharacterEncoding("UTF-8");

        String strId = null;
        String[] uriSplit = req.getRequestURI().split("/");
        if (uriSplit.length != 8) {
            resp.setStatus(400);
            ResponseMessage responseMessage = new ResponseMessage(400, "Bad request", "Invalid parameter!");
            resp.getWriter().println(RestfulHelper.gson.toJson(responseMessage));
            return;
        }
        strId = req.getRequestURI().split("movie/")[1];

        Showtime showtime = ofy().load().type(Showtime.class).id(strId).now();
        if (showtime == null || showtime.getStatus() == 0) {
            resp.setStatus(404);
            ResponseMessage responseMessage = new ResponseMessage(404, "Not found", "Object is not exist or has been deleted!");
            resp.getWriter().println(RestfulHelper.gson.toJson(responseMessage));
            return;
        }
        showtime.setStatus(0);
        if (ofy().save().entity(showtime).now() == null){
            resp.setStatus(500);
            ResponseMessage responseMessage = new ResponseMessage(500, "Server error", "Please try again later!");
            resp.getWriter().println(RestfulHelper.gson.toJson(responseMessage));
            return;
        }
        ResponseMessage responseMessage = new ResponseMessage(200, "Ok", "Object has been deleted!");
        resp.getWriter().println(RestfulHelper.gson.toJson(responseMessage));
    }
}
