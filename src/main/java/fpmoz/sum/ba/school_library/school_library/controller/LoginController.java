package fpmoz.sum.ba.school_library.school_library.controller;

import fpmoz.sum.ba.school_library.school_library.SchoolLibraryApplication;
import fpmoz.sum.ba.school_library.school_library.model.Database;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginController {

    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    @FXML
    public void goBack(ActionEvent evt){

    }

    @FXML
    public void login(ActionEvent evt){

        if(username.getText().isEmpty() || password.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Upozorenje");
            alert.setContentText("Sva polja su obavezna");
            alert.showAndWait();
            return;
        }


        if(username.getText().equals("admin") && password.getText().equals("admin")){
            Stage stage = (Stage) ((Node) evt.getSource()).getScene().getWindow();
            SchoolLibraryApplication.swapScene(stage, "home-screen-view.fxml");
            return;
        }

        try{

            Connection connection = Database.CONNECTION;
            String query = "SELECT id FROM user WHERE username = ? AND password = ?";

            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, username.getText());
            ps.setString(2, password.getText());

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                Long id = rs.getLong(1);
                UserScreenController.currentUserId = id;
            }

            if(UserScreenController.currentUserId == null){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Upozorenje");
                alert.setContentText("Korisničko ime/lozinka nisu ispravni");
                alert.showAndWait();
                return;
            }

            Stage stage = (Stage) ((Node) evt.getSource()).getScene().getWindow();
            SchoolLibraryApplication.swapScene(stage, "user-screen-view.fxml");
            return;

        }catch (Exception e){
            System.out.println("Greška prilikom prijave korisnika u sustav");
        }


    }
}
