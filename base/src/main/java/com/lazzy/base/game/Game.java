package com.lazzy.base.game;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.stage.Stage;

/**
 * JavaFX 界面组件
 * 目前该模块不能正常运行。
 * java11后jdk不再支持JavaFX需要额外下载jar包
 */
public class Game {
    public static void main(String[] args) {
        JFXPanel fxPanel = new JFXPanel();
        Platform.runLater(()->{
            Stage stage = new Stage();
            LocalGameStage localGameStage = new LocalGameStage(stage);
            localGameStage.show();
        } );

    }
}
