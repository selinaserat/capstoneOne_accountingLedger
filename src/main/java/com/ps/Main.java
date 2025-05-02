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
    static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");


    public static void main(String[] args) {

        loadFile();
        int mainMenuCommand;

        do {

            System.out.println("\n" + Ansi.BG_PURPLE + Ansi.BLACK + Ansi.BOLD +
                    "****************************************" + Ansi.RESET + "\n" + Ansi.BG_PURPLE + Ansi.BLACK + Ansi.BOLD +
                    "*                                      *" + Ansi.RESET + "\n" + Ansi.BG_PURPLE + Ansi.BLACK + Ansi.BOLD +
                    "*   WELCOME TO THE BAGEL BYTES BANK    *" + Ansi.RESET + "\n" + Ansi.BG_PURPLE + Ansi.BLACK + Ansi.BOLD +
                    "*                                      *" + Ansi.RESET + "\n" + Ansi.BG_PURPLE + Ansi.BLACK + Ansi.BOLD +
                    "****************************************"
                    + Ansi.RESET);

            System.out.println("\nPlease choose one of the options below");
            System.out.println(Ansi.ANSI_BRIGHT_MAGENTA + "\n1) Add deposit");
            System.out.println("2) Make payment");
            System.out.println("3) Display the ledger screen");
            System.out.println("0) Exit" + Ansi.ANSI_RESET + "\n");
            System.out.print(Ansi.ANSI_ITALIC + "What would you like to do?" + Ansi.RESET + " ");
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
        System.out.println("\n" + Ansi.BG_BLUE + Ansi.BLACK + Ansi.BOLD + "---Display Ledger---" + Ansi.RESET + "\n");
        System.out.println(Ansi.BLUE + "1) All entries");
        System.out.println("2) Deposits");
        System.out.println("3) Payments");
        System.out.println("4) Reports");
        System.out.println("0) Home");
        System.out.print(Ansi.RESET + Ansi.ANSI_ITALIC + "\nWhat would you like to display? ");
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
                System.out.println("\n\uD83C\uDFE0 Going to home screen \uD83C\uDFE0");
                break;
            default:
                System.out.println("\nIncorrect command, going back");
        }
    }

    private static void displayReports() {
        System.out.println("\n" + Ansi.BG_CYAN + Ansi.BLACK + Ansi.BOLD + "---Display Reports---" + Ansi.RESET + "\n");
        System.out.println(Ansi.CYAN + "1) Month to Date");
        System.out.println("2) Previous Month");
        System.out.println("3) Year to Date");
        System.out.println("4) Previous Year");
        System.out.println("5) Search by Vendor");
        System.out.println("0) Back");
        System.out.print(Ansi.RESET + Ansi.ANSI_ITALIC + "\nHow would you like to display the reports? ");

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
                displayLedger();
                break;
            default:
                System.out.println("\nIncorrect command, going back");
        }
    }

    private static void yearToDate() {

        LocalDate today = LocalDate.now();
        int currentYear = today.getYear();


        System.out.println("\nYEAR TO DATE TRANSACTIONS\n");
        for (Transaction transaction : transactions) {
            LocalDate localDate = LocalDate.parse(transaction.getDate(), dateFormatter);
            if (localDate.getYear() == currentYear) {
                System.out.println(transaction);
            }
        }
        displayReports();
    }


    private static void previousYear() {

        LocalDate previousYearDate = LocalDate.now().minusYears(1);
        int previousYear = previousYearDate.getYear();

        System.out.println("\nPREVIOUS YEAR TRANSACTIONS\n");

        for (Transaction transaction : transactions) {
            LocalDate localDate = LocalDate.parse(transaction.getDate(), dateFormatter);
            if (localDate.getYear() == previousYear) {
                System.out.println(transaction);
            }
        }
        displayReports();
    }


    private static void searchByVendor() {
        scanner.nextLine();
        System.out.print("\nPlease enter the vendor name: ");
        String userVendorChoice = scanner.nextLine();
        System.out.println("\nVENDOR TRANSACTIONS\n");


            boolean found = false;
            for (Transaction transaction : transactions) {
                if (userVendorChoice.equalsIgnoreCase(transaction.getVendor())) {
                    System.out.println(transaction);
                    found = true;
                }
            }

            if (!found) {
                System.out.println("\nNo transactions found for vendor: " + userVendorChoice + ". Try again");
            }

            displayReports();
    }


    private static void previousMonth() {


        System.out.println("\nPREVIOUS MONTH TRANSACTIONS\n");
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
            displayReports();

    }


    private static void monthToDate() {


        System.out.println("\nMONTH TO DATE TRANSACTIONS\n");
        LocalDate today = LocalDate.now();
        int currentYear = today.getYear();
        int currentMonth = today.getMonthValue();

        for (Transaction transaction : transactions) {
            LocalDate localDate = LocalDate.parse(transaction.getDate(), dateFormatter);
            if (localDate.getYear() == currentYear && localDate.getMonthValue() == currentMonth) {
                System.out.println(transaction);
            }
        }
        displayReports();
    }


    private static void displayPayments() {

        System.out.println("\nPAYMENT TRANSACTIONS\n");

        for (Transaction transaction : transactions) {
            if (transaction.getAmount() < 0) {
                System.out.println(transaction);
            }
        }
        displayLedger();
    }


    private static void displayDeposits() {

        System.out.println("\nDEPOSIT TRANSACTIONS\n");

        for (Transaction transaction : transactions) {
            if (transaction.getAmount() > 0) {
                System.out.println(transaction);
            }
        }
        displayLedger();
    }


    private static void displayAllEntries() {

        System.out.println("\nALL TRANSACTIONS\n");

        for (Transaction transaction : transactions) {
            System.out.println(transaction);
        }
        displayLedger();
    }

    private static void makePayment() {
        try {
            FileWriter fileWriter = new FileWriter("transactions.csv", true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            String[] userPayment = new String[5];

            System.out.println("\nADDING PAYMENT");
            System.out.print("\nEnter the date of the payment (yyyy-MM-dd): ");
            userPayment[0] = scanner.nextLine().trim();

            System.out.print("Enter the time of the payment (HH:mm:ss): ");
            userPayment[1] = scanner.nextLine().trim();

            System.out.print("Enter the description of the payment: ");
            userPayment[2] = scanner.nextLine().trim();

            System.out.print("Enter the name of the vendor you paid: ");
            userPayment[3] = scanner.nextLine().trim();

            System.out.print("Enter the amount of the payment: ");
            userPayment[4] = scanner.nextLine().trim();


            LocalDate userPaymentDate = LocalDate.parse(userPayment[0]);
            LocalTime userPaymentTime = LocalTime.parse(userPayment[1]);
            String userPaymentDescription = userPayment[2];
            String userPaymentVendor = userPayment[3];
            double userPaymentAmount = -Double.parseDouble(userPayment[4]);

            String userPaymentFormatted = String.format("%s|%s|%s|%s|%.2f", userPaymentDate, userPaymentTime, userPaymentDescription, userPaymentVendor, userPaymentAmount);

            bufferedWriter.write(userPaymentFormatted);
            bufferedWriter.newLine();
            bufferedWriter.close();

            System.out.println("\nYou have added this payment: " + userPaymentFormatted + "\n");

        } catch (IOException e) {
            throw new RuntimeException("File error: " + e.getMessage());
        }
    }


    private static void AddDeposit() {
        try {
            FileWriter fileWriter = new FileWriter("transactions.csv", true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            String[] userDeposit = new String[5];

            System.out.println("\nADDING DEPOSIT");
            System.out.print("\nEnter the date of the deposit (yyyy-MM-dd): ");
            userDeposit[0] = scanner.nextLine().trim();

            System.out.print("Enter the time of the deposit (HH:mm:ss): ");
            userDeposit[1] = scanner.nextLine().trim();

            System.out.print("Enter the description of the deposit: ");
            userDeposit[2] = scanner.nextLine().trim();

            System.out.print("Enter the name of the vendor that paid you: ");
            userDeposit[3] = scanner.nextLine().trim();

            System.out.print("Enter the amount of the deposit: ");
            userDeposit[4] = scanner.nextLine().trim();


            LocalDate userDepositDate = LocalDate.parse(userDeposit[0]);
            LocalTime userDepositTime = LocalTime.parse(userDeposit[1]);
            String userDepositDescription = userDeposit[2];
            String userDepositVendor = userDeposit[3];
            double userDepositAmount = Double.parseDouble(userDeposit[4]);

            String userDepositFormatted = String.format("%s|%s|%s|%s|%.2f", userDepositDate, userDepositTime, userDepositDescription, userDepositVendor, userDepositAmount);

            bufferedWriter.write(userDepositFormatted);
            bufferedWriter.newLine();
            bufferedWriter.close();

            System.out.println("\nYou have added this payment: " + userDepositFormatted + "\n");

        } catch (IOException e) {
            throw new RuntimeException("File error: " + e.getMessage());
        }
    }
}


