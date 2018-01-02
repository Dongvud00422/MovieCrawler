package com.Endpoint;

import com.Crawler.entity.Movie;
import com.Crawler.entity.Showtime;
import com.Crawler.entity.Theater;
import com.Crawler.entity.gson.ResponseMessage;
import com.googlecode.objectify.ObjectifyService;
import com.untility.FullTextSearchHandle;
import com.untility.RestfulHelper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.googlecode.objectify.ObjectifyService.ofy;

public class SearchAPI extends HttpServlet {
    static {
        ObjectifyService.register(Showtime.class);
        ObjectifyService.register(Theater.class);
        ObjectifyService.register(Movie.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/gson");
        String reqName = req.getParameter("name");
        String reqCity = req.getParameter("city");
        String reqStartTime = req.getParameter("startTime");
        String reqEndTime = req.getParameter("endTime");
        String reqCategory = req.getParameter("category");

        if (reqEndTime != null && reqStartTime != null && reqCategory != null) {
            System.out.println(reqStartTime +" - "+reqEndTime);
            List<Movie> list = new ArrayList<>();
            try {
                Long endTime = Long.parseLong(reqStartTime);
                Long startTime = Long.parseLong(reqStartTime);
                reqCity = (reqCity == null) ? "cgv_city_3" : reqCity;

                List<Theater> listTheater = ofy().load().type(Theater.class).filter("cityId", reqCity).list();
                List<Movie> listMovie = getListByCategory(reqCategory);
                List<Showtime> listShowtime = ofy().load().type(Showtime.class).filter("startTimeMLS >=", startTime).filter("startTimeMLS <=", endTime).list();
                if (listMovie == null || listShowtime == null) {
                    resp.setStatus(404);
                    ResponseMessage responseMessage = new ResponseMessage(404, "Not Found", "not found");
                    resp.getWriter().println(RestfulHelper.gson.toJson(responseMessage));
                    return;
                }
                System.out.println("theater " + listTheater.size() + "--- mov " + listMovie.size() + "----show " + listShowtime.size());
                for (int i = 0; i < listShowtime.size(); i++) {
                    for (int k = 0; k < listMovie.size(); k++) {
                        if (listShowtime.get(i).getMovieId().equals(listMovie.get(k).getId())) {
                            list.add(listMovie.get(k));
                        }
                    }
                }

            } catch (NumberFormatException ignored) {
                resp.setStatus(400);
                ResponseMessage responseMessage = new ResponseMessage(400, "Bad request", "wrong showtime");
                resp.getWriter().println(RestfulHelper.gson.toJson(responseMessage));
            }

            resp.getWriter().println(RestfulHelper.gson.toJson(list));
            System.out.println(RestfulHelper.gson.toJson(list));
        } else if (reqName != null) {
            List<Movie> listMovie = FullTextSearchHandle.getInstance().searchMovie(reqName);
            resp.setStatus(200);
            resp.getWriter().println(RestfulHelper.gson.toJson(listMovie));
        } else if (reqStartTime != null) {
            try {
                Long endTime = Long.parseLong(reqStartTime);
                Long startTime = Long.parseLong(reqStartTime);
                reqCity = (reqCity == null) ? "cgv_city_3" : reqCity;

                List<Theater> listTheater = ofy().load().type(Theater.class).filter("cityId", reqCity).list();
                List<Showtime> listShowtime = ofy().load().type(Showtime.class).filter("startTimeMLS >=", startTime).filter("startTimeMLS <=", endTime).list();
                if (listShowtime.size() == 0) {
                    resp.setStatus(404);
                    ResponseMessage responseMessage = new ResponseMessage(404, "Not Found", "List showtime notfound");
                    resp.getWriter().println(RestfulHelper.gson.toJson(responseMessage));
                    return;
                }
                List<String> listMovieId = new ArrayList<>();
                for (Showtime aListShowtime : listShowtime) {
                    listMovieId.add(aListShowtime.getMovieId());
                }
                Map<String, Movie> listMovie = ofy().load().type(Movie.class).ids(listMovieId);
                resp.getWriter().println(RestfulHelper.gson.toJson(listMovie));

            } catch (NumberFormatException e) {
                resp.setStatus(400);
                ResponseMessage responseMessage = new ResponseMessage(400, "Bad request", "wrong showtime");
                resp.getWriter().println(RestfulHelper.gson.toJson(responseMessage));
            }
        } else if (reqCategory != null) {
            System.out.println(reqCategory);
            resp.getWriter().println(RestfulHelper.gson.toJson(getListByCategory(reqCategory)));

        } else {
            resp.setStatus(404);
            ResponseMessage responseMessage = new ResponseMessage(404, "not found", "Movie not found");
            resp.getWriter().println(RestfulHelper.gson.toJson(responseMessage));
        }
    }

    private List<Movie> getListByCategory(String reqCategory) {
        //            1.Hành động  2.Kinh Dị  3.Tình cảm  4.Tâm Lý  5.Hài  6.Hoạt Hình  7.Gia đình

        List<Movie> list = new ArrayList<>();
        String[] reqCategotySplit = reqCategory.split(",");
        List<Movie> listMovie = ofy().load().type(Movie.class).list();

        for (int i = 0; i < listMovie.size(); i++) {
            Boolean check = false;
            for (int j = 1; j <= reqCategotySplit.length; j++) {
                String movCat = "";
                switch (reqCategotySplit[i-1]) {
                    case "1":
                        movCat = "Hành Động";
                        break;
                    case "2":
                        movCat = "Kinh Dị";
                        break;
                    case "3":
                        movCat = "Tình cảm";
                        break;
                    case "4":
                        movCat = "Tâm Lý";
                        break;
                    case "5":
                        movCat = "Hài";
                        break;
                    case "6":
                        movCat = "Hoạt Hình";
                        break;
                    case "7":
                        movCat = "Gia đình";
                        break;
                }
                try {
                    check = listMovie.get(i).getCategory().contains(movCat);
                    if (check) break;
                } catch (Exception e) {
                    System.out.println("null");
                }
            }

            if (check) {
                list.add(listMovie.get(i));
                //System.out.println(i+"=========="+listMovie.get(i).getCategory());
            }
        }
        return list;
    }
}
