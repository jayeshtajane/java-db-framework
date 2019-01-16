/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jayesh.myframework.configuration.database.store.update;

import com.jayesh.myframework.binding.analize.Analizing;
import com.jayesh.myframework.configuration.database.update.UpdateDatabaseData;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jayesh
 */
public class Update 
{
    public void updateRow(Object obj, int id)
    {
        synchronized(obj)
        {
            Class objClass=obj.getClass();
            int analize = Analizing.analize(obj);
            if(analize!=0)
            {
                return;
            }
        }
        Class<?> c;
        Field fields[];
        Field field1;
        String className;
        ArrayList field;
        ArrayList value;
        int autoIncr=0;
        boolean embeded;
        String primaryKeyColumn=null;
                
        c = obj.getClass(); 
        
        fields=c.getDeclaredFields();
        
        Field.setAccessible(fields, true); 
        
        field1=fields[1];
        
        className=c.getName();
        int IndexOf = className.indexOf(".");
        System.out.println("Index of= "+IndexOf);
        //Removing the charactrs to the dot(.)
        className = className.substring(IndexOf+1);
        int i=0;
        //Find another dot(.) AND removing characters to the dot
        while((IndexOf=className.indexOf("."))!=-1) {
            className = className.substring(IndexOf+1);
            i++;
        }  
        System.out.println("Class name= "+className);
        
        field=new ArrayList();
        value=new ArrayList();
        
        Class string=String.class; 
        Class sqlDate=java.sql.Date.class;
        Class javaDate=java.util.Date.class;
        
        for(i=0;i<fields.length;i++)
        {
            embeded=false;
            autoIncr=0;
            field1=fields[i];
            Annotation[] annotations = field1.getAnnotations();
            System.out.println("num annotation= "+annotations.length);
            for(int j=0;j<annotations.length;j++)
            {
                Annotation annotation = annotations[j];
                Class<? extends Annotation> annotationType = annotation.annotationType();
                String annotationTypename = annotationType.getName();
                System.out.println("Annotation name= "+annotationTypename);
                if("myframework.Annotation.table_annotation.AutoIncr".equals(annotationTypename))
                {
                    System.out.println("Auto increment");
                    autoIncr++;
                }
                
                if("myframework.Annotation.table_annotation.relationship.OneToOne".equals(annotationTypename))
                {
                    System.out.println("In One To One");
                }
                
                if("myframework.Annotation.table_annotation.relationship.OneToMany".equals(annotationTypename))
                {
                    System.out.println("In One To Many");
                }
                
                if("myframework.Annotation.table_annotation.relationship.ManyToOne".equals(annotationTypename))
                {
                    System.out.println("In Many To One");
                }
                
                if("myframework.Annotation.table_annotation.relationship.ManyToMany".equals(annotationTypename))
                {
                    System.out.println("In Many To Many");
                }
                
                if("myframework.Annotation.table_annotation.PrimaryKey".equals(annotationTypename))
                {
                    System.out.println("In try if");
                    primaryKeyColumn=field1.getName();
                }
                
                if("myframework.Annotation.table_annotation.Embedable".equals(annotationTypename))
                {
                    System.out.println("Embadable");
                    Class<?> embededClass = field1.getType();
                    System.out.println(embededClass);
                    Field[] embededFields = embededClass.getDeclaredFields();
                    Constructor<?> constructor=null;
                    try {
                        constructor = embededClass.getDeclaredConstructor();
                    } catch (NoSuchMethodException ex) {
                        Logger.getLogger(Update.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (SecurityException ex) {
                        Logger.getLogger(Update.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                    constructor.setAccessible(true);
                    Object embededObject=null;
                    
                    try {
                        embededObject = constructor.newInstance();
                    } catch (InstantiationException ex) {
                        Logger.getLogger(Update.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IllegalAccessException ex) {
                        Logger.getLogger(Update.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IllegalArgumentException ex) {
                        Logger.getLogger(Update.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (InvocationTargetException ex) {
                        Logger.getLogger(Update.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    Field.setAccessible(embededFields, true); 
                    System.out.println("num embedable fields= "+embededFields.length);
                    for(int k=0;k<embededFields.length;k++)
                    {
                        
                        Field embededField=embededFields[k];
                        Class<?> embededFieldType = embededField.getType();
                        
//                        Object embededObject;
//                        embededObject = embededFiel;
//                        System.out.println("TYPE== "+embededFieldType);
                        if(embededFieldType==string)
                        {
                            System.out.println("In Embeded String "+embededField.getName());                 
                            field.add(embededField.getName());
                            try {
                                value.add(embededField.get(embededObject));
                            } catch (IllegalArgumentException ex) {
                                Logger.getLogger(Update.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (IllegalAccessException ex) {
                                Logger.getLogger(Update.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        if(embededFieldType==sqlDate)
                        {
                            System.out.println("In Embeded SQL Date");
                            field.add(embededField.getName());
                            try {
                                value.add(embededField.get(embededObject));
                            } catch (IllegalArgumentException ex) {
                                Logger.getLogger(Update.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (IllegalAccessException ex) {
                                Logger.getLogger(Update.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        if(embededFieldType==javaDate)
                        {
                            System.out.println("In Embeded JAVA Date");

                            java.util.Date now = new java.util.Date();
                            String pattern = "yyyy-MM-dd";
                            SimpleDateFormat formatter = new SimpleDateFormat(pattern);
                            String mysqlDateString = formatter.format(now);
                            System.out.println("Java's Default Date Format: " + now);
                            System.out.println("Mysql's Default Date Format: " + mysqlDateString);

                            field.add(embededField.getName());
                            try {
                                value.add(embededField.get(embededObject));
                            } catch (IllegalArgumentException ex) {
                                Logger.getLogger(Update.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (IllegalAccessException ex) {
                                Logger.getLogger(Update.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        if(embededFieldType.isPrimitive())
                        {
                            String fieldDatatype=embededFieldType.getTypeName();
                            if(fieldDatatype=="int")
                            {
                                try {
                                    System.out.println("In Embeded int");
                                    field.add(embededField.getName());
                                    value.add(embededField.getInt(embededObject));
                                } catch (IllegalArgumentException ex) {
                                    Logger.getLogger(Update.class.getName()).log(Level.SEVERE, null, ex);
                                } catch (IllegalAccessException ex) {
                                    Logger.getLogger(Update.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                            if(fieldDatatype=="double")
                            {
                                try {
                                    System.out.println("In Embeded Double");
                                    field.add(embededField.getName());
                                    value.add(embededField.getDouble(embededObject));
                                } catch (IllegalArgumentException ex) {
                                    Logger.getLogger(Update.class.getName()).log(Level.SEVERE, null, ex);
                                } catch (IllegalAccessException ex) {
                                    Logger.getLogger(Update.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                            if(fieldDatatype=="char")
                            {
                                try {
                                    System.out.println("In Embeded Char");
                                    field.add(embededField.getName());
                                    value.add(embededField.getChar(embededObject));
                                } catch (IllegalArgumentException ex) {
                                    Logger.getLogger(Update.class.getName()).log(Level.SEVERE, null, ex);
                                } catch (IllegalAccessException ex) {
                                    Logger.getLogger(Update.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                            if(fieldDatatype=="float")
                            {
                                try {
                                    System.out.println("In Embeded Char");
                                    field.add(embededField.getName());
                                    value.add(embededField.getFloat(embededObject));
                                } catch (IllegalArgumentException ex) {
                                    Logger.getLogger(Update.class.getName()).log(Level.SEVERE, null, ex);
                                } catch (IllegalAccessException ex) {
                                    Logger.getLogger(Update.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                            if(fieldDatatype=="long")
                            {
                                try {
                                    System.out.println("In Embeded Long Int");
                                    field.add(embededField.getName());
                                    value.add(embededField.getLong(embededObject));
                                } catch (IllegalArgumentException ex) {
                                    Logger.getLogger(Update.class.getName()).log(Level.SEVERE, null, ex);
                                } catch (IllegalAccessException ex) {
                                    Logger.getLogger(Update.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                            if(fieldDatatype=="short")
                            {
                                try {
                                    System.out.println("In Embeded Short");
                                    field.add(embededField.getName());
                                    value.add(embededField.getShort(embededObject));
                                } catch (IllegalArgumentException ex) {
                                    Logger.getLogger(Update.class.getName()).log(Level.SEVERE, null, ex);
                                } catch (IllegalAccessException ex) {
                                    Logger.getLogger(Update.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                        }
                    }
                    embeded=true;
                }
            }
            if(autoIncr==1)
            {
                continue;
            }
            if(embeded)
            {
                continue;
            }
            field1=fields[i];
            Class<?> type = field1.getType();
            System.out.println("TYPE== "+type);
            if(type==string)
            {
                try {
                    System.out.println("In String");
                    field.add(field1.getName());
                    value.add(field1.get(obj));
                } catch (IllegalArgumentException ex) {
                    Logger.getLogger(Update.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalAccessException ex) {
                    Logger.getLogger(Update.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if(type==sqlDate)
            {
                try {
                    System.out.println("In SQL Date");
                    field.add(field1.getName());
                    value.add(field1.get(obj));
                } catch (IllegalArgumentException ex) {
                    Logger.getLogger(Update.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalAccessException ex) {
                    Logger.getLogger(Update.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if(type==javaDate)
            {
                try {
                    System.out.println("In JAVA Date");
                    
                    java.util.Date now = new java.util.Date();
                    String pattern = "yyyy-MM-dd";
                    SimpleDateFormat formatter = new SimpleDateFormat(pattern);
                    String mysqlDateString = formatter.format(now);
                    System.out.println("Java's Default Date Format: " + now);
                    System.out.println("Mysql's Default Date Format: " + mysqlDateString);
                    
                    field.add(field1.getName());
                    value.add(field1.get(obj));
                } catch (IllegalArgumentException ex) {
                    Logger.getLogger(Update.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalAccessException ex) {
                    Logger.getLogger(Update.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if(type.isPrimitive())
            {
                String fieldDatatype=type.getTypeName();
                if(fieldDatatype=="int")
                {
                    try {
                        System.out.println("In int");
                        field.add(field1.getName());
                        value.add(field1.getInt(obj));
                    } catch (IllegalArgumentException ex) {
                        Logger.getLogger(Update.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IllegalAccessException ex) {
                        Logger.getLogger(Update.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                if(fieldDatatype=="double")
                {
                    try {
                        System.out.println("In Double");
                        field.add(field1.getName());
                        value.add(field1.getDouble(obj));
                    } catch (IllegalArgumentException ex) {
                        Logger.getLogger(Update.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IllegalAccessException ex) {
                        Logger.getLogger(Update.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                if(fieldDatatype=="char")
                {
                    try {
                        System.out.println("In Char");
                        field.add(field1.getName());
                        value.add(field1.getChar(obj));
                    } catch (IllegalArgumentException ex) {
                        Logger.getLogger(Update.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IllegalAccessException ex) {
                        Logger.getLogger(Update.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                if(fieldDatatype=="float")
                {
                    try {
                        System.out.println("In Char");
                        field.add(field1.getName());
                        value.add(field1.getFloat(obj));
                    } catch (IllegalArgumentException ex) {
                        Logger.getLogger(Update.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IllegalAccessException ex) {
                        Logger.getLogger(Update.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                if(fieldDatatype=="long")
                {
                    try {
                        System.out.println("In Long Int");
                        field.add(field1.getName());
                        value.add(field1.getLong(obj));
                    } catch (IllegalArgumentException ex) {
                        Logger.getLogger(Update.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IllegalAccessException ex) {
                        Logger.getLogger(Update.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                if(fieldDatatype=="short")
                {
                    try {
                        System.out.println("In Short");
                        field.add(field1.getName());
                        value.add(field1.getShort(obj));
                    } catch (IllegalArgumentException ex) {
                        Logger.getLogger(Update.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IllegalAccessException ex) {
                        Logger.getLogger(Update.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
        System.out.println(field);
        System.out.println(value);
        try {
            //        saveData(className,field,value);
            UpdateDatabaseData.updateData(className, field, value,primaryKeyColumn, id);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Update.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Update.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
