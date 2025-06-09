/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.bank_project;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDateTime;
import javax.swing.JOptionPane;

/**
 *
 * @author Krish
 */
public class DataBase {
       static  String url  = "jdbc:mysql://localhost:3306/my_bank";  
    
    
       static int generateNewAccountNumber() throws Exception {
       Class.forName("com.mysql.jdbc.Driver");
       Connection conn = DriverManager.getConnection(url, "root", "root");

       Statement stmt = conn.createStatement();
       ResultSet rs = stmt.executeQuery("SELECT MAX(accountnumber) AS max_account FROM customers");

       int newAccountNumber = 100000; // Default starting account number if table is empty

       if (rs.next()) {
        int maxAccount = rs.getInt("max_account");
        newAccountNumber = maxAccount + 100;
       }

       conn.close();
       return newAccountNumber;
}

       
       
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
        
        addTransaction(accountnumber,String.valueOf(LocalDateTime.now()),Double.valueOf(amount), "CREDIT");
        
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
      
    } else {
      
        conn.close();
        return false;
    }
    }
    
    
    static void changePassword(int accountnumber, String password, String newpassword) throws Exception {
        
    
    Class.forName("com.mysql.jdbc.Driver");
    Connection conn = DriverManager.getConnection(url, "root", "root");

    Statement stmt = conn.createStatement();
    ResultSet rs = stmt.executeQuery("SELECT password FROM customers WHERE accountnumber='" + accountnumber + "'");

    if (rs.next()) {
     
            stmt.executeUpdate("UPDATE customers SET password='" + newpassword + "' WHERE accountnumber='" + accountnumber + "'");
            System.out.println("Password changed successfully.");
            JOptionPane.showMessageDialog(new Change_password(), "PASSWORD CHANGED SUCCESSFULLY");
       
    } else {
        System.out.println("SOMETHING WENT WRONG.");
    }

    conn.close();
}
  
    
    static void closeAccount(int accountnumber, String password) throws Exception {
    Class.forName("com.mysql.jdbc.Driver");
    Connection conn = DriverManager.getConnection(url, "root", "root");

    Statement stmt = conn.createStatement();
    ResultSet rs = stmt.executeQuery("SELECT * FROM customers WHERE accountnumber='" + accountnumber + "' AND password='" + password + "'");

    if (rs.next()) {
        stmt.executeUpdate("DELETE FROM customers WHERE accountnumber='" + accountnumber + "'");
        JOptionPane.showMessageDialog(new Close_account(), "Account closed successfully. All data deleted.");
        System.out.println("Account closed successfully.");
    } else {
        JOptionPane.showMessageDialog(new Close_account(), "Invalid account number or password.");
        System.out.println("Invalid account number or password.");
    }

    conn.close();
}

    
    static boolean checkAccount(int receiveraccountnumber) throws Exception {
    Class.forName("com.mysql.jdbc.Driver");
    Connection conn = DriverManager.getConnection(url, "root", "root");

    Statement stmt = conn.createStatement();
    ResultSet rs = stmt.executeQuery("SELECT accountnumber FROM customers WHERE accountnumber='" + receiveraccountnumber + "'");
    
     if (rs.next()) {
        conn.close();
         return true;
       
     
    } else {
      
        conn.close();
        return false;
    }
   
}

    
    static boolean checkAmount(int accountnumber, float amount) throws Exception {
    Class.forName("com.mysql.jdbc.Driver");
    Connection conn = DriverManager.getConnection(url, "root", "root");

    Statement stmt = conn.createStatement();
    ResultSet rs = stmt.executeQuery("SELECT balance FROM customers WHERE accountnumber='" + accountnumber + "'");

    if (rs.next()) {
        double balance = rs.getDouble("balance");
        conn.close();
        return amount <= balance;
    } else {
        conn.close();
        return false; // Account not found
    }
}

    
    static boolean sendMoney(int accountnumber, int receiveraccountnumber, float amount) throws Exception {
    Class.forName("com.mysql.jdbc.Driver");
    Connection conn = DriverManager.getConnection(url, "root", "root");
    conn.setAutoCommit(false); // Begin transaction

    try {
        Statement stmt = conn.createStatement();

        // Deduct from sender
        stmt.executeUpdate("UPDATE customers SET balance = balance - " + amount + " WHERE accountnumber = '" + accountnumber + "'");

        // Add to receiver
        stmt.executeUpdate("UPDATE customers SET balance = balance + " + amount + " WHERE accountnumber = '" + receiveraccountnumber + "'");
        addTransaction(accountnumber, url, amount, url);
        conn.commit(); // Commit changes
        conn.close();
        return true;

    } catch (Exception e) {
        conn.rollback(); // Undo changes on error
        conn.close();
        throw e;
    }
}

 
    public static void addTransaction(int accountNumber, String time,double amount, String type) {
    try {
        // Load the MySQL JDBC driver
        Class.forName("com.mysql.jdbc.Driver");

        // Connect to your "my_bank" schema
        Connection conn = DriverManager.getConnection(
            "jdbc:mysql://localhost:3306/my_bank", "root", "root");
        
        
        Statement stmt = conn.createStatement(); 
           
        stmt.executeUpdate("insert into transactions values ('"+accountNumber+"','"+time+"','"+amount+"','"+type+"')");
        
  
            // closing connection 
            conn.close(); 

        // SQL query to insert the transaction
        

    } catch (Exception e) {
        System.out.println("⚠️ Error adding transaction: " + e.getMessage());
    }
}

    
    
    
}

    
    
    
    
    

    

