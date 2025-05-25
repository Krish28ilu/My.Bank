/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.bank_project;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;

/**
 *
 * @author Krish
 */
public class DataBase {
       static  String url  = "jdbc:mysql://localhost:3306/my_bank";  
    
    
    static void saveCustomer(int accountnumber, String fullName, String fatherName, String motherName, String phone, String email, String aadharNumber, String panNumber, String address, String gender, String dob, String password) throws Exception {
       
        Class.forName("com.mysql.jdbc.Driver");
        Connection conn = DriverManager.getConnection( 
                url, "root", "root"); 
            
        Statement stmt = conn.createStatement(); 
           
        stmt.executeUpdate("insert into customers values ('"+accountnumber+"','"+fullName+"','"+fatherName+"','"+motherName+"','"+phone+"','"+email+"','"+aadharNumber+"','"+panNumber+"','"+address+"','"+gender+"','"+dob+"','"+password+"', 0)");
        
  
            // closing connection 
            conn.close(); 
    }
    
    static Boolean getCustomer(int accountnumber, String password) throws Exception{
       Class.forName("com.mysql.jdbc.Driver");
        Connection conn = DriverManager.getConnection( 
                url, "root", "root"); 
            
        Statement stmt = conn.createStatement(); 
        
        ResultSet rs = stmt.executeQuery("Select * from customers where accountnumber='"+accountnumber+"' AND password='"+password+"'");
        return rs.next();
        
        
        
    }
    
    static void addMoney(int accountnumber, String password, double amount) throws Exception {
    Class.forName("com.mysql.jdbc.Driver");
    Connection conn = DriverManager.getConnection(url, "root", "root");

    Statement stmt = conn.createStatement();
    // Verify user credentials
    ResultSet rs = stmt.executeQuery("SELECT * FROM customers WHERE accountnumber='" + accountnumber + "' AND password='" + password + "'");
    
    if (rs.next()) {
        // Add money to the existing balance
        stmt.executeUpdate("UPDATE customers SET balance = balance + " + amount + " WHERE accountnumber='" + accountnumber + "'");
        
        JOptionPane.showMessageDialog(new Add_money(), "Money added successfully");
    } else {
        JOptionPane.showMessageDialog( new Add_money(), "Invalid account number or password.");
        System.out.println("Invalid account number or password.");
    }

    conn.close();
}

    
    static void withdrawMoney(int accountnumber, String password, double amount) throws Exception {
    Class.forName("com.mysql.jdbc.Driver");
    Connection conn = DriverManager.getConnection(url, "root", "root");

    Statement stmt = conn.createStatement();
   
    ResultSet rs = stmt.executeQuery("SELECT balance FROM customers WHERE accountnumber='" + accountnumber + "' AND password='" + password + "'");

    if (rs.next()) {
        double currentBalance = rs.getDouble("balance");
        if (currentBalance >= amount) {
            stmt.executeUpdate("UPDATE customers SET balance = balance - " + amount + " WHERE accountnumber='" + accountnumber + "'");
            JOptionPane.showMessageDialog(new Withdraw_money(), "Money Withdrawn successfully");
            
        } else {
            System.out.println("Insufficient balance.");
            JOptionPane.showMessageDialog(new Withdraw_money(), "Insufficient balance");
        }
    } else {
        JOptionPane.showMessageDialog( new Withdraw_money(), "Invalid account number or password.");
        System.out.println("Invalid account number or password.");
    }

    conn.close();
}

    
    static double viewBalance(int accountnumber, String password) throws Exception {
    Class.forName("com.mysql.jdbc.Driver");
    Connection conn = DriverManager.getConnection(url, "root", "root");

    Statement stmt = conn.createStatement();
    ResultSet rs = stmt.executeQuery("SELECT balance FROM customers WHERE accountnumber='" + accountnumber + "' AND password='" + password + "'");

    double balance = -1;
    if (rs.next()) {
        balance = rs.getDouble("balance");
       return balance;
        
        
    } else {
        
        JOptionPane.showMessageDialog(new View_Balance(), "Invalid account number or password");
        System.out.println("Invalid account number or password.");
    }

    conn.close();
           return 0;
    
}

    
    static boolean checkPassword(int accountnumber, String password) throws Exception {
    Class.forName("com.mysql.jdbc.Driver");
    Connection conn = DriverManager.getConnection(url, "root", "root");

    Statement stmt = conn.createStatement();
    ResultSet rs = stmt.executeQuery("SELECT password FROM customers WHERE accountnumber='" + accountnumber + "'AND password ='" + password +"'");
    
    if (rs.next()) {
        conn.close();
         return true;
       //  String currentPassword = rs.getString("password");
        //Change_password.newpassword1.setVisible(true);
      
      
      
      
      
    } else {
        System.out.println("Account not found.");
        conn.close();
        return false;
    }

   
}

    
    
    
    
    
}
    
    

