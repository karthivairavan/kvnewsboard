package com.example.demo.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NewsService {
    private static final String API_KEY = "Sw7Ag5qvOzSQNHr01O_U7x5Uddlkukv5YIqaDg-Og9ZEM4ck"; // Replace with your API key
    private static final String NEWS_API_URL = "https://api.currentsapi.services/v1/latest-news?apiKey=" + API_KEY;

    private final OkHttpClient client = new OkHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public List<NewsItem> fetchNews() throws Exception {
        Request request = new Request.Builder()
                .url(NEWS_API_URL)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new Exception("Unexpected code " + response);
            }

            String jsonResponse = response.body().string();
            JsonNode rootNode = objectMapper.readTree(jsonResponse);
            JsonNode newsArray = rootNode.path("news");

            List<NewsItem> newsList = new ArrayList<>();

            for (JsonNode newsItem : newsArray) {
                String title = newsItem.path("title").asText();
                String url = newsItem.path("url").asText();
                String description = newsItem.path("description").asText();
                String author = newsItem.path("author").asText();

                // Check if the image exists, otherwise use a dummy image URL
                String image = newsItem.path("image").asText();
                if (image == null || image.isEmpty()) {
                    image = "/images/dummy-news-image-bk.jpg"; // Path to the dummy image
                }

                // Add the news item to the list
                newsList.add(new NewsItem(title, url, description, author, image));
            }

            return newsList;
        }
    }

    public static class NewsItem {
        private final String title;
        private final String url;
        private final String description;
        private final String author; // Add author
        private final String image;  // Add image URL

        public NewsItem(String title, String url, String description, String author, String image) {
            this.title = title;
            this.url = url;
            this.description = description;
            this.author = author;    // Initialize author
            this.image = image;      // Initialize image
        }

        // Getters
        public String getTitle() {
            return title;
        }

        public String getUrl() {
            return url;
        }

        public String getDescription() {
            return description;
        }

        public String getAuthor() {
            return author; // Getter for author
        }

        public String getImage() {
            return image; // Getter for image
        }
    }
}
