/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sakis.anthologium.photos;

import com.jfoenix.controls.JFXTextField;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
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
    @FXML
    private Label photoInfoLabel;


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.model = new PhotoModel();
        tilePane.setPrefRows(1);
        populateTilePane();
        populateBigImageView(new Image((InputStream)new ByteArrayInputStream(this.model.getCurrentPhotoData().getImage())));
        photoInfoLabel.setText(this.model.getPhotoInfo(this.model.getCurrentPhotoData().getPhotoId()));
    }    
    
    
    /**
     * Populate the tilePane with photos from database
     * Use therefore the ObservableList<Image> images from the Model 
     * the Model updates the list by its function readPhotoDataList
     * which is called by the listener of the searchStringProperty, 
     * which in turn is updated by the searchTextField handler
     * 
     * @param images 
     */
    public void populateTilePane() {
        this.tilePane.getChildren().clear();
        this.tilePane.setPrefColumns(this.model.getPhotoDataList().size());
        for (int i=0; i< this.model.getPhotoDataList().size(); i++) {
            InputStream is = new ByteArrayInputStream(this.model.getPhotoDataList().get(i).getImage());
            int photoId = this.model.getPhotoDataList().get(i).getPhotoId();
            Image img = new Image(is);
            ImageView iv = new ImageView(img);
            iv.setFitHeight(150);
            iv.setFitWidth(150);
            iv.setPreserveRatio(true);
            iv.setOnMouseClicked(e->{
                populateBigImageView(img);
                photoInfoLabel.setText(this.model.getPhotoInfo(photoId));
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
            populateTilePane();
            populateBigImageView(new Image((InputStream)new ByteArrayInputStream(this.model.getCurrentPhotoData().getImage())));
            photoInfoLabel.setText(this.model.getPhotoInfo(this.model.getCurrentPhotoData().getPhotoId()));
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
