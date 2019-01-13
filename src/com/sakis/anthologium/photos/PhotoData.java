/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sakis.anthologium.photos;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 *
 * @author Sakis Tosounidis <sakis.tosounidis@gmail.com>
 */
public class PhotoData {
        private final IntegerProperty photoId;
    private byte[] image;

    /**
     * Constructor
     *
     * @param photoId
     * @param imageBytes
     */
    public PhotoData(int photoId, byte[] imageBytes) {
        this.photoId = new SimpleIntegerProperty(photoId);
        this.image = imageBytes;
    }

    /**
     * Getter for Photo ID
     */
    public int getPhotoId() {
        return this.photoId.get();
    }

    public IntegerProperty getPhotoIdProperty() {
        return this.photoId;
    }

    /**
     * Getter and Setter for image
     */
    public byte[] getImage() {
        return this.image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

}
