package com.example.eps263631.sudoku;

import java.util.ArrayList;
import java.util.Random;

public class Solver {

    int [][] board;
    ArrayList<ArrayList<Object>> emptyBoxIndex;

    int selected_row;
    int selected_column;

    Solver(){
        selected_row=-1;
        selected_column=-1;

        board = new int[9][9];
        for (int r=0; r<9; r++){
            for (int c=0; c<9; c++){
                board[r][c]=0;
            }
        }

        emptyBoxIndex=new ArrayList<>();
    }

    public void getEmptyBoxIndexes(){
        for (int r=0; r<9; r++){
            for (int c=0; c<9; c++){
                if(this.board[r][c]==0){
                    this.emptyBoxIndex.add(new ArrayList<>());
                    this.emptyBoxIndex.get(this.emptyBoxIndex.size()-1).add(r);
                    this.emptyBoxIndex.get(this.emptyBoxIndex.size()-1).add(c);
                }
            }
        }
    }


    private boolean check(int row, int col){
        if(this.board[row][col]>0){
            for(int i=0; i<9;i++){
                if (this.board[i][col]==this.board[row][col] && row != i){
                    return false;
                }

                if (this.board[row][i]==this.board[row][col] && col != i){
                    return false;
                }
            }
            int boxRow = row/3;
            int boxCol = col/3;

            for(int r=boxRow*3; r<boxRow*3 + 3; r++){
                for(int c=boxCol*3; c<boxCol*3 + 3; c++){
                    if(this.board[r][c] == this.board[row][col] && row != r && col!=c){
                        return false;
                    }
                }
            }
        }
        return true;
    }


    public boolean solve(SudokuBoard display){    //pierwotna
        int row = -1;
        int col = -1;

        for(int r=0;r<9;r++){
            for(int c=0;c<9;c++){
                if(this.board[r][c] == 0){
                    row=r;
                    col=c;
                    break;
                }
            }
        }

        if(row == -1 || col == -1){
            return true;
        }

        for(int i=1; i<10;i++){
            this.board[row][col] = i;
            display.invalidate();

            if(check(row,col)){
                if(solve(display)){
                    return true;
                }
            }
            this.board[row][col]=0;
        }
        return false;
    }






    public void resetBoard(){
        for (int r=0; r<9; r++){
            for (int c=0; c<9; c++){
                board[r][c]=0;
            }
        }
        this.emptyBoxIndex = new ArrayList<>();
    }

    public void setNumberPos(int num) {
        if (this.selected_row != -1 && this.selected_column != -1) {
            if(this.board[this.selected_row-1][this.selected_column-1] == num){
                this.board[this.selected_row-1][this.selected_column-1] = 0;
            }
            else{
                this.board[this.selected_row-1][this.selected_column-1] = num;
                if (!check(this.selected_row-1, this.selected_column-1)){
                    this.board[this.selected_row-1][this.selected_column-1] = 0;
                }
            }
        }
    }


    public enum DifficultyLevel {
        EASY,
        MEDIUM,
        HARD,
        EXPERT,
        MASTER
    }

    private DifficultyLevel difficultyLevel = DifficultyLevel.MEDIUM;

    public void setDifficultyLevel(DifficultyLevel level) {
        this.difficultyLevel = level;
    }

    private int getDifficultyCellCount() {
        switch (difficultyLevel) {
            case EASY:
                return 43;
            case MEDIUM:
                return 51;
            case HARD:
                return 53;
            case EXPERT:
                return 55;
            case MASTER:
                return 58;
            default:
                return 51;
        }
    }






    public void fillRandomCells(SudokuBoard display) {
        resetBoard();
        Random random = new Random();
        for (int i = 0; i < 3; i++) {
            int row = random.nextInt(9);
            int col = random.nextInt(9);
            int num = random.nextInt(9) + 1; // Random number between 1 and 9
            this.board[row][col] = num;
        }

        solve(display);

        int removedCells = 0;
        while (removedCells < getDifficultyCellCount()) {
            int row = random.nextInt(9);
            int col = random.nextInt(9);
            if (this.board[row][col] != 0) {
                this.board[row][col] = 0;
                removedCells++;
            }
        }
        display.invalidate();
    }

    private ArrayList<Integer> getValidCandidates(int row, int col) {
        ArrayList<Integer> candidates = new ArrayList<>();
        for (int num = 1; num <= 9; num++) {
            this.board[row][col] = num;
            if (check(row, col)) {
                candidates.add(num);
            }
        }
        this.board[row][col] = 0; // Przywracamy poprzednią wartość
        return candidates;
    }


    public int [][] getBoard(){
        return this.board;
    }

    public ArrayList<ArrayList<Object>> getEmptyBoxIndex(){
        return this.emptyBoxIndex;
    }

    public int getSelectedRow(){
        return selected_row;
    }
    public int getSelectedColumn(){
        return selected_column;
    }

    public void setSelectedRow(int r){
        selected_row=r;
    }

    public void setSelectedColumn(int c){
        selected_column=c;
    }
}