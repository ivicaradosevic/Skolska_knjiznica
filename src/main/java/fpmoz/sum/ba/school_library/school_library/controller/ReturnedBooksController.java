package fpmoz.sum.ba.school_library.school_library.controller;

import fpmoz.sum.ba.school_library.school_library.SchoolLibraryApplication;
import fpmoz.sum.ba.school_library.school_library.model.Database;
import fpmoz.sum.ba.school_library.school_library.model.IssuedBooksTableModel;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ReturnedBooksController implements Initializable {

    @FXML
    private TableView<IssuedBooksTableModel> returnedBooksTableView;

    @FXML
    private TableColumn<IssuedBooksTableModel, String> bookTitle;

    @FXML
    private TableColumn<IssuedBooksTableModel, String> bookReturner;

    @FXML
    private TableColumn<IssuedBooksTableModel, LocalDate> returnDate;

    @FXML
    private TableColumn<IssuedBooksTableModel, String> category;

    @FXML
    private TableColumn<IssuedBooksTableModel, String> userType;

    @FXML
    public void goBack(ActionEvent evt) {
        Stage stage = (Stage) ((Node) evt.getSource()).getScene().getWindow();
        SchoolLibraryApplication.swapScene(stage, "home-screen-view.fxml");
    }

    @FXML
    public void returnBook(ActionEvent evt) {
        Stage stage = (Stage) ((Node) evt.getSource()).getScene().getWindow();
        SchoolLibraryApplication.swapScene(stage, "return-book-view.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.bookTitle.setCellValueFactory(new PropertyValueFactory<>("bookTitle"));
        this.returnDate.setCellValueFactory(new PropertyValueFactory<>("issueDate"));
        this.category.setCellValueFactory(new PropertyValueFactory<>("bookCategory"));
        this.bookReturner.setCellValueFactory(new PropertyValueFactory<>("bookIssuer"));
        this.userType.setCellValueFactory(new PropertyValueFactory<>("userType"));


        try {
            Connection connection = Database.CONNECTION;

            String query = "SELECT b.title, rb.returned_date, u.first_name, u.last_name, c.name, ut.name  FROM books b " +
                    "INNER JOIN returnedbooks rb ON b.id = rb.books_id " +
                    "INNER JOIN user u ON u.id = rb.user_id " +
                    "INNER JOIN usertype ut ON u.user_type_id = ut.id " +
                    "INNER JOIN category c ON c.id = b.category_id";

            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            List<IssuedBooksTableModel> list = new ArrayList<>();

            while (rs.next()) {
                String title = rs.getString(1);
                Date date = rs.getDate(2);
                String firstName = rs.getString(3);
                String lastName = rs.getString(4);
                String category = rs.getString(5);
                String userType = rs.getString(6);

                IssuedBooksTableModel model = new IssuedBooksTableModel();
                model.setBookTitle(title);
                model.setBookIssuer(firstName + " " + lastName);
                model.setIssueDate(date.toLocalDate());
                model.setBookCategory(category);
                model.setUserType(userType);
                list.add(model);
            }

            returnedBooksTableView.setItems(FXCollections.observableList(list));

        } catch (Exception e) {
            System.out.println(e);
            System.out.println("Greška prilikom dohvaćanja vraćenih knjiga");
        }

    }
}
