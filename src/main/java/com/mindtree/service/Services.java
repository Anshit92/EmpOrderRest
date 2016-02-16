package com.mindtree.service;

import java.io.BufferedReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONObject;

import com.mindtree.jdbc.JdbcConnection;

@Path("/")
public class Services {
	
	public Connection connec;
	
	@GET
	@Path("/display/{table}/{name}")
	@Produces(MediaType.TEXT_PLAIN)
	public Response display(InputStream incomingData,@PathParam("name") String name,@PathParam("table") String table)  {
		JSONObject jsonObject = new JSONObject();
		try {
	    connec=JdbcConnection.getConnectionObject();
		Statement stmt=connec.createStatement();
		String query = "select * from "+table+" where name='"+name+"'";
	    ResultSet rs =  stmt.executeQuery(query);
	    ResultSetMetaData rsmd = rs.getMetaData();
	    int columnsNumber = rsmd.getColumnCount();
	    
	    while (rs.next()) {
	        for (int i = 1;i <= columnsNumber; i++) {
	            String columnValue = rs.getString(i);
	            System.out.println(columnValue);
	            jsonObject.put(rsmd.getColumnName(i), columnValue);
	        }
	    }} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return Response.status(200).entity(""+jsonObject.toString()).build(); 
	}
	
	@POST
	@Path("/insertEmp")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response insertEmp(InputStream incomingData)  {
		StringBuilder jsonO = new StringBuilder();
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(incomingData));
			String line = null;
			while ((line = in.readLine()) != null) {
				jsonO.append(line);
			}
			JSONObject json= new JSONObject(jsonO.toString());
			String name = (String) json.get("name");
			int age= (Integer) json.get("age");
			String gender = (String) json.get("gender");
	    connec=JdbcConnection.getConnectionObject();
		Statement stmt=connec.createStatement();
		String query = "insert into employee values('"+name+"',"+age+",'"+gender+"')";
		stmt.executeUpdate(query);
	  } catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		return Response.status(200).entity("Inserted Successfully").build();
	}
	

	@POST
	@Path("/insertOrder")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response insertOrder(InputStream incomingData)  {
		StringBuilder jsonO = new StringBuilder();
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(incomingData));
			String line = null;
			while ((line = in.readLine()) != null) {
				jsonO.append(line);
			}
			JSONObject json= new JSONObject(jsonO.toString());
			String name = (String) json.get("name");
			String desc = (String) json.get("description");
			URLEncoder.encode (desc,"UTF-8").replace("+", "%20");
	    connec=JdbcConnection.getConnectionObject();
		Statement stmt=connec.createStatement();
		String query = "insert into orders values('"+name+"','"+desc+"')";
		stmt.executeUpdate(query);
	  } catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		return Response.status(200).entity("Inserted Successfully ").build();
	}
	
}
