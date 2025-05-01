package com.ps;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;


public class Main {
    static Scanner scanner = new Scanner(System.in);
    static ArrayList<Transaction> transactions = new ArrayList<>();
    static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd, HH:mm:ss");
    static DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");


    public static void main(String[] args) {

        loadFile();
        int mainMenuCommand;

        do {
            System.out.println("WELCOME TO THE BAGEL BYTES BANK");
            System.out.println("1) Add deposit");
            System.out.println("2) Make payment");
            System.out.println("3) Display the ledger screen");
            System.out.println("0) Exit");
            System.out.print("What would you like to do? ");
            mainMenuCommand = scanner.nextInt();

            switch (mainMenuCommand) {
                case 1:
                    scanner.nextLine();
                    AddDeposit();
                    break;
                case 2:
                    scanner.nextLine();
                    makePayment();
                    break;
                case 3:
                    scanner.nextLine();
                    displayLedger();
                    break;
                case 0:
                    scanner.nextLine();
                    System.out.println("Exiting");
                    break;
                default:
                    scanner.nextLine();
                    System.out.println("Command not found! Try again");
            }

        } while (mainMenuCommand != 0);
    }

    private static void loadFile() {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("transactions.csv"));

            String input;

            while ((input = bufferedReader.readLine()) != null) {
                String[] fields = input.split("\\|");

                String date = fields[0];
                String time = fields[1];
                String description = fields[2];
                String vendor = fields[3];
                double amount = Double.parseDouble(fields[4]);

                Transaction transaction = new Transaction(date, time, description, vendor, amount);
                transactions.add(transaction);

            }

            bufferedReader.close();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    private static void displayLedger() {
        System.out.println("---Display Ledger---");
        System.out.println("1) All entries");
        System.out.println("2) Deposits");
        System.out.println("3) Payments");
        System.out.println("4) Reports");
        System.out.println("0) Home");
        System.out.print("What would you like to do? ");
        int displayLedgerCommand = scanner.nextInt();

        switch (displayLedgerCommand) {
            case 1:
                displayAllEntries();
                break;
            case 2:
                displayDeposits();
                break;
            case 3:
                displayPayments();
                break;
            case 4:
                displayReports();
                break;
            case 0:
                System.out.println("Going to home screen");
                break;
            default:
                System.out.println("Incorrect command, going back");
        }
    }

    private static void displayReports() {
        System.out.println("---Display Reports---");
        System.out.println("1) Month to Date");
        System.out.println("2) Previous Month");
        System.out.println("3) Year to Date");
        System.out.println("4) Previous Year");
        System.out.println("5) Search by Vendor");
        System.out.println("0) Back");
        System.out.print("What would you like to do? ");

        int displayCartCommand = scanner.nextInt();

        switch (displayCartCommand) {
            case 1:
                monthToDate();
                break;
            case 2:
                previousMonth();
                break;
            case 3:
                yearToDate();
                break;
            case 4:
                previousYear();
                break;
            case 5:
                searchByVendor();
                break;
            case 0:
                System.out.println("Going back");
                break;
            default:
                System.out.println("Incorrect command, going back");
        }
    }

    private static void yearToDate() {

        LocalDate today = LocalDate.now();
        int currentYear = today.getYear();

        for (Transaction transaction : transactions) {
            LocalDate localDate = LocalDate.parse(transaction.getDate(), dateFormatter);
            if (localDate.getYear() == currentYear) {
                System.out.println(transaction);
            }
        }
    }


    private static void previousYear() {

        LocalDate previousYearDate = LocalDate.now().minusYears(1);
        int previousYear = previousYearDate.getYear();


        for (Transaction transaction : transactions) {
            LocalDate localDate = LocalDate.parse(transaction.getDate(), dateFormatter);
            if (localDate.getYear() == previousYear) {
                System.out.println(transaction);
            }
        }
    }


    private static void searchByVendor() {
        scanner.nextLine();
        System.out.print("Please enter the vendor: ");
        String userVendorChoice = scanner.nextLine();

        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("transactions.csv"));
            String input;


            while ((input = bufferedReader.readLine()) != null) {
                String[] fields = input.split("\\|");

                String date = fields[0];
                String time = fields[1];
                String description = fields[2];
                String vendor = fields[3];
                double amount = Double.parseDouble(fields[4]);

                Transaction transaction = new Transaction(date, time, description, vendor, amount);
                transactions.add(transaction);
            }

            bufferedReader.close();

            boolean found = false;
            for (Transaction transaction : transactions) {
                if (userVendorChoice.equalsIgnoreCase(transaction.getVendor())) {
                    System.out.println(transaction);
                    found = true;
                }
            }

            if (!found) {
                System.out.println("No transactions found for vendor: " + userVendorChoice);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    private static void previousMonth() {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("transactions.csv"));
            String input;


            while ((input = bufferedReader.readLine()) != null) {
                String[] fields = input.split("\\|");

                String date = fields[0];
                String time = fields[1];
                String description = fields[2];
                String vendor = fields[3];
                double amount = Double.parseDouble(fields[4]);

                Transaction transaction = new Transaction(date, time, description, vendor, amount);
                transactions.add(transaction);
            }
            bufferedReader.close();

            LocalDate today = LocalDate.now();
            LocalDate previousMonthDate = today.minusMonths(1);
            int previousMonth = previousMonthDate.getMonthValue();
            int currentYear = today.getYear();


            for (Transaction transaction : transactions) {
                LocalDate localDate = LocalDate.parse(transaction.getDate(), dateFormatter);
                if (localDate.getYear() == currentYear && localDate.getMonthValue() == previousMonth) {
                    System.out.println(transaction);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    private static void monthToDate() {


        LocalDate today = LocalDate.now();
        int currentYear = today.getYear();
        int currentMonth = today.getMonthValue();

        for (Transaction transaction : transactions) {
            LocalDate localDate = LocalDate.parse(transaction.getDate(), dateFormatter);
            if (localDate.getYear() == currentYear && localDate.getMonthValue() == currentMonth) {
                System.out.println(transaction);
            }
        }
    }


    private static void displayPayments() {


        for (Transaction transaction : transactions) {
            if (transaction.getAmount() < 0) {
                System.out.println(transaction);
            }
        }
    }


    private static void displayDeposits() {


        for (Transaction transaction : transactions) {
            if (transaction.getAmount() > 0) {
                System.out.println(transaction);
            }
        }
    }


    private static void displayAllEntries() {


        for (Transaction transaction : transactions) {
            System.out.println(transaction);
        }
    }

    private static void makePayment() {
        try {
            FileWriter fileWriter = new FileWriter("transactions.csv", true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            String[] userPayment = new String[5];

            System.out.print("Enter the date of the payment (yyyy-MM-dd): ");
            userPayment[0] = scanner.nextLine();

            System.out.print("Enter the time of the payment (HH:mm:ss): ");
            userPayment[1] = scanner.nextLine();

            System.out.print("Enter the description of the payment: ");
            userPayment[2] = scanner.nextLine();

            System.out.print("Enter the name of the vendor you paid: ");
            userPayment[3] = scanner.nextLine();

            System.out.print("Enter the amount of the payment: ");
            userPayment[4] = scanner.nextLine();


            LocalDate userPaymentDate = LocalDate.parse(userPayment[0]);
            LocalTime userPaymentTime = LocalTime.parse(userPayment[1]);
            String userPaymentDescription = userPayment[2];
            String userPaymentVendor = userPayment[3];
            double userPaymentAmount = -Double.parseDouble(userPayment[4]);

            String userPaymentFormatted = String.format("%s|%s|%s|%s|%.2f", userPaymentDate, userPaymentTime, userPaymentDescription, userPaymentVendor, userPaymentAmount);

            bufferedWriter.write(userPaymentFormatted);
            bufferedWriter.newLine();
            bufferedWriter.close();

            System.out.println("You have added this payment: " + userPaymentFormatted);

        } catch (IOException e) {
            throw new RuntimeException("File error: " + e.getMessage());
        }
    }


    private static void AddDeposit() {
        try {
            FileWriter fileWriter = new FileWriter("transactions.csv", true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            String[] userDeposit = new String[5];

            System.out.print("Enter the date of the deposit (yyyy-MM-dd): ");
            userDeposit[0] = scanner.nextLine();

            System.out.print("Enter the time of the deposit (HH:mm:ss): ");
            userDeposit[1] = scanner.nextLine();

            System.out.print("Enter the description of the deposit: ");
            userDeposit[2] = scanner.nextLine();

            System.out.print("Enter the name of the vendor that paid you: ");
            userDeposit[3] = scanner.nextLine();

            System.out.print("Enter the amount of the deposit: ");
            userDeposit[4] = scanner.nextLine();


            LocalDate userDepositDate = LocalDate.parse(userDeposit[0]);
            LocalTime userDepositTime = LocalTime.parse(userDeposit[1]);
            String userDepositDescription = userDeposit[2];
            String userDepositVendor = userDeposit[3];
            double userDepositAmount = Double.parseDouble(userDeposit[4]);

            String userDepositFormatted = String.format("%s|%s|%s|%s|%.2f", userDepositDate, userDepositTime, userDepositDescription, userDepositVendor, userDepositAmount);

            bufferedWriter.write(userDepositFormatted);
            bufferedWriter.newLine();
            bufferedWriter.close();

            System.out.println("You have added this payment: " + userDepositFormatted);

        } catch (IOException e) {
            throw new RuntimeException("File error: " + e.getMessage());
        }
    }
}


