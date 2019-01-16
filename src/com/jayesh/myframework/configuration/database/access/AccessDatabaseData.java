/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jayesh.myframework.configuration.database.access;

import com.jayesh.myframework.configuration.ConfigureFactory;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author Jayesh
 */
public class AccessDatabaseData {
    public ArrayList<Object> getTableData(String tablename, int id, String pkColumn) throws ClassNotFoundException, SQLException
    {
        Connection con;
        Statement stmt;
        ArrayList colNames=new ArrayList();
        ArrayList colValues=new ArrayList();
        ArrayList colType=new ArrayList();
        ArrayList row=new ArrayList();
        ResultSet rs=null;
        PreparedStatement pstmt;
        
        con=ConfigureFactory.getConnectionObject();
        System.out.println("In DB FILE "+con);

        //Creating the insert Query
        String query="select * from "+tablename+" where "+pkColumn+"="+id;
        
        System.out.println("Query= "+query);
        //Creating the statement
        
        stmt=con.createStatement();
        //Executing the query
        
        pstmt=con.prepareStatement("select * from "+tablename+" where "+pkColumn+"=?");
        pstmt.setInt(1,id);
        
        rs= pstmt.executeQuery(query);
        System.out.println("getting from db");
        //Close the statement
        
        //stmt.close();
//        //Close the connection
        if(rs.next())
        {
            ResultSetMetaData metaData = rs.getMetaData();
            String colName=metaData.getColumnLabel(1);
            System.out.println("Column name= "+colName);
            System.out.println("primary key= "+rs.getString(1));

            int numCols=metaData.getColumnCount();

            for(int i=1;i<=numCols;i++)
            {
//                if(i==1)
//                    rs.next();
                colNames.add(metaData.getColumnLabel(i));
                colType.add(metaData.getColumnClassName(i));
                colValues.add(rs.getString(i));
            }

            row.add(colNames);
            row.add(colType);
            row.add(colValues);

            System.out.println("Column names= "+colNames);
            System.out.println("Column type= "+colType);
            System.out.println("Column value= "+colValues);

            System.out.println("Row= "+row);

            return row;
        }
        return null;
    }
    
    public static <T> ResultSet getTableData(String tableName,ConfigureFactory configFactory,int idValue,String idName) throws ClassNotFoundException, SQLException
    {
        Connection con=configFactory.getConnectionObject();
        Statement stmt;
        ResultSet rs=null;
        String query;

        query="select * from "+tableName+" where "+idName+" ="+idValue;
        System.out.println("Query= "+query);
        
        stmt=con.createStatement();
        rs=stmt.executeQuery(query);

        return rs;
    }
}
