package com.api.Endpoint;

import com.Crawler.entity.City;
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

public class CityEndpoint extends HttpServlet {
    static {
        ObjectifyService.register(City.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/gson");
        resp.setCharacterEncoding("UTF-8");

        int action = 1;
        String id = null;
        String[] uriSplit = req.getRequestURI().split("/");
        if (uriSplit.length == 6) {
            action = 2;
            id = uriSplit[uriSplit.length - 1];
        }
        switch (action) {
            case 1:
                getListCity(req, resp);
            case 2:
                getDetailCity(req, resp, id);
            default:
                break;
        }
    }

    private void getListCity(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int page = 1;
        int limit = 8;

        try {
            page = Integer.parseInt(req.getParameter("page"));
            limit = Integer.parseInt(req.getParameter("limit"));
        } catch (Exception e) {
            page = 1;
            limit = 8;
        }
        Query<City> cityQuery = ofy().load().type(City.class);
        int totalItem = cityQuery.count();
        int totalPage = totalItem / limit + ((totalItem % limit) > 0 ? 1 : 0);
        List<City> listCity = ofy().load().type(City.class).limit(limit).offset((page - 1) * limit).list();
        HashMap<String, Object> responseCity = new HashMap<>();
        responseCity.put("dataCity", listCity);
        responseCity.put("page", page);
        responseCity.put("limit", limit);
        responseCity.put("totaItem", totalItem);
        responseCity.put("totaPage", totalPage);

        resp.getWriter().println(RestfulHelper.gson.toJson(responseCity));
    }

    private void getDetailCity(HttpServletRequest req, HttpServletResponse resp, String id) throws ServletException, IOException {
        City city = ofy().load().type(City.class).id(id).now();
        if (city == null) {
            resp.setStatus(404);
            ResponseMessage responseMessage = new ResponseMessage(404, "Not found", "Object is not exist or has been deleted!");
            resp.getWriter().println(RestfulHelper.gson.toJson(responseMessage));
            return;
        }
        resp.getWriter().println(RestfulHelper.gson.toJson(city));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/gson");
        resp.setCharacterEncoding("UTF-8");


            String content = RestfulHelper.parseStringInputStream(req.getInputStream());
            City city = RestfulHelper.gson.fromJson(content, City.class);

            HashMap<String, String> errors = city.validate();
            if (errors.size() > 0) {
                resp.setStatus(400);
                ResponseMessage responseMessage = new ResponseMessage(400, "Bad request", RestfulHelper.gson.toJson(errors));
                resp.getWriter().println(RestfulHelper.gson.toJson(responseMessage));
                return;
            }

            if (ofy().save().entity(city).now() == null) {
                resp.setStatus(500);
                ResponseMessage responseMessage = new ResponseMessage(500, "Server error", "Please try again later!");
                resp.getWriter().println(RestfulHelper.gson.toJson(responseMessage));
                return;
            }
            resp.setStatus(201);
            resp.getWriter().println(RestfulHelper.gson.toJson(city));
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/gson");
        resp.setCharacterEncoding("UTF-8");

        String strId = null;
        String[] uriSplit = req.getRequestURI().split("/");
        if (uriSplit.length != 6) {
            resp.setStatus(400);
            ResponseMessage responseMessage = new ResponseMessage(400, "Bad request", "Invalid parameter!");
            resp.getWriter().println(RestfulHelper.gson.toJson(responseMessage));
            return;
        }
        strId = uriSplit[uriSplit.length - 1];

        City city = ofy().load().type(City.class).id(strId).now();
        if (city == null) {
            resp.setStatus(404);
            ResponseMessage responseMessage = new ResponseMessage(404, "Not found", "Object is not exist or has been deleted!");
            resp.getWriter().println(RestfulHelper.gson.toJson(responseMessage));
            return;
        }
        try {
            String content = RestfulHelper.parseStringInputStream(req.getInputStream());
            City cityUpdate = RestfulHelper.gson.fromJson(content, City.class);
            HashMap<String, String> errors = cityUpdate.validate();
            if (errors.size() > 0) {
                resp.setStatus(400);
                ResponseMessage responseMessage = new ResponseMessage(400, "Bad request", RestfulHelper.gson.toJson(errors));
                resp.getWriter().println(RestfulHelper.gson.toJson(responseMessage));
                return;
            }
            city.setName(cityUpdate.getName());
            if (ofy().save().entity(city).now() == null) {
                resp.setStatus(500);
                ResponseMessage responseMessage = new ResponseMessage(500, "Server error", "Please try again later!");
                resp.getWriter().println(RestfulHelper.gson.toJson(responseMessage));
                return;
            }
            resp.setStatus(200);
            resp.getWriter().println(RestfulHelper.gson.toJson(city));
        } catch (JsonSyntaxException e) {
            resp.setStatus(400);
            ResponseMessage responseMessage = new ResponseMessage(400, "Bad request", "Invalid parameter!");
            resp.getWriter().println(RestfulHelper.gson.toJson(responseMessage));
            return;
        }
    }
}

