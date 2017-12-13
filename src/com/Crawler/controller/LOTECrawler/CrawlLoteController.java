package com.Crawler.controller.LOTECrawler;

import com.Crawler.entity.Movie;
import com.Crawler.model.ObjectifyModel;
import com.Crawler.test.test;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import static com.googlecode.objectify.ObjectifyService.ofy;

public class CrawlLoteController {
    private Document loteCoreData;

    private Document getDocument(String url){
        Document doc = new test().fakeLote();
        return doc;
    }
    public void getMovieBasicInfo() throws IOException {
        Document loteCoreData = new test().fakeLote();
        // Document loteCoreData = Jsoup.connect("https://lottecinemavn.com/vi-vn/phim.aspx").get();
        Elements listMoviePoster = loteCoreData.select(".tab-pane#nowPlaying .movie-img");
        Movie movie = new Movie();
        List<Movie> listMovie = new ArrayList<>();
        for (int i = 0; i < listMoviePoster.size(); i++) {
            movie.setPoster("https://lottecinemavn.com/getattachment/" + listMoviePoster.get(i).attr("style").split("/")[2] + "/NodeAlias.aspx?width=275&amp;height=390");
        }
        Elements filmFooter = loteCoreData.select(".movie-name");
        for (int i =0; i<filmFooter.size();i++){
            movie.setMovieId("https://lottecinemavn.com"+filmFooter.get(i).getElementsByAttribute("href").attr("href").trim());
            try {
                movie.setMovieName(filmFooter.get(i).getElementsByAttribute("title").attr("title").split("\\)")[1].trim());
            } catch (ArrayIndexOutOfBoundsException e){
                movie.setMovieName(filmFooter.get(i).getElementsByAttribute("title").attr("title").trim());
                movie.setStatus(2);
                listMovie.add(movie);
            }
        }
    //    ofy().save().entities(listMovie).now();
    }
    public void test() throws IOException {
        if (loteCoreData == null){
            loteCoreData = Jsoup.connect("https://lottecinemavn.com/vi-vn/phim/hang-yeu-truyen.aspx").get();
        }
        System.out.println(loteCoreData.select(".caption h3").text().split("\\)")[0].split("\\(")[1]);
        Elements filmInfo = loteCoreData.select(".caption .list-unstyled li");
        for (int i=0; i<filmInfo.size();i++){
            try {
                System.out.println(loteCoreData.select(".caption .list-unstyled li").get(i).getElementsByTag("span").get(1).text());
            } catch (IndexOutOfBoundsException e){
            }
        }
    }

    public static void main(String[] args) {
        CrawlLoteController c = new CrawlLoteController();
        try {
            c.test();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}