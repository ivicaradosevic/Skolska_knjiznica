package fpmoz.sum.ba.school_library.school_library.controller;

import fpmoz.sum.ba.school_library.school_library.SchoolLibraryApplication;
import fpmoz.sum.ba.school_library.school_library.model.Category;
import fpmoz.sum.ba.school_library.school_library.model.Database;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AddBookController implements Initializable {

    @FXML
    private TextField title;

    @FXML
    private TextField publisher;

    @FXML
    private TextField edition;

    @FXML
    private TextField isbn;

    @FXML
    private TextField stockNumber;

    @FXML
    private TextField author;

    @FXML
    private ComboBox<Category> category;

    @FXML
    public void goBack(ActionEvent evt) {
        Stage stage = (Stage) ((Node) evt.getSource()).getScene().getWindow();
        SchoolLibraryApplication.swapScene(stage, "books-view.fxml");
    }


    @FXML
    public void saveBook() {

        if (title.getText().isEmpty() ||
                isbn.getText().isEmpty() ||
                publisher.getText().isEmpty() ||
                author.getText().isEmpty() ||
                edition.getText().isEmpty() ||
                stockNumber.getText().isEmpty() ||
                category.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Obavezni podaci");
            alert.setContentText("Molim popunite obavezne podatke");
            alert.showAndWait();
            return;
        }

        boolean isValid = true;
        for (int i = 0; i < edition.getText().length(); i++) {
            char c = edition.getText().charAt(i);
            if (!Character.isDigit(c)) {
                isValid = false;
                break;
            }
        }

        if (!isValid) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Obavezni podaci");
            alert.setContentText("Izdanje mora biti brojčana vrijednost");
            alert.showAndWait();
            return;
        }

        isValid = true;
        for (int i = 0; i < stockNumber.getText().length(); i++) {
            char c = stockNumber.getText().charAt(i);
            if (!Character.isDigit(c)) {
                isValid = false;
                break;
            }
        }

        if (!isValid) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Obavezni podaci");
            alert.setContentText("Količina mora biti brojčana vrijednost");
            alert.showAndWait();
            return;
        }


        try {

            Connection connection = Database.CONNECTION;

            String query = "INSERT INTO books(title, author, isbn, publisher, edition, stock_number, category_id) VALUES(?, ?, ?, ?, ?, ?, ?)";

            Category category = this.category.getValue();

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, title.getText());
            preparedStatement.setString(2, author.getText());
            preparedStatement.setString(3, isbn.getText());
            preparedStatement.setString(4, publisher.getText());
            preparedStatement.setInt(5, Integer.parseInt(edition.getText()));
            preparedStatement.setInt(6, Integer.parseInt(stockNumber.getText()));
            preparedStatement.setLong(7, category.getId());
            preparedStatement.execute();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Uspjesna radnja");
            alert.setContentText("Knjiga uspješno dodana!");
            alert.show();


            title.setText("");
            isbn.setText("");
            author.setText("");
            publisher.setText("");
            edition.setText("");
            stockNumber.setText("");
            this.category.setValue(null);

        } catch (Exception e) {
            System.out.println("Greška prilikom spremanja knjige");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Greška");
            alert.setContentText("Dogodila se greška prilikom dodavanja knjige!");
            alert.show();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            Connection connection = Database.CONNECTION;

            Statement statement = connection.createStatement();
            String query = "SELECT id, name FROM category";

            ResultSet resultSet = statement.executeQuery(query);

            List<Category> categories = new ArrayList<>();

            while (resultSet.next()) {
                Long id = resultSet.getLong(1);
                String name = resultSet.getString(2);

                Category category = new Category();
                category.setName(name);
                category.setId(id);
                categories.add(category);
            }

            this.category.setItems(FXCollections.observableList(categories));
        } catch (Exception e) {
            System.out.println("Greška prilikom dohvaćanja kategorija");
        }
    }
}
