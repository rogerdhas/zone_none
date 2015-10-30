package com.zonenone.test;

import javax.servlet.http.HttpServletRequest;

import org.junit.Test;

import com.zonenone.bo.InitService;

public class InitServiceTest {

	@Test
	public void createTable() {
		InitService initService = new InitService();
		try {
			initService.createTable();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void find() {
		InitService initService = new InitService();
		try {
			HttpServletRequest request = null;
			initService.find("test", "1020", "900", request);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void save() {
		InitService initService = new InitService();
		try {
			initService.save("test", "1200", "900", "112.122.121.12", "Firefox");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void browser() {
		InitService initService = new InitService();
		try {
			HttpServletRequest request = null;
			initService.insertBrowser("http://zonenone.cfapps.io/", "test", request, 99, "1212", 98);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
