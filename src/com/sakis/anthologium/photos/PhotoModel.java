/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sakis.anthologium.photos;

import com.sakis.anthologium.util.Database;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
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

    private String photoInfoString;

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

        try {
            readPhotoDataList(this.searchStringProperty);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Look up in the database the names of the actors shown in the photo and
     * save the result in the variable photoInfoString
     *
     * @param photoId
     */
    public String getPhotoInfo(int photoId) {
        String infoString = "";
        try {
            String sql = "select actor_photo.*, actor.lastname, actor.firstname\n" + "from actor_photo\n"
                    + "inner join actor on actor_photo.actor_id = actor.actor_id\n" + "where photo_id=?\n";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, photoId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                if (!infoString.equals("")) {
                    infoString += ", ";
                }
                infoString = infoString + resultSet.getString("firstname") + " " + resultSet.getString("lastname");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (!infoString.equals("")) {
            return infoString;
        } else {
            return "No info";
        }

    }

    /**
     * Setter for the searchStringProperty
     *
     * @param searchString
     */
    public void setSearchStringProperty(String searchString) {
        this.searchStringProperty.set(searchString);
    }

    /**
     * Getter for the currentPhotoData
     *
     * @return
     */
    public PhotoData getCurrentPhotoData() {
        return currentPhotoData;
    }

    public ObservableList<PhotoData> getPhotoDataList() {
        return photoDataList;
    }

    /**
     * Read the data from database depending on searchString and set the
     * contents of the variables photoDataList and currentPhotoData
     *
     * @param searchString
     * @throws IOException
     */
    private void readPhotoDataList(StringProperty searchString) throws IOException {
        photoDataList.clear();

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
                byte[] imageBytes = resultSet.getBytes("image");
                InputStream is = new ByteArrayInputStream(imageBytes);

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

}
