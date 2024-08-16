package com.example.oop_2048;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.util.Arrays;
import java.util.Objects;
import java.util.Random;

public class GameController {

    @FXML private static final Integer WIN = 2048;
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

    @FXML private Label current_score;
    @FXML private Label high_score;
    @FXML private final int NUM_COL = 4;

    @FXML public Integer[][] grid = new Integer[NUM_COL][NUM_COL];

    @FXML
    public void initialize(){
        current_score.setText("0");
        high_score.setText("0");
        for (int i = 0; i < NUM_COL; i++) {
            Arrays.fill(grid[i],0);
        }
        init_number();
    }
    @FXML
    private void init_number() {
        Random random = new Random();
        int first_row, second_row, first_colum, second_column;
        first_row = random.nextInt(0,NUM_COL);
        first_colum = random.nextInt(0,NUM_COL);
        do {
            second_row = random.nextInt(0,NUM_COL);
            second_column = random.nextInt(0,NUM_COL);
        } while (first_row == second_row && first_colum == second_column);
        grid[first_row][first_colum] = random.nextInt(1,3) * 2;
        write_label(first_row, first_colum);
        grid[second_row][second_column] = random.nextInt(1,3) * 2;
        write_label(second_row, second_column);
    }

    @FXML
    public Label grid_to_label (int row, int col){
        int cell = row * NUM_COL + col;
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
    private void  write_label(int row, int col){
        int value = grid[row][col];
        Label label = grid_to_label(row, col);
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
                movement_left();
                break;
            case KeyCode.RIGHT:
                current_score.setText("Right");
                movement_right();
                break;
        }
        check_win_or_loss();
        add_number();
    }

    private void check_win_or_loss() {
        boolean win = false;
        for (int i = 0; i < NUM_COL; i++) {
            for (int j = 0; j < NUM_COL; j++) {
                if (Objects.equals(grid[i][j], WIN)) {
                    high_score.setText("WIN");
                }
            }
        }
    }

    private void add_number() {
        Random random = new Random();
        int row, col;
        do {
            row = random.nextInt(0, NUM_COL);
            col = random.nextInt(0, NUM_COL);
        } while (grid[row][col] != 0);
        grid[row][col] = random.nextInt(1,3) * 2;
        write_label(row, col);
    }

    private void movement_right() {
        for (int j = NUM_COL - 2; j >= 0; j--) {
            for (int i = NUM_COL - 1; i >= 0; i--) {
                int counter = 0;
                for (int k = 1; k <= NUM_COL - 1 - j; k++) {
                    if (grid[i][j + k] == 0){
                        counter++;
                    }
                }
                if (counter > 0){
                    grid[i][j + counter] = grid[i][j];
                    grid[i][j] = 0;
                    write_label(i, j);
                    write_label(i, j + counter);
                }
            }
        }
        for (int j = NUM_COL - 2; j >= 0; j--) {
            for (int i = NUM_COL - 1; i >= 0; i--) {
                if (Objects.equals(grid[i][j], grid[i][j + 1])) {
                    grid[i][j + 1] *= 2;
                    write_label(i, j + 1);
                    grid[i][j] = 0;
                    write_label(i, j);
                    for (int k = j; k > 0 ; k--) {
                        grid[i][k] = grid[i][k - 1];
                        grid[i][k - 1] = 0;
                        write_label(i, k);
                    }
                    grid[i][0] = 0;
                    write_label(i, 0);
                }
            }
        }
    }

    private void movement_left() {
        for (int j = 1; j < NUM_COL; j++) {
            for (int i = 0; i < NUM_COL; i++) {
                int counter = 0;
                for (int k = 1; k < j + 1; k++) {
                    if (grid[i][j - k] == 0){
                        counter++;
                    }
                }
                if (counter > 0){
                    grid[i][j - counter] = grid[i][j];
                    grid[i][j] = 0;
                    write_label(i, j);
                    write_label(i, j - counter);
                }
            }
        }
        for (int j = 1; j < NUM_COL; j++) {
            for (int i = 0; i < NUM_COL; i++) {
                if (Objects.equals(grid[i][j - 1], grid[i][j])){
                    grid[i][j - 1] *= 2;
                    write_label(i, j - 1);
                    grid[i][j] = 0;
                    write_label(i, j);
                    for (int k = j; k < NUM_COL - 1; k++) {
                        grid[i][k] = grid[i][k + 1];
                        grid[i][k + 1] = 0;
                        write_label(i, k);
                    }
                    grid[i][NUM_COL - 1] = 0;
                    write_label(i, NUM_COL - 1);
                }
            }
        }
    }

    private void movement_down() {
        for (int i = NUM_COL - 2; i >= 0; i--) {
            for (int j = NUM_COL - 1; j >= 0; j--) {
                int counter = 0;
                for (int k = 1; k <= NUM_COL - 1 - i; k++) {
                    if (grid[i + k][j] == 0){
                        counter++;
                    }
                }
                if (counter > 0){
                    grid[i + counter][j] = grid[i][j];
                    grid[i][j] = 0;
                    write_label(i, j);
                    write_label(i + counter, j);
                }
            }
        }
        for (int i = NUM_COL - 2; i >= 0; i--) {
            for (int j = NUM_COL - 1; j >= 0; j--) {
                if (Objects.equals(grid[i][j], grid[i + 1][j])) {
                    grid[i + 1][j] *= 2;
                    write_label(i + 1, j);
                    grid[i][j] = 0;
                    write_label(i, j);
                    for (int k = i; k > 0 ; k--) {
                        grid[k][j] = grid[k - 1][j];
                        grid[k - 1][j] = 0;
                        write_label(k, j);
                    }
                    grid[0][j] = 0;
                    write_label(0, j);
                }
            }
        }
    }

    @FXML
    private void movement_up() {
        for (int i = 1; i < NUM_COL; i++) {
            for (int j = 0; j < NUM_COL; j++) {
                int counter = 0;
                for (int k = 1; k < i + 1; k++) {
                    if (grid[i - k][j] == 0){
                        counter++;
                    }
                }
                if (counter > 0) {
                    grid[i - counter][j] = grid[i][j];
                    grid[i][j] = 0;
                    write_label(i, j);
                    write_label(i - counter, j);
                }
            }
        }
        for (int i = 1; i < NUM_COL; i++) {
            for (int j = 0; j < NUM_COL; j++) {
                if (Objects.equals(grid[i - 1][j], grid[i][j])){
                    grid[i - 1][j] *= 2;
                    write_label(i - 1, j);
                    grid[i][j] = 0;
                    write_label(i,j);
                    for (int k = i; k < NUM_COL - 1; k++) {
                        grid[k][j] = grid[k + 1][j];
                        grid[k + 1][j] = 0;
                        write_label(k, j);
                    }
                    grid[NUM_COL - 1][j] = 0;
                    write_label(NUM_COL - 1, j);
                }
            }
        }
    }
}