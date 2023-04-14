package com.rest.example.Controller;

import com.rest.example.Service.SearchService;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.io.IOException;

@RestController
@RequestMapping("/search")
public class SearchController {

    @Autowired
    SearchService searchService;

    // will return 5 related articles with url
    @GetMapping("/findRelatedArticles")
    public ResponseEntity findRelatedArticles(@RequestParam String query) throws IOException, ParseException {

        JSONObject json;
        try {
            json = searchService.findRelatedArticles(query);
        } catch (Exception e) {
            return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(json, HttpStatus.ACCEPTED);

    }

    // will return page using page id
    @GetMapping("/findArticleById")
    public ResponseEntity findArticleById(@RequestParam int id) throws IOException, ParseException {

        JSONObject json;
        try {
            json = searchService.findArticleById(id);
        } catch (Exception e) {
            return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(json, HttpStatus.OK);

    }

    // finds the page by title
    @GetMapping("/findByTitle")
    public ResponseEntity getContentByTitle(@RequestParam String title) {

        JSONObject json;
        try {
            json = searchService.getContentByTitle(title);
        } catch (Exception e) {
            return new ResponseEntity(e, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(json, HttpStatus.OK);

    }

}
