package fpmoz.sum.ba.school_library.school_library.controller;

import fpmoz.sum.ba.school_library.school_library.SchoolLibraryApplication;
import fpmoz.sum.ba.school_library.school_library.model.Books;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class HomeScreenController{


    @FXML
    public void manageBooks(ActionEvent evt){
        Stage stage = (Stage) ((Node) evt.getSource()).getScene().getWindow();
        SchoolLibraryApplication.swapScene(stage, "books-view.fxml");
    }

    @FXML
    public void manageUsers(ActionEvent evt){
        Stage stage = (Stage) ((Node) evt.getSource()).getScene().getWindow();
        SchoolLibraryApplication.swapScene(stage, "user-view.fxml");
    }

    @FXML
    public void issueBook(ActionEvent evt){
        Stage stage = (Stage) ((Node) evt.getSource()).getScene().getWindow();
        SchoolLibraryApplication.swapScene(stage, "issued-books-view.fxml");
    }

    @FXML
    public void returnBook(ActionEvent evt){
        Stage stage = (Stage) ((Node) evt.getSource()).getScene().getWindow();
        SchoolLibraryApplication.swapScene(stage, "returned-books-view.fxml");
    }

    @FXML
    public void logout(ActionEvent evt){
        Stage stage = (Stage) ((Node) evt.getSource()).getScene().getWindow();
        stage.close();
    }
}