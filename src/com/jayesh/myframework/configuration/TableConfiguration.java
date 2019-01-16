/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jayesh.myframework.configuration;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Jayesh
 */
public class TableConfiguration {
    public static boolean checkAndCreateTable(Class c) throws SQLException, ClassNotFoundException
    {
        String tableName=c.getName();
        Connection con=ConfigureFactory.getConnectionObject();
        DatabaseMetaData meta = con.getMetaData();
        ResultSet res = meta.getTables(null, null, tableName, new String[] {"TABLE"});
        
        while (res.next()) 
        {
           System.out.println(res.getString("TABLE_NAME"));
           if(tableName==res.getString("TABLE_NAME"))
                System.out.println("Table exixsts");
           else
                System.out.println("Table is not exists");
        }
        return true;
    }
}
