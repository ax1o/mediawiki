package com.rest.example;

import com.rest.example.Service.SearchService;
import org.json.JSONException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.test.context.SpringBootTest;
import java.io.IOException;


@SpringBootTest
class ExampleApplicationTests {

	@Mock
	SearchService searchService;

	@Test
	public void testFindArticleById() throws IOException, ParseException, JSONException {

		//setup
		int id = 38930;

		//creating expected response
		String response = "{\"query\":{\"pages\":{\"38930\":{\"title\":\"Title\",\"extract\":\"Extract\",\"description\":\"Description\"}}}}";
		JSONObject expected = (JSONObject) new JSONParser().parse(response);
		Mockito.when(searchService.findArticleById(id)).thenReturn(expected);

		//creating actual response
		JSONObject actual = searchService.findArticleById(id);

		//comparing actual and expected
		JSONAssert.assertEquals(expected.toJSONString(),actual.toJSONString(),true);


	}

	@Test
	public void testGetContentByTitle() throws ParseException, IOException, JSONException {

		//setup
		String var = "Java";

		//creating expected response
		String response = "{ \"parse\":{\"wikitext\":{\"s\":\"Extract\" },\"title\":\"Title\",\"pageid\":69336}} ";
		JSONObject expected = (JSONObject) new JSONParser().parse(response);
		Mockito.when(searchService.getContentByTitle(var)).thenReturn(expected);

		//creating actual response
		JSONObject actual = searchService.getContentByTitle(var);

		//comparing actual and expected
		JSONAssert.assertEquals(expected.toJSONString(),actual.toJSONString(),true);


	}

	@Test
	public void testFindRelatedArticles() throws IOException, ParseException, JSONException {

		//setup
		String var = "earth";

		//creating expected response
		String response = "{\"pages\": [{\"thumbnail\":{},\"matched_title\":\"\",\"description\":\"Description\",\"id\":\"38930\",\"title\":\"Title\",\"excerpt\":\"Excerpt\",\"key\":\"Key\"},{},{},{},{}]}";
		JSONObject expected = (JSONObject) new JSONParser().parse(response);
		Mockito.when(searchService.findRelatedArticles(var)).thenReturn(expected);

		//creating actual response
		JSONObject actual = searchService.findRelatedArticles(var);

		//comparing actual and expected
		JSONAssert.assertEquals(expected.toJSONString(),actual.toJSONString(),true);

	}




}
