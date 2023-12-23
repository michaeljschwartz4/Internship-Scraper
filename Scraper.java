package com.internship;

/*
 * Class that scrapes a webpage for data with a given keyword
 */

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.net.Proxy;
import org.xml.sax.SAXException;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.time.Duration;

public class Scraper {
    private String url;

    public Scraper() {
        url = "";
    }
    public static List<PositionOpportunity> scrape(String url) {
        List<PositionOpportunity> positions = new ArrayList<>();

        try {
            Document document = Jsoup.connect(url).get();
            Elements postingElements = document.select(".jobTitle css-1u6tfqq eu4oa1w0"); // Check!

            for (Element posting : postingElements) {
                String jobTitle = posting.select(".title-class").text();
                String company = posting.select(".company-class").text();
                String location = posting.select(".location-class").text();
                String dueDate = posting.select(".due-date-class").text();
                String payRange = posting.select(".pay-class").text();
                String jobLink = posting.select(".job-link-class").attr("href");

                PositionOpportunity opportunity = new PositionOpportunity(dueDate, payRange, jobLink, jobTitle, company, location, false);

                positions.add(opportunity);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return positions;
    }
}