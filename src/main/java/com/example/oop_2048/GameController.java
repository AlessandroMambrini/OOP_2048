package com.example.oop_2048;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.util.Random;

public class GameController {

    @FXML private Label label00;
    @FXML private Label label33;
    @FXML private Label label01;
    @FXML private Label label12;
    @FXML private Label label11;
    @FXML private Label label10;
    @FXML private Label label03;
    @FXML private Label label02;
    @FXML private Label label32;
    @FXML private Label label31;
    @FXML private Label label30;
    @FXML private Label label23;
    @FXML private Label label22;
    @FXML private Label label21;
    @FXML private Label label20;
    @FXML private Label label13;
    @FXML private Button background;
    @FXML private Label current_score;
    @FXML private Label high_score;
    @FXML private final int GRID_DIMENSION = 16;
    @FXML private final int NUM_COL = 4;

    @FXML public Integer[] grid = new Integer[GRID_DIMENSION];

    @FXML
    public void initialize(){
        current_score.setText("0");
        high_score.setText("0");
        for (int i = 0; i < GRID_DIMENSION; i++) {
            grid[i] = 0;
            write_label(i,grid[i]);
        }
        init_number();
    }
    @FXML
    private void init_number() {
        Random random = new Random();
        int first, second;
        first = random.nextInt(0,GRID_DIMENSION);
        do {
            second = random.nextInt(0,GRID_DIMENSION);
        } while (first == second);
        grid[first] = random.nextInt(1,3) * 2;
        write_label(first,grid[first]);
        grid[second] = random.nextInt(1,3) * 2;
        write_label(second,grid[second]);
    }

    @FXML
    public Label grid_to_label (int cell){
        return switch (cell){
            case 0 -> label00;
            case 1 -> label01;
            case 2 -> label02;
            case 3 -> label03;
            case 4 -> label10;
            case 5 -> label11;
            case 6 -> label12;
            case 7 -> label13;
            case 8 -> label20;
            case 9 -> label21;
            case 10 -> label22;
            case 11 -> label23;
            case 12 -> label30;
            case 13 -> label31;
            case 14 -> label32;
            case 15 -> label33;
            default -> throw new IllegalStateException("Unexpected value: " + cell);
        };
    }

    @FXML
    private void  write_label(int cell, int value){
        Label label = grid_to_label(cell);
        if (value == 0){
            label.setText("");
        } else {
        String string = value + "";
        label.setText(string);
        }
    }

    @FXML
    public void chosen_direction(KeyEvent keyEvent) {
        switch (keyEvent.getCode()){
            case KeyCode.UP:
                current_score.setText("Up");
                movement_up();
                break;
            case KeyCode.DOWN:
                current_score.setText("Down");
                movement_down();
                break;
            case KeyCode.LEFT:
                current_score.setText("Left");
                break;
            case KeyCode.RIGHT:
                current_score.setText("Right");
                break;
        }
    }

    private void movement_down() {
        for (int i = GRID_DIMENSION - NUM_COL - 1; i >= 0; i--) {
            if (i > (NUM_COL * 2) - 1 && grid[i + NUM_COL] == 0){
                print_and_set_new_grid_for_down(i,1);
            }
            //--------------------------------------------------------------------------
            if (i < NUM_COL * 2 && i >= NUM_COL){
                int counter = 0;
                for (int j = 1; j < 3; j++) {
                    if (grid[i + NUM_COL * j] == 0){
                        counter++;
                    }
                }
                print_and_set_new_grid_for_down(i,counter);
            }
            //---------------------------------------------------------------------------
            if (i < NUM_COL){
                int counter = 0;
                for (int j = 1; j < 4; j++) {
                    if (grid[i + NUM_COL * j] == 0){
                        counter++;
                    }
                }
                print_and_set_new_grid_for_down(i,counter);
            }
        }
    }

    private void print_and_set_new_grid_for_down(int cell, int counter) {
        if (counter != 0){
            grid[cell + NUM_COL * counter] = grid[cell];
            grid[cell] = 0;
            write_label(cell, grid[cell]);
            write_label(cell + NUM_COL * counter, grid[cell + NUM_COL * counter]);
        }
    }

    @FXML
    private void movement_up() {
        for (int i = NUM_COL; i < GRID_DIMENSION; i++) {
            if (i < NUM_COL * 2 && grid[i - NUM_COL] == 0){
                print_and_set_new_grid_for_up(i,1);
            }
            //----------------------------------------------------------------------
            if (i >= NUM_COL * 2 && i < NUM_COL * 3){
                int counter = 0;
                for (int j = 1; j < 3; j++) {
                    if (grid[i - NUM_COL * j] == 0){
                        counter++;
                    }
                }
                print_and_set_new_grid_for_up(i, counter);
            }
            //-------------------------------------------------------------------------
            if (i >= NUM_COL * 3){
                int counter = 0;
                for (int j = 1; j < 4; j++) {
                    if (grid[i - NUM_COL * j] == 0){
                        counter++;
                    }
                }
                print_and_set_new_grid_for_up(i, counter);
            }
        }
    }

    private void print_and_set_new_grid_for_up(int cell, int counter) {
        if (counter != 0){
            grid[cell - NUM_COL * counter] = grid[cell];
            grid[cell] = 0;
            write_label(cell, grid[cell]);
            write_label(cell - NUM_COL * counter, grid[cell - NUM_COL * counter]);
        }
    }
}