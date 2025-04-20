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

    public List<Book> searchBooks(String query) {
        List<Book> results = new ArrayList<>();
        try {
            String apiUrl = "https://www.googleapis.com/books/v1/volumes?q=" + URLEncoder.encode(query, StandardCharsets.UTF_8);
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(apiUrl)).build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(response.body());

            for (JsonNode item : root.path("items")) {
                JsonNode volumeInfo = item.path("volumeInfo");

                String title = volumeInfo.path("title").asText("제목 없음");

                // authors 배열 처리
                List<String> authorsList = new ArrayList<>();
                JsonNode authorsNode = volumeInfo.path("authors");
                if (authorsNode.isArray()) {
                    for (JsonNode author : authorsNode) {
                        authorsList.add(author.asText());
                    }
                }

                // 썸네일 처리
                String thumbnail = volumeInfo.path("imageLinks").path("thumbnail").asText(null);

                results.add(new Book(title, authorsList, thumbnail));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return results;
    }
}
