/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jayesh.myframework.configuration.database.store;

import com.jayesh.myframework.configuration.ConfigureFactory;
import com.jayesh.myframework.configuration.database.query.InsertQuery;
import com.jayesh.myframework.configuration.database.query.UpdateQuery;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author Jayesh
 */
public class StoreDatabaseData
{
    public static void saveData(String tableName,ArrayList colName,ArrayList colValue) throws ClassNotFoundException, SQLException
    {
        Connection con=ConfigureFactory.getConnectionObject();
        Statement stmt;
        InsertQuery iq=new InsertQuery();

        //Creating the insert Query
        String query=iq.getInsertQuery(tableName,colName,colValue);
        System.out.println("Query= "+query);
        
        //Creating the statement
        stmt=con.createStatement();
        
        //Executing the query
        stmt.executeUpdate(query);
        System.out.println("Record insered");
        
        //Close the statement
        stmt.close();
    }
}
