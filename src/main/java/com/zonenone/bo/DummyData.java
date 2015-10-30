package com.zonenone.bo;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.Random;

import com.zonenone.utils.ZoneNoneCF;

public class DummyData {

	public void insertData() throws SQLException {

		Connection con = ZoneNoneCF.getInstance().getCon();
		Statement stmt = con.createStatement();
		try {
			Random randomGenerator = new Random();
			/*
			 * String sql =
			 * "CREATE TABLE if not exists USER_AGENT (USER_ID TEXT NOT NULL," +
			 * " USER_AGENT TEXT , SCREEN_HEIGHT TEXT, OFFSET TEXT, SCREEN_WIDTH TEXT, CLIENT_IP_ADDR TEXT,LOC TEXT)"
			 * ; stmt.executeUpdate(sql); sql =
			 * "CREATE TABLE if not exists USER_LINKS (USER_ID TEXT NOT NULL," +
			 * " LINK TEXT NOT NULL, DURATION TEXT, TITLE TEXT, CREATED_TSP CURRENT_TIMESTAMP)"
			 * ; stmt.executeUpdate(sql); sql =
			 * "CREATE TABLE if not exists APP_BROWSER (USER_ID TEXT NOT NULL,BROWSER_TYPE TEXT, BROWSER_COUNT INT NOT NULL,"
			 * +
			 * " APP_NAME TEXT NOT NULL, DURATION INT, TITLE TEXT, USER_AGENT TEXT, LOAD_TIME INT, CREATED_TSP CURRENT_TIMESTAMP)"
			 * ; stmt.executeUpdate(sql); sql =
			 * "CREATE TABLE if not exists Referrer(USER_ID TEXT NOT NULL,BROWSER_URL TEXT,  DURATION INT, CREATED_TSP CURRENT_TIMESTAMP)"
			 * ; stmt.executeUpdate(sql);
			 */

			String[] strrefferer = { "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:40.0) Gecko/20100101 Firefox/40.1",
					"Opera/9.80 (X11; Linux i686; Ubuntu/14.10) Presto/2.12.388 Version/12.16",
					"Mozilla/5.0 (compatible; MSIE 10.0; Windows NT 6.1; WOW64; Trident/6.0)",
					"Mozilla/5.0 (Windows; U; Windows NT 6.1; rv:2.2) Gecko/20110201" };
			String[] screenhight = { "1920", "1080", "1066", "1366", "1600" };
			String[] screenwidth = { "1080", "768", "1200", "1050", "900" };
			String[] location = { "India", "China", "USA", "Germany", "Russia", "Burma" };
			String[] title = { "News", "Sports", "Tutorials", "Advertisement", "Finanace", "Educatinon" };
			String[] ips = { "103.21.244.22", "103.22.200.22", "103.31.4.22", "104.16.0.12", "108.162.192.18",
					"141.101.64.18", "162.158.0.15", "172.64.0.13", "173.245.48.20", "188.114.96.20", "190.93.240.20",
					"197.234.240.", "198.41.128.17", "199.27.128.21" };

			String[] BROWSER_TYPE = { "IE", "Firefox", "Chrome", "Safari" };
			String[] refurl = { "google.com", "oracle.com", "verizon.com" };
			String[] userId = { "91827981745", "91879285", "1123498279485", "183759187359" };
			String[] DEVICE_TYPE = { "Firefox", "Chrome", "Opera" };
			String[] DEVICE_MODEL = { "Apple ipad", "Motorola", "Lenovo" };
			String[] DEVEICE_BRAND = { "Samsung", "Apple iPhone", "Nokia" };
			String[] OS = { "Mac OS", "Windows 7", "Android KitKat" };

			String sql = "CREATE TABLE IF NOT EXISTS USER_AGENT (USER_ID TEXT, USER_AGENT TEXT, SCREEN_HEIGHT INT, OFFSET TEXT, SCREEN_WIDTH INT,"
					+ " CLIENT_IP_ADDR TEXT, LOC TEXT)";
			stmt.executeUpdate(sql);
			sql = "CREATE TABLE IF NOT EXISTS APP_BROWSER(USER_ID TEXT, BROWSER_TYPE TEXT, BROWSER_COUNT INT, APP_NAME TEXT, DURATION INT,"
					+ " TITLE TEXT, USER_AGENT TEXT, LOAD_TIME INT, CREATED_TSP TIMESTAMP)";
			stmt.executeUpdate(sql);
			sql = "CREATE TABLE IF NOT EXISTS DEVICE_DETAILS(USER_ID TEXT, DEVICE_TYPE TEXT, DEVICE_BRAND TEXT,"
					+ " DEVICE_MODEL TEXT, OS TYPE);";
			stmt.executeUpdate(sql);
			for (int idx = 1; idx <= 100; ++idx) {
				int randomInt = randomGenerator.nextInt(100);

				sql = "INSERT INTO USER_AGENT (USER_ID,USER_AGENT,SCREEN_HEIGHT,OFFSET,SCREEN_WIDTH,CLIENT_IP_ADDR,LOC) "
						+ "VALUES (" + userId[new Random().nextInt(userId.length)] + ", '"
						+ strrefferer[new Random().nextInt(strrefferer.length)] + "',"
						+ screenhight[new Random().nextInt(screenhight.length)] + ", '-" + Integer.toString(randomInt)
						+ "'," + screenwidth[new Random().nextInt(screenwidth.length)] + ",'"
						+ ips[new Random().nextInt(ips.length)] + "','"
						+ location[new Random().nextInt(location.length)] + "') ;";
				System.out.println(sql);
				stmt.executeUpdate(sql);
				sql = "INSERT INTO APP_BROWSER (USER_ID,BROWSER_TYPE,BROWSER_COUNT,APP_NAME,DURATION,TITLE,USER_AGENT,LOAD_TIME,CREATED_TSP) "
						+ "Values (" + userId[new Random().nextInt(userId.length)] + ", '"
						+ BROWSER_TYPE[new Random().nextInt(BROWSER_TYPE.length)] + "',"
						+ Integer.toString(randomGenerator.nextInt(100)) + ",'"
						+ refurl[new Random().nextInt(refurl.length)] + "',"
						+ Integer.toString(randomGenerator.nextInt(100)) + ",'"
						+ title[new Random().nextInt(title.length)] + "','"
						+ strrefferer[new Random().nextInt(strrefferer.length)] + "',"
						+ Integer.toString(randomGenerator.nextInt(100)) + ",'" + new Date() + "');";
				System.out.println(sql);
				stmt.executeUpdate(sql);
				sql = "INSERT INTO DEVICE_DETAILS(USER_ID, DEVICE_TYPE, DEVICE_BRAND, DEVICE_MODEL, OS) VALUES ("
						+ userId[new Random().nextInt(userId.length)] + ", '"
						+ DEVICE_TYPE[new Random().nextInt(DEVICE_TYPE.length)] + "','"
						+ DEVEICE_BRAND[new Random().nextInt(DEVEICE_BRAND.length)] + "','"
						+ DEVICE_MODEL[new Random().nextInt(DEVICE_MODEL.length)] + "','"
						+ OS[new Random().nextInt(OS.length)] + "');";
				System.out.println(sql);
				stmt.executeUpdate(sql);
			}

			stmt.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		System.out.println("Table created successfully");
	}

}