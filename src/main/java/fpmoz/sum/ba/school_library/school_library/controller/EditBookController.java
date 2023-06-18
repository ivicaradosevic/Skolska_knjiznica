package fpmoz.sum.ba.school_library.school_library.controller;

import fpmoz.sum.ba.school_library.school_library.SchoolLibraryApplication;
import fpmoz.sum.ba.school_library.school_library.model.Books;
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

public class EditBookController implements Initializable {

   static Books selectedBook;

    @FXML
    private TextField title;

    @FXML
    private TextField publisher;

    @FXML
    private TextField edition;

    @FXML
    private TextField isbn;

    @FXML
    private TextField author;

    @FXML
    private TextField stockNumber;

    @FXML
    private ComboBox<Category> category;

    @FXML
    public void goBack(ActionEvent evt) {
        Stage stage = (Stage) ((Node) evt.getSource()).getScene().getWindow();
        SchoolLibraryApplication.swapScene(stage, "books-view.fxml");
    }

    @FXML
    public void saveBook(ActionEvent evt) {

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

            String query = "UPDATE books " +
                    "SET title = ?, " +
                    "author = ?, " +
                    "isbn = ?, " +
                    "publisher = ?, " +
                    "edition = ?, " +
                    "stock_number = ?, " +
                    "category_id = ? " +
                    "WHERE id = ?";

            Category category = this.category.getValue();

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, title.getText());
            preparedStatement.setString(2, author.getText());
            preparedStatement.setString(3, isbn.getText());
            preparedStatement.setString(4, publisher.getText());
            preparedStatement.setInt(5, Integer.parseInt(edition.getText()));
            preparedStatement.setInt(6, Integer.parseInt(stockNumber.getText()));
            preparedStatement.setLong(7, category.getId());
            preparedStatement.setLong(8, selectedBook.getId());
            preparedStatement.execute();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Uspjesna radnja");
            alert.setContentText("Knjiga uspješno uređena!");
            alert.showAndWait();

            Stage stage = (Stage) ((Node) evt.getSource()).getScene().getWindow();
            SchoolLibraryApplication.swapScene(stage, "books-view.fxml");
        } catch (Exception e) {
            System.out.println("Greška prilikom ažuriranja knjige - " + e);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Greška");
            alert.setContentText("Dogodila se pogreška prilikom ažuriranja knjige!");
            alert.show();
        }


    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        getCategoriesFromDatabase();

        title.setText(selectedBook.getTitle());
        isbn.setText(selectedBook.getIsbn());
        isbn.setEditable(false);
        author.setText(selectedBook.getAuthor());
        publisher.setText(selectedBook.getPublisher());
        edition.setText(String.valueOf(selectedBook.getEdition()));
        stockNumber.setText(String.valueOf(selectedBook.getStock_number()));

        Category selectedCategory = new Category();

        for(Category c : this.category.getItems()){
            if(c.getId().equals(selectedBook.getCategory_id())){
                selectedCategory = c;
            }
        }

        this.category.setValue(selectedCategory);

    }

    private void getCategoriesFromDatabase() {
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
