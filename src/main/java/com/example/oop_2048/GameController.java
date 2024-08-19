package com.example.oop_2048;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.util.Objects;
import java.util.Random;

import static java.lang.Integer.parseInt;

public class GameController {

    @FXML private static final int WIN = 2048;
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
    @FXML Label[][] labels = new Label[NUM_COL][NUM_COL];

    @FXML
    public void initialize(){
        current_score.setText("0");
        high_score.setText("0");
        labels = new Label[][]{ {label00, label01, label02, label03},
                                {label10, label11, label12, label13},
                                {label20, label21, label22, label23},
                                {label30, label31, label32, label33}};
        reset_grid();
        init_number();
    }

    @FXML
    private void reset_grid() {
        for (int i = 0; i < NUM_COL; i++) {
            for (int j = 0; j < NUM_COL; j++) {
                labels[i][j].setText("");
            }
        }
    }

    @FXML
    private void init_number() {
        /*Random random = new Random();
        int first_row, second_row, first_colum, second_column;
        first_row = random.nextInt(0,NUM_COL);
        first_colum = random.nextInt(0,NUM_COL);
        do {
            second_row = random.nextInt(0,NUM_COL);
            second_column = random.nextInt(0,NUM_COL);
        } while (first_row == second_row && first_colum == second_column);
        labels[first_row][first_colum].setText(random.nextInt(1,3) * 2 + "");
        labels[second_row][second_column].setText(random.nextInt(1,3) * 2 + "");*/
        labels[3][0].setText("128");
        labels[2][0].setText("128");
    }

    @FXML
    public void chosen_direction(KeyEvent keyEvent) {
        switch (keyEvent.getCode()){
            case KeyCode.UP:
                movement_up();
                break;
            case KeyCode.DOWN:
                movement_down();
                break;
            case KeyCode.LEFT:
                movement_left();
                break;
            case KeyCode.RIGHT:
                movement_right();
                break;
            case KeyCode.R:
                start_a_new_game();
            default:
                return;
        }
        check_win_or_loss();
    }

    private void check_win_or_loss() {
        boolean is_a_zero = true, end = true;
        for (int i = 0; i < NUM_COL; i++) {
            for (int j = 0; j < NUM_COL; j++) {
                if (Objects.equals(labels[i][j].getText(), "")){
                    is_a_zero = false;
                } else if (Objects.equals(Integer.parseInt(labels[i][j].getText()), WIN)) {
                    high_score.setText("WIN");
                }
            }
        }
        if (is_a_zero){
            for (int i = 1; i < NUM_COL - 1; i++) {
                for (int j = 0; j < NUM_COL; j++) {
                    if (Objects.equals(labels[i][j].getText(), labels[i - 1][j].getText()) || Objects.equals(labels[i][j].getText(), labels[i + 1][j].getText())) {
                        end = false;
                        break;
                    }
                }
            }
            if (end){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("YOU LOST");
                alert.setHeaderText("No more moves are possible");
                alert.setContentText("Please start a new game");
                alert.showAndWait();
                start_a_new_game();
            }
        } /*else {
            add_number();
        }*/
    }

    private void add_number() {
        Random random = new Random();
        int row, col;
        do {
            row = random.nextInt(0, NUM_COL);
            col = random.nextInt(0, NUM_COL);
        } while (!Objects.equals(labels[row][col].getText(), ""));
        labels[row][col].setText(random.nextInt(1,3) * 2 + "");
    }

    private void movement_right() {
        for (int j = NUM_COL - 2; j >= 0; j--) {
            for (int i = NUM_COL - 1; i >= 0; i--) {
                int counter = 0;
                for (int k = 1; k <= NUM_COL - 1 - j; k++) {
                    if (Objects.equals(labels[i][j + k].getText(), "")){
                        counter++;
                    }
                }
                if (counter > 0){
                    labels[i][j + counter].setText(labels[i][j].getText());
                    labels[i][j].setText("");
                }
            }
        }
        for (int j = NUM_COL - 2; j >= 0; j--) {
            for (int i = NUM_COL - 1; i >= 0; i--) {
                if (Objects.equals(labels[i][j].getText(), ""))
                    continue;
                if (Objects.equals(labels[i][j].getText(), labels[i][j + 1].getText())) {
                    labels[i][j + 1].setText(Integer.parseInt(labels[i][j + 1].getText()) * 2 + "");
                    update_current_score(Integer.parseInt(labels[i][j + 1].getText()));
                    labels[i][j].setText("");
                    for (int k = j; k > 0 ; k--) {
                        labels[i][k].setText(labels[i][k - 1].getText());
                        labels[i][k - 1].setText("");
                    }
                    labels[i][0].setText("");
                }
            }
        }
    }

    private void movement_left() {
        for (int j = 1; j < NUM_COL; j++) {
            for (int i = 0; i < NUM_COL; i++) {
                int counter = 0;
                for (int k = 1; k < j + 1; k++) {
                    if (Objects.equals(labels[i][j - k].getText(), "")){
                        counter++;
                    }
                }
                if (counter > 0){
                    labels[i][j - counter].setText(labels[i][j].getText());
                    labels[i][j].setText("");
                }
            }
        }
        for (int j = 1; j < NUM_COL; j++) {
            for (int i = 0; i < NUM_COL; i++) {
                if (Objects.equals(labels[i][j].getText(), ""))
                    continue;
                if (Objects.equals(labels[i][j - 1].getText(), labels[i][j].getText())){
                    labels[i][j - 1].setText(Integer.parseInt(labels[i][j - 1].getText()) * 2 + "");
                    update_current_score(Integer.parseInt(labels[i][j - 1].getText()));
                    labels[i][j].setText("");
                    for (int k = j; k < NUM_COL - 1; k++) {
                        labels[i][k].setText(labels[i][k + 1].getText());
                        labels[i][k + 1].setText("");
                    }
                    labels[i][NUM_COL - 1].setText("");
                }
            }
        }
    }

    private void movement_down() {
        for (int i = NUM_COL - 2; i >= 0; i--) {
            for (int j = NUM_COL - 1; j >= 0; j--) {
                int counter = 0;
                for (int k = 1; k <= NUM_COL - 1 - i; k++) {
                    if (Objects.equals(labels[i + k][j].getText(), "")){
                        counter++;
                    }
                }
                if (counter > 0){
                    labels[i + counter][j].setText(labels[i][j].getText());
                    labels[i][j].setText("");
                }
            }
        }
        for (int i = NUM_COL - 2; i >= 0; i--) {
            for (int j = NUM_COL - 1; j >= 0; j--) {
                if (Objects.equals(labels[i][j].getText(), ""))
                    continue;
                if (Objects.equals(labels[i][j].getText(), labels[i + 1][j].getText())) {
                    labels[i + 1][j].setText(Integer.parseInt(labels[i + 1][j].getText()) * 2 + "");
                    update_current_score(Integer.parseInt(labels[i + 1][j].getText()));
                    labels[i][j].setText("");
                    for (int k = i; k > 0 ; k--) {
                        labels[k][j].setText(labels[k - 1][j].getText());
                        labels[k - 1][j].setText("");
                    }
                    labels[0][j].setText("");
                }
            }
        }
    }

    @FXML
    private void movement_up() {
        for (int i = 1; i < NUM_COL; i++) {
            for (int j = 0; j < NUM_COL; j++) {
                if (Objects.equals(labels[i][j].getText(), ""))
                    continue;
                int counter = 0;
                for (int k = 1; k < i + 1; k++) {
                    if (Objects.equals(labels[i - k][j].getText(), "")){
                        counter++;
                    }
                }
                if (counter > 0) {
                    labels[i - counter][j].setText(labels[i][j].getText());
                    labels[i][j].setText("");
                }
            }
        }
        for (int i = 1; i < NUM_COL; i++) {
            for (int j = 0; j < NUM_COL; j++) {
                if (Objects.equals(labels[i][j].getText(), ""))
                    continue;
                if (Objects.equals(labels[i - 1][j].getText(), labels[i][j].getText())){
                    labels[i - 1][j].setText(Integer.parseInt(labels[i - 1][j].getText()) * 2 + "");
                    update_current_score(Integer.parseInt(labels[i - 1][j].getText()));
                    labels[i][j].setText("");
                    for (int k = i; k < NUM_COL - 1; k++) {
                        labels[k][j].setText(labels[k + 1][j].getText());
                        labels[k + 1][j].setText("");
                    }
                    labels[NUM_COL - 1][j].setText("");
                }
            }
        }
    }

    @FXML
    private void start_a_new_game() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("New game");
        alert.setHeaderText("A new game will start");
        alert.showAndWait();
        if (parseInt(current_score.getText()) > parseInt(high_score.getText())){
            high_score.setText(current_score.getText());
        }
        current_score.setText("0");
        reset_grid();
        init_number();
    }

    @FXML
    private void update_current_score(int points){
        current_score.setText(parseInt(current_score.getText()) + points + "");
    }
}