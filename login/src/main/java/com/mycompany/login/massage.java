/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.login;

/**
 *
 * @author Student
 */
 
import java.util.Random;

public class massage {

    private String messageID;
    private int messageNumber;
    private String recipient;
    private String messageText;

    // Constructor
    public massage(int messageNumber, String recipient, String messageText) {

        this.messageNumber = messageNumber;
        this.recipient = recipient;
        this.messageText = messageText;
        this.messageID = generateMessageID();
    }

    // Generate random 10-digit message ID
    public String generateMessageID() {

        Random random = new Random();
        long number = 1000000000L + (long)(random.nextDouble() * 9000000000L);
        return String.valueOf(number);
    }

    // Check recipient cell number
    public boolean checkRecipientCell() {

        return recipient.matches("^\\+27[1-9][0-9]{8}$");
    }

    // Check message length
    public boolean checkMessageLength() {

        return messageText.length() <= 250;
    }

    // Return extra characters if message too long
    public String returnTotalCharacters() {

        int extra = messageText.length() - 250;
        return "Message exceeds 250 characters by " + extra + ", please reduce size.";
    }

    // Display message details
    public void displayMessage() {

        System.out.println("\n===== MESSAGE DETAILS =====");
        System.out.println("Message ID: " + messageID);
        System.out.println("Message Number: " + messageNumber);
        System.out.println("Recipient: " + recipient);
        System.out.println("Message: " + messageText);
    }
}