package fpmoz.sum.ba.school_library.school_library.controller;

import fpmoz.sum.ba.school_library.school_library.SchoolLibraryApplication;
import fpmoz.sum.ba.school_library.school_library.model.Books;
import fpmoz.sum.ba.school_library.school_library.model.Database;
import fpmoz.sum.ba.school_library.school_library.model.User;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class UserController implements Initializable {

    @FXML
    private TableView<User> usersTableView;

    @FXML
    private TableColumn<User, Long> columnId;

    @FXML
    private TableColumn<User, String> columnFirstName;

    @FXML
    private TableColumn<User, String> columnLastName;

    @FXML
    private TableColumn<User, String> columnUsername;

    @FXML
    private TableColumn<User, String> columnEmail;

    @FXML
    private TableColumn<Books, String> columnUserType;

    @FXML
    private TableColumn<User, String> columnYear;

    @FXML
    private TableColumn<User, String> columnOib;

    @FXML
    public void goBack(ActionEvent evt) {
        Stage stage = (Stage) ((Node) evt.getSource()).getScene().getWindow();
        SchoolLibraryApplication.swapScene(stage, "home-screen-view.fxml");
    }

    @FXML
    public void addUser(ActionEvent evt) {
        Stage stage = (Stage) ((Node) evt.getSource()).getScene().getWindow();
        SchoolLibraryApplication.swapScene(stage, "add-user-view.fxml");
    }

    @FXML
    public void editUser(ActionEvent evt) {

        User user = usersTableView.getSelectionModel().getSelectedItem();

        if (user == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Upozorenje");
            alert.setContentText("Molim odaberite redak koji želite urediti!");
            alert.show();
        }

        EditUserController.selectedUser = user;

        Stage stage = (Stage) ((Node) evt.getSource()).getScene().getWindow();
        SchoolLibraryApplication.swapScene(stage, "edit-user-view.fxml");
    }

    @FXML
    public void deleteUser(ActionEvent evt) {
        User user = usersTableView.getSelectionModel().getSelectedItem();

        if (user == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Upozorenje");
            alert.setContentText("Molim odaberite redak koji želite obrisati!");
            alert.show();
            return;
        }

        try {

            Connection connection = Database.CONNECTION;

            String query = "DELETE FROM user WHERE id = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, user.getId());
            preparedStatement.execute();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Uspjesna radnja");
            alert.setContentText("Korisnik uspješno obrisan!");
            alert.show();
            usersTableView.getItems().remove(user);


        } catch (Exception e) {
            System.out.println("Greška prilikom brisanja korisnika");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Greška");
            alert.setContentText("Greška prilikom brisanja korisnika!");
            alert.show();
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        columnId.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnOib.setCellValueFactory(new PropertyValueFactory<>("oib"));
        columnFirstName.setCellValueFactory(new PropertyValueFactory<>("first_name"));
        columnLastName.setCellValueFactory(new PropertyValueFactory<>("last_name"));
        columnUsername.setCellValueFactory(new PropertyValueFactory<>("username"));
        columnEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        columnYear.setCellValueFactory(new PropertyValueFactory<>("year"));

        try {
            Connection c = Database.CONNECTION;
            Statement s = c.createStatement();

            String query = "SELECT id, first_name, last_name, oib, username, password, email, year, user_type_id FROM user";

            ResultSet resultSet = s.executeQuery(query);

            List<User> userList = new ArrayList<>();

            while (resultSet.next()) {

                Long id = resultSet.getLong(1);
                String firstName = resultSet.getString(2);
                String lastName = resultSet.getString(3);
                String oib = resultSet.getString(4);
                String username = resultSet.getString(5);
                String password = resultSet.getString(6);
                String email = resultSet.getString(7);
                Integer year = resultSet.getInt(8);
                Long userTypeId = resultSet.getLong(9);

                String userTypeName = "";
                if (userTypeId != null) {
                    String categoryQuery = "SELECT name FROM usertype WHERE id = ?";
                    PreparedStatement preparedStatement = c.prepareStatement(categoryQuery);
                    preparedStatement.setLong(1, userTypeId);

                    ResultSet userTypeResultSet = preparedStatement.executeQuery();
                    while (userTypeResultSet.next()) {
                        userTypeName = userTypeResultSet.getString(1);
                    }
                }

                String finalUserTypeName = userTypeName;
                columnUserType.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Books, String>, ObservableValue<String>>() {
                    @Override
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<Books, String> p) {
                        if (p.getValue() != null) {
                            return new SimpleStringProperty(finalUserTypeName);
                        } else {
                            return new SimpleStringProperty("");
                        }
                    }
                });

                User user = new User();
                user.setId(id);
                user.setFirst_name(firstName);
                user.setLast_name(lastName);
                user.setOib(oib);
                user.setUsername(username);
                user.setPassword(password);
                user.setEmail(email);
                user.setYear(year);
                user.setUser_type_id(userTypeId);

                userList.add(user);
            }

            ObservableList<User> list = FXCollections.observableList(userList);
            usersTableView.setItems(list);

        } catch (Exception e) {
            System.out.println("Problem prilikom čitanja korisnika iz baze podataka"+ e);
        }

    }
}
