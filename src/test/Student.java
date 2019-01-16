/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import com.jayesh.myframework.binding.Bind;
import com.jayesh.myframework.binding.access.Access;
import com.jayesh.myframework.binding.access.AccessData;
import com.jayesh.myframework.binding.saving.Save;
import com.jayesh.myframework.binding.saving.SaveObject;
import com.jayesh.myframework.configuration.ConfigureFactory;
import com.jayesh.myframework.configuration.database.access.delete.Delete;
import com.jayesh.myframework.configuration.database.store.update.Update;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import com.jayesh.myframework.Annotation.table_annotation.AutoIncr;
import com.jayesh.myframework.Annotation.table_annotation.Embedable;
import com.jayesh.myframework.Annotation.table_annotation.PrimaryKey;
import com.jayesh.myframework.configuration.TableConfiguration;

/**
 *
 * @author Jayesh
 */

class Employee
{
    @AutoIncr
    @PrimaryKey
    int eid=2;
    String ename="Ramesh";
    double salary=27000.00;

    @Override
    public String toString() {
        return "Employee{" + "eid=" + eid + ", ename=" + ename + ", salary=" + salary + '}';
    }
}

class Student 
{
    @Embedable
    Book bname;
    double marks=75.25;
    @PrimaryKey
    int rno=2;

    @Override
    public String toString() {
        return "Student{" + "rno=" + rno + ", sname=" + bname + ", marks=" + marks + '}';
    }
}
class Book
{
    int bno=1;
    String bname="Servlet & JSP";
    double price=850;
    int stud_id=1;
    
    @Override
    public String toString() {
        return "Name{" + "BookName=" + bname + ", price=" + price + '}';
    }
    
}

class Test 
{
    public static void main(String args[]) throws ClassNotFoundException, SQLException, InstantiationException, 
            IllegalAccessException, NoSuchMethodException, IllegalArgumentException, InvocationTargetException
    {
        Test t=new Test();
        t.get();
        
    }
    
    void del() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException, NoSuchMethodException, IllegalArgumentException, InvocationTargetException
    {
        ConfigureFactory cf=new ConfigureFactory("test");
        Delete delete=new Delete();
        Employee s=new Employee();
//        Register_Persons s=(Register_Persons) access.get(Register_Persons.class, 1);
        delete.deleteRow(Employee.class, 2);
    }
    
    void update1() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException, NoSuchMethodException, IllegalArgumentException, InvocationTargetException
    {
        ConfigureFactory cf=new ConfigureFactory("test");
        Update update=new Update();
        Employee s=new Employee();
//        Register_Persons s=(Register_Persons) access.get(Register_Persons.class, 1);
        update.updateRow(s, 1);
//        A s=(A)access.test();
        System.out.println("In get "+s);
    }
    
    void get() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException, 
            NoSuchMethodException, IllegalArgumentException, InvocationTargetException
    {
        ConfigureFactory cf=new ConfigureFactory("demo3");
        //Access access=new Access();
//        Register_Persons s=(Register_Persons) access.get(Register_Persons.class, 1);
        //Employee s =(Employee) access.get(Employee.class, 11);
//        A s=(A)access.test();
        //System.out.println("In get "+s);
        
        TableConfiguration.checkAndCreateTable(Employee.class);
    }
    
    void save2() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException, NoSuchMethodException, IllegalArgumentException, InvocationTargetException
    {
        ConfigureFactory cf=new ConfigureFactory("test");
        Save save=new Save();
        Employee s=new Employee();
//        Register_Persons s=new Register_Persons();
        save.save(s);
    }
    
    void save1() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException
    {
        Student s=new Student();
        ConfigureFactory cf=new ConfigureFactory("test");
        Bind b=new Bind();
        AccessData access=new AccessData();
//        b.bindValAndCol("rno", s.rno);
//        b.bindValAndCol("sname", s.sname);
//        b.bindValAndCol("marks", s.marks);
        cf.setBinding(b);
        
        SaveObject so=new SaveObject(b,cf,"student");
        so.save();

        s=(Student)access.get(Student.class);
        
        System.out.println(s);
    }
}