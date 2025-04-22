package com.bookme.service;

import com.bookme.dto.Book;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Service
public class GoogleBooksService {

    public List<Book> searchBooks(String query, int startIndex, int size) {
        List<Book> results = new ArrayList<>();
        try {
            String apiUrl = "https://www.googleapis.com/books/v1/volumes?q=" +
                    URLEncoder.encode(query, StandardCharsets.UTF_8) +
                    "&startIndex=" + startIndex +
                    "&maxResults=" + size +
                    "&langRestrict=ko";

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(apiUrl)).build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(response.body());

            for (JsonNode item : root.path("items")) {
                String title = item.path("volumeInfo").path("title").asText("제목 없음");

                List<String> authors = new ArrayList<>();
                JsonNode authorsNode = item.path("volumeInfo").path("authors");
                if (authorsNode.isArray()) {
                    for (JsonNode author : authorsNode) {
                        authors.add(author.asText());
                    }
                }

                String thumbnail = item.path("volumeInfo").path("imageLinks").path("thumbnail").asText(null);
                String infoLink = item.path("volumeInfo").path("infoLink").asText(null);
                results.add(new Book(title, authors, thumbnail, infoLink));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return results;
    }
}
