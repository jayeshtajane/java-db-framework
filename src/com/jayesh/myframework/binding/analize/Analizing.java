/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jayesh.myframework.binding.analize;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 *
 * @author Jayesh
 */
public class Analizing 
{
    public static int analize(Object obj)
    {
        Class<?> analiseClass = obj.getClass();
        int numPrimaryKey = 0;
        System.out.println("Analize "+analiseClass);
        int numAutoIncr= 0;
        
        Field field,field1;
        int i,primaryKeyCount=0;
        String primaryKeyColumn;
        Field[] declaredFields = analiseClass.getDeclaredFields();
        Field.setAccessible(declaredFields, true);
        
        try
        {
            Field fields[]=analiseClass.getDeclaredFields();
            Field.setAccessible(fields, true);
            System.out.println("length in try in analize= "+fields.length);
            for(i=0;i<fields.length;i++)
            {
                field1=fields[i];
                Annotation annotations[]=field1.getAnnotations(); 
                int annotationSize=annotations.length;
                System.out.println("Field in try in analize= "+field1);
                System.out.println("Num Annotation in try in analize= "+annotationSize);
                for(int j=0;j<annotationSize;j++)
                {
                    Annotation annotation = annotations[j];
                    Class<? extends Annotation> annotationType = annotation.annotationType();
                    String name = annotationType.getName();
                    System.out.println("Annotation name in try in analize= "+name);
                    if("myframework.Annotation.table_annotation.PrimaryKey".equals(name))
                    {
                        System.out.println("In try if primary key in analize");
                        primaryKeyColumn=field1.getName();
                        numPrimaryKey++;
                    }
                    
                    if("myframework.Annotation.table_annotation.AutoIncr".equals(name))
                    {
                        System.out.println("In try if auto increment in analize");
                        primaryKeyColumn=field1.getName();
                        numAutoIncr++;
                    }
//                    
//                    if("myframework.Annotation.table_annotation.Date".equals(name))
//                    {
//                        System.out.println("In try if Date key");
//                        primaryKeyColumn=field1.getName();
//                    }
                }
            }
        }
        catch(SecurityException e)
        {
            e.printStackTrace();
        }
        System.out.println("num primary key in analize= "+numPrimaryKey);
        if(numPrimaryKey==0)
        {
            System.out.println("No primary key givan in the class");
            return 1;
        }
        if(numPrimaryKey>1)
        {
            System.out.println("Many primary key given in the class");
            return 1;
        }
        if(numAutoIncr>1)
        {
            System.out.println("Many auto increment given in the class");
            return 1;
        }
        System.out.println("num auto increment in analize= "+numAutoIncr);
        return 0;
    }
    
    public static int findNumAutoIncr(Class c)
    {
        Field field,field1;
        int i,primaryKeyCount=0;
        String primaryKeyColumn;
        Field[] declaredFields = c.getDeclaredFields();
        Field.setAccessible(declaredFields, true);
        
        try
        {
            Field fields[]=c.getDeclaredFields();
            Field.setAccessible(fields, true);
            System.out.println("length in try in analize= "+fields.length);
            for(i=0;i<fields.length;i++)
            {
                field1=fields[i];
                Annotation annotations[]=field1.getAnnotations(); 
                int annotationSize=annotations.length;
                System.out.println("Field in try in analize= "+field1);
                System.out.println("Num Annotation in try in analize= "+annotationSize);
                for(int j=0;j<annotationSize;j++)
                {
                    Annotation annotation = annotations[j];
                    Class<? extends Annotation> annotationType = annotation.annotationType();
                    String name = annotationType.getName();
                    System.out.println("Annotation name in try in analize= "+name);
                    if("myframework.Annotation.table_annotation.PrimaryKey".equals(name))
                    {
                        System.out.println("In try if primary key in analize");
                        primaryKeyColumn=field1.getName();
                        primaryKeyCount++;
                    }
//                    
//                    if("myframework.Annotation.table_annotation.Date".equals(name))
//                    {
//                        System.out.println("In try if Date key");
//                        primaryKeyColumn=field1.getName();
//                    }
                }
            }
        }
        catch(SecurityException e)
        {
            e.printStackTrace();
        }
//        System.out.println("OK");
//        if(primaryKeyCount!=1)
//        {
//            return 1;
//        }
        System.out.println("OK OK in analize");
        return primaryKeyCount;
    }
}
