package com.rest.example.Service;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

@Service
public class SearchService {


    //returns 5 related articles with their URLs.
    public JSONObject findRelatedArticles(String query) throws IOException, ParseException {
        //preparing connection
        URL url = new URL("https://en.wikipedia.org/w/rest.php/v1/search/page?q=" + query + "&limit=5");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestMethod("GET");

        int responseCode = con.getResponseCode();
        JSONObject json;

        //if response is successful then proceed further otherwise throw exception
        if (responseCode != 200) {
            throw new RuntimeException("HttpResponseCode: " + responseCode);
        } else {
            String inline = "";
            Scanner scanner = new Scanner(url.openStream());

            //Write all the JSON data into a string using a scanner
            while (scanner.hasNext()) {
                inline += scanner.nextLine();
            }

            //Close the scanner
            scanner.close();

            //convert the string to json Object
            JSONParser parser = new JSONParser();
            json = (JSONObject) parser.parse(inline);
        }

        //disconnect connection
        con.disconnect();

        return json;
    }


    //returns the page extract and description based on Article id
    public JSONObject findArticleById(int id) throws IOException, ParseException {
        //preparing connection
        //setting url param such that it returns extract and description
        URL url = new URL("https://en.wikipedia.org/w/api.php?action=query&prop=extracts|description&pageids=" + id + "&format=json");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestMethod("GET");

        JSONObject json;

        //if response is successful then proceed further otherwise throw exception
        if (con.getResponseCode() != 200) {
            throw new RuntimeException("HttpResponseCode: " + con.getResponseCode());
        } else {
            String inline = "";
            Scanner scanner = new Scanner(url.openStream());

            //Write all the JSON data into a string using a scanner
            while (scanner.hasNext()) {
                inline += scanner.nextLine();
            }

            //Close the scanner
            scanner.close();

            //convert the string to json Object
            JSONParser parser = new JSONParser();
            json = (JSONObject) parser.parse(inline);
        }

        con.disconnect();

        return json;
    }

    //finds the page by title.
    public JSONObject getContentByTitle(String title) throws IOException, ParseException {

        //preparing connection
        URL url = new URL("https://en.wikipedia.org/w/api.php?action=parse&prop=wikitext&page=" + title + "&format=json");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestMethod("GET");

        JSONObject json;

        //if request is successful then proceed, otherwise throw an exception
        if (con.getResponseCode() != 200) {
            throw new RuntimeException("HttpResonseCode: " + con.getResponseCode());
        } else {
            String inline = "";
            Scanner scanner = new Scanner(url.openStream());

            //Write all the JSON data into a string using a scanner
            while (scanner.hasNext()) {
                inline += scanner.nextLine();
            }

            //Close the scanner
            scanner.close();

            //convert to json object
            JSONParser parser = new JSONParser();
            json = (JSONObject) parser.parse(inline);
        }

        con.disconnect();

        return json;



    }


}
