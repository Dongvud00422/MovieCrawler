package com.Crawler.controller.CGVCrawler;

import com.Crawler.entity.City;
import com.Crawler.entity.Movie;
import com.Crawler.entity.Showtime;
import com.Crawler.entity.Theater;
import com.googlecode.objectify.ObjectifyService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
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

    public CrawlCgvController() throws IOException {
        LOG.info("Calling contructor.");
        this.cgvCoreData = getDocument("https://www.cgv.vn/default/cinox/site/");
    }

    private Document getDocument(String url) throws IOException {
        LOG.info("Start get document.");
        URL u = new URL(url);


        cgvCoreData = Jsoup.connect(url)
                .header("Host", "www.cgv.vn")
                .userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10.12; rv:59.0) Gecko/20100101 Firefox/59.0")
                .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
                .header("Accept-Language", "en-US,en;q=0.5")
                .header("Accept-Encoding", "gzip, deflate, br")
                .header("Cookie", "TS015ef8cd=018ea3cdda4cc2e1df3a2d3fc281e63af4751896e9eb5c8607eefd385f6ea3cb89e9ac2ded; TS01faf9b1=018ea3cdda9a6001df9fcf60d4fde1f16c43cf8b198c7127dbb5d079333386bbd8769e6379bf0c1263b467f9c0d9c77cc105ad0b11f0fceda77d79a9ac16a9f7e4c3266ed6; TS015ef8cd_77=2086_40ce275283edaf45_rsb_0_rs_https%3A%2F%2Fwww.cgv.vn%2Fdefault%2Fcinox%2Fsite%2F_rs_0_rs_0; frontend=c6h0q83hh2mvoif8b54e98ujq1; frontend_cid=iXdUAXJzhUxhxWw0")
                .header("DNT", "1")
                .header("Connection", "keep-alive")
                .header("Upgrade-Insecure-Requests", "1")
                .header("Cache-Control", "max-age=0")
                .get();


        LOG.info("Get document success.");
        return cgvCoreData;
    }

    public void getCgvCity() throws IOException {
        Elements listElementsCity = cgvCoreData.select("div.cinemas-area li");
        Elements listElementsCinema = cgvCoreData.select("div.cinemas-list li");
        LOG.info("Get city.");

        List<Theater> theaters = new ArrayList<>();
        List<City> cities = new ArrayList<>();

        for (int i = 0; i < listElementsCity.size(); i++) {
            String cityId = listElementsCity.get(i).getElementsByAttribute("id").attr("id");
            String cityName = listElementsCity.get(i).text();
            City city = new City();
            city.setCityId(cityId);
            city.setName(cityName);
            cities.add(city);
            for (int j = 0; j < listElementsCinema.size(); j++) {
                Elements listTheater = listElementsCinema.get(j).getElementsByClass(cityId);
                for (int k = 0; k < listTheater.size(); k++) {
                    Theater theater = new Theater();
                    String theaterId = listTheater.get(k).getElementsByTag("span").attr("onclick").split("'")[1];
                    String theaterName = listTheater.get(k).getElementsByTag("span").text();
                    theater.setTheaterId(theaterId);
                    theater.setCityId(cityId);
                    theater.setTheaterName(theaterName);
                    theaters.add(theater);
                }
            }
        }

        if (ofy().save().entities(theaters).now() != null){
            LOG.info("Crawl theater base info success");
        }else {
            LOG.severe("Crawl theater base info error");
        }
        if (ofy().save().entities(cities).now() != null) {
            LOG.info("Crawl cityInfo success");
        } else {
            LOG.severe("Crawl cityInfo error");
        }
        LOG.info("Get city success.");

    }

    public void getTheaterInfo() throws IOException {
        LOG.info("Get theater info.");

        List<Theater> existTheaters = ofy().load().type(Theater.class).list();
        List<Theater> updateTheaters = new ArrayList<>();
        if (existTheaters.size() > 0) {
            LOG.info("runing get theater info.....");

            for (Theater theater : existTheaters) {
                cgvCoreData = getDocument(theater.getTheaterId());
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
                updateTheaters.add(theater);
                LOG.info("Get theater success.");
            }
            if (ofy().save().entities(updateTheaters).now() != null) {
                LOG.info("Crawl theater full info success");
            } else {
                LOG.severe("Crawl theater full info error");
            }

        }
        LOG.info("Get theater error.");

    }

    public void getShowTimeInfo() throws IOException {
        LOG.info("getShowTimeInfo.....");
        List<Theater> listTheater = ofy().load().type(Theater.class).list();

        if (listTheater.size() > 0) {
            LOG.info("getShowTimeInfo..2...");
            List<Showtime> listShowtimes = new ArrayList<>();
            List<Movie> listMovies = new ArrayList<>();
            for (Theater theater : listTheater) {
                cgvCoreData = getDocument(theater.getTheaterId());

                // Lấy tất cả các thẻ và nội dung bên trong của thẻ cha có class là "day" để lấy ra lịch chiếu của các ngày tiếp theo.
                Elements time = cgvCoreData.getElementsByClass("day");
                for (int i = time.size() - 1; i >= 0; i--) {
                    Movie movie = new Movie();
                    String date = time.get(i).getElementsByTag("em").text();
                    int day = Integer.parseInt(time.get(i).getElementsByTag("strong").text());
                    int month = Integer.parseInt(time.get(i).getElementsByTag("span").text());

                    cgvCoreData.select(".film-label h3 a span").remove();
                    Elements filmLabel = time.parents().next().get(0).getElementsByClass("film-label");

                    for (int j = filmLabel.size() - 1; j >= 0; j--) {
                        String movieId = filmLabel.get(j).getElementsByAttribute("href").attr("href");
                        String filmName = filmLabel.get(j).text();
                        String poster = filmLabel.get(j).getElementsByTag("img").attr("src");

                        movie.setMovieId(movieId);
                        movie.setMovieName(filmName);
                        movie.setPoster(poster);
                        movie.setStatus(2);

                        Elements movieType = filmLabel.next().get(j).nextElementSibling().getElementsByClass("film-screen");

                        for (int k = movieType.size() - 1; k >= 0; k--) {
                            movie.setType(movieType.get(k).text());
                            if (ofy().save().entity(movie).now() != null) {
                                LOG.info("Crawl movie base info success");
                            } else {
                                LOG.severe("Crawl movie base info error");
                            }

                            Elements listShowTime = movieType.get(k).nextElementSibling().select(".item");
                            for (int l = listShowTime.size() - 1; l >= 0; l--) {
                                String houtStr = listShowTime.get(l).select("span:first-child").text().split(" ")[0].split(":")[0];
                                String minuteStr = listShowTime.select("span:first-child").get(l).text().split(" ")[0].split(":")[1];

                                Showtime showTime = new Showtime();
                                showTime.setDate(date);
                                showTime.setDay(day);
                                showTime.setMonth(month);
                                showTime.setTheaterId(theater.getTheaterId());
                                showTime.setMovieId(movieId);
                                showTime.setHour(Integer.parseInt(houtStr));
                                showTime.setMinute(Integer.parseInt(minuteStr));
                                showTime.setShowTimeId(Integer.parseInt(listShowTime.get(l).select("a").attr("href").split("id/")[1]));
                                showTime.setSlotLeft(Integer.parseInt(listShowTime.get(l).select("span:last-child").text().split(" ")[0]));
                                listShowtimes.add(showTime);
                            }
                        }
                    }
                }
                if (ofy().save().entities(listShowtimes).now() != null) {
                    LOG.info("Crawl showtime info success");
                } else {
                    LOG.severe("Crawl showtime info error");
                }
            }
        }
    }

    public void getMovieInfo() throws IOException {
        LOG.info("getMovieInfo.....");
        List<Movie> existMovie = ofy().load().type(Movie.class).list();
        if (existMovie.size() > 0) {
            LOG.info("getMovieInfo..2...");
            List<Movie> updateMovies = new ArrayList<>();
            for (Movie movie : existMovie) {
                System.out.println(movie.getMovieId());
                cgvCoreData = getDocument(movie.getMovieId());

                String duration ="",language="",director="",actor="",openAt="",description="",trailer="",category="",minAge = "";
                try {
                    duration = cgvCoreData.select(".movie-actress div").get(1).text().split(" ")[0].trim();
                    System.out.println(duration);
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