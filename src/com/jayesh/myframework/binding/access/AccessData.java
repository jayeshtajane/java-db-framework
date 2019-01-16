/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jayesh.myframework.binding.access;

import java.lang.reflect.Field;

/**
 *
 * @author Jayesh
 */
public class AccessData {
    public Object get(Class c) throws InstantiationException, IllegalAccessException
    {
        //c=(Class) c.newInstance();
        Object newInstance = c.newInstance();
        System.out.println("first= "+newInstance);
        
        //Geting the fiels of a class
        Field[] declaredFields = c.getDeclaredFields();
        System.out.println("fields="+declaredFields[1]);
        
        declaredFields[1].setAccessible(true);

        Field a=declaredFields[0];
        a.set(newInstance, "Jayesh");
        Field b=declaredFields[1];
        b.setInt(newInstance, 111); 
        
        Class<?> type = b.getType(); System.out.println("Type of this= "+type);
        Class<?> declaringClass = a.getDeclaringClass();
        String name1 = declaringClass.getName();
        System.out.println("Declare class= "+declaringClass);
        System.out.println("Name of entity= "+name1);
        String typeName = type.getTypeName();
        System.out.println("Type name= "+typeName);
        Class string=String.class;
        for(int i=0;i<declaredFields.length;i++)
        {
            a=declaredFields[i];
            type=a.getType();
            if(type==string)
            {
                System.out.println("In String");
                a.set(newInstance, "Pranoti");
            }
            if(type.isPrimitive())
            {
                String typeName1 = type.getTypeName();
                if(typeName1=="int")
                {
                    System.out.println("In int");
                    a.setInt(newInstance, 101);
                }
                if(typeName1=="double")
                {
                    System.out.println("In double");
                }
            }
        }
        
        System.out.println("modified= "+newInstance);
        return newInstance;
    }
}