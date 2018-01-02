package com.Crawler.main;

import com.Crawler.controller.CrawlCgvController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Logger;

public class RunDailyCrawler extends HttpServlet {

    private Logger LOG = Logger.getLogger(RunDailyCrawler.class.getName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOG.info("Calling crawler.");
        try {
            CrawlCgvController crawlData = new CrawlCgvController();
            crawlData.getShowTimeInfo();
            System.out.println("done showtime");
            crawlData.getMovieInfo();
            System.out.println("done movie");
        } catch (Exception e) {
            e.printStackTrace();
            LOG.warning(e.getMessage());
        }
    }
}
