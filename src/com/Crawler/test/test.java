package com.Crawler.test;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.*;

public class test {
    public Document fakeLote() {
        BufferedReader bf= null;
        StringBuilder strBuider = null;
        try {
            bf = new BufferedReader(new InputStreamReader(new FileInputStream(new File("/Volumes/Document/Study/IdeaProjects/prje/src/com/Crawler/test/LoteResp.html"))));
            String line = "";
            strBuider = new StringBuilder();
            while((line = bf.readLine()) != null){
                strBuider.append(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Document doc = Jsoup.parse(strBuider.toString(),"UTF-8");
        return doc;
    }
}
