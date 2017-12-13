package com.Crawler.main;

import com.Crawler.controller.CGVCrawler.CrawlCgvController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Logger;

public class RunMonthlyCrawler extends HttpServlet{
    Logger LOG = Logger.getLogger(RunMonthlyCrawler.class.getSimpleName());
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOG.info("Run monthly");
        resp.getWriter().println("crawler is runing");
        try {
            CrawlCgvController cgvCrawler = new CrawlCgvController();
           //cgvCrawler.getCgvCity();
            System.out.println("done city");
            cgvCrawler.getTheaterInfo();
            System.out.println("done theater");
        } catch (IOException e){
            e.printStackTrace(System.err);
        }
    }
}
