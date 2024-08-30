package com.example.oop_2048;

import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.util.HashMap;
import java.util.Objects;
import java.util.Random;

import static java.lang.Integer.parseInt;
import static javafx.scene.control.ButtonType.OK;

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
    private Label[][] labels = new Label[NUM_COL][NUM_COL];
    private final HashMap<Integer, Color> colors = new HashMap<>();
    @FXML
    public void initialize(){
        current_score.setText("0");
        high_score.setText("0");
        labels = new Label[][]{ {label00, label01, label02, label03},
                                {label10, label11, label12, label13},
                                {label20, label21, label22, label23},
                                {label30, label31, label32, label33}};
        reset_grid();
        load_colors();
        init_number();
    }

    /**
     * Inizializza ad ogni possibile valore di ogni Label un colore di sfondo
     */
    private void load_colors() {
        colors.put(2, Color.rgb(238,199,107));
        colors.put(4, Color.rgb(240,190,41));
        colors.put(8, Color.rgb(192,152,43));
        colors.put(16, Color.rgb(234,133,83));
        colors.put(32, Color.rgb(228,104,40));
        colors.put(64, Color.rgb(183,84,32));
        colors.put(128, Color.rgb(229,74,80));
        colors.put(256, Color.rgb(215,14,23));
        colors.put(512, Color.rgb(172,25,23));
        colors.put(1024, Color.rgb(83,0,112));
        colors.put(2048, Color.rgb(40,1,52));
    }
    private void assign_colors() {
        for (int i = 0; i < NUM_COL; i++) {
            for (int j = 0; j < NUM_COL; j++) {
                int value = 0;
                if (!Objects.equals(labels[i][j].getText(), "")){
                    value = Integer.parseInt(labels[i][j].getText());
                }
                labels[i][j].setBackground(Background.fill(colors.get(value)));
            }
        }
    }
    @FXML
    private void reset_grid() {
        for (int i = 0; i < NUM_COL; i++) {
            for (int j = 0; j < NUM_COL; j++) {
                labels[i][j].setText("");
            }
        }
    }

    /**
     * Questo metodo inizializza 2 numeri che possono essere 2 e 4, entrambi 2 o entrambi 4
     * in 2 posizioni diverse
     */
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
        labels[first_row][first_colum].setText(random.nextInt(1,3) * 2 + "");
        labels[second_row][second_column].setText(random.nextInt(1,3) * 2 + "");
        assign_colors();
    }

    @FXML
    void keyPressed(KeyEvent keyEvent) {
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
                start_a_new_game(false);
            default:
                return;
        }
        check_win_or_loss();
        assign_colors();
    }

    /**
     * Controlla che sia presente una cella con il valore 2048 e nel caso dichiara la vittoria
     * e forza il riavvio di un'altra partita
     * Se la partita non risulta vinta controlla anche che siano ancora possibili delle mosse
     * in caso contratrio dichiara la sconfitta e forza il riavvio di un'altra partita
     */
    private void check_win_or_loss() {
        boolean is_a_zero = true, end = true;
        for (int i = 0; i < NUM_COL; i++) {
            for (int j = 0; j < NUM_COL; j++) {
                if (Objects.equals(labels[i][j].getText(), "")){
                    is_a_zero = false;
                } else if (Objects.equals(Integer.parseInt(labels[i][j].getText()), WIN)) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("YOU WON");
                    alert.setHeaderText("Congratulations!!!");
                    alert.setContentText("Please start a new game");
                    alert.showAndWait();
                    start_a_new_game(true);
                }
            }
        }
        if (is_a_zero){
            for (int i = 1; i < NUM_COL - 1; i++) {
                for (int j = 0; j < NUM_COL; j++) {
                    if (Objects.equals(labels[i][j].getText(), labels[i - 1][j].getText()) || Objects.equals(labels[i][j].getText(), labels[i + 1][j].getText()) || Objects.equals(labels[j][i].getText(), labels[j][i - 1].getText()) || Objects.equals(labels[j][i].getText(), labels[j][i + 1].getText())) {
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
                start_a_new_game(true);
            }
        } else {
            add_number();
        }
    }

    /**
     * Ad ogni mossa viene aggiunto in una posizione libera un 2 o un 4
     * ogni volta che viene aggiunto un numero una piccola animazione lo fa pulsare per renderlo più visibile
     */
    private void add_number() {
        Random random = new Random();
        int row, col;
        do {
            row = random.nextInt(0, NUM_COL);
            col = random.nextInt(0, NUM_COL);
        } while (!Objects.equals(labels[row][col].getText(), ""));
        labels[row][col].setText(random.nextInt(1,3) * 2 + "");
        labels[row][col].isFocused();

        ScaleTransition bigger = new ScaleTransition(Duration.millis(250));
        bigger.setToX(1.15);
        bigger.setToY(1.15);
        bigger.setInterpolator(Interpolator.LINEAR);

        ScaleTransition smaller = new ScaleTransition(Duration.millis(250));
        smaller.setToX(1);
        smaller.setToY(1);
        smaller.setInterpolator(Interpolator.LINEAR);

        SequentialTransition transition = new SequentialTransition(
                labels[row][col],
                bigger,
                smaller
                );
        transition.play();
    }

    private void movement_right() {
        for (int j = NUM_COL - 2; j >= 0; j--) {
            for (int i = NUM_COL - 1; i >= 0; i--) {
                int counter = 0;
                if (!labels[i][j].getText().isEmpty()) {
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
                for (int k = NUM_COL - 1; k > 0; k--) {
                    if (k - counter - 1 <= 0){
                        counter = 0;
                    }
                    if (!labels[i][k].getText().isEmpty() && labels[i][k].getText().equals(labels[i][k - counter - 1].getText())){
                        labels[i][k].setText(Integer.parseInt(labels[i][k - counter - 1].getText()) * 2 + "");
                        update_current_score(Integer.parseInt(labels[i][k].getText()));
                        labels[i][k - counter - 1].setText("");
                    }
                }
            }
        }
    }
    private void movement_left() {
        for (int j = 1; j < NUM_COL; j++) {
            for (int i = 0; i < NUM_COL; i++) {
                int counter = 0;
                if (!labels[i][j].getText().isEmpty()) {
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
                for (int k = 0; k < NUM_COL - 1; k++) {
                    if (k + counter + 1 >= 3)
                        counter = 0;
                    if (!labels[i][k].getText().isEmpty() && labels[i][k].getText().equals(labels[i][k + counter + 1].getText())){
                        labels[i][k].setText(Integer.parseInt(labels[i][k + counter + 1].getText()) * 2 + "");
                        update_current_score(Integer.parseInt(labels[i][k].getText()));
                        labels[i][k + counter + 1].setText("");
                    }
                }
            }
        }
    }
    private void movement_down() {
        for (int i = NUM_COL - 2; i >= 0; i--) {
            for (int j = NUM_COL - 1; j >= 0; j--) {
                int counter = 0;
                if (!labels[i][j].getText().isEmpty()) {
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
                for (int k = NUM_COL - 1; k > 0; k--) {
                    if (k - counter - 1 <= 0){
                        counter = 0;
                    }
                    if (!labels[k][j].getText().isEmpty() && labels[k][j].getText().equals(labels[k - counter - 1][j].getText())){
                        labels[k][j].setText(Integer.parseInt(labels[k - counter - 1][j].getText()) * 2 + "");
                        update_current_score(Integer.parseInt(labels[k][j].getText()));
                        labels[k - counter - 1][j].setText("");
                    }
                }
            }
        }
    }
    @FXML
    private void movement_up() {
        for (int i = 1; i < NUM_COL; i++) {
            for (int j = 0; j < NUM_COL; j++) {
                int counter = 0;
                if (!labels[i][j].getText().isEmpty()){
                    for (int k = 1; k < i + 1; k++) {
                        if (labels[i - k][j].getText().isEmpty()){
                            counter++;
                        } else {
                            break;
                        }
                    }
                    if (counter > 0) {
                        labels[i - counter][j].setText(labels[i][j].getText());
                        labels[i][j].setText("");
                    }
                }
                for (int k = 0; k < NUM_COL - 1; k++) {
                    if (k + counter + 1 >= 3)
                        counter = 0;
                    if (!labels[k][j].getText().isEmpty() && labels[k][j].getText().equals(labels[k + counter + 1][j].getText())){
                        labels[k][j].setText(Integer.parseInt(labels[k + counter + 1][j].getText()) * 2 + "");
                        update_current_score(Integer.parseInt(labels[k][j].getText()));
                        labels[k + counter + 1][j].setText("");
                    }
                }
            }
        }
    }

    /**
     * @param forced se TRUE genera un alert che informa dell'inizio di una nuova partita
     *               se FALSE genera un alert che chiede se si vuole iniziare una partita
     * In entrambi i casi se il punteggio attuale è maggiore del punteggio massimo, aggiorna quest'ultimo
     */
    @FXML
    private void start_a_new_game(boolean forced) {
        Alert alert;
        if (forced){
            alert = new Alert(Alert.AlertType.INFORMATION);
        } else {
            alert = new Alert(Alert.AlertType.CONFIRMATION);
        }
        alert.setTitle("New game");
        alert.setHeaderText("A new game will start");
        alert.showAndWait().ifPresent(result -> {
            if(result == OK){
                if (parseInt(current_score.getText()) > parseInt(high_score.getText())){
                    high_score.setText(current_score.getText());
                }
                current_score.setText("0");
                reset_grid();
                init_number();
            }
        });
    }

    /**
     * @param points Sono il risultato di una somma eseguita,
     *               ogni volta che viene eseguita una somma il risultato di essa viene aggiunto allo score
     */
    @FXML
    private void update_current_score(int points){
        current_score.setText(parseInt(current_score.getText()) + points + "");
    }
}