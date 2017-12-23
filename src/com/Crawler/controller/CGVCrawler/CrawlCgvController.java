package com.Crawler.controller.CGVCrawler;

import com.Crawler.entity.City;
import com.Crawler.entity.Movie;
import com.Crawler.entity.Showtime;
import com.Crawler.entity.Theater;
import com.googlecode.objectify.ObjectifyService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;
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
        String strDoc = new CgvDocument().getDocument(target);
        System.out.println(strDoc);
        return Jsoup.parse(strDoc);
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
            city.setCityId(cityId);
            city.setName(cityName);
            System.out.println(city.getName());
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
        if (existTheaters.size() > 0) {
            LOG.info("runing get theater info.....");

            for (Theater theater : existTheaters) {
                loadDocument(theater.getTheaterId());
                // Lấy tên rạp.
                String theaterName = cgvCoreData.getElementsByClass("theater-title").text();
                theater.setTheaterName(theaterName);

                // Lấy địa chỉ rạp.
                String theaterAddress = cgvCoreData.getElementsByClass("theater-address").text();
                theater.setAddress(theaterAddress);

                // Fax.
                String faxNumber = cgvCoreData.select(".fax .fax-input").text();
                theater.setFax(faxNumber);

                // Hotline.
                String hotline = cgvCoreData.select(".hotline .fax-input").text();
                theater.setHotline(hotline);

                theater.setCityId(theater.getCityId());
                System.out.println("load id done");

                updateTheaters.add(theater);
                LOG.info("add theater success.");
            }
            ofy().save().entities(updateTheaters).now();
            System.out.println("save theater done");

        }
        LOG.info("Get theater error. existTheaters.size() <= 0 .");

    }

    synchronized public void getShowTimeInfo() throws Exception {
        LOG.info("getShowTimeInfo.....");
        List<Theater> listTheater = ofy().load().type(Theater.class).list();
        System.out.println(listTheater.size());
        if (listTheater.size() > 0) {
            List<Showtime> listShowtimes = new ArrayList<>();
            List<Movie> listMovies = new ArrayList<>();
            ArrayList<Document> listDOM = new ArrayList<>();
            for (Theater t : listTheater) {
                listDOM.add(loadDocument(t.getTheaterId()));
                LOG.info("get list DOM done");
            }
            int count = 0;
            for (Theater theater : listTheater) {
                cgvCoreData = listDOM.get(count);
                // Lấy tất cả các thẻ và nội dung bên trong của thẻ cha có class là "day" để lấy ra lịch chiếu của các ngày tiếp theo.
                Elements time = cgvCoreData.getElementsByClass("day");
                for (int i = time.size() - 1; i >= 0; i--) {
                    Movie movie = new Movie();
                    String date = time.get(i).getElementsByTag("em").text();
                    int day = Integer.parseInt(time.get(i).getElementsByTag("strong").text());
                    int month = Integer.parseInt(time.get(i).getElementsByTag("span").text());

                    Elements filmList = time.parents().next().get(0).getElementsByClass("film-list");
                    for (int j = filmList.size() - 1; j >= 0; j--) {

                        String poster = filmList.get(j).getElementsByTag("img").attr("src");
                        String filmName = filmList.get(j).getElementsByTag("a").attr("title");
                        String filmId = filmList.get(j).getElementsByTag("a").attr("href").trim();
                        movie.setStatus(2);
                        movie.setMovieId(filmId);
                        movie.setMovieName(filmName);
                        movie.setPoster(poster);
                        listMovies.add(movie);

                            Elements listShowTime = cgvCoreData.select(".film-screen .item");
                            for (int l = listShowTime.size() - 1; l >= 0; l--) {
                                String houtStr = listShowTime.get(l).select("span:first-child").text().split(" ")[0].split(":")[0];
                                String minuteStr = listShowTime.select("span:first-child").get(l).text().split(" ")[0].split(":")[1];

                                Showtime showTime = new Showtime();
                                showTime.setDate(date);
                                showTime.setDay(day);
                                showTime.setMonth(month);
                                showTime.setTheaterId(theater.getTheaterId());
                                showTime.setHour(Integer.parseInt(houtStr));
                                showTime.setMinute(Integer.parseInt(minuteStr));
                                showTime.setShowTimeId(Integer.parseInt(listShowTime.get(l).select("a").attr("href").split("id/")[1].trim()));
                                showTime.setSlotLeft(Integer.parseInt(listShowTime.get(l).select("span:last-child").text().split(" ")[0]));
                                listShowtimes.add(showTime);
                            }
                        }
                    }
                    count++;
                }
            if (ofy().save().entities(listMovies).now() != null) {
                LOG.info("Crawl movie base info success");
            } else {
                LOG.severe("Crawl movie base info error");
                if (ofy().save().entities(listShowtimes).now() != null) {
                    LOG.info("Crawl showtime info success");
                } else {
                    LOG.severe("Crawl showtime info error");
                }
            }
        }
    }

    public void getMovieInfo() throws Exception {
        LOG.info("getMovieInfo.....");
        List<Movie> existMovie = ofy().load().type(Movie.class).list();
        if (existMovie.size() > 0) {
            LOG.info("getMovieInfo..2...");
            List<Movie> updateMovies = new ArrayList<>();
            for (Movie movie : existMovie) {
                System.out.println(movie.getMovieId());
                loadDocument(movie.getMovieId());

                String duration ="",language="",director="",actor="",openAt="",description="",trailer="",category="",minAge = "";
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
                    Movie updateMovie = new Movie();
                    updateMovie.setMovieId(movie.getMovieId());
                    updateMovie.setMovieName(movie.getMovieName());
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

                    if (ofy().save().entity(updateMovie).now() != null) {
                        LOG.info("Crawl movie full info success");
                    } else {
                        LOG.severe("Crawl movie full info err");
                    }
                } catch (IndexOutOfBoundsException e){

                }
            }
        }
    }
}