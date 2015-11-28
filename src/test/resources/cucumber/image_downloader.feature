Feature: Image Downloader
  As a user I want to use an Image Downloader when I need to download images from a given url

  Scenario: Downloading images
    Given I have a url for a web page with following images:
      |http://placehold.it/350x150.jpg|
      |http://placehold.it/500x200.jpg|
      |http://placehold.it/300x200.jpg|
    When I start the image downloader
    Then All images defined in the web page are downloaded
    And For each image downloaded I have three different dimensions
    And For each image downloaded I have two different formats