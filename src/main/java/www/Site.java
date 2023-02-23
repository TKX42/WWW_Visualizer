package www;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import static crawler.crawler.crawlLinks;
import static util.GlobalLogger.log;
import static util.UrlEditor.getBaseUrl;
import static util.UrlEditor.getCutUrl;

public class Site {
    private String url;
    private List<Site> links;
    private final static int MAX_LOG_DEPTH = 6;

    // For jackson
    public Site() {

    }

    public Site(String url) {
        if (url.strip().endsWith("/")) {
            this.url = url.strip().substring(0, url.strip().length() - 1);
        } else {
            this.url = url.strip();
        }

        this.url = getCutUrl(this.url);

        links = new ArrayList<>();
    }

    public String getUrl() {
        return url;
    }

    public List<Site> getLinks() {
        return links;
    }

    public void setLinks(List<Site> links) {
        this.links = links;
    }

    public void crawl(int currentDepth, int maximumDepth) throws IOException {
        System.out.println("Crawling " + url + " (depth " + currentDepth + ")");
        setLinks(crawlLinks(url));
        if (currentDepth < maximumDepth) {
            for (int i = 0; i < links.size(); i++) {
                links.get(i).crawl(currentDepth + 1, maximumDepth);
                if (currentDepth < MAX_LOG_DEPTH) {
                    log(Level.INFO, "Finished crawling " + (i + 1) + "/" + links.size() + " on depth " + currentDepth);
                }
            }
        }
    }

    public Site find(String url) {
        if(this.equals(new Site(url))) return this;
        for(Site site : getLinks()) {
            if(site != null) {
                Site found = site.find(url);
                if(found != null) return found;
            }
        }
        return null;
    }

    public void replace(Site replacement) {
        this.setLinks(replacement.getLinks());
    }

    // Returns if the two sides have the same base link
    @Override
    public boolean equals(Object b) {
        String urlB = ((Site) b).getUrl();
        return sameBaseLink(getUrl(), urlB) || sameBaseLink(urlB, getUrl());
    }

    private boolean sameBaseLink(String urlA, String urlB) {
        // Filter out same base links

        String substring = "";
        try {
            substring = getBaseUrl(urlB);
        } catch (StringIndexOutOfBoundsException ex) {
            return url.equals(getUrl());
        }

        return urlA.contains(substring);
    }
}
