/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sakis.anthologium.photos;

import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author Sakis Tosounidis <sakis.tosounidis@gmail.com>
 */
public class PhotosController implements Initializable {
       
    private PhotoModel model;

    @FXML
    private HBox root;
    @FXML
    private VBox leftVBox;
    @FXML
    private JFXTextField searchTextField;
    @FXML
    private VBox rightVBox;
    @FXML
    private TilePane tilePane;
    @FXML
    private ImageView bigImageView;


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.model = new PhotoModel();
        tilePane.setPrefRows(1);
        populateTilePane(model.getCurrentImages());
        populateBigImageView(this.model.getCurrentImage());
    }    
    
    
    /**
     * Populate the tilePane with photos from database
     * Use therefore the ObservableList<Image> images from the Model 
     * the Model updates the list by its function readPhotoDataList
     * which is called by the listener of the searchStringProperty, 
     * which in turn is updated by the searchTextField handler
     * 
     * This parameter (ObservableList<Image> images) could also be
     * omitted, because it is always the same (from the Model)
     * Maybe I will drop it in the future
     * 
     * @param images 
     */
    public void populateTilePane(ObservableList<Image> images) {
        this.tilePane.getChildren().clear();
        this.tilePane.setPrefColumns(images.size());
        for (int i=0; i<images.size(); i++) {
            Image img = images.get(i);
            ImageView iv = new ImageView(img);
            iv.setFitHeight(150);
            iv.setFitWidth(150);
            iv.setPreserveRatio(true);
            iv.setOnMouseClicked(e->{
                populateBigImageView(img);
            });
            this.tilePane.getChildren().add(iv);
        }
    }

    /**
     * React on ENTER when inputting a searchString 
     * update the searchStringProperty of the model, which then
     * calls the listener to update the current photo list....
     * @param event 
     */
    @FXML
    private void handleSearchFieldKeyPressed(KeyEvent event) {
        if (event.getCode()==KeyCode.ENTER) {
            this.model.setSearchStringProperty(searchTextField.getText());
            populateTilePane(this.model.getCurrentImages());
            populateBigImageView(this.model.getCurrentImage());
        }
    }
    
    /**
     * Populate the bigImageView with the current image 
     * got it from the model
     * this parameter maybe drop in future, because it is 
     * always the same (from the model)
     * @param image 
     */
    private void populateBigImageView (Image image) {
        this.bigImageView.setImage(image);
    }

}
