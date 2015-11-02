package com.zonenone.bo;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONException;
import org.json.JSONObject;

import com.zonenone.utils.ZoneNoneCF;
import com.zonenone.utils.ZonoNoneUtills;

public class InitService {

	Connection con = ZoneNoneCF.getInstance().getCon();

	public String find(String offset, String height, String width, HttpServletRequest request) throws SQLException {
		Statement stmt = con.createStatement();
		String ipAddress = ZonoNoneUtills.getClientIpAddress(request);
		String userAgent = ZonoNoneUtills.getUserAgent(request);
		ResultSet rs = stmt.executeQuery("SELECT * FROM user_agent where OFFSET='" + offset + "' and "
				+ " SCREEN_HEIGHT='" + height + "' and SCREEN_WIDTH='" + width + "' and CLIENT_IP_ADDR='" + ipAddress
				+ "' " + " and USER_AGENT='" + userAgent + "' ;");
		String id = "0";
		while (rs.next()) {
			id = rs.getString("USER_ID");
			System.out.println(rs.getString("USER_AGENT"));
		}
		if (id.equals("0")) {
			id = save(offset, height, width, ipAddress, userAgent);
		}
		System.out.println("ID = " + id);
		rs.close();
		stmt.close();
		return id;
	}

	public String save(String offSet, String height, String width, String ipAddress, String userAgent)
			throws SQLException {
		UUID id = UUID.randomUUID();
		String userId = String.valueOf(id);
		Statement stmt = con.createStatement();
		String sql = "INSERT INTO user_agent (USER_ID,OFFSET,SCREEN_HEIGHT,SCREEN_WIDTH, CLIENT_IP_ADDR, USER_AGENT) "
				+ "VALUES ('" + userId + "', '" + offSet + "', '" + height + "', '" + width + "', '" + ipAddress
				+ "', '" + userAgent + "');";
		System.out.println(sql);
		stmt.executeUpdate(sql);
		stmt.close();
		return userId;
	}

	public void insertBrowser(String appName, String userId, HttpServletRequest request, int duration, String title,
			int loadTime) throws SQLException {
		String userAgent = ZonoNoneUtills.getUserAgent(request);
		String browserType = ZonoNoneUtills.getBrowserType(userAgent);
		Statement stmt = con.createStatement();
		String selectQuery = "SELECT COUNT(BROWSER_COUNT) as TOTAL_BROWSER_COUNT FROM APP_BROWSER where APP_NAME='"
				+ appName + "' " + "and BROWSER_TYPE='" + browserType + "' ;";
		System.out.println(selectQuery);
		ResultSet rs = stmt.executeQuery(selectQuery);
		int browserCount = 0;
		while (rs.next()) {
			browserCount = rs.getInt("TOTAL_BROWSER_COUNT");
			System.out.println(browserCount);
		}
		if (browserCount == 0) {
			String sql = "INSERT INTO APP_BROWSER (USER_ID,BROWSER_TYPE,BROWSER_COUNT, USER_AGENT, APP_NAME, DURATION, TITLE, LOAD_TIME) VALUES ('"
					+ userId + "', '" + browserType + "', " + (++browserCount) + ", '" + userAgent + "', '" + appName
					+ "', " + duration + ", '" + title + "', " + loadTime + ");";
			System.out.println(sql);
			stmt.executeUpdate(sql);
		} else {
			String sql = "UPDATE APP_BROWSER SET BROWSER_COUNT=" + (++browserCount) + " where USER_ID='" + userId
					+ "';";
			System.out.println(sql);
			stmt.executeUpdate(sql);
		}
		rs.close();
		stmt.close();
	}

	public void dropTable() throws SQLException {

		Statement stmt = con.createStatement();
		String sql = "DROP TABLE IF EXISTS USER_AGENT;";
		stmt.executeUpdate(sql);
		sql = "DROP TABLE IF EXISTS USER_LINKS;";
		stmt.executeUpdate(sql);
		sql = "DROP TABLE IF EXISTS APP_BROWSER;";
		stmt.executeUpdate(sql);
		stmt.close();
	}

	public void createTable() throws SQLException {

		Statement stmt = con.createStatement();
		String sql = "CREATE TABLE if not exists USER_AGENT (USER_ID TEXT NOT NULL,"
				+ " USER_AGENT TEXT , SCREEN_HEIGHT TEXT, OFFSET TEXT, SCREEN_WIDTH TEXT, CLIENT_IP_ADDR TEXT)";
		stmt.executeUpdate(sql);
		sql = "CREATE TABLE if not exists USER_LINKS (USER_ID TEXT NOT NULL,"
				+ " LINK TEXT NOT NULL, DURATION TEXT, TITLE TEXT, CREATED_TSP CURRENT_TIMESTAMP)";
		stmt.executeUpdate(sql);
		sql = "CREATE TABLE if not exists APP_BROWSER (USER_ID TEXT NOT NULL,BROWSER_TYPE TEXT, BROWSER_COUNT INT NOT NULL,"
				+ " APP_NAME TEXT NOT NULL, DURATION INT, TITLE TEXT, USER_AGENT TEXT, LOAD_TIME INT, CREATED_TSP CURRENT_TIMESTAMP)";
		stmt.executeUpdate(sql);
		stmt.close();
	}

	public String dashboardMatrix() throws SQLException, JSONException {
		Statement stmt = con.createStatement();
		String sql = "select BROWSER_TYPE, count(BROWSER_TYPE) as total from APP_BROWSER GROUP BY BROWSER_TYPE;";
		ResultSet rs = stmt.executeQuery(sql);
		List<JSONObject> list = new ArrayList<JSONObject>();
		JSONObject jsonObj = null;
		int count=1;
		while (rs.next()) {
			System.out.println(rs.getString("BROWSER_TYPE") + " :: " + rs.getInt("total"));
			jsonObj = new JSONObject();
			jsonObj.put("count", count++);
			jsonObj.put("name", rs.getString("BROWSER_TYPE"));
			jsonObj.put("total", rs.getInt("total"));
			list.add(jsonObj);
		}
		System.out.println("test close");
		stmt.close();
		String jsonStr = list.toString();
		return jsonStr;
	}

	public String totalAppVisitors() throws SQLException, JSONException {
		Statement stmt = con.createStatement();
		String sql = "select APP_NAME, count(*) as total from APP_BROWSER GROUP BY APP_NAME";
		ResultSet rs = stmt.executeQuery(sql);
		List<JSONObject> list = new ArrayList<JSONObject>();
		JSONObject jsonObj = null;
		int count=1;
		while (rs.next()) {
			System.out.println(rs.getString("APP_NAME") + " :: " + rs.getInt("total"));
			jsonObj = new JSONObject();
			jsonObj.put("count", count++);
			jsonObj.put("name", rs.getString("APP_NAME"));
			jsonObj.put("total", rs.getInt("total"));
			list.add(jsonObj);
		}
		stmt.close();
		String jsonStr = list.toString();
		return jsonStr;
	}

	public String selectDeviceType() throws SQLException, JSONException {
		Statement stmt = con.createStatement();
		String sql = "select DEVICE_TYPE, count(DEVICE_TYPE) as total from DEVICE_DETAILS GROUP BY DEVICE_TYPE;";
		ResultSet rs = stmt.executeQuery(sql);
		List<JSONObject> list = new ArrayList<JSONObject>();
		JSONObject jsonObj = null;
		int count=1;
		while (rs.next()) {
			System.out.println(rs.getString("DEVICE_TYPE") + " :: " + rs.getInt("total"));
			jsonObj = new JSONObject();
			jsonObj.put("count", count++);
			jsonObj.put("name", rs.getString("DEVICE_TYPE"));
			jsonObj.put("total", rs.getInt("total"));
			list.add(jsonObj);
		}
		stmt.close();
		String jsonStr = list.toString();
		return jsonStr;
	}

	public String selectMODEL() throws SQLException, JSONException {
		Statement stmt = con.createStatement();
		String sql = "select DEVICE_MODEL, count(DEVICE_MODEL) as total from DEVICE_DETAILS GROUP BY DEVICE_MODEL;";
		ResultSet rs = stmt.executeQuery(sql);
		List<JSONObject> list = new ArrayList<JSONObject>();
		JSONObject jsonObj = null;
		int count=1;
		while (rs.next()) {
			System.out.println(rs.getString("DEVICE_MODEL") + " :: " + rs.getInt("total"));
			jsonObj = new JSONObject();
			jsonObj.put("count", count++);
			jsonObj.put("name", rs.getString("DEVICE_MODEL"));
			jsonObj.put("total", String.valueOf(rs.getInt("total")));
			list.add(jsonObj);
		}
		stmt.close();
		String jsonStr = list.toString();
		return jsonStr;
	}

	public String selectBrand() throws SQLException, JSONException {
		Statement stmt = con.createStatement();
		String sql = "select DEVICE_BRAND, count(DEVICE_BRAND) as total from DEVICE_DETAILS GROUP BY DEVICE_BRAND;";
		ResultSet rs = stmt.executeQuery(sql);
		List<JSONObject> list = new ArrayList<JSONObject>();
		JSONObject jsonObj = null;
		int count=1;
		while (rs.next()) {
			System.out.println(rs.getString("DEVICE_BRAND") + " :: " + rs.getInt("total"));
			jsonObj = new JSONObject();
			jsonObj.put("count", count++);
			jsonObj.put("name", rs.getString("DEVICE_BRAND"));
			jsonObj.put("total", rs.getInt("total"));
			list.add(jsonObj);
		}
		stmt.close();
		String jsonStr = list.toString();
		return jsonStr;
	}

	public String selectos() throws SQLException, JSONException {
		Statement stmt = con.createStatement();
		String sql = "select OS, count(OS) as total from DEVICE_DETAILS GROUP BY OS;";
		ResultSet rs = stmt.executeQuery(sql);
		List<JSONObject> list = new ArrayList<JSONObject>();
		JSONObject jsonObj = null;
		int count=1;
		while (rs.next()) {
			System.out.println(rs.getString("OS") + " :: " + rs.getInt("total"));
			jsonObj = new JSONObject();
			jsonObj.put("count", count++);
			jsonObj.put("name", rs.getString("OS"));
			jsonObj.put("total", rs.getInt("total"));
			list.add(jsonObj);
		}
		stmt.close();
		String jsonStr = list.toString();
		return jsonStr;
	}

	public String loadTime() throws SQLException, JSONException {
		Statement stmt = con.createStatement();
		String sql = "select USER_AGENT, avg(DURATION) as duration, avg(LOAD_TIME) as loadTime from APP_BROWSER GROUP BY USER_AGENT;";
		ResultSet rs = stmt.executeQuery(sql);
		List<JSONObject> list = new ArrayList<JSONObject>();
		JSONObject jsonObj = null;
		int count=1;
		String[] refurl = { "index.do", "contact.html", "sitemesh.jsp", "contact.do", "admin.do", "createUser.do", "editUser.do", "test.do", "connectUrl.do" };
		while (rs.next()) {
			jsonObj = new JSONObject();
			jsonObj.put("count", count++);
			jsonObj.put("pageName", (refurl[new Random().nextInt(refurl.length)]).concat("?").concat("dat=").concat(String.valueOf(rs.getInt("duration"))));
			jsonObj.put("name", rs.getString("USER_AGENT"));
			jsonObj.put("duration", rs.getInt("duration"));
			jsonObj.put("loadTime", rs.getInt("loadTime"));
			list.add(jsonObj);
		}
		stmt.close();
		String jsonStr = list.toString();
		System.out.println(jsonStr);
		return jsonStr;
	}

	public String dashBoardLogTime() throws SQLException, JSONException {
		Statement stmt = con.createStatement();
		String sql = "select * from LOG_TIME order by LOG_ID asc;";
		ResultSet rs = stmt.executeQuery(sql);
		List<JSONObject> list = new ArrayList<JSONObject>();
		JSONObject jsonObj = null;
		int count=1;
		while (rs.next()) {
			jsonObj = new JSONObject();
			jsonObj.put("count", count++);
			jsonObj.put("logTime", rs.getString("LOG_TIME"));
			jsonObj.put("userCount", rs.getInt("USER_COUNT"));
			list.add(jsonObj);
		}
		stmt.close();
		String jsonStr = list.toString();
		System.out.println(jsonStr);
		return jsonStr;
	}
	


	public  String countVisitors() throws SQLException, JSONException {
		Statement stmt = con.createStatement();
		String sql = "select count(*) as totalUsers from APP_BROWSER  ";
		ResultSet rs = stmt.executeQuery(sql);
		List<JSONObject> list = new ArrayList<JSONObject>();
		JSONObject jsonObj = null;
		int count=1;
		while (rs.next()) {
			jsonObj = new JSONObject();
			jsonObj.put("userCount",   rs.getInt("totalUsers"));
			list.add(jsonObj);     
		}
		stmt.close();
		String jsonStr = list.toString();
		System.out.println(jsonStr);
		return jsonStr;  
	}

	public  String uniqueVisitors() throws SQLException, JSONException {
		Statement stmt = con.createStatement();
		String sql = "select distinct USER_ID as totalUsers from APP_BROWSER";
		ResultSet rs = stmt.executeQuery(sql);
		List<JSONObject> list = new ArrayList<JSONObject>();
		JSONObject jsonObj = null;
		int count=0;
		while (rs.next()) {
			++count;
		}
		stmt.close();
		jsonObj = new JSONObject();
		jsonObj.put("userCount", count);
		list.add(jsonObj);
		String jsonStr = list.toString();
		System.out.println(jsonStr);
		return jsonStr;
	}

}
