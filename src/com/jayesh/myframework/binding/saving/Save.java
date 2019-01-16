/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jayesh.myframework.binding.saving;

import com.jayesh.myframework.binding.analize.Analizing;
import com.jayesh.myframework.configuration.ConfigureFactory;
import com.jayesh.myframework.configuration.database.query.InsertQuery;
import com.jayesh.myframework.configuration.database.store.StoreDatabaseData;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 *
 * @author Jayesh
 */
final public class Save {
    
    public void save(Object obj) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, NoSuchMethodException, IllegalArgumentException, InvocationTargetException
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
                                
                if("myframework.Annotation.table_annotation.Embedable".equals(annotationTypename))
                {
                    System.out.println("Embadable");
                    Class<?> embededClass = field1.getType();
                    System.out.println(embededClass);
                    Field[] embededFields = embededClass.getDeclaredFields();
                    Constructor<?> constructor = embededClass.getDeclaredConstructor();
                    constructor.setAccessible(true);
                    Object embededObject = constructor.newInstance();
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
                            value.add(embededField.get(embededObject));
                        }
                        if(embededFieldType==sqlDate)
                        {
                            System.out.println("In Embeded SQL Date");
                            field.add(embededField.getName());
                            value.add(embededField.get(embededObject));
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
                            value.add(embededField.get(embededObject));
                        }
                        if(embededFieldType.isPrimitive())
                        {
                            String fieldDatatype=embededFieldType.getTypeName();
                            if(fieldDatatype=="int")
                            {
                                System.out.println("In Embeded int");
                                field.add(embededField.getName());
                                value.add(embededField.getInt(embededObject));
                            }
                            if(fieldDatatype=="double")
                            {
                                System.out.println("In Embeded Double");
                                field.add(embededField.getName());
                                value.add(embededField.getDouble(embededObject));
                            }
                            if(fieldDatatype=="char")
                            {
                                System.out.println("In Embeded Char");
                                field.add(embededField.getName());
                                value.add(embededField.getChar(embededObject));
                            }
                            if(fieldDatatype=="float")
                            {
                                System.out.println("In Embeded Char");
                                field.add(embededField.getName());
                                value.add(embededField.getFloat(embededObject));
                            }
                            if(fieldDatatype=="long")
                            {
                                System.out.println("In Embeded Long Int");
                                field.add(embededField.getName());
                                value.add(embededField.getLong(embededObject));
                            }
                            if(fieldDatatype=="short")
                            {
                                System.out.println("In Embeded Short");
                                field.add(embededField.getName());
                                value.add(embededField.getShort(embededObject));
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
                System.out.println("In String");                 
                field.add(field1.getName());
                value.add(field1.get(obj));
            }
            if(type==sqlDate)
            {
                System.out.println("In SQL Date");
                field.add(field1.getName());
                value.add(field1.get(obj));
            }
            if(type==javaDate)
            {
                System.out.println("In JAVA Date");

                java.util.Date now = new java.util.Date();
                String pattern = "yyyy-MM-dd";
                SimpleDateFormat formatter = new SimpleDateFormat(pattern);
                String mysqlDateString = formatter.format(now);
                System.out.println("Java's Default Date Format: " + now);
                System.out.println("Mysql's Default Date Format: " + mysqlDateString);

                field.add(field1.getName());
                value.add(field1.get(obj));
            }
            if(type.isPrimitive())
            {
                String fieldDatatype=type.getTypeName();
                if(fieldDatatype=="int")
                {
                    System.out.println("In int");
                    field.add(field1.getName());
                    value.add(field1.getInt(obj));
                }
                if(fieldDatatype=="double")
                {
                    System.out.println("In Double");
                    field.add(field1.getName());
                    value.add(field1.getDouble(obj));
                }
                if(fieldDatatype=="char")
                {
                    System.out.println("In Char");
                    field.add(field1.getName());
                    value.add(field1.getChar(obj));
                }
                if(fieldDatatype=="float")
                {
                    System.out.println("In Char");
                    field.add(field1.getName());
                    value.add(field1.getFloat(obj));
                }
                if(fieldDatatype=="long")
                {
                    System.out.println("In Long Int");
                    field.add(field1.getName());
                    value.add(field1.getLong(obj));
                }
                if(fieldDatatype=="short")
                {
                    System.out.println("In Short");
                    field.add(field1.getName());
                    value.add(field1.getShort(obj));
                }
            }
        }
        System.out.println(field);
        System.out.println(value);
//        saveData(className,field,value);
        StoreDatabaseData.saveData(className, field, value);
    }
    
    void saveData(String tableName,ArrayList colName,ArrayList colValue) throws ClassNotFoundException, SQLException
    {
        Connection con=ConfigureFactory.getConnectionObject();
        Statement stmt;
        
//        Class.forName("com.mysql.jdbc.Driver");
//        System.out.println("Driver loaded");
//        con=DriverManager.getConnection("jdbc:mysql://localhost:3306/test","root","");
//        System.out.println("Connected");

        //Creating the insert Query
        InsertQuery q=new InsertQuery();
        String query=q.getInsertQuery(tableName,colName,colValue);
        System.out.println("Query= "+query);
        
        //Creating the statement
        stmt=con.createStatement();
        
        //Executing the query
        stmt.executeUpdate(query);
        System.out.println("Record insered");
        
        //Close the statement
        stmt.close();
    }
    
    ArrayList<String> getTablesList(Connection conn)
            throws SQLException {

        ArrayList<String> listofTable = new ArrayList<String>();

        DatabaseMetaData md = conn.getMetaData();

        ResultSet rs = md.getTables(null, null, "%", null);

        while (rs.next()) {
            if (rs.getString(4).equalsIgnoreCase("TABLE")) {
                listofTable.add(rs.getString(3));
            }
        }
        System.out.println("Tables= "+listofTable);

        return listofTable;
    }
}
