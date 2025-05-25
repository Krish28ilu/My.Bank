/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.bank_project;

/**
 *
 * @author Krish
 */
class AccountNumberGenerator {
    
    private static int baseAccountNumber = 1000;
    
    static int generateAccountNumber() {
        return baseAccountNumber++;
    }
    
}
