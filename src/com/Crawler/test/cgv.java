package com.Crawler.test;

import com.Crawler.entity.City;
import com.Crawler.entity.Theater;
import com.google.appengine.api.urlfetch.HTTPResponse;
import com.google.appengine.api.urlfetch.URLFetchServicePb;
import com.google.appengine.repackaged.com.google.api.client.http.HttpRequest;
import com.google.appengine.repackaged.com.google.api.client.http.HttpResponse;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import static com.googlecode.objectify.ObjectifyService.ofy;

public class cgv extends HttpServlet{
    Logger LOG = Logger.getLogger(cgv.class.getSimpleName());
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        StringBuilder strBuilder = new StringBuilder();
        try {
            URL url = new URL("http://www.cgv.vn/default/cinox/site");
            URLFetchServicePb.URLFetchResponse()

            BufferedReader bf = new BufferedReader(new InputStreamReader(url.openStream()));
            String line = "";
            while ((line = bf.readLine()) != null) {
                strBuilder.append(line);
            }
        }catch (Exception e){
            e.printStackTrace(System.err);
        }
        Document doc = Jsoup.parse(strBuilder.toString());
        try {
            new cgv().getCgvCity(doc);
        } catch (IOException e) {
            e.printStackTrace(System.err);
        }
    }

    public static void main(String[] args) {
        StringBuilder strBuilder = new StringBuilder();
        Logger LOG = Logger.getLogger(cgv.class.getSimpleName());

        try {
            URL url = new URL("https://www.cgv.vn/default/cinox/site");
            BufferedReader bf = new BufferedReader(new InputStreamReader(url.openStream()));
            String line = "";
            while ((line = bf.readLine()) != null) {
                strBuilder.append(line);
            }
        }catch (Exception e){
            e.printStackTrace(System.err);
        }
        Document doc = Jsoup.parse(strBuilder.toString());
        try {
            new cgv().getCgvCity(doc);
        } catch (IOException e) {
            e.printStackTrace(System.err);
        }
    }

    public void getCgvCity(Document cgvCoreData) throws IOException {
        Elements listElementsCity = cgvCoreData.select("div.cinemas-area li");
        Elements listElementsCinema = cgvCoreData.select("div.cinemas-list li");
        LOG.info("Get city.");
        for (int i = 0; i < listElementsCity.size(); i++) {
            String cityId = listElementsCity.get(i).getElementsByAttribute("id").attr("id");
            String cityName = listElementsCity.get(i).text();
            City city = new City();
            LOG.info(cityId);
            LOG.info(cityName);
        }
        LOG.info("Get city success.");

    }

}
