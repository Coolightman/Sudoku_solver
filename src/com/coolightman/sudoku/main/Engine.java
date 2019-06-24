package com.coolightman.sudoku.main;
//created by Coolightman
//29.05.2019 9:01


import com.coolightman.sudoku.models.Board;
import com.coolightman.sudoku.models.Cell;

import java.util.*;

import static com.coolightman.sudoku.main.RowColumn.COLUMN;
import static com.coolightman.sudoku.main.RowColumn.ROW;
import static com.coolightman.sudoku.main.RowColumn.ZONE;
import static com.coolightman.sudoku.models.Board.createBoard;
import static com.coolightman.sudoku.models.Board.getCellArrayList;

class Engine {

    private static ArrayList<Cell> cellArrayList = getCellArrayList();
    private static int amtOfEmptyCells;
    private static int cashAmtOfEmptyCells = -1;
    private static int lastStepAmtOfEmptyCells;
    private static ArrayList<int[]> boardCellsValues;

    static void startFindDecision() {
        createBoard();
        runFinder();
    }

    private static void runFinder() {
        do {
            simpleMethod();
            if (!checkWin(amtOfEmptyCells)) {
                hardMethod();
            } else {
                System.out.println("\nSolution found!");
                Board.printBoard();
            }

            if (amtOfEmptyCells == cashAmtOfEmptyCells) {
                System.out.println("Need another methods");
                break;
            }
            cashAmtOfEmptyCells = amtOfEmptyCells;

        } while (!checkWin(amtOfEmptyCells));
    }

    private static void hardMethod() {
        int NUMBER_OF_ROWS_OR_LINES = 9;
        for (int i = 0; i < NUMBER_OF_ROWS_OR_LINES; i++) {
            findRowException(i);
            findColumnException(i);
            findZoneException(i);
        }
    }

    private static void findZoneException(int zoneNumber) {
        ArrayList<int[]> zoneOfValues;
        zoneOfValues = receiveZoneOfValues(zoneNumber);
        findException(zoneNumber, zoneOfValues, ZONE);
    }

    private static ArrayList<int[]> receiveZoneOfValues(int zoneNumber) {
        ArrayList<int[]> zoneList = new ArrayList<>();
        int[] zoneCords = zoneCordsFinder(zoneNumber);
        for (int zoneCord : zoneCords) {
            int[] currentCell = boardCellsValues.get(zoneCord);
            zoneList.add(currentCell);
        }

        return zoneList;
    }

    private static int[] zoneCordsFinder(int zoneNumber) {
        int fv; // first value in zone numbers array
        if (zoneNumber < 3) {
            fv = zoneNumber * 3;
        } else if (zoneNumber < 6) {
            fv = (zoneNumber - 3) * 3 + 27;
        } else {
            fv = (zoneNumber - 6) * 3 + 54;
        }

        return new int[]{fv, fv + 1, fv + 2, fv + 9, fv + 10, fv + 11, fv + 18, fv + 19, fv + 20};
    }

    private static void findColumnException(int columnNumber) {
        ArrayList<int[]> columnOfValues;
        columnOfValues = receiveColumnOfValues(columnNumber);
        findException(columnNumber, columnOfValues, COLUMN);
    }

    private static ArrayList<int[]> receiveColumnOfValues(int columnNumber) {
        ArrayList<int[]> columnList = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            int cellNumber = (i * 9) + columnNumber;
            int[] currentCell = boardCellsValues.get(cellNumber);
            columnList.add(currentCell);
        }
        return columnList;
    }

    private static void findRowException(int rowNumber) {
        ArrayList<int[]> rowOfValues;
        rowOfValues = receiveRowOfValues(rowNumber);
        findException(rowNumber, rowOfValues, ROW);
    }

    private static void findException(int number, ArrayList<int[]> values, RowColumn rowOrColumn) {
        int MAX_CELL_VALUE = 9;
        for (int i = 0; i < MAX_CELL_VALUE; i++) {
            int amtOfValue = findAmtOfValue(values, i);
            if (amtOfValue == 1) {
                int cellNumberExc = findCellNumberExc(values, i, number, rowOrColumn);
                writeValueInCell(i, cellNumberExc);
                System.out.println("Exception is number " + i + " in " + rowOrColumn + " " + number);
            }
        }
    }

    private static int findCellNumberExc(ArrayList<int[]> values, int findValue, int number, RowColumn rowOrColumn) {
        int cellCounter = 0;
        int cellNumber = -1;
        for (int[] cellValues : values) {
            for (int cellValue : cellValues) {
                if (cellValue == findValue) {
                    switch (rowOrColumn) {
                        case ROW:
                            cellNumber = 9 * number + cellCounter;
                            break;

                        case COLUMN:
                            cellNumber = 9 * cellCounter + number;
                            break;

                        case ZONE:
                            int[] zoneCords = zoneCordsFinder(number);
                            cellNumber = zoneCords[cellCounter];
                            break;
                    }
                }
            }
            cellCounter++;
        }
        return cellNumber;
    }

    private static void writeValueInCell(int value, int cellNumber) {
        Board.getCellArrayList().get(cellNumber).setCellValue(value);
    }

    private static int findAmtOfValue(ArrayList<int[]> values, int findValue) {
        int valueCounter = 0;
        for (int[] cellValues : values) {
            if (cellValues.length > 1) { // ignore cells with one value because it is true value

                for (int cellValue : cellValues) {
                    if (cellValue == findValue) {
                        valueCounter++;
                    }
                }
            }
            if (valueCounter > 1) {
                break;
            }
        }
        return valueCounter;
    }

    private static ArrayList<int[]> receiveRowOfValues(int rowNumber) {
        ArrayList<int[]> rowList = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            int cellNumber = (rowNumber * 9) + i;
            int[] currentCell = boardCellsValues.get(cellNumber);
            rowList.add(currentCell);
        }
        return rowList;
    }

    private static void simpleMethod() {
        do {
            boardCellsValues = findBoardCellsValues();
            rewriteCellsValuesForFound(boardCellsValues);
            amtOfEmptyCells = countEmptyCells();
            printBoard(boardCellsValues);
        } while (!checkWinOrRepeat(amtOfEmptyCells));
    }

    private static boolean checkWinOrRepeat(int amtOfEmptyCells) {
        if (!checkRepeat(amtOfEmptyCells)) {
            return checkWin(amtOfEmptyCells);
        }
        return true;
    }

    private static boolean checkRepeat(int amtOfEmptyCells) {

        if (amtOfEmptyCells == lastStepAmtOfEmptyCells) {
            return true;
        } else {
            lastStepAmtOfEmptyCells = amtOfEmptyCells;
            return false;
        }
    }

    private static boolean checkWin(int amtOfEmptyCells) {
        return amtOfEmptyCells == 0;
    }

    private static int countEmptyCells() {
        int amtOfEmptyCells = 0;
        for (int i = 0; i < 81; i++) {
            if (Board.getCellArrayList().get(i).getCellValue().get(0) == 0) {
                amtOfEmptyCells++;
            }
        }
        return amtOfEmptyCells;
    }

    private static void rewriteCellsValuesForFound(ArrayList<int[]> boardCellsValues) {
        int indexOfCurrentCell = 0;
        for (int[] boardCellsValue : boardCellsValues) {
            if (boardCellsValue.length == 1) {
                Board.getCellArrayList().get(indexOfCurrentCell).setCellValue(boardCellsValue[0]);
            }
            if (boardCellsValue.length == 0) {
                break;
            }
            indexOfCurrentCell++;
        }
    }

    private static ArrayList<int[]> findBoardCellsValues() {
        ArrayList<int[]> boardCellsValues = new ArrayList<>();

        for (int cellNumb = 0; cellNumb < 81; cellNumb++) {

            int cellValue = receiveCellValue(cellNumb);
            int[] value = findCellValueOptions(cellNumb, cellValue);
            boardCellsValues.add(value);
        }
        return boardCellsValues;
    }

    private static int[] findCellValueOptions(int cellNumb, int cellValue) {

        ArrayList<Integer> cellValueOptions;

        if (cellValue == 0) {

            cellValueOptions = findDecisionOptions(cellNumb);
            int[] value = new int[cellValueOptions.size()];
            for (int j = 0; j < cellValueOptions.size(); j++) {
                value[j] = cellValueOptions.get(j);
            }
            return value;
        } else {
            int[] value = new int[1];
            value[0] = cellValue;
            return value;
        }
    }

    private static void printBoard(ArrayList<int[]> boardVar) {
        int cellNumber = 0;
        System.out.println();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                System.out.print(Arrays.toString(boardVar.get(cellNumber)) + "\t");
                cellNumber++;
            }
            System.out.println();
        }
    }

    private static int receiveCellValue(int cellNumber) {
        ArrayList<Integer> cellValue = cellArrayList.get(cellNumber).getCellValue();

        if (cellValue.size() > 1) {
            return 0;
        }
        return cellValue.get(0);
    }

    private static ArrayList<Integer> findDecisionOptions(int cellNumber) {
        ArrayList<Integer> cells;
        ArrayList<Integer> cellsValues = new ArrayList<>();
        ArrayList<Integer> sampleVars = new ArrayList<>();

        for (int i = 0; i < 9; i++) {
            sampleVars.add(i + 1);
        }

        cells = findCoincidenceCells(cellNumber);

        for (Integer cell : cells) {
            cellsValues.add(receiveCellValue(cell));
        }
        cellsValues.removeIf(cellVal -> cellVal == 0);
        Collections.sort(cellsValues);
        Set<Integer> set = new LinkedHashSet<>(cellsValues);
        ArrayList<Integer> varList = new ArrayList<>(set);

        for (Integer varListOne : varList) {
            sampleVars.remove(varListOne);
        }
        return sampleVars;
    }

    private static ArrayList<Integer> findCoincidenceCells(int cellNumber) {
        int cellRow = findCellRow(cellNumber);
        int cellColumn = findCellColumn(cellNumber);
        int[] cellZone = findCellZone(cellNumber, cellRow, cellColumn);
        ArrayList<Integer> allValForFindCoincidence = new ArrayList<>();

        for (int i = 0; i < 9; i++) {
            int currentVal = i + (9 * cellRow);
            if (currentVal != cellNumber) {
                allValForFindCoincidence.add(currentVal);
            }
        }

        for (int i = 0; i < 9; i++) {
            int currentVal = (i * 9) + cellColumn;
            if (currentVal != cellNumber) {
                allValForFindCoincidence.add(currentVal);
            }
        }

        for (int cellNumb : cellZone) {
            allValForFindCoincidence.add(cellNumb);
        }

        return allValForFindCoincidence;
    }

    private static int[] findCellZone(int cellNumber, int cellRow, int cellColumn) {
        int[] cellZone = new int[4];
        int sosed1 = -1;
        int sosed2 = -1;
        switch (cellColumn % 3) {
            case 0:
                sosed1 = cellNumber + 1;
                sosed2 = cellNumber + 2;
                break;

            case 1:
                sosed1 = cellNumber - 1;
                sosed2 = cellNumber + 1;
                break;

            case 2:
                sosed1 = cellNumber - 1;
                sosed2 = cellNumber - 2;
                break;

            default:
                System.err.println("Wrong %3 of cell number");
        }

        switch (cellRow % 3) {
            case 0:
                cellZone[0] = sosed1 + 9;
                cellZone[1] = sosed1 + 18;
                cellZone[2] = sosed2 + 9;
                cellZone[3] = sosed2 + 18;
                break;

            case 1:
                cellZone[0] = sosed1 - 9;
                cellZone[1] = sosed1 + 9;
                cellZone[2] = sosed2 - 9;
                cellZone[3] = sosed2 + 9;
                break;

            case 2:
                cellZone[0] = sosed1 - 9;
                cellZone[1] = sosed1 - 18;
                cellZone[2] = sosed2 - 9;
                cellZone[3] = sosed2 - 18;
                break;

            default:
                System.err.println("Wrong cell zone");
        }

        return cellZone;
    }

    private static int findCellColumn(int cellNumber) {
        return cellNumber % 9;
    }

    private static int findCellRow(int cellNumber) {
        return cellNumber / 9;
    }

}

