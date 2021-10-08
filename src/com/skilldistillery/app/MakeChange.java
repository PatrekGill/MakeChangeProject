package com.skilldistillery.app;

import java.util.Scanner;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class MakeChange {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            boolean startTransaction = mainMenu(scanner);

            if (startTransaction){
                startTransaction(scanner);

            } else {
                break;
            }
        }

        System.out.println("End");
        scanner.close();
    }

    private static void startTransaction(Scanner scanner) {
        System.out.print("Enter in the price of the item: ");
        BigDecimal priceOfItem = scanner.nextBigDecimal();
        scanner.nextLine();

        System.out.print("Enter in the amount tendered by the customer: ");
        BigDecimal amountTendered = scanner.nextBigDecimal();
        scanner.nextLine();

        if (amountTendered.compareTo(priceOfItem) < 0) {
            System.out.println("The amount provided is less than the price of the item. Please provide more payment.");

        } else if (amountTendered.compareTo(priceOfItem) == 0) {
            System.out.println("Exact amount provided. No change is required.");

        } else {
            makeChange(priceOfItem, amountTendered);
        }

    }

    public static void makeChange(BigDecimal priceOfItem, BigDecimal amountTendered) {
        BigDecimal totalChange = amountTendered.subtract(priceOfItem).setScale(2,RoundingMode.HALF_UP);

        // 100s
        //BigDecimal changeLeft = showChangeForDenomination(totalChange,new BigDecimal("100.00"),"hundred",false,"");

        // 20s
        BigDecimal changeLeft = showChangeForDenomination(totalChange,new BigDecimal("20.00"),"twenty",false,"");
        //changeLeft = showChangeForDenomination(changeLeft,new BigDecimal("20.00"),"twenty",!changeLeft.equals(totalChange),""); // if uncommenting 100s, uncomment this line

        // 10s
        changeLeft = showChangeForDenomination(changeLeft,new BigDecimal("10.00"),"ten",!changeLeft.equals(totalChange),"");

        // 5s
        changeLeft = showChangeForDenomination(changeLeft,new BigDecimal("5.00"),"five",!changeLeft.equals(totalChange),"");

        // 1s
        changeLeft = showChangeForDenomination(changeLeft,new BigDecimal("1.00"),"one",!changeLeft.equals(totalChange),"");

        // quarters
        changeLeft = showChangeForDenomination(changeLeft,new BigDecimal("0.25"),"quarter",!changeLeft.equals(totalChange),"");

        // dime
        changeLeft = showChangeForDenomination(changeLeft,new BigDecimal("0.10"),"dime",!changeLeft.equals(totalChange),"");

        // nickel
        changeLeft = showChangeForDenomination(changeLeft,new BigDecimal("0.05"),"nickel",!changeLeft.equals(totalChange),"");

        // pennies
        changeLeft = showChangeForDenomination(changeLeft,new BigDecimal("0.01"),"penny",!changeLeft.equals(totalChange), "pennies");

        System.out.println();
        System.out.println("The total change is: " + totalChange + "\n");
    }

    private static BigDecimal showChangeForDenomination(BigDecimal changeLeft, BigDecimal denomination, String denominationString, boolean addCommaAndSpace, String specialPluralString){

        if (changeLeft.compareTo(denomination) >= 0){
            BigDecimal modAmount = changeLeft.remainder(denomination);
            BigDecimal numberThatIsDivisible = changeLeft.subtract(modAmount);
            BigDecimal amountOfNumber = numberThatIsDivisible.divide(denomination,RoundingMode.HALF_EVEN);
            if (denomination.doubleValue() < 1.00) {
                printCentAmount(amountOfNumber.intValue(),denominationString,addCommaAndSpace,specialPluralString);

            } else {
                printDollarAmount(amountOfNumber.intValue(),denominationString,addCommaAndSpace);

            }

            changeLeft = changeLeft.subtract(numberThatIsDivisible);
        }


        return changeLeft;
    }

    private static void printDollarAmount(int amountOfNumber,String denominationString, boolean addCommaAndSpace) {
        String printString = "";
        if (addCommaAndSpace) {
            printString += ", ";
        }

        printString += amountOfNumber + " " + denominationString + " dollar bill";
        if (amountOfNumber > 1) {
            printString += "s";
        }

        System.out.print(printString);
    }

    private static void printCentAmount(int amountOfNumber,String denominationString, boolean addCommaAndSpace, String specialPluralString) {
        String printString = "";
        if (addCommaAndSpace) {
            printString += ", ";
        }

        boolean isPlural = amountOfNumber > 1;
        if (!specialPluralString.equals("") && isPlural){
            printString += amountOfNumber + " " + specialPluralString;

        } else {
            printString += amountOfNumber + " " + denominationString;
            if (isPlural) {
                printString += "s";
            }
        }

        System.out.print(printString);
    }

    private static boolean mainMenu(Scanner scanner) {
        boolean startTransaction = true;

        while (true) {
            System.out.println("=============================");
            System.out.println("| 0. Quit");
            System.out.println("| 1. Query A New Transaction");
            System.out.println("==============================");

            if (scanner.hasNextInt()) {
                int menuSelection = scanner.nextInt();

                if (menuSelection == 0) {
                    startTransaction = false;
                    System.out.println("Quitting...");
                    break;

                } else if (menuSelection == 1) {
                    System.out.println("Moving to transaction menu...\n");
                    break;

                } else {
                    System.out.println("Unknown menu item requested, please try again.\n");
                }

            } else {
                System.out.println("Entered invalid character, please try again.\n");

            }
            scanner.nextLine();
        }

        return startTransaction;
    }


}
