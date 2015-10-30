package com.zonenone.bo;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

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
		String selectQuery = "SELECT COUNT(BROWSER_COUNT) as TOTAL_BROWSER_COUNT FROM APP_BROWSER where APP_NAME='" + appName
				+ "' " + "and BROWSER_TYPE='" + browserType + "' ;";
		System.out.println(selectQuery);
		ResultSet rs = stmt.executeQuery(selectQuery);
		int browserCount = 0;
		while (rs.next()) {
			browserCount = rs.getInt("TOTAL_BROWSER_COUNT");
			System.out.println(browserCount);
		}
		if (browserCount == 0) {
			String sql = "INSERT INTO APP_BROWSER (USER_ID,BROWSER_TYPE,BROWSER_COUNT, USER_AGENT, APP_NAME, DURATION, TITLE, LOAD_TIME) VALUES ('"
					+ userId + "', '" + browserType + "', " + (++browserCount) + "', '" + userAgent + "', '" + appName
					+ "', " + duration + "', '" + title + "' " + loadTime + ");";
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
}