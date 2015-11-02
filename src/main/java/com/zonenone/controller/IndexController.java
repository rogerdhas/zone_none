package com.zonenone.controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;

import com.zonenone.bo.DummyData;
import com.zonenone.bo.InitService;
import com.zonenone.utils.ZonoNoneUtills;

@Controller
public class IndexController {

	@Autowired
	private ServletContext servContext;

	@Autowired
	private WebApplicationContext appContext;

	/**
	 * First Method to load Default Page
	 * 
	 * @param model
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/save.do", method = RequestMethod.GET)
	public ModelAndView save(HttpServletRequest request, HttpServletResponse response) {
		InitService initService;
		try {
			ModelAndView mv = new ModelAndView("home");
			initService = (InitService) appContext.getBean("initService");

			String offset = request.getParameter("o");
			String height = request.getParameter("h");
			String width = request.getParameter("w");

			String ipAddress = ZonoNoneUtills.getClientIpAddress(request);
			String userAgent = ZonoNoneUtills.getUserAgent(request);
			initService.save(offset, height, width, ipAddress, userAgent);
			mv.addObject("result", "Success");
			/*
			 * RedirectView redirectView = new RedirectView();
			 * redirectView.setUrl("persons.do"); mv.setView(redirectView);
			 */
			return mv;
		} catch (Exception e) {
			e.printStackTrace();
			return new ModelAndView("home");
		}
	}

	@RequestMapping(value = "/s.do", method = RequestMethod.GET)
	@ResponseBody
	public String find(HttpServletRequest request) throws SQLException {

		InitService initService = (InitService) appContext.getBean("initService");
		String offset = request.getParameter("o");
		String height = request.getParameter("h");
		String width = request.getParameter("w");
		String userId = initService.find(offset, height, width, request);
		System.out.println(userId);
		return userId;
	}

	@RequestMapping(value = "/l.do", method = RequestMethod.GET)
	@ResponseBody
	public void closeBrowser(HttpServletRequest request) throws SQLException {

		InitService initService = (InitService) appContext.getBean("initService");
		Integer duration = Integer.parseInt(request.getParameter("d"));
		String appName = request.getParameter("r");
		String title = request.getParameter("t");
		String userId = request.getParameter("u");
		Integer loadTime = Integer.parseInt(request.getParameter("l"));
		initService.insertBrowser(appName, userId, request, duration, title, loadTime);
	}

	@RequestMapping(value = "/create.do", method = RequestMethod.GET)
	public String create() throws SQLException {

		ModelAndView mv = new ModelAndView("home");

		InitService initService = (InitService) appContext.getBean("initService");

		initService.dropTable();

		// initService.createTable();
		DummyData data = new DummyData();
		data.insertData();

		// Attach persons to the Model
		mv.addObject("result", "success");

		// This will resolve to /WEB-INF/jsp/personspage.jsp
		return "home";
	}

	@RequestMapping(value = "/matrix.do", method = RequestMethod.GET)
	@ResponseBody
	public String matrix(HttpServletRequest request) throws SQLException, JSONException {

		InitService initService = (InitService) appContext.getBean("initService");
		String dataTyp = request.getParameter("dataTyp");
		String str = "";
		if (dataTyp.equalsIgnoreCase("dashboard")) {
			str = initService.dashboardMatrix();
		} else if (dataTyp.equalsIgnoreCase("appVisitors")) {
			str = initService.totalAppVisitors();
		} else if (dataTyp.equalsIgnoreCase("deviceTyp")) {
			str = initService.selectDeviceType();
		} else if (dataTyp.equalsIgnoreCase("deviceModel")) {
			str = initService.selectMODEL();
		} else if (dataTyp.equalsIgnoreCase("deviceBrand")) {
			str = initService.selectBrand();
		} else if (dataTyp.equalsIgnoreCase("os")) {
			str = initService.selectos();
		} else if (dataTyp.equalsIgnoreCase("loadTime")) {
			str = initService.loadTime();
		} else if (dataTyp.equalsIgnoreCase("dashLogTime")) {
			str = initService.dashBoardLogTime();
		}
		return str;

	}

}
