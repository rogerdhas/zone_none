package com.zonenone.bo;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.zonenone.utils.ZoneNoneCF;

public class InsertDataMain {

	static Connection con = ZoneNoneCF.getInstance().getCon();
	static Statement stmt = null;

	public static void main(String[] args) throws SQLException, JSONException {
		// TODO Auto-generated method stub
		stmt = con.createStatement();
		InitService ins = new InitService();
		ins.dropTable();
		DummyData dummy = new DummyData();
		dummy.insertData();
		//selectBrandName();
		//totalVisitors();
		//selectBrand();
		//selectBrands();
		//selectDeviceType();
		//selectMODEL();
		//selectos();
		//loadTime();
		dashBoardLogTime();
		//countVisitors();
		//uniqueVisitors();
		//zoneNoneUniqueVisitors();
		stmt.close();
	}

	public static String zoneNoneUniqueVisitors() throws SQLException, JSONException {
		Statement stmt = con.createStatement();
		String sql = "select distinct USER_ID as totalUsers from APP_BROWSER where APP_NAME='zonenone-demo.cfapps.io'";
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

	public static String countVisitors() throws SQLException, JSONException {
		Statement stmt = con.createStatement();
		String sql = "select count(*) as totalUsers from APP_BROWSER";
		ResultSet rs = stmt.executeQuery(sql);
		List<JSONObject> list = new ArrayList<JSONObject>();
		JSONObject jsonObj = null;
		int count=1;
		while (rs.next()) {
			jsonObj = new JSONObject();
			jsonObj.put("userCount", rs.getInt("totalUsers"));
			list.add(jsonObj);
		}
		stmt.close();
		String jsonStr = list.toString();
		System.out.println(jsonStr);
		return jsonStr;
	}

	public static String uniqueVisitors() throws SQLException, JSONException {
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

	public static String dashBoardLogTime() throws SQLException, JSONException {
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

	private static String loadTime() throws SQLException, JSONException {
		Statement stmt = con.createStatement();
		String sql = "select APP_NAME, avg(DURATION) as duration, avg(LOAD_TIME) as loadTime from APP_BROWSER GROUP BY APP_NAME;";
		ResultSet rs = stmt.executeQuery(sql);
		List<JSONObject> list = new ArrayList<JSONObject>();
		JSONObject jsonObj = null;
		while (rs.next()) {
			jsonObj = new JSONObject();
			jsonObj.put("name", rs.getString("APP_NAME"));
			jsonObj.put("duration", rs.getInt("duration"));
			jsonObj.put("loadTime", rs.getInt("loadTime"));
			list.add(jsonObj);
		}
		stmt.close();
		String jsonStr = list.toString();
		System.out.println(jsonStr);
		return jsonStr;
	}
	
	private static String selectBrands() throws SQLException, JSONException {
		Statement stmt = con.createStatement();
		String sql = "select DEVICE_BRAND, count(DEVICE_BRAND) as total from DEVICE_DETAILS GROUP BY DEVICE_BRAND;";
		ResultSet rs = stmt.executeQuery(sql);
		List<JSONObject> list = new ArrayList<JSONObject>();
		JSONObject jsonObj = null;
		while (rs.next()) {
			System.out.println(rs.getString("DEVICE_BRAND") + " :: " + rs.getInt("total"));
			jsonObj = new JSONObject();
			jsonObj.put("name", rs.getString("DEVICE_BRAND"));
			jsonObj.put("total", rs.getInt("total"));
			list.add(jsonObj);
		}
		stmt.close();
		String jsonStr = list.toString();
		System.out.println(jsonStr.replace("[", "").replace("]", ""));
		return jsonStr;
	}

	private static void selectBrandName() throws SQLException {
		String sql = "select BROWSER_TYPE, count(BROWSER_TYPE) as total from APP_BROWSER GROUP BY BROWSER_TYPE;";
		ResultSet rs = stmt.executeQuery(sql);
		System.out.println("test");
		while (rs.next()) {
			System.out.println(rs.getString("BROWSER_TYPE") + " :: " + rs.getInt("total"));
		}
		System.out.println("test close");
	}
	
	private static void totalVisitors() throws SQLException {
		String sql = "select APP_NAME, count(*) as total from APP_BROWSER GROUP BY APP_NAME";
		ResultSet rs = stmt.executeQuery(sql);
		System.out.println("test");
		while (rs.next()) {
			System.out.println(rs.getString("APP_NAME") + " :: " + rs.getInt("total"));
		}
		System.out.println("test close");
	}


	private static void selectDeviceType() throws SQLException {
		String sql = "select DEVICE_TYPE, count(DEVICE_TYPE) as total from DEVICE_DETAILS GROUP BY DEVICE_TYPE;";
		ResultSet rs = stmt.executeQuery(sql);
		System.out.println("test");
		while (rs.next()) {
			System.out.println(rs.getString("DEVICE_TYPE") + " :: " + rs.getInt("total"));
		}
		System.out.println("test close");
	}

	private static void selectMODEL() throws SQLException {
		String sql = "select DEVICE_MODEL, count(DEVICE_MODEL) as total from DEVICE_DETAILS GROUP BY DEVICE_MODEL;";
		ResultSet rs = stmt.executeQuery(sql);
		System.out.println("test");
		while (rs.next()) {
			System.out.println(rs.getString("DEVICE_MODEL") + " :: " + rs.getInt("total"));
		}
		System.out.println("test close");
	}

	private static void selectBrand() throws SQLException {
		String sql = "select DEVICE_BRAND, count(DEVICE_BRAND) as total from DEVICE_DETAILS GROUP BY DEVICE_BRAND;";
		ResultSet rs = stmt.executeQuery(sql);
		System.out.println("test");
		while (rs.next()) {
			System.out.println(rs.getString("DEVICE_BRAND") + " :: " + rs.getInt("total"));
		}
		System.out.println("test close");
	}

	private static void selectos() throws SQLException {
		String sql = "select OS, count(OS) as total from DEVICE_DETAILS GROUP BY OS;";
		ResultSet rs = stmt.executeQuery(sql);
		System.out.println("test");
		while (rs.next()) {
			System.out.println(rs.getString("OS") + " :: " + rs.getInt("total"));
		}
		System.out.println("test close");
	}

}
