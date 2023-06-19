package fpmoz.sum.ba.school_library.school_library.controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import fpmoz.sum.ba.school_library.school_library.SchoolLibraryApplication;
import fpmoz.sum.ba.school_library.school_library.model.Books;
import fpmoz.sum.ba.school_library.school_library.model.Database;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;

public class BooksController implements Initializable {

    @FXML
    private TableView<Books> booksTableView;

    @FXML
    private TableColumn<Books, Long> columnId;

    @FXML
    private TableColumn<Books, String> columnTitle;

    @FXML
    private TableColumn<Books, String> columnIsbn;

    @FXML
    private TableColumn<Books, String> columnAuthor;

    @FXML
    private TableColumn<Books, String> columnPublisher;

    @FXML
    private TableColumn<Books, Integer> columnEdition;

    @FXML
    private TableColumn<Books, Integer> columnStockNumber;

    @FXML
    public void goBack(ActionEvent evt) {
        Stage stage = (Stage) ((Node) evt.getSource()).getScene()
            .getWindow();
        SchoolLibraryApplication.swapScene(stage, "home-screen-view.fxml");
    }

    @FXML
    public void addBook(ActionEvent evt) {
        Stage stage = (Stage) ((Node) evt.getSource()).getScene()
            .getWindow();
        SchoolLibraryApplication.swapScene(stage, "add-book-view.fxml");
    }

    @FXML
    public void editBook(ActionEvent evt) {
        Books book = booksTableView.getSelectionModel()
            .getSelectedItem();

        if (book == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Upozorenje");
            alert.setContentText("Molim odaberite redak koji želite urediti!");
            alert.show();
        }

        EditBookController.selectedBook = book;

        Stage stage = (Stage) ((Node) evt.getSource()).getScene()
            .getWindow();
        SchoolLibraryApplication.swapScene(stage, "edit-book-view.fxml");
    }

    @FXML
    public void deleteBook(ActionEvent evt) {

        Books book = booksTableView.getSelectionModel()
            .getSelectedItem();

        if (book == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Upozorenje");
            alert.setContentText("Molim odaberite redak koji želite obrisati!");
            alert.show();
        }

        try {
            Connection connection = Database.CONNECTION;

            String query = "DELETE FROM books WHERE id = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, book.getId());
            preparedStatement.execute();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Uspjesna radnja");
            alert.setContentText("Knjiga uspješno obrisana!");
            alert.show();
            booksTableView.getItems()
                .remove(book);

        } catch (Exception e) {
            System.out.println("Greška prilikom brisanja knjige");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Greška");
            alert.setContentText("Greška prilikom brisanja knjige!");
            alert.show();
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        columnId.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        columnIsbn.setCellValueFactory(new PropertyValueFactory<>("isbn"));
        columnAuthor.setCellValueFactory(new PropertyValueFactory<>("author"));
        columnEdition.setCellValueFactory(new PropertyValueFactory<>("edition"));
        columnPublisher.setCellValueFactory(new PropertyValueFactory<>("publisher"));
        columnStockNumber.setCellValueFactory(new PropertyValueFactory<>("stock_number"));

        try {
            Connection c = Database.CONNECTION;
            Statement s = c.createStatement();

            String query = "SELECT id, title,author, isbn, publisher, edition, category_id, stock_number" + " FROM books";

            ResultSet resultSet = s.executeQuery(query);

            List<Books> booksList = new ArrayList<>();

            while (resultSet.next()) {

                Long id = resultSet.getLong(1);
                String title = resultSet.getString(2);
                String author = resultSet.getString(3);
                String isbn = resultSet.getString(4);
                String publisher = resultSet.getString(5);
                Integer edition = resultSet.getInt(6);
                Long categoryId = resultSet.getLong(7);
                Integer stockNumber = resultSet.getInt(8);

                Books books = new Books();
                books.setId(id);
                books.setTitle(title);
                books.setIsbn(isbn);
                books.setPublisher(publisher);
                books.setEdition(edition);
                books.setCategory_id(categoryId);
                books.setStock_number(stockNumber);
                books.setAuthor(author);

                booksList.add(books);
            }

            ObservableList<Books> list = FXCollections.observableList(booksList);
            booksTableView.setItems(list);

        } catch (Exception e) {
            System.out.println("Problem prilikom čitanja knjiga iz baze podataka");
        }
    }
}
