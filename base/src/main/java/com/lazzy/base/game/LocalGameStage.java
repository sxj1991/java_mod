package com.lazzy.base.game;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;



public class LocalGameStage extends Stage {
    private final int size = 15;
    private final int margin = 50;
    private final int gap = 40;
    private boolean blackFirst = true;
    private Chess[] chessArr = new Chess[size * size];

    public LocalGameStage(Stage thisStage) {

        setResizable(false);

        setTitle("五子棋");

        Pane pane = new Pane();
        pane.setBackground(new Background(new BackgroundFill(Color.CHOCOLATE, null, null)));

        int width = 660;
        int height = 700;
        Scene scene = new Scene(pane, width, height);

        setScene(scene);


        for (int i = 0; i < size; i++) {
            Line line = new Line(margin, margin + i * gap, margin + gap * (size - 1), margin + i * gap);
            pane.getChildren().add(line);
        }

        for (int i = 0; i < size; i++) {
            Line line = new Line(margin + i * gap, margin, margin + i * gap, margin + gap * (size - 1));
            pane.getChildren().add(line);
        }

        Button restartButton = new Button();
        restartButton.setText("重新开始");
        restartButton.setPrefSize(150, 50);
        restartButton.setLayoutX(90);
        restartButton.setLayoutY(635);
        pane.getChildren().add(restartButton);

        Button stopButton = new Button();
        stopButton.setText("停止");
        stopButton.setPrefSize(150, 50);
        stopButton.setLayoutX(420);
        stopButton.setLayoutY(635);
        pane.getChildren().add(stopButton);
        restartButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                chessArr = new Chess[size * size];
                blackFirst = true;
                pane.getChildren().removeIf(c -> c.getClass() == Circle.class);
            }
        });
        stopButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                thisStage.close();
                System.exit(0);
            }
        });

        pane.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 1) {

                    int x = (int) event.getX();
                    int y = (int) event.getY();

                    int chessX = (x - margin + gap / 2) / gap * gap + margin;
                    int chessY = (y - margin + gap / 2) / gap * gap + margin;
                    if (chessX > margin + (size - 1) * gap || chessY > margin + (size - 1) * gap) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.show();
                        return;
                    }
                    if (hasChess(chessX, chessY)) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.show();
                        return;
                    }

                    Circle circle = new Circle();
                    circle.setCenterX(chessX);
                    circle.setCenterY(chessY);
                    circle.setRadius(16);

                    Color color = null;
                    if (blackFirst) {
                        color = Color.BLACK;
                        blackFirst = false;
                    } else {
                        color = Color.WHITE;
                        blackFirst = true;
                    }
                    circle.setFill(color);
                    pane.getChildren().add(circle);

                    for (int i = 0; i < chessArr.length; i++) {
                        if (chessArr[i] == null) {
                            chessArr[i] = new Chess(chessX, chessY, color);
                            break;
                        }
                    }

                    if (isWin(chessX, chessY, color)) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setContentText(color.equals(Color.BLACK) ? "黑棋胜" : "白棋胜");
                        alert.show();

                        pane.setOnMouseClicked(null);
                    }
                }
            }
        });
    }


    public boolean isWin(int x, int y, Color color) {

        int count = 1;

        for (int i = 1; i <= 4; i++) {

            int newX = x - (i * gap);
            int newY = y;
            if (hasChess(newX, newY, color)) {
                count++;
            } else {
                break;
            }
        }

        for (int i = 1; i <= 4; i++) {

            int newX = x + (i * gap);
            int newY = y;
            if (hasChess(newX, newY, color)) {
                count++;
            } else {
                break;
            }
        }
        if (count >= 5) {
            return true;
        }

        count = 1;

        for (int i = 1; i <= 4; i++) {
            int newX = x;
            int newY = y - (i * gap);
            if (hasChess(newX, newY, color)) {
                count++;
            } else {
                break;
            }
        }

        for (int i = 1; i <= 4; i++) {
            int newX = x;
            int newY = y + (i * gap);
            if (hasChess(newX, newY, color)) {
                count++;
            } else {
                break;
            }
        }
        if (count >= 5) {
            return true;
        }

        count = 1;

        for (int i = 1; i <= 4; i++) {
            int newX = x - (i * gap);
            int newY = y - (i * gap);
            if (hasChess(newX, newY, color)) {
                count++;
            } else {
                break;
            }
        }
        for (int i = 1; i <= 4; i++) {
            int newX = x + (i * gap);
            int newY = y + (i * gap);
            if (hasChess(newX, newY, color)) {
                count++;
            } else {
                break;
            }
        }
        if (count >= 5) {
            return true;
        }

        count = 1;

        for (int i = 1; i <= 4; i++) {
            int newX = x - (i * gap);
            int newY = y + (i * gap);
            if (hasChess(newX, newY, color)) {
                count++;
            } else {
                break;
            }
        }
        for (int i = 1; i <= 4; i++) {
            int newX = x + (i * gap);
            int newY = y - (i * gap);
            if (hasChess(newX, newY, color)) {
                count++;
            } else {
                break;
            }
        }
        if (count >= 5) {
            return true;
        }
        return false;
    }


    public boolean hasChess(int x, int y) {
        boolean hasChess = false;
        for (int i = 0; i < chessArr.length; i++) {
            if (chessArr[i] == null) {
                break;
            }
            if (chessArr[i].getX() == x && chessArr[i].getY() == y) {
                hasChess = true;
                break;
            }
        }
        return hasChess;
    }


    public boolean hasChess(int x, int y, Color color) {
        boolean hasChess = false;
        for (int i = 0; i < chessArr.length; i++) {
            if (chessArr[i] == null) {
                break;
            }
            if (chessArr[i].getX() == x && chessArr[i].getY() == y && chessArr[i].getColor().equals(color)) {
                hasChess = true;
                break;
            }
        }
        return hasChess;
    }
}
