/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sakis.anthologium.util;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.prefs.Preferences;
import org.sqlite.SQLiteConfig;

/**
 *
 * @author Sakis Tosounidis <sakis.tosounidis@gmail.com>
 */
public class Database {

    private static final String[] TYPES = {"TABLE", "VIEW"};

    /**
     * Create the applications connection
     *
     * @return Connection if successful, otherwise null
     * @throws SQLException
     */
    public static Connection getConnection() throws SQLException {

        Preferences preferences = Preferences.userNodeForPackage(com.sakis.anthologium.main.Anthologium.class);

        String connection = preferences.get("connection", null);

        SQLiteConfig config = new SQLiteConfig();
        config.enforceForeignKeys(true);
        config.setEncoding(SQLiteConfig.Encoding.UTF16);
        config.enableCaseSensitiveLike(true);

        if (connection == null) {
            preferences.put("connection", "jdbc:sqlite:anthologium.sqlite");
            connection = preferences.get("connection", null);
        }

        String classname = preferences.get("classname", null);

        if (classname == null) {
            preferences.put("classname", "org.sqlite.JDBC");
            classname = preferences.get("classname", null);
        }

        try {
            Class.forName(classname);
            return DriverManager.getConnection(connection, config.toProperties());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Lookup in the database connected, if table with name "tableName" exists
     *
     * @param tableName
     * @return true, if table exists, else false
     */
    public static boolean tableExists(String tableName) {
        boolean tableFound = false;
        try {
            DatabaseMetaData metaData = getConnection().getMetaData();
            ResultSet resultSet = metaData.getTables(null, null, "%", TYPES);
            while (resultSet.next()) {
                String tblName = resultSet.getString("TABLE_NAME").toLowerCase();
                if (tblName.equals(tableName.toLowerCase())) {
                    tableFound = true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tableFound;
    }

    /**
     * Create the actor table
     */
    public static void createTableActor() {
        try {
            String sql = "CREATE TABLE actor (actor_id INTEGER PRIMARY KEY AUTOINCREMENT, firstname TEXT, lastname TEXT, nickname TEXT, born DATE, died DATE, information BLOB)";
            Statement statement = getConnection().createStatement();
            statement.execute(sql);
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Create the photo table
     */
    public static void createTablePhoto() {
        try {
            String sql = "CREATE TABLE photo (photo_id INTEGER PRIMARY KEY AUTOINCREMENT, image BLOB)";
            Statement statement = getConnection().createStatement();
            statement.execute(sql);
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Create the actor_photo table
     */
    public static void createTableActorPhoto() {
        try {
            String sql
                    = "CREATE TABLE actor_photo ("
                    + "actor_photo_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "actor_id INTEGER, "
                    + "photo_id INTEGER, "
                    + "FOREIGN KEY (actor_id) REFERENCES actor(actor_id) ON DELETE CASCADE ON UPDATE NO ACTION, "
                    + "FOREIGN KEY (photo_id) REFERENCES photo(photo_id) ON DELETE CASCADE ON UPDATE NO ACTION"
                    + ")";

            Statement statement = getConnection().createStatement();
            statement.execute(sql);
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Create the songs table
     */
    public static void createTableSongs() {
        String sql
                = "CREATE TABLE songs ("
                + "song_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "title TEXT, "
                + "composer INTEGER, "
                + "lyrics INTEGER, "
                + "tone TEXT, "
                + "scale TEXT, "
                + "FOREIGN KEY (composer) REFERENCES actor(actor_id) ON DELETE CASCADE ON UPDATE NO ACTION, "
                + "FOREIGN KEY (lyrics) REFERENCES actor(actor_id) ON DELETE CASCADE ON UPDATE NO ACTION"
                + ")";

        try {
            Statement statement = getConnection().createStatement();
            statement.execute(sql);
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Create the song_url table
     */
    public static void createTableSongURLs() {
        String sql
                = "CREATE TABLE song_url ("
                + "song_id INTEGER, "
                + "url TEXT, "
                + "PRIMARY KEY (song_id, url), "
                + "FOREIGN KEY (song_id) REFERENCES songs (song_id) ON DELETE CASCADE ON UPDATE NO ACTION"
                + ")";

        try {
            Statement statement = getConnection().createStatement();
            statement.execute(sql);
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
