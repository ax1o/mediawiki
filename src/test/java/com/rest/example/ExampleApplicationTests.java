package com.rest.example;

import com.rest.example.Service.SearchService;
import org.json.JSONException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import static org.junit.Assert.*;
import org.apache.http.entity.ContentType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import org.json.simple.JSONObject;


@SpringBootTest
class ExampleApplicationTests {

	@Mock
	private RestTemplate restTemplate;

	@InjectMocks
	private SearchService searchService ;

	@Test
	public void testFindArticleById() throws IOException, ParseException, JSONException {

		//setup
		int id = 38930;

		//reading expected json
		JSONParser parser = new JSONParser();
		Object obj = parser.parse(new FileReader("src/main/resources/findByID.json"));
		JSONObject mockJson = (JSONObject)obj;

		//mocking behaviour
		Mockito
				.when(restTemplate.getForEntity("https://en.wikipedia.org/w/api.php?action=query&prop=extracts|description&pageids=" + id + "&format=json",JSONObject.class))
				.thenReturn(new ResponseEntity(mockJson,HttpStatus.OK));

		//actual
		JSONObject actualJson = searchService.findArticleById(id);

		JSONAssert.assertEquals(mockJson.toJSONString(),actualJson.toJSONString(),false);


	}

	@Test
	public void testGetContentByTitle() throws ParseException, IOException, JSONException {

		//setup
		String var = "earth";

		//reading expected json
		JSONParser parser = new JSONParser();
		Object obj = parser.parse(new FileReader("src/main/resources/findByTitle.json"));
		JSONObject mockJson = (JSONObject)obj;

		//mocking behaviour
		Mockito
				.when(restTemplate.getForEntity("https://en.wikipedia.org/w/api.php?action=parse&prop=wikitext&page="+var+"&format=json",JSONObject.class))
				.thenReturn(new ResponseEntity(mockJson,HttpStatus.OK));

		//actual
		JSONObject actualJson = searchService.getContentByTitle(var);

		JSONAssert.assertEquals(mockJson.toJSONString(),actualJson.toJSONString(),false);



	}

	@Test
	public void testFindRelatedArticles() throws IOException, ParseException, JSONException {
		//setup
		String var = "earth";

		//reading expected json
		JSONParser parser = new JSONParser();
		Object obj = parser.parse(new FileReader("src/main/resources/findRelatedArticles.json"));
		JSONObject mockJson = (JSONObject)obj;

		//mocking behaviour
		Mockito
				.when(restTemplate.getForEntity("https://en.wikipedia.org/w/rest.php/v1/search/page?q=" + var + "&limit=5",JSONObject.class))
				.thenReturn(new ResponseEntity(mockJson,HttpStatus.OK));

		//actual
		JSONObject actualJson = searchService.findRelatedArticles(var);

		JSONAssert.assertEquals(mockJson.toJSONString(),actualJson.toJSONString(),false);


	}




}
