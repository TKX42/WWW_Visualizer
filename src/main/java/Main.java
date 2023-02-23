import com.fasterxml.jackson.databind.ObjectMapper;
import www.Site;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;

import static util.GlobalLogger.log;

public class Main {
    static Scanner reader = new Scanner(System.in);
    static ObjectMapper mapper = new ObjectMapper();
    static String fileAPath = "./A.json";
    static String fileBPath = "./B.json";

    public static void main(String[] args) throws IOException {
        System.out.println("Enter mode (crawl, append):");
        System.out.print("> ");
        String mode = reader.nextLine();
        System.out.println();
        switch (mode) {
            case "crawl":
                crawler();
                break;
            case "append":
                append();
                break;
            default:
                System.out.println("ERROR: Couldn't find specified mode");
                break;
        }

        // cleanup
        reader.close();
    }

    static void logInfo(String info) {
        log(Level.INFO, info);
    }

    static void append() throws IOException {
        logInfo("WWW VISUALIZER APPENDER");

        logInfo("Loading file A...");
        Site siteA = readSite(fileAPath);
        logInfo("Loading file B...");
        Site siteB = readSite(fileBPath);

        logInfo("Resolving site B inside side A...");
        Site toBeReplaced = siteA.find(siteB.getUrl());
        if(toBeReplaced == null) {
            log(Level.SEVERE, "ERROR: Couldn't resolve side B inside side A!");
            System.exit(1);
        }

        // Doesn't change the reference but replaces the links
        toBeReplaced.replace(siteB);
        mapper.writeValue(new File("./result.json"), siteA);
    }

    private static Site readSite(String filePath) {
        File file = new File(filePath);
        try {
            return mapper.readValue(file, Site.class);
        } catch (IOException e) {
            log(Level.SEVERE, "Error when trying to load " + filePath + "\n" + e);
            System.exit(1);
        }
        return null;
    }

    static void crawler() throws IOException {
        System.out.print("Enter root site: ");
        String rootSiteUrl = reader.nextLine();

        System.out.print("Enter maximum depth: ");
        int maximumDepth = reader.nextInt();
        System.out.println();

        Site rootSite = new Site(rootSiteUrl);

        logInfo("WWW VISUALIZER CRAWLER");
        logInfo("Crawling root site " + rootSite.getUrl() + " with maximum depth " + maximumDepth + ".\n\n");

        rootSite.crawl(0, maximumDepth);

        mapper.writeValue(new File("./root.json"), rootSite);
        System.out.println("Done, wrote data to ./root.json.");
    }
}
