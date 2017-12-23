package com.untility;

import com.Crawler.entity.Movie;
import com.Crawler.entity.Theater;
import com.google.appengine.api.search.*;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class FullTextSearchHandle {
    private static Logger logger = Logger.getLogger(FullTextSearchHandle.class.getSimpleName());
    private static IndexSpec indexSpec = IndexSpec.newBuilder().setName("bookFullTextSearch").build();
    private static Index index = SearchServiceFactory.getSearchService().getIndex(indexSpec);

    public static FullTextSearchHandle getInstance() {
        return new FullTextSearchHandle();
    }

    public static void add(Document document) {
        try {
            index.put(document);
            logger.info("Index thành công.");
        } catch (PutException e) {
            logger.severe("Index lỗi");
            e.printStackTrace(System.err);
        }
    }

    public static void delete(String id) {
        try {
            index.delete(id);
            logger.info("Remove index thành công.");
        } catch (PutException e) {
            logger.severe("Remove index lỗi");
            e.printStackTrace(System.err);
        }
    }

    public List<Movie> searchMovie(String keyword) {
        List<Movie> listMovie = new ArrayList<>();
        Results<ScoredDocument> results = index.search(keyword);
        if (results != null && results.getNumberReturned() > 0) {
            for (ScoredDocument document : results) {
                try {
                    Movie movie = new Movie();
                    movie.setType(document.getOnlyField("type").getText());
                    //movie.setTrailer(document.getOnlyField("trailer").getText());
                    //movie.setPoster(document.getOnlyField("poster").getText());
                    movie.setOpenAt(document.getOnlyField("openAt").getText());
                    movie.setMovieId(document.getOnlyField("movieId").getText());
                    movie.setDuration(Integer.parseInt(document.getOnlyField("duration").getText()));
                    movie.setLanguage(document.getOnlyField("language").getText());
                    movie.setDirector(document.getOnlyField("director").getText());
                    movie.setDescription(document.getOnlyField("description").getText());
                    movie.setActor(document.getOnlyField("actor").getText());
                    movie.setMovieName(document.getOnlyField("movieName").getText());
                    //movie.setMinAge(document.getOnlyField("minAge").getText());
                    listMovie.add(movie);
                } catch (Exception e) {
                    logger.severe("Convert document lỗi.");
                    e.printStackTrace(System.err);
                }
            }
        }
        return listMovie;

    }

    public List<Theater> searchTheater(String keyword) {
        List<Theater> listTheater = new ArrayList<>();
        Results<ScoredDocument> results = index.search(keyword);
        if (results != null && results.getNumberReturned() > 0) {
            for (ScoredDocument document : results) {
                try {
                    Theater theater = new Theater();
                    theater.setTheaterName(document.getOnlyField("theaterName").getText());
                    theater.setTheaterId(document.getOnlyField("theaterId").getText());
                    listTheater.add(theater);
                } catch (Exception e) {
                    logger.severe("Convert document lỗi.");
                    e.printStackTrace(System.err);
                }

            }
        }
        return listTheater;
    }

}
