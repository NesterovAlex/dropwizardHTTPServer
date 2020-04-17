package com.nesterov.dw_hibernate.db;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Names {
	
	 
     public List namesList() {
    	 
    	FileInputStream fis;
        Properties property = new Properties();
    	final List nameList = new ArrayList();
    	
        try {
        	
        	fis = new FileInputStream("src/main/resources/config.properties");
            property.load(fis);
            
            String driver = property.getProperty("db.driver");
            String urlProperty = property.getProperty("db.host");
            String loginProperty = property.getProperty("db.login");
            String passwordProperty = property.getProperty("db.password");
            String queryProperty = property.getProperty("db.query");
            String columnProperty = property.getProperty("db.column");
            
            Class.forName(driver);
            String url = urlProperty;
            String login = loginProperty;
            String password = passwordProperty ;
            Connection con = DriverManager.getConnection(url, login, password);
            try {
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(queryProperty);
                int i = 0;
                
                while (rs.next()) {
                    String str = rs.getString(columnProperty);
                    nameList.add(str);
                    System.out.println(nameList.get(i));
                    i++;
                }
                rs.close();
                stmt.close();
            } finally {
                con.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return nameList;
    }
}
