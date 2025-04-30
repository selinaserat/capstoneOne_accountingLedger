package com.ps;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileNotFoundException;


public class Main {
    static Scanner scanner = new Scanner(System.in);
    static ArrayList<Transaction> inventory = new ArrayList<>();

    public static void main(String[] args) {
        loadTransactions();

        int mainMenuCommand;

        do {
            System.out.println("Welcome to the Accounting Ledger");
            System.out.println("1) Add deposit");
            System.out.println("2) Make payment (debit)");
            System.out.println("3) Display the ledger screen");
            System.out.println("0) Exit");
            System.out.print("What would you like to do? ");
            mainMenuCommand = scanner.nextInt();

            switch (mainMenuCommand) {
                case 1:
                    AddDeposit();
                    break;
                case 2:
                    makePayment();
                    break;
                case 3:
                    displayLedger();
                    break;
                case 0:
                    System.out.println("Exiting");
                    break;
                default:
                    System.out.println("Command not found! Try again");
            }

        } while (mainMenuCommand != 0);
    }

    private static void loadTransactions() {
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
    }

    private static void previousYear() {
    }

    private static void searchByVendor() {
    }

    private static void previousMonth() {
    }


    private static void monthToDate() {
    }


    private static void displayPayments() {
    }

    private static void displayDeposits() {
    }

    private static void displayAllEntries() {
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
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static void makePayment() {
    }

    private static void AddDeposit() {
    }

}