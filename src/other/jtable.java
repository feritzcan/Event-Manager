package other;
  

import javax.swing.*;
import javax.swing.table.*;
import javax.swing.event.*;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;
import com.mysql.jdbc.StatementImpl;

import java.awt.*;
import java.io.*;
import java.util.*;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.*;

public class jtable extends AbstractTableModel{
  
	
  private String[] culumnNames=
    {"EventName","date"};

  
  adminInfo info;
  public jtable(adminInfo info)
  {
	  this.info=info;
  }
  
	  

  public int getColumnCount(){
    return 2;
  }        
  public int getRowCount(){
	  
	  

		   int numberRow = 0;
		   
		try{
			Connection connect = (Connection) DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/admin"+
					"?verifyServerCertificate=false"+
					"&useSSL=false"+
					"&requireSSL=true","root","feritferit");
		    String query = "select count(*) from "+info.name;
		    PreparedStatement st = (PreparedStatement) connect.prepareStatement(query);
		    ResultSet rs = st.executeQuery();
		    while(rs.next()){
		        numberRow = rs.getInt("count(*)");
		    }
			connect.close();

		}catch (Exception ex){
		    System.out.println(ex.getMessage());
		}
		
		return numberRow;
		}      
     
  public String getColumnName(int column){
    return culumnNames[column];
  }
  public String getValueAt(int rowIndex, int columnIndex){
   
	String name="",date="";
	
	try {
		Connection connect = (Connection) DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/admin"+
				"?verifyServerCertificate=false"+
				"&useSSL=false"+
				"&requireSSL=true","root","feritferit");
		
		
		String query = "SELECT * FROM "+info.name;
		Statement st = (Statement) connect.createStatement();
		ResultSet rs = st.executeQuery(query);
		if(rs.next())
		{
			for(int a=0;a<rowIndex;a++)
			{
				rs.next();
			}
			name=rs.getString("organization");
			date=rs.getString("date");
		}

		connect.close();


	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

		
		
	
	
    if(columnIndex==0){
      return name;
    }else if(columnIndex==1){
        return date;              
   
    }
    
    return "unknown";
    
  }}  