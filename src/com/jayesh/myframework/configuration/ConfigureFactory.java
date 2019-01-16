/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jayesh.myframework.configuration;

import com.jayesh.myframework.binding.Bind;
import java.sql.Connection;
import java.sql.SQLException;
/**
 *
 * @author Jayesh
 */
public final class ConfigureFactory implements Configuration{
    private static Connection connection;
    private Bind bind;
    
    public ConfigureFactory()
    {
        
    }
    
    public ConfigureFactory(String dbName) throws ClassNotFoundException, SQLException 
    {
        if(isConnected())
        {
            System.out.println("You are not connected to the database");
            connection=getConnection(dbName);
        }
    }
    
    public int setDatabase(String dbname) throws ClassNotFoundException, SQLException
    {
        connection=getConnection(dbname);
        if(connection!=null)
            return 1;
        else
            return 0;
    }
    
    Configuration getConfiguration() throws ClassNotFoundException, SQLException
    {
        Configuration cf=new ConfigureFactory();
        return cf;
    }
    
    private Connection getConnection(String dbName) throws ClassNotFoundException, SQLException
    {
        Connection con=connect(dbName);
        return con; 
    }
    
    public static Connection getConnectionObject() throws ClassNotFoundException, SQLException
    {
        return connection;
    }
    public boolean isConnected()
    {
        if(connection!=null)
            return false;
        else
            return true;
    }
    
    public void setBinding(Bind b)
    {
        bind=b;
    }
    public Bind getBinding()
    {
        return bind;
    }
}
