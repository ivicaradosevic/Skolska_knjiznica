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

public class IssuedBooksController implements Initializable {

    @FXML
    private TableView<IssuedBooksTableModel> issuedBooksTableView;

    @FXML
    private TableColumn<IssuedBooksTableModel, String> bookTitle;

    @FXML
    private TableColumn<IssuedBooksTableModel, String> bookIssuer;

    @FXML
    private TableColumn<IssuedBooksTableModel, LocalDate> issueDate;

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
    public void issueBook(ActionEvent evt) {
        Stage stage = (Stage) ((Node) evt.getSource()).getScene().getWindow();
        SchoolLibraryApplication.swapScene(stage, "issue-book-view.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.bookTitle.setCellValueFactory(new PropertyValueFactory<>("bookTitle"));
        this.issueDate.setCellValueFactory(new PropertyValueFactory<>("issueDate"));
        this.category.setCellValueFactory(new PropertyValueFactory<>("bookCategory"));
        this.bookIssuer.setCellValueFactory(new PropertyValueFactory<>("bookIssuer"));
        this.userType.setCellValueFactory(new PropertyValueFactory<>("userType"));


        try {
            Connection connection = Database.CONNECTION;

            String query = "SELECT b.title, ib.issue_date, u.first_name, u.last_name, c.name, ut.name  FROM books b " +
                    "INNER JOIN issuedbooks ib ON b.id = ib.books_id " +
                    "INNER JOIN user u ON u.id = ib.user_id " +
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

            issuedBooksTableView.setItems(FXCollections.observableList(list));

        } catch (Exception e) {
            System.out.println("Greška prilikom dohvaćanja posuđenih knjiga");
        }

    }
}
