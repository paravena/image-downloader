Feature: Image Downloader
  As a user I want to use an Image Downloader when I need to download images from a given url

  Scenario: Downloading images
    Given I have a url for a web page
    When I start the image downloader
    Then all images defined in the web page are downloaded
    And for each image downloaded I have three different dimensions
    And for each image downloaded I have two different formats