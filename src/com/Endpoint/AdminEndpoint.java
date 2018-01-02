package com.Endpoint;

import com.Crawler.entity.Account;
import com.Crawler.entity.gson.ResponseMessage;
import com.google.gson.JsonSyntaxException;
import com.googlecode.objectify.ObjectifyService;
import com.untility.RestfulHelper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

import static com.googlecode.objectify.ObjectifyService.ofy;


public class AdminEndpoint extends HttpServlet {

    static{
        ObjectifyService.register(Account.class);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String[] uriSplit = req.getRequestURI().split("/");
        if (uriSplit.length != 5) {
            resp.setStatus(400);
            ResponseMessage responseMessage = new ResponseMessage(400, "Bad request", "URI is split");
            resp.getWriter().println(RestfulHelper.gson.toJson(responseMessage));
        }

        String endpoint = uriSplit[uriSplit.length - 1];
        switch (endpoint) {
            case "login":
                loginAdmin(req,resp);
                break;
            case "register":
                registerAdmin(req,resp);
                break;
            case "checkAccount":
                checkAccount(req,resp);
                break;
            default:
                resp.setStatus(400);
                ResponseMessage responseMessage = new ResponseMessage(400, "Bad request", "URI is split");
                resp.getWriter().println(RestfulHelper.gson.toJson(responseMessage));
        }

    }

    private void checkAccount(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String content = RestfulHelper.parseStringInputStream(req.getInputStream());
        Account check = RestfulHelper.gson.fromJson(content,Account.class);
        Account admin = ofy().load().type(Account.class).id(check.getAccount()).now();
        if (admin == null){
            resp.getWriter().println("ok");
        } else {
            resp.getWriter().println("exist");
        }

    }

    private void loginAdmin(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String content = RestfulHelper.parseStringInputStream(req.getInputStream());
        Account checkAdmin = RestfulHelper.gson.fromJson(content,Account.class);
        Account admin = ofy().load().type(Account.class).id(checkAdmin.getAccount()).now();
        if (admin == null){
            resp.setStatus(403);
            ResponseMessage responseMessage = new ResponseMessage(403,"Forbidden","account/password is incorrect");
            resp.getWriter().println(RestfulHelper.gson.toJson(responseMessage));
            return;
        }
        if(!checkAdmin.getPassword().equals(admin.getPassword())){
            resp.setStatus(403);
            ResponseMessage responseMessage = new ResponseMessage(403,"Forbidden","account/password is incorrect");
            resp.getWriter().println(RestfulHelper.gson.toJson(responseMessage));
            return;
        }
        resp.getWriter().println(RestfulHelper.gson.toJson(admin));
    }


    private void registerAdmin(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            String content = RestfulHelper.parseStringInputStream(req.getInputStream());
            Account admin = RestfulHelper.gson.fromJson(content,Account.class);

            HashMap<String,String> errors = admin.validate();
            if (errors.size() > 0 ){
                resp.setStatus(400);
                ResponseMessage responseMessage = new ResponseMessage(400,"Bad request",RestfulHelper.gson.toJson(errors));
                resp.getWriter().println(RestfulHelper.gson.toJson(responseMessage));
                return;
            }
            if(ofy().save().entity(admin).now() == null){
                resp.setStatus(500);
                ResponseMessage responseMessage = new ResponseMessage(500,"Server error","contact to admin");
                resp.getWriter().println(RestfulHelper.gson.toJson(responseMessage));
                return;
            }
            resp.getWriter().println(RestfulHelper.gson.toJson(admin));
        } catch(JsonSyntaxException e){
            resp.setStatus(400);
            ResponseMessage responseMessage = new ResponseMessage(400, "Bad request", "Invalid parameter");
            resp.getWriter().println(RestfulHelper.gson.toJson(responseMessage));
            return;
        }
    }
}
