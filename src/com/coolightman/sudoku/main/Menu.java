package com.coolightman.sudoku.main;
//created by Coolightman
//29.05.2019 8:44

import java.util.Scanner;

import static com.coolightman.sudoku.main.Engine.startFindDecision;

class Menu {
    static void start() {
        printHello();
        showMenu();
    }

    private static void showMenu() {
        printMenu();
        int userMenuVal = mainMenuInner();
        switch (userMenuVal) {
            case 1:
                startFindDecision();
                break;

            case 2:
                printExit();
                break;

            default:
                System.out.println("Wrong menu number. Try again:");
                showMenu();
        }
    }

    private static void printHello() {
        System.out.println("Hello! It's program can help you to find decision " +
                "of random sudoku game!");
    }

    private static void printExit() {
        System.out.println("Bye!");
    }

    private static void printMenu() {
        System.out.println("1 - start\n" +
                "2 - exit\n" +
                ">");
    }

    private static int mainMenuInner() {
        Scanner menuScanner = new Scanner(System.in);
        int inMenuVal = -1;
        try {
            inMenuVal = menuScanner.nextInt();
        } catch (Exception e) {
            System.out.println("Inner Value has wrong format! Try again:");
            mainMenuInner();
        }
        return inMenuVal;
    }
}
