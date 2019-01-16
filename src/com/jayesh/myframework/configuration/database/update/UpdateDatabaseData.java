/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jayesh.myframework.configuration.database.update;

import com.jayesh.myframework.configuration.ConfigureFactory;
import com.jayesh.myframework.configuration.database.query.UpdateQuery;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author Jayesh
 */
public class UpdateDatabaseData {
    public static void updateData(String tableName,ArrayList colName,ArrayList colValue,String pk,int id) throws ClassNotFoundException, SQLException
    {
        Connection con=ConfigureFactory.getConnectionObject();
        Statement stmt;
        UpdateQuery iq=new UpdateQuery();

        //Creating the insert Query
        String query=iq.getUpdateQuery(tableName,colName,colValue,pk,id);
        System.out.println("Query= "+query);
        
        //Creating the statement
        stmt=con.createStatement();
        
        //Executing the query
        stmt.executeUpdate(query);
        System.out.println("Record Updated");
        
        //Close the statement
        stmt.close();
    }
}
