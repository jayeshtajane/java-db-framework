/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jayesh.myframework.binding.access;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.ArrayList;

import com.jayesh.myframework.configuration.database.access.AccessDatabaseData;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 *
 * @author Jayesh
 */
final public class Access {
    public Object get(Class c,int id) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException, NoSuchMethodException, IllegalArgumentException, InvocationTargetException
    {
        ArrayList row;
        ArrayList colName;
        ArrayList colValues;
        Field field,field1;
        String primaryKeyColumn=null;
        int primaryKeyCount=0;
        AccessDatabaseData db=new AccessDatabaseData();
        Object obj=null;
        
        String tablename = c.getName();
        int IndexOf = tablename.indexOf(".");
        System.out.println("Index of= "+IndexOf);
        //Removing the charactrs to the dot(.)
        tablename = tablename.substring(IndexOf+1);
        int i=0;
        //Find another dot(.) AND removing characters to the dot
        while((IndexOf=tablename.indexOf("."))!=-1) {
            tablename = tablename.substring(IndexOf+1);
            i++;
        }  
        System.out.println("Table name= "+tablename);
        
        Constructor<?> constructor = c.getDeclaredConstructor();
        constructor.setAccessible(true);//ABRACADABRA!

        obj = constructor.newInstance();
        
        //Class<?> forName = Class.forName("test."+tablename);
        System.out.println("Clas Class= "+obj);
        //obj=c.newInstance();
        Field[] declaredFields = c.getDeclaredFields();
        Field.setAccessible(declaredFields, true);
        
        try
        {
            Field fields[]=c.getDeclaredFields();
            Field.setAccessible(fields, true);
            System.out.println("length= "+fields.length);
            for(i=0;i<fields.length;i++)
            {
                field1=fields[i];
                Annotation annotations[]=field1.getAnnotations(); 
                int annotationSize=annotations.length;
                System.out.println("Field in try1= "+field1);
                for(int j=0;j<annotationSize;j++)
                {
                    Annotation annotation = annotations[j];
                    Class<? extends Annotation> annotationType = annotation.annotationType();
                    String name = annotationType.getName();
                    System.out.println("Annotation name= "+name);
                    if("myframework.Annotation.table_annotation.PrimaryKey".equals(name))
                    {
                        System.out.println("In try if");
                        primaryKeyColumn=field1.getName();
                        primaryKeyCount++;
                    }
                }
            }
        }
        catch(SecurityException e)
        {
            e.printStackTrace();
        }
        System.out.println("OK");
        if(primaryKeyCount!=1)
        {
            return null;
        }
        System.out.println("OK OK");
        
        row=db.getTableData(tablename,id,primaryKeyColumn);
        
        if(row==null)
        {
            return null;
        }
        
        System.out.println("OK OK OK");
        
        colName=(ArrayList)row.get(0);
        colValues=(ArrayList)row.get(2);
        System.out.println("ColNames= "+colName);
        
        System.out.println("Table Name= "+c.getName());
        
        System.out.println("Primary key= "+primaryKeyColumn);
        
        Field.setAccessible(declaredFields, true); 
        int rowSize=row.size();
        int colNameSize=colName.size();
        for(i=0;i<rowSize;i++)
        {            
            for(int j=0;j<colNameSize;j++)
            {
                field=declaredFields[j];
                String fieldName=field.getName();
                
                System.out.println("in");
                String column =(String) colName.get(j);

                if(fieldName.equals(column))
                {
                    Class<?> type = field.getType();
                    String typeName = type.getTypeName();
                    System.out.println("Type name jayesh= "+typeName);
                    
                    if(typeName.equals("java.lang.String"))
                    {
                        System.out.println("In String");
                        field.set(obj, colValues.get(j)); 
                    }
                    
                    if(typeName.equals("int"))
                    {
                        System.out.println("In Int");
                        field.set(obj, Integer.parseInt((String)colValues.get(j))); 
                    }
                    
                    if(typeName.equals("double"))
                    {
                        System.out.println("In Double");
                        field.set(obj, Double.parseDouble((String)colValues.get(j))); 
                    }
                }
            }
        }
        return obj;
    }
}
