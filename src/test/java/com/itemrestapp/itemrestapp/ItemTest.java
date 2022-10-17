package com.itemrestapp.itemrestapp;

import static org.junit.jupiter.api.Assertions.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.dao.ItemDao;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.model.Item;

@SpringBootTest
class ItemTest {

	@Autowired
	ItemDao itemDao;
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void test() 
	{
		Item item=new Item();
		item.setItemName("Coffee");
		item.setPrice(20);
		item.setQuantity(5);
		
		itemDao.save(item);
		Item item1=itemDao.findById(item.getItemId()).get();
		
		assertEquals(item.getItemName(),item1.getItemName());
	}
	
	@Test
	void testByName()
	{
		Item item=itemDao.findByItemName("Coffee");
		assertEquals(item.getPrice(),20);
	}
	
	@Test 
	void testByPrice()
	{
		Item item=new Item();
		item.setItemName("Tea");
		item.setPrice(25);
		item.setQuantity(8);
		
		itemDao.save(item);
		Item item1=itemDao.findByPrice(25);
		
		assertEquals(item1.getItemName(),"Tea");
	}
	
	@Test
	void countByName()
	{
		int count=itemDao.countByItemName("Tea");
		assertEquals(count,1);
	}

	@Test 
	void testFindById() throws URISyntaxException,JsonProcessingException
	{
		RestTemplate rt=new RestTemplate();
		final String url="http://localhost:8080/findbyid/1";
		URI uri=new URI(url);
		
		ResponseEntity<String> res=rt.getForEntity(uri, String.class);
		assertEquals(HttpStatus.OK, res.getStatusCode());
	}
	
	@Test
	void testAddItem() throws URISyntaxException,JsonProcessingException
	{
		RestTemplate rt=new RestTemplate();
		final String url="http://localhost:8080/additem";
		
		URI uri=new URI(url);
		HttpHeaders headers=new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<Item> entity=new HttpEntity<Item>(headers);
		
		
		ResponseEntity<String> res=rt.exchange(uri, HttpMethod.POST,entity,String.class);
		assertEquals(HttpStatus.OK, res.getStatusCode());
	}
}
