/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jayesh.myframework.configuration.database.query;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Jayesh
 */
public class InsertQuery {
    public String getInsertQuery(String tableName,ArrayList colName,ArrayList colValue) throws SQLException
    {        
        String a="insert into "+tableName+"(";
        String b="(";
        String q=null;
        int i=colName.size();
        int j=colValue.size();
        if(i==j)
        {
            for(i=0;i<j;i++)
            {
                a=a+colName.get(i);
                
                if(colValue.get(i).getClass().getName()=="java.lang.String")
                    b=b+"'"+colValue.get(i)+"'";
                else
                    b=b+colValue.get(i);
                
                if(i!=j-1)
                {
                    a=a+",";
                    b=b+",";
                }
            }
            q=a+") values"+b+")";
        }
        return q;
    }
}
