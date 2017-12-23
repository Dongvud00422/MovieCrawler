package com.Crawler.main;

import com.Crawler.controller.CGVCrawler.CrawlCgvController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Logger;

public class RunMonthlyCrawler extends HttpServlet{

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Logger LOG = Logger.getLogger(RunMonthlyCrawler.class.getSimpleName());
        LOG.info("Run monthly");
        try {
            CrawlCgvController cgvCrawler = new CrawlCgvController();
            cgvCrawler.getCgvCity();
            System.out.println("done city");
            cgvCrawler.getTheaterInfo();
            System.out.println("done theater");
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
    }

}
