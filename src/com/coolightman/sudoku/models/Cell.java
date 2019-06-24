package com.coolightman.sudoku.models;
//created by Coolightman
//29.05.2019 9:21

import java.util.ArrayList;

public class Cell {
    private ArrayList<Integer> cellValue;

    Cell(ArrayList<Integer> cellValue) {
        this.cellValue = cellValue;
    }

    void printCell(){
        System.out.print(String.valueOf(cellValue)+" ");
    }

    public ArrayList<Integer> getCellValue() {
        return cellValue;
    }

    public void setCellValue(int cellValue) {
        ArrayList<Integer> xy = new ArrayList<>();
        xy.add(cellValue);
        this.cellValue = xy;
    }
}
