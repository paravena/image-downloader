package com.mtt.image_downloader.bdd.steps;

import com.mtt.image_downloader.ImageDownloader;
import com.mtt.image_downloader.Start;
import com.mtt.image_downloader.Utility;
import com.mtt.image_downloader.bdd.support.WebServer;
import cucumber.api.DataTable;
import cucumber.api.PendingException;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static junit.framework.TestCase.assertTrue;

public class ImageDownloaderSteps {
    private WebServer webServer;
    private List<String> imageUrls;
    private Utility utility;

    @Before
    public void initialize() throws IOException {
        webServer = new WebServer(9090);
        webServer.start(50000, false);
        utility = Utility.getInstance();
    }

    @After
    public void tearDown() {
        webServer.stop();
    }

    @Given("^I have a url for a web page with following images:$")
    public void createWebPage(DataTable dataTable) throws IOException {
        imageUrls = dataTable.asList(String.class);
        StringBuilder sb = new StringBuilder("<html><body>");
        sb.append("<p>following images must be downloaded:</b>");
        sb.append("<br/>");
        for (String imageUrl : imageUrls) {
            sb.append("<img src=\"").append(imageUrl).append("\">");
            sb.append("<br/>");
        }
        sb.append("</body></html>");
        webServer.setContent(sb.toString());
    }

    @When("^I start the image downloader$")
    public void startImageDownloader() {
        Start.main(new String[] {"-url", "http://localhost:9090/content", "-out", "/tmp"});
        try {
            Thread.sleep(5000L);
        } catch (InterruptedException ex) {
            ex.printStackTrace(System.out);
        }
    }

    @Then("^All images defined in the web page are downloaded$")
    public void shouldDownloadAllImages() {
        for (String imageUrl : imageUrls) {
            String imageName = utility.extractImageNameFromUrl(imageUrl);
            assertTrue(new File("/tmp/" + imageName + ".jpg").exists());
            assertTrue(new File("/tmp/" + imageName + ".png").exists());
        }
    }

    @Then("^For each image downloaded I have three different dimensions with two formats jpg and png$")
    public void shouldBeThreeDifferentDimensionsForEachImageDownloaded() {
        for (String imageUrl : imageUrls) {
            String imageName = utility.extractImageNameFromUrl(imageUrl);
            for (int width : ImageDownloader.RESIZE_WIDTHS) {
                assertTrue(new File("/tmp/" + imageName + "_" + width + ".jpg").exists());
                assertTrue(new File("/tmp/" + imageName + "_" + width +  ".png").exists());
            }
        }
    }
}
