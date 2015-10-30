package com.zonenone.utils;

import java.sql.Connection;
import java.sql.DriverManager;

public class ZoneNoneCF {
	
	private static ZoneNoneCF cf = null;

    private static Connection con = null;
	
	protected ZoneNoneCF(){
		
	}

	public static ZoneNoneCF getInstance(){
		if(cf == null){
			cf = new ZoneNoneCF();
		    try {
		      Class.forName("org.sqlite.JDBC");
		      con = DriverManager.getConnection("jdbc:sqlite:test.db");
		    } catch ( Exception e ) {
		      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		      System.exit(0);
		    }
		}
		return cf;
	}

	public static Connection getCon() {
		return con;
	}
	
}
