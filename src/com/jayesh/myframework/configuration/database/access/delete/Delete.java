/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jayesh.myframework.configuration.database.access.delete;

import com.jayesh.myframework.configuration.database.access.DeleteDatabaseData;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Jayesh
 */
public class Delete 
{
    public int deleteRow(Class c,int id) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException, NoSuchMethodException, IllegalArgumentException, InvocationTargetException
    {
        ArrayList row;
        ArrayList colName;
        ArrayList colValues;
        Field field,field1;
        String primaryKeyColumn=null;
        int primaryKeyCount=0;
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
            return 0;
        }
        System.out.println("OK OK");
        
        DeleteDatabaseData.deleteData(tablename, primaryKeyColumn, id);
        
        return 1;
    }
}
