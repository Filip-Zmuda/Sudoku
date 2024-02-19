package com.example.eps263631.sudoku;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private SudokuBoard gameBoard;
    private Solver gameBoardSolver;
    private Button solveBTN;
    private Button newGameBTN;
    private Spinner difficultySpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gameBoard = findViewById(R.id.SudokuBoard);
        gameBoardSolver = gameBoard.getSolver();

        solveBTN=findViewById(R.id.solveButton);
        newGameBTN=findViewById(R.id.newGameButton);
        difficultySpinner = findViewById(R.id.difficultySpinner);

        difficultySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                switch (position) {
                    case 0:
                        gameBoardSolver.setDifficultyLevel(Solver.DifficultyLevel.EASY);
                        break;
                    case 1:
                        gameBoardSolver.setDifficultyLevel(Solver.DifficultyLevel.MEDIUM);
                        break;
                    case 2:
                        gameBoardSolver.setDifficultyLevel(Solver.DifficultyLevel.HARD);
                        break;
                    case 3:
                        gameBoardSolver.setDifficultyLevel(Solver.DifficultyLevel.EXPERT);
                        break;
                    case 4:
                        gameBoardSolver.setDifficultyLevel(Solver.DifficultyLevel.MASTER);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });
    }

    public void BTNOnePress (View view){
        gameBoardSolver.setNumberPos(1);
        gameBoard.invalidate();
    }

    public void BTNTwoPress (View view){
        gameBoardSolver.setNumberPos(2);
        gameBoard.invalidate();
    }

    public void BTNThreePress (View view){
        gameBoardSolver.setNumberPos(3);
        gameBoard.invalidate();
    }

    public void BTNFourPress (View view){
        gameBoardSolver.setNumberPos(4);
        gameBoard.invalidate();
    }

    public void BTNFivePress (View view){
        gameBoardSolver.setNumberPos(5);
        gameBoard.invalidate();
    }

    public void BTNSixPress (View view){
        gameBoardSolver.setNumberPos(6);
        gameBoard.invalidate();
    }

    public void BTNSevenPress (View view){
        gameBoardSolver.setNumberPos(7);
        gameBoard.invalidate();
    }

    public void BTNEightPress (View view){
        gameBoardSolver.setNumberPos(8);
        gameBoard.invalidate();
    }

    public void BTNNinePress (View view){
        gameBoardSolver.setNumberPos(9);
        gameBoard.invalidate();
    }


    public void solve(View view){
        if(solveBTN.getText().toString().equals(getString(R.string.solve))){
            solveBTN.setText(getString(R.string.clear));

            gameBoardSolver.getEmptyBoxIndexes();

            SolveBoardThread solveBoardThread = new SolveBoardThread();

            new Thread(solveBoardThread).start();

            newGameBTN.setEnabled(false);

            gameBoard.invalidate();
        }
        else{
            solveBTN.setText(getString(R.string.solve));

            gameBoardSolver.resetBoard();
            gameBoard.invalidate();
        }
        newGameBTN.setEnabled(true);
    }

    public void newGame(View view) {
        solveBTN.setText(getString(R.string.solve));

        int selectedPosition = difficultySpinner.getSelectedItemPosition();
        switch (selectedPosition) {
            case 0:
                gameBoardSolver.setDifficultyLevel(Solver.DifficultyLevel.EASY);
                break;
            case 1:
                gameBoardSolver.setDifficultyLevel(Solver.DifficultyLevel.MEDIUM);
                break;
            case 2:
                gameBoardSolver.setDifficultyLevel(Solver.DifficultyLevel.HARD);
                break;
            case 3:
                gameBoardSolver.setDifficultyLevel(Solver.DifficultyLevel.EXPERT);
                break;
            case 4:
                gameBoardSolver.setDifficultyLevel(Solver.DifficultyLevel.MASTER);
                break;
        }

        gameBoardSolver.fillRandomCells(gameBoard);
        gameBoard.invalidate();
    }

    class SolveBoardThread implements Runnable{
        @Override
        public void run(){
            gameBoardSolver.solve(gameBoard);
        }
    }
}