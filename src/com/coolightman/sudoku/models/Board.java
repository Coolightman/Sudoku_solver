package com.coolightman.sudoku.models;
//created by Coolightman
//29.05.2019 8:55

import java.util.ArrayList;
import java.util.Scanner;

public class Board {
    private static ArrayList<Cell> cellArrayList = new ArrayList<>();
    private static int BOARD_SIZE = 9;
    private static int inVal;

    public static void createBoard() {
//        createByReceiveFromUser();
        createByTable();
        printBoard();
    }

    private static void createByTable() {

//        int[][] table = {
//                {0, 0, 0, 0, 0, 0, 0, 0, 0},
//                {0, 0, 0, 0, 0, 0, 0, 0, 0},
//                {0, 0, 0, 0, 0, 0, 0, 0, 0},
//                {0, 0, 0, 0, 0, 0, 0, 0, 0},
//                {0, 0, 0, 0, 0, 0, 0, 0, 0},
//                {0, 0, 0, 0, 0, 0, 0, 0, 0},
//                {0, 0, 0, 0, 0, 0, 0, 0, 0},
//                {0, 0, 0, 0, 0, 0, 0, 0, 0},
//                {0, 0, 0, 0, 0, 0, 0, 0, 0},
//        };

        int[][] table = {
                {0, 0, 0, 0, 7, 0, 0, 6, 3},
                {1, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 7, 0},
                {0, 6, 3, 0, 0, 0, 0, 4, 0},
                {0, 0, 0, 8, 0, 0, 2, 0, 0},
                {0, 0, 0, 5, 0, 0, 0, 0, 0},
                {5, 0, 0, 2, 0, 0, 1, 0, 0},
                {0, 7, 0, 0, 6, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 5, 0, 0},
        };


        for (int j = 0; j < BOARD_SIZE; j++) {
            for (int i = 0; i < BOARD_SIZE; i++) {
                ArrayList<Integer> value = new ArrayList<>();
                value.add(table[j][i]);
                Cell cell = new Cell(value);
                cellArrayList.add(cell);
            }
        }
    }

    private static void createByReceiveFromUser() {
        for (int j = 0; j < BOARD_SIZE; j++) {
            System.out.println("Please enter values for row " + (j + 1));
            for (int i = 0; i < BOARD_SIZE; i++) {
                System.out.println("Please enter value column " + (i + 1));
                receiveValueFromUser();
                System.out.println("value = " + inVal);
                ArrayList<Integer> val = new ArrayList<>(inVal);
                Cell cell = new Cell(val);
                cellArrayList.add(cell);
            }
        }
    }

    private static void receiveValueFromUser() {
        Scanner menuScanner = new Scanner(System.in);
        try {
            inVal = menuScanner.nextInt();
            if (inVal < 0 || inVal > 8) {
                System.out.println("Inner value is out of bounds. Please try again:");
                receiveValueFromUser();
            }
        } catch (Exception e) {
            System.out.println("Inner Value has wrong format! Try again:");
            receiveValueFromUser();
        }
    }

    public static void printBoard() {
        int k = 0;
        System.out.println();
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                cellArrayList.get(k).printCell();
                k++;
            }
            System.out.println();
        }
    }

    public static ArrayList<Cell> getCellArrayList() {
        return cellArrayList;
    }

}
