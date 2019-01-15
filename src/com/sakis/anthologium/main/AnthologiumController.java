/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sakis.anthologium.main;

import com.jfoenix.controls.JFXButton;
import com.sakis.anthologium.photos.PhotosController;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

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
    
    private FXMLLoader photosLoader;
    private PhotosController photosController;
    private StackPane photosStackPane;

    @Override
    public void initialize(URL location, ResourceBundle resources){
        this.anthologiumModel = new AnthologiumModel();
        
        // initialize ONCE the content of the menu buttons, so we do not have to load
        // everytime new content with new controller etc.... 
        // so, if you switch pages eg from photos to actoρs , when you come back, 
        // you woll continue from the point on you left ... 
        initPhotos();
        
    }
    
    /**
     * Initializes the loader, the pane and the controller for Photos
     */
    private void initPhotos() {
        photosLoader = new FXMLLoader(getClass().getResource("/com/sakis/anthologium/photos/Photos.fxml"));   
        try {
            photosStackPane  = (StackPane)photosLoader.load();
        } catch (IOException ex) {
            Logger.getLogger(AnthologiumController.class.getName()).log(Level.SEVERE, null, ex);
        }
        photosController  = photosLoader.getController();
    }
    
    @FXML
    private void handlePhotosButton(ActionEvent event) throws IOException{
        // StackPane fxml = (StackPane)FXMLLoader.load(getClass().getResource("/com/sakis/anthologium/photos/Photos.fxml"));        
        // borderPane.setCenter(fxml);
        borderPane.setCenter(photosStackPane);
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
