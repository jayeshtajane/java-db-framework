/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jayesh.myframework.binding.saving;

import com.jayesh.myframework.binding.Bind;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import com.jayesh.myframework.configuration.ConfigureFactory;


/**
 *
 * @author Jayesh
 */
public class SaveObject <T> {
    ConfigureFactory config;
    String tableName;
    Bind bind=null;
    
    public SaveObject(Bind b,ConfigureFactory cf, String table) throws ClassNotFoundException, SQLException
    {
        bind=b;
        config=cf;
        tableName=table;
    }
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        
    }

    /**
     * 
     * @return 
     */
    public int save() throws ClassNotFoundException, SQLException {
        System.out.println("Table name "+tableName);
        
        /**
         * @param className --> As a table name
         * To saving the data to call savaData() and passing the className as a table name
         */
        this.savaData(tableName);
        return 0;
    }
    
    final private void savaData(String tableName) throws ClassNotFoundException, SQLException {
        Connection con=config.getConnectionObject();
        Statement stmt;
        try
        {
            System.out.println();
            /**
             * This condition is check the you connected or not connected to the database
             * using this.con
             */
            
            if(con==null) {
                System.out.println("You are not connected to database 123");
                return;
            }
            
            /**
             * This statement gives you the ArrayList of the column name and values from the Configuration class
             */
            ArrayList colName=bind.getColsName();
            ArrayList value=bind.getValues();

            //numCols store the how many column are exist in ArrayList of colName
            int numCols=colName.size();
            
            /**This is use for creating order of column name and values for creating the query -->
             * e.g. After this for loop if you printing the cols variable it should print rno,sname,marks something in this format
             * And if you printing the val variable it should print 101,'Jayesh',60.30 something in this format
             */
            String cols="";
            String val="";
            for(int i=0;i<numCols;i++)
            {
                //Append one by on column
                cols=cols+colName.get(i);
       
                //This condition check the given value is String or not
                //if it is String then value wrap in single quotation
                if(value.get(i).getClass().getName()=="java.lang.String")
                    val=val+"'"+value.get(i)+"'";
                else
                    val=val+value.get(i);
                
                //Its check it is last variable or not
                //if it is last variable then comma is not append tho the string otherwise comma append to the string
                if(i!=numCols-1)
                {
                    cols=cols+",";
                    val=val+",";
                }
                System.out.println(cols);
            }
            
            
            
            //Creating the insert Query
            String query="insert into "+tableName+"("+cols+")values("+val+")";
            System.out.println("Query= "+query);
            //Creating the statement
            stmt=con.createStatement();
            //Executing the query
            stmt.executeUpdate(query);
            System.out.println("Record insered");
            //Close the statement
            stmt.close();
            //Close the connection
        }
        catch(Exception e)
        {
                System.out.println(e);
        }
    } 
    
    public Bind getBinbing()
    {
        return bind;
    }
}

