package crawler;

import www.Site;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class crawler {
    // List of the urls of all crawled sites
    static List<String> crawled = new ArrayList<>();

    // List of crawled sites
    static List<Site> crawledSites = new ArrayList<>();

    public static List<Site> crawlLinks(String url) throws IOException {
        // don't crawl the same site twice, the second time only the url should be saved
        if (crawled.stream().anyMatch(x -> x.equals(url))) {
            System.out.println("Found duplicate: " + url);
            return new ArrayList<>();
        }
        crawled.add(url);

        Document doc;
        try {
            doc = Jsoup.connect(url).get();
        } catch (Exception ex) {
            System.out.println(ex);
            return new ArrayList<>();
        }
        List<Site> result = new ArrayList<>();

        Elements links = doc.select("a[href]");
        for (Element link : links) {
            String linkUrl = link.attr("abs:href");
            if (!validLink(url, linkUrl)) {
                continue;
            }
            Site site = new Site(linkUrl);

            // Don't add the side if it has the same base link as an already added site
            if (!crawledSites.contains(site)) {
                result.add(site);
                crawledSites.add(site);
            }
        }

        return result;
    }

    private static boolean validLink(String url, String linkUrl) {
        if (!linkUrl.contains(".")) return false;
        String type = linkUrl.substring(linkUrl.lastIndexOf("."));
        return validProtocol(linkUrl) && !linkUrl.contains(url) && !ignoredType(type);
    }

    private static boolean validProtocol(String url) {
        return url.substring(0, url.indexOf(":")).equals("https");
    }

    private static boolean ignoredType(String type) {
        return Arrays.asList(new String[]{".png", ".jpg", ".jpeg", ".gif", ".svg", ".mp3", ".mp4", ".apk", ".pdf"}).contains(type);
    }
}
