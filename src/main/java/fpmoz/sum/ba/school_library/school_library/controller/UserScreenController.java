package fpmoz.sum.ba.school_library.school_library.controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import fpmoz.sum.ba.school_library.school_library.model.Database;
import fpmoz.sum.ba.school_library.school_library.model.UserScreenTableModel;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class UserScreenController implements Initializable {

    public static Long currentUserId;

    @FXML
    private TableView<UserScreenTableModel> userTableView;

    @FXML
    private TableColumn<UserScreenTableModel, String> bookTitle;

    @FXML
    private TableColumn<UserScreenTableModel, String> bookCategory;

    @FXML
    private TableColumn<UserScreenTableModel, LocalDate> issueDate;

    @FXML
    private TableColumn<UserScreenTableModel, LocalDate> returnDate;

    @FXML
    public void logout(ActionEvent evt) {
        currentUserId = null;
        Stage stage = (Stage) ((Node) evt.getSource()).getScene()
            .getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.bookTitle.setCellValueFactory(new PropertyValueFactory<>("bookTitle"));
        this.bookCategory.setCellValueFactory(new PropertyValueFactory<>("bookCategory"));
        this.issueDate.setCellValueFactory(new PropertyValueFactory<>("issueDate"));
        this.returnDate.setCellValueFactory(new PropertyValueFactory<>("returnDate"));

        try {

            Connection connection = Database.CONNECTION;
            String query = "SELECT b.title, c.name, ib.issue_date, rb.returned_date FROM books b " +
                    "INNER JOIN category c ON c.id = b.category_id "
                    + "INNER JOIN issuedbooks ib ON ib.books_id = b.id AND ib.user_id = ? "
                + "LEFT JOIN returnedbooks rb ON rb.books_id = b.id AND ib.user_id = ?";

            PreparedStatement ps = connection.prepareStatement(query);
            ps.setLong(1, currentUserId);
            ps.setLong(2, currentUserId);

            List<UserScreenTableModel> list = new ArrayList<>();

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String title = rs.getString(1);
                String category = rs.getString(2);
                Date issueDate = rs.getDate(3);
                Date returnedDate = rs.getDate(4);

                UserScreenTableModel model = new UserScreenTableModel();
                model.setBookTitle(title);
                model.setBookCategory(category);
                model.setIssueDate(issueDate.toLocalDate());
                if(returnedDate != null){
                    model.setReturnDate(returnedDate.toLocalDate());
                }
                list.add(model);

            }

            this.userTableView.setItems(FXCollections.observableList(list));

        } catch (Exception e) {
            System.out.println("Greška prilikom dohvaćanja podataka vezanih uz korisnika");
        }
    }
}
