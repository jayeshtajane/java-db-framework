/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jayesh.myframework.configuration.database.query;

import java.util.ArrayList;

/**
 *
 * @author Jayesh
 */
public class UpdateQuery 
{
    public String getUpdateQuery(String tableName,ArrayList colName,ArrayList colValue,String primaryKey,int id)
    {
        //"update room set status='free',type=?,price=? where rno=?"
        //UPDATE `book` SET `bid`=[value-1],`bname`=[value-2],`price`=[value-3],`stud_id`=[value-4] WHERE 1
        String query="update "+tableName+" set ";
        int i=colName.size();
        int j=colValue.size();
        if(i==j)
        {
            for(i=0;i<j;i++)
            {
                query=query+colName.get(i)+"=";
                if(colValue.get(i).getClass().getName()=="java.lang.String")
                    query=query+"'"+colValue.get(i)+"'";
                else
                    query=query+colValue.get(i);
                
                if(i!=j-1)
                {
                    query=query+",";
                }
            }
            query=query+" where "+primaryKey+"="+id;
        }
        else
        {
            System.out.println("Something wrong");
            return null;
        }
        
//        String b=null;
//        String q=null;
//        int i=colName.size();
//        int j=colValue.size();
//        if(i==j)
//        {
//            for(i=0;i<j;i++)
//            {
//                a=a+colName.get(i);
//                
//                if(colValue.get(i).getClass().getName()=="java.lang.String")
//                    b=b+"'"+colValue.get(i)+"'";
//                else
//                    b=b+colValue.get(i);
//                
//                if(i!=j-1)
//                {
//                    a=a+",";
//                    b=b+",";
//                }
//            }
//            q=a+") values"+b+")";
//        }
        return query;
    }
}
