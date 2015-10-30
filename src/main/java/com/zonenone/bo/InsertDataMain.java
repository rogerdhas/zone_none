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
		selectBrands();
		//selectDeviceType();
		//selectMODEL();
		//selectos();
		stmt.close();
	}

	private static String selectBrands() throws SQLException, JSONException {
		Statement stmt = con.createStatement();
		String sql = "select DEVICE_BRAND, count(DEVICE_BRAND) as total from DEVICE_DETAILS GROUP BY DEVICE_BRAND;";
		ResultSet rs = stmt.executeQuery(sql);
		List<JSONObject> list = new ArrayList<>();
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
		System.out.println(jsonStr);
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
