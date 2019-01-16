/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jayesh.myframework.configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import com.jayesh.myframework.binding.Bind;
import com.jayesh.myframework.binding.access.AccessData;
import com.jayesh.myframework.binding.saving.SaveObject;
import com.jayesh.myframework.binding.access.AccessData;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author Jayesh
 * @param <T>
 */
interface Configuration <T> {
    
    default public Connection connect(String dbName) throws ClassNotFoundException, SQLException
    {
        Connection con;
        Class.forName("com.mysql.jdbc.Driver");
        System.out.println("Driver loaded");
        if(checkDBExists(dbName))
        {
            System.out.println("Databse is exists");
            con=DriverManager.getConnection("jdbc:mysql://localhost:3306/"+dbName,"root","");
            System.out.println("Connected");
            return(con);
        }
        else
        {
            System.out.println("Database is not exists");
            con = DriverManager.getConnection("jdbc:mysql://localhost/?user=root&password="); 
            Statement stmt=con.createStatement();
            int Result=stmt.executeUpdate("CREATE DATABASE "+dbName);
            System.out.println("New database created");
            con=DriverManager.getConnection("jdbc:mysql://localhost:3306/"+dbName,"root","");
            System.out.println("Connected");
            return(con);
        }
    }
    
    default public boolean checkDBExists(String dbName)
    {
        try
        {
            Class.forName("com.mysql.jdbc.Driver"); //Register JDBC Driver

            System.out.println("Creating a connection...");
            Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/"+dbName,"root",""); //Open a connection

            ResultSet resultSet = con.getMetaData().getCatalogs();

            while (resultSet.next()) 
            {

              String databaseName = resultSet.getString(1);
                if(databaseName.equals(dbName))
                {
                    return true;
                }
                System.out.println("da name= "+databaseName);
            }
            resultSet.close();

        }
        catch(Exception e)
        {
            //e.printStackTrace();
        }

        return false;
    }
}