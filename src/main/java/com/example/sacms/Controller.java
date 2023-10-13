package com.example.sacms;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class Controller {

    private Stage stage;
    private Scene scene;
    @FXML
    private Button splashScreenButton;


    @FXML
    protected void onSplashScreenButtonClicked(ActionEvent event) throws IOException {
        Parent root = (Parent) FXMLLoader.load(this.getClass().getResource("signInOne.fxml"));
        this.stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        this.scene = new Scene(root);
        this.stage.setTitle("SignIn");
        //this.scene.getStylesheets().add(this.getClass().getResource("mainstylesheet.css").toExternalForm());
        this.stage.setScene(this.scene);
        this.stage.show();
        this.stage.setResizable(false);
    }
}