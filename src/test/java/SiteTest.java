import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestTemplate;
import www.Site;

import static org.junit.jupiter.api.Assertions.*;
import static util.UrlEditor.getBaseUrl;

public class SiteTest {
    @Test
    void testSite() {
        Site site = new Site(" https://www.site.com/ ");
        assertEquals("https://www.site.com", site.getUrl());
    }

    @Test
    void testBaseUrl() {
        assertEquals("site.net", getBaseUrl("https://www.site.net"));
        assertEquals("site.net", getBaseUrl("https://site.net"));
        assertEquals("siteA.net", getBaseUrl("https://siteA.net"));

        assertEquals("site.net", getBaseUrl("https://www.site.net/websiteA.html"));
        assertEquals("x 123.abc", getBaseUrl("https://x 123.abc/websiteA.html"));
    }

    @Test
    void testCutUrl() {
        assertEquals("https://site.net", new Site("https://site.net").getUrl());
        assertEquals("https://www.site.net", new Site("https://www.site.net").getUrl());
        assertEquals("https://site.net", new Site("https://site.net/a/b?x=y").getUrl());
        assertEquals("https://long.link.com", new Site("https://long.link.com/site").getUrl());
        assertEquals("https://www.weforum.org", new Site("https://www.weforum.org/agenda/2022/01/how-15-leaders-are-changing-the-world-through-trust").getUrl());
    }

    @Test
    void testEquals() {
        assertTrue(new Site("https://site.net").equals(new Site("https://site.net")));

        assertTrue(new Site("https://site.net").equals(new Site("https://site.net/")));
        assertTrue(new Site("https://site.net/").equals(new Site("https://site.net")));

        assertTrue(new Site("https://www.site.net").equals(new Site("https://site.net")));
        assertTrue(new Site("https://site.net").equals(new Site("https://www.site.net")));

        assertTrue(new Site("https://site.net/websiteA").equals(new Site("https://www.site.net/websiteB")));
        assertTrue(new Site("https://www.site.net/websiteX").equals(new Site("https://site.net//")));

        assertFalse(new Site("https://siteA.net").equals(new Site("https://www.site.net")));
        assertFalse(new Site("https://www.site.net").equals(new Site("https://siteA.net")));

        assertFalse(new Site("https://www.site.a").equals(new Site("https://www.site.b")));
        assertFalse(new Site("https://www.site.b").equals(new Site("https://www.site.a")));

        assertFalse(new Site("https://a.site.a").equals(new Site("https://b.site.b")));
        assertFalse(new Site("https://b.site.b").equals(new Site("https://a.site.a")));
    }

    @Test
    void testSearchSimplePath() {
        Site rootSite = new Site("rootSite");
        Site site2 = new Site("site2");
        site2.getLinks().add(new Site("finalSite"));
        rootSite.getLinks().add(site2);

        Site result = rootSite.find("finalSite");
        assertEquals(result.getUrl(), "finalSite");
    }

    @Test
    void testSearchRoot() {
        Site rootSite = new Site("rootSite");
        assertEquals(rootSite, rootSite.find("rootSite"));
    }

    @Test
    void testSearchMoreComplexPath() {
        Site rootSite = new Site("rootSite");
        Site site2 = new Site("site2");
        site2.getLinks().add(new Site("sitea"));
        rootSite.getLinks().add(site2);
        rootSite.getLinks().add(new Site("site3"));
        Site b = new Site("siteab");
        site2.getLinks().add(b);
        b.getLinks().add(new Site("finalSite"));
        assertEquals("finalSite", rootSite.find("finalSite").getUrl());
    }
}
