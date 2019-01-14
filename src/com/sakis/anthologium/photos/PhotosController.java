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

    @FXML
    private void handleSearchFieldKeyPressed(KeyEvent event) {
        if (event.getCode()==KeyCode.ENTER) {
            this.model.setSearchStringProperty(searchTextField.getText());
            populateTilePane(this.model.getCurrentImages());
            populateBigImageView(this.model.getCurrentImage());
        }
    }
    
    private void populateBigImageView (Image image) {
        this.bigImageView.setImage(image);
    }

}
