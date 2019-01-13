/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sakis.anthologium.main;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;

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
    private void handlePhotosButton(ActionEvent event) throws IOException{
        //borderPane.setCenter(null);
        StackPane fxml = (StackPane)FXMLLoader.load(getClass().getResource("/com/sakis/anthologium/photos/Photos.fxml"));
        
        borderPane.setCenter(fxml);
    }

    @FXML
    private void handleActorsButton(ActionEvent event) {
        borderPane.setCenter(null);
    }

    @FXML
    private void handleSongsButton(ActionEvent event) {
        borderPane.setCenter(null);
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
