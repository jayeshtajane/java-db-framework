/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jayesh.myframework.configuration.database.access;

import com.jayesh.myframework.configuration.ConfigureFactory;
import com.jayesh.myframework.configuration.database.query.DeleteQuery;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Jayesh
 */
public class DeleteDatabaseData 
{
    public static void deleteData(String tableName,String pk,int id) throws ClassNotFoundException, SQLException
    {
        Connection con=ConfigureFactory.getConnectionObject();
        Statement stmt;
        DeleteQuery iq=new DeleteQuery();

        //Creating the insert Query
        String query=iq.getDeleteQuery(tableName, pk, id);
        System.out.println("Query= "+query);
        
        //Creating the statement
        stmt=con.createStatement();
        
        //Executing the query
        int executeUpdate = stmt.executeUpdate(query);
        System.out.println(executeUpdate+" Record Deleted");
        
        //Close the statement
        stmt.close();
    }
}
