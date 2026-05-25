/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.login;

/**
 *
 * @author Student
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Login {

    // Database simulation
    public static Map<String, User> users = new HashMap<>();

    // Store sent messages
    public static ArrayList<massage> sentMessages = new ArrayList<>();

    // Add user to database
    public static void setDB(String username, String firstName, String lastName, String phone, String password) {
        users.put(username, new User(firstName, lastName, username, phone, password));
    }

    // Check username
    public static boolean checkUserName(String username) {
        return username.contains("_") && username.length() <= 5;
    }

    // Check password complexity
    public static boolean checkPasswordComplexity(String password) {
        return password.length() >= 8 &&
                Pattern.compile("[A-Z]").matcher(password).find() &&
                Pattern.compile("[0-9]").matcher(password).find() &&
                Pattern.compile("[^a-zA-Z0-9]").matcher(password).find();
    }

    // Check South African cellphone number
    public static boolean checkCellPhoneNumber(String phoneNumber) {
        String regex = "^\\+27[1-9][0-9]{8}$";
        return phoneNumber.matches(regex);
    }

    // Registration messages
    public static void registerUser(boolean username, boolean password, boolean phoneNumber) {

        if (username) {
            System.out.println("Username successfully captured.");
        } else {
            System.out.println("Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.");
        }

        if (password) {
            System.out.println("Password successfully captured.");
        } else {
            System.out.println("Password incorrectly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.");
        }

        if (phoneNumber) {
            System.out.println("Cell number successfully captured.");
        } else {
            System.out.println("Cell number incorrectly formatted or does not contain international code.");
        }
    }

    // Login method
    public static boolean loginUser(String username, String password) {

        if (users.containsKey(username)) {
            User user = users.get(username);
            return user.getPassword().equals(password);
        }

        return false;
    }

    // Return login status
    public static String returnLoginStatus(boolean loginSuccess, String username) {

        if (loginSuccess) {
            User user = users.get(username);
            return "Welcome " + user.getFirstName() + " " + user.getLastName() + ", it is great to see you again.";
        } else {
            return "Username or password incorrect, please try again.";
        }
    }

    // Main method
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        // Registration
        System.out.println("===== USER REGISTRATION =====");

        System.out.print("Enter first name: ");
        String firstName = scanner.nextLine();

        System.out.print("Enter last name: ");
        String lastName = scanner.nextLine();

        System.out.print("Enter username: ");
        String username = scanner.nextLine();

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        System.out.print("Enter South African phone number (+27): ");
        String phone = scanner.nextLine();

        // Validation
        boolean validUsername = checkUserName(username);
        boolean validPassword = checkPasswordComplexity(password);
        boolean validPhone = checkCellPhoneNumber(phone);

        // Display registration results
        registerUser(validUsername, validPassword, validPhone);

        // Save user if all validations pass
        if (validUsername && validPassword && validPhone) {

            setDB(username, firstName, lastName, phone, password);

            System.out.println("\nUser registered successfully!");

            // Login section
            System.out.println("\n===== LOGIN =====");

            System.out.print("Enter username: ");
            String loginUsername = scanner.nextLine();

            System.out.print("Enter password: ");
            String loginPassword = scanner.nextLine();

            boolean loginSuccess = loginUser(loginUsername, loginPassword);

            System.out.println(returnLoginStatus(loginSuccess, loginUsername));

            // QUICKCHAT SECTION
            if (loginSuccess) {

                int choice;
                int totalMessages;

                System.out.println("\nWelcome to QuickChat.");

                System.out.print("Enter the number of messages you wish to send: ");
                totalMessages = scanner.nextInt();
                scanner.nextLine();

                do {

                    System.out.println("\n===== MENU =====");
                    System.out.println("1. Send Messages");
                    System.out.println("2. Show recently sent messages");
                    System.out.println("3. Quit");
                    System.out.print("Choose an option: ");

                    choice = scanner.nextInt();
                    scanner.nextLine();

                    switch (choice) {

                        case 1:

                            for (int i = 1; i <= totalMessages; i++) {

                                System.out.print("Enter recipient number (+27): ");
                                String recipient = scanner.nextLine();

                                System.out.print("Enter your message: ");
                                String text = scanner.nextLine();

                                massage msg = new massage(i, recipient, text);

                                // Validate recipient number
                                if (!msg.checkRecipientCell()) {
                                    System.out.println("Cell number is incorrectly formatted.");
                                    continue;
                                }

                                // Validate message length
                                if (!msg.checkMessageLength()) {
                                    System.out.println(msg.returnTotalCharacters());
                                    continue;
                                }

                                sentMessages.add(msg);

                                System.out.println("Message sent successfully!");
                                msg.displayMessage();
                            }

                            break;

                        case 2:

                            System.out.println("\n===== RECENTLY SENT MESSAGES =====");

                            if (sentMessages.isEmpty()) {
                                System.out.println("Coming Soon.");
                            } else {

                                for (massage m : sentMessages) {
                                    m.displayMessage();
                                }
                            }

                            break;

                        case 3:

                            System.out.println("Exiting QuickChat...");
                            break;

                        default:

                            System.out.println("Invalid option.");
                    }

                } while (choice != 3);
            }

        } else {
            System.out.println("\nRegistration failed.");
        }

        scanner.close();
    }
}

