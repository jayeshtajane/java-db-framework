/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jayesh.myframework.configuration.database.query;


/**
 *
 * @author Jayesh
 */
public class DeleteQuery 
{
    public String getDeleteQuery(String tableName,String primaryKey,int id)
    {
        //DELETE FROM `employee` WHERE 
        String query="delete from "+tableName+" where "+primaryKey+"="+id;
        return query;
    }
}
