/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sakis.anthologium.main;

import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

/**
 *
 * @author Sakis Tosounidis <sakis.tosounidis@gmail.com>
 */
public class AnthologiumController implements Initializable {

    @FXML
    private JFXButton photoOverviewButton;
    @FXML
    private JFXButton actorsButton;
    @FXML
    private JFXButton songsButton;
    @FXML
    private JFXButton exitButton;
    @FXML
    private JFXButton minimizeButton;
    @FXML
    private BorderPane borderPane;
    
    private AnthologiumModel anthologiumModel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.anthologiumModel = new AnthologiumModel();
    }
    
    @FXML
    private void handlePhotosButton(ActionEvent event) {
    }

    @FXML
    private void handleActorsButton(ActionEvent event) {
    }

    @FXML
    private void handleSongsButton(ActionEvent event) {
    }

    @FXML
    private void handleExitButton(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    private void handleMinimizeButton(ActionEvent event) {
        Anthologium.stage.setIconified(true);
    }

    
}
