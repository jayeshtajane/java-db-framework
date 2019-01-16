/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jayesh.myframework.binding;

import java.util.ArrayList;

/**
 *
 * @author Jayesh
 * @param <T>
 */
public class Bind {
    
    //ArrayList for storing the column name
    private ArrayList colName=new ArrayList();
    //ArrayList for storing the values
    private ArrayList value=new ArrayList();
    
    public <T> void bindValAndCol(String colNamel,T valuel) 
    {
        colName.add(colNamel);
        value.add(valuel);
    }
    
    public Bind()
    {
        
    }
    
    public ArrayList getColsName() 
    {
        return colName;
    }

    public ArrayList getValues()
    {
        return value;
    }
}
