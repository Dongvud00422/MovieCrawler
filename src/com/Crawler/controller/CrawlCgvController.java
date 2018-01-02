package com.Crawler.controller.CGVCrawler;

import com.Crawler.entity.*;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;
import com.untility.Convert;
import com.untility.FullTextSearchHandle;
import com.untility.RestfulHelper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import static com.googlecode.objectify.ObjectifyService.ofy;

public class CrawlCgvController {
    static {
        ObjectifyService.register(City.class);
        ObjectifyService.register(Theater.class);
        ObjectifyService.register(Movie.class);
        ObjectifyService.register(Showtime.class);
    }

    private Document cgvCoreData;
    private Logger LOG = Logger.getLogger(CrawlCgvController.class.getName());

    private Document loadDocument(String target) throws Exception {
//        String strDoc = new CgvDocument().getDocument(target);
//        System.out.println(strDoc);
//        return Jsoup.parse(strDoc);

        return new CgvDocument().loadDocument(target);
    }

    public void getCgvCity() throws Exception {
        List<City> cities = new ArrayList<>();
        List<Theater> theaters = new ArrayList<>();
        cgvCoreData = loadDocument("https://www.cgv.vn/default/cinox/site/");
        Elements listElementsCity = cgvCoreData.select("div.cinemas-area li");
        Elements listElementsCinema = cgvCoreData.select("div.cinemas-list li");

        for (int i = 0; i < listElementsCity.size(); i++) {
            String cityId = listElementsCity.get(i).getElementsByAttribute("id").attr("id").trim();
            String cityName = listElementsCity.get(i).text();
            City city = new City();
            city.setId(cityId);
            city.setName(cityName);
            System.out.println(city.getName());
            System.out.println(RestfulHelper.gson.toJson(city));
            Jsoup.connect("https://flashmovie-fpt.appspot.com/_api/v1/city")
                    .ignoreContentType(true)
                    .requestBody(RestfulHelper.gson.toJson(city))
                    .post();
            cities.add(city);

            for (int j = 0; j < listElementsCinema.size(); j++) {
                Elements listTheater = listElementsCinema.get(j).getElementsByClass(cityId);
                for (int k = 0; k < listTheater.size(); k++) {
                    Theater theater = new Theater();
                    String theaterId = listTheater.get(k).getElementsByTag("span").attr("onclick").split("'")[1].trim();
                    String theaterName = listTheater.get(k).getElementsByTag("span").text();
                    theater.setTheaterId(theaterId);
                    theater.setCityId(cityId);
                    theater.setTheaterName(theaterName);
                    theaters.add(theater);
                }
            }
        }
        ofy().save().entities(cities).now();
        ofy().save().entities(theaters).now();
        LOG.info("Get city  & theater base info success.");
    }

    public void getTheaterInfo() throws Exception {
        List<Theater> existTheaters = ofy().load().type(Theater.class).list();
        List<Theater> updateTheaters = new ArrayList<>();

        for (Theater theater : existTheaters) {
            LOG.info(theater.getTheaterId());
            cgvCoreData = loadDocument(theater.getTheaterId());
            Theater updateTheater = new Theater();
            updateTheater.setTheaterId(theater.getTheaterId());
            updateTheater.setStatus(1);
            updateTheater.setCityId(theater.getCityId());
            updateTheater.setTheaterName(theater.getTheaterName());

            // Lấy địa chỉ rạp.
            String theaterAddress = cgvCoreData.select(".theater-address").text();
            updateTheater.setAddress(theaterAddress);

            // Fax.
            String faxNumber = cgvCoreData.select(".fax .fax-input").text();
            updateTheater.setFax(faxNumber);

            // Hotline.
            String hotline = cgvCoreData.select(".hotline .fax-input").text();
            updateTheater.setHotline(hotline);
            updateTheaters.add(updateTheater);
        }
        Map<Key<Theater>, Theater> a = ofy().save().entities(updateTheaters).now();
        if (a != null) {
            LOG.info("add theater success.");
        }
    }

    public void getShowTimeInfo() throws Exception {

        List<Theater> listTheater = ofy().load().type(Theater.class).list();
        System.out.println(listTheater.size());

        List<Showtime> listShowtimes = new ArrayList<>();
        List<Movie> listMovies = new ArrayList<>();

        for (Theater theater : listTheater) {
            cgvCoreData = loadDocument(theater.getTheaterId());
            Elements date = cgvCoreData.select(".tab");

            for (int i = 1; i < date.size() - 1; i++) {
                Movie movie = new Movie();
                int day = Integer.parseInt(date.get(i).getElementsByTag("strong").text());
                int month = Integer.parseInt(date.get(i).getElementsByTag("span").text());
                Elements filmList = date.get(i).nextElementSibling().getElementsByClass("film-list");

                for (int a = 0; a < filmList.size(); a++) {
                    String poster = filmList.get(a).getElementsByTag("img").attr("src");
                    String filmName = filmList.get(a).getElementsByTag("a").attr("title");
                    String filmId = filmList.get(a).getElementsByTag("a").attr("href").trim();

                    movie.setStatus(2);
                    movie.setName(filmName);
                    movie.setPoster(poster);


                    Elements type = filmList.get(a).select(".film-right .film-screen");
                    Elements showTimes = filmList.get(a).select(".film-right .film-showtimes");
                    for (int b = 0; b < type.size(); b++) {

                        String language = type.get(b).text().trim();
                        movie.setType(language);
                        Elements show = showTimes.get(b).getElementsByClass("item");
                        movie.setId(filmId);
                        listMovies.add(movie);

                        for (int c = 0; c < show.size(); c++) {
                            try {
                            System.out.println("link "+show.get(c).select("a").attr("href"));

                            String showid = show.get(c).select("a").attr("href").split("id/")[1].trim();
                            System.out.println("ok1");

                            String time = show.get(c).select("span:first-child").text().split(" ")[0].trim();
                            System.out.println("ok2");

                            String slot = show.get(c).select("span:last-child").text().split(" ")[0];
                            System.out.println("ok3");


                            Showtime showTime = new Showtime();
                            showTime.setTheaterId(theater.getTheaterId());
                            showTime.setId(Long.valueOf(showid));
                            showTime.setMovieId(filmId);
                            showTime.setSlotLeft(Integer.parseInt(slot));
                            showTime.setStatus(1);
                            showTime.setMovieType(language);
                            String t = day + "/" + month + " " + time;
                            long s = Convert.dateTimeToMilisecond(t);
                            showTime.setStartTimeMLS(s);
                            listShowtimes.add(showTime);
                            } catch (Exception e){
                                System.out.println("no url");
                            }

                        }
                    }
                }
            }
        }
        if (ofy().save().entities(listMovies).now() != null) {
            LOG.info("Crawl movie base info success");
        } else {
            LOG.severe("Crawl movie base info error");
        }
        if (ofy().save().entities(listShowtimes).now() != null) {
            LOG.info("Crawl showtime info success");
        } else {
            LOG.severe("Crawl showtime info error");
        }
    }

    public void getMovieInfo() throws Exception {
        List<Movie> existMovie = ofy().load().type(Movie.class).filter("status",2).list();
        List<Movie> updateMovies = new ArrayList<>();

        for (Movie movie : existMovie) {
            System.out.println(movie.getId());
            cgvCoreData = loadDocument(movie.getId());

            String duration = "", language = "", director = "", actor = "", openAt = "", description = "", trailer = "", category = "", minAge = "",type="";
            try {
                duration = cgvCoreData.select(".movie-actress div").get(1).text().split(" ")[0].trim();
                language = cgvCoreData.select(".movie-language div").text();
                director = cgvCoreData.select(".movie-director div").text();
                actor = cgvCoreData.select(".movie-actress div").get(0).text();
                openAt = cgvCoreData.select(".movie-release div").text();
                description = cgvCoreData.select(".tab-content div").get(0).text();
                trailer = cgvCoreData.select(".tab-content div").get(1).getElementsByTag("iframe").attr("src");
                category = cgvCoreData.select(".movie-genre div").text();
                minAge = cgvCoreData.select(".movie-rating-detail").text().split(":")[1].trim();
                type = cgvCoreData.getElementsByClass("movie-detail-icon-type").text();
                System.out.println(category);


                Movie updateMovie = new Movie();
                updateMovie.setCategory(category);
                updateMovie.setId(movie.getId());
                updateMovie.setName(movie.getName());
                updateMovie.setPoster(movie.getPoster());
                updateMovie.setType(movie.getType());
                updateMovie.setDuration(Integer.parseInt(duration));
                updateMovie.setActor(actor);
                updateMovie.setDirector(director);
                updateMovie.setLanguage(language);
                updateMovie.setOpenAt(openAt);
                updateMovie.setDescription(description);
                updateMovie.setTrailer(trailer);
                updateMovie.setMinAge(minAge);
                updateMovie.setStatus(1);
                updateMovie.setType(type);
                FullTextSearchHandle.add(updateMovie.toSearchDocument());

                updateMovies.add(updateMovie);
            } catch (IndexOutOfBoundsException e) {
                e.printStackTrace(System.err);

            }
            if (ofy().save().entities(updateMovies).now() != null) {
                LOG.info("Crawl movie full info success");
            } else {
                LOG.severe("Crawl movie full info err");
            }
        }
    }
}