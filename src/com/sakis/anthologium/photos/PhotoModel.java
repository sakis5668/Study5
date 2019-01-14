/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sakis.anthologium.photos;

import com.sakis.anthologium.util.Database;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;

/**
 *
 * @author Sakis Tosounidis <sakis.tosounidis@gmail.com>
 */
public class PhotoModel {
    private Connection connection;
    private StringProperty searchStringProperty = new SimpleStringProperty("");


    private ObservableList<PhotoData> photoDataList;
    private ObservableList<PhotoActorData> photoActorDataList;
    private PhotoData currentPhotoData;

    private ObservableList<Image> currentImages;
    private Image currentImage;

    
    /**
     * Constructor
     */
    public PhotoModel() {
        try {
            this.connection = Database.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        searchStringProperty.addListener((observable, oldValue, newValue) -> {
            try {
                readPhotoDataList(new SimpleStringProperty(newValue));
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });

        photoDataList = FXCollections.observableArrayList();
        photoActorDataList = FXCollections.observableArrayList();
        currentImages = FXCollections.observableArrayList();

        try {
            readPhotoDataList(this.searchStringProperty);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * read data from database into PhotoDataList, CurrentPhotoData and
     * CurrentImages
     *
     * @param searchString
     * @throws IOException
     */
    private void readPhotoDataList(StringProperty searchString) throws IOException {
        photoDataList.clear();
        currentImages.clear();

        String sql = "";

        if (!searchString.get().equals("")) {
            sql
                    = "SELECT photo.*, actor_photo.*, actor.lastname, actor.firstname, actor.nickname\n"
                    + "FROM ((photo\n"
                    + "INNER JOIN actor_photo ON photo.photo_id=actor_photo.photo_id)\n"
                    + "INNER JOIN actor ON actor_photo.actor_id=actor.actor_id)\n"
                    + "WHERE actor.lastname LIKE '%" + searchString.get() + "%'\n"
                    + "OR actor.firstname LIKE '%" + searchString.get() + "%'\n"
                    + "OR actor.nickname LIKE '%" + searchString.get() + "%'";
        } else {
            sql = "SELECT * FROM photo LIMIT 10";
        }

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                int photoId = resultSet.getInt("photo_id");
                InputStream is = resultSet.getBinaryStream("image");
                currentImage = new Image(is);
                currentImages.add(currentImage);

                ByteArrayOutputStream buffer = new ByteArrayOutputStream();
                int nRead;
                byte[] data = new byte[1024];
                while ((nRead = is.read(data, 0, data.length)) != -1) {
                    buffer.write(data, 0, nRead);
                }
                buffer.flush();
                byte[] imageBytes = buffer.toByteArray();

                currentPhotoData = new PhotoData(
                        photoId,
                        imageBytes
                );
                photoDataList.add(currentPhotoData);
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Return the CurrentImages list
     * @return 
     */
    public ObservableList<Image> getCurrentImages() {
        return currentImages;
    }

    /**
     * Setter for the searchStringProperty
     * @param searchString 
     */
    public void setSearchStringProperty(String searchString) {
        this.searchStringProperty.set(searchString);
    }

    /**
     * Getter for the currentPhotoData
     * @return 
     */
    public PhotoData getCurrentPhotoData() {
        return currentPhotoData;
    }

    /**
     * Getter for the current Image 
     * @return 
     */
    public Image getCurrentImage() {
        return this.currentImage;
    }
    
}
