/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.bank_project;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import java.util.Random;

/**
 *
 * @author Krish
 */
public class Smssender {
     public static String getOtp() {

        Random random = new Random();
        int a = random.nextInt(10);
        int b = random.nextInt(10);
        int c = random.nextInt(10);
        int d = random.nextInt(10);

        String otp = a+""+b+""+c+""+d;
        return otp;

    }
  private  static final String ACCOUNT_SID  = "";
  private static final String AUTH_TOKEN = "";

    public static String sendSms(String toPhone)
    {

        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        String otp = getOtp();

        Message message = Message.creator(
                        new com.twilio.type.PhoneNumber("whatsapp:+91"+toPhone),
                        new com.twilio.type.PhoneNumber("whatsapp:+14155238886"),
                        "This is your otp., we are from bank "+otp)
                .create();

        System.out.println("Sms send success");
        return otp;
    }

    public static void sendSms(String toPhone,String msg)
    {

        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);


        Message message = Message.creator(
                        new com.twilio.type.PhoneNumber("whatsapp:+91"+toPhone),
                        new com.twilio.type.PhoneNumber("whatsapp:+14155238886"),
                        msg)
                .create();

        System.out.println("Sms send success");

    }
    
}
