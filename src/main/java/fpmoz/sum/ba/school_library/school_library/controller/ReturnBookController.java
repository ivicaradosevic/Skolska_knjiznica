package fpmoz.sum.ba.school_library.school_library.controller;

import fpmoz.sum.ba.school_library.school_library.SchoolLibraryApplication;
import fpmoz.sum.ba.school_library.school_library.model.Books;
import fpmoz.sum.ba.school_library.school_library.model.Database;
import fpmoz.sum.ba.school_library.school_library.model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class ReturnBookController implements Initializable {

    private Books selectedBook;
    private User selectedUser;
    private String selectedCategoryName;
    private String selectedUserType;


    @FXML
    private TextField searchBookTF;

    @FXML
    private TextField searchUserTF;

    @FXML
    private TextField bookTitle;

    @FXML
    private TextField bookIsbn;

    @FXML
    private TextField bookAuthor;

    @FXML
    private TextField bookPublisher;

    @FXML
    private TextField bookEdition;

    @FXML
    private TextField bookNumber;

    @FXML
    private TextField bookCategory;

    @FXML
    private TextField userFirstName;

    @FXML
    private TextField userLastName;

    @FXML
    private TextField userOib;

    @FXML
    private TextField userUsername;

    @FXML
    private TextField userEmail;

    @FXML
    private TextField userType;

    @FXML
    private TextField userYear;

    @FXML
    private DatePicker returnDate;

    @FXML
    public void goBack(ActionEvent evt){
        Stage stage = (Stage) ((Node) evt.getSource()).getScene().getWindow();
        SchoolLibraryApplication.swapScene(stage, "returned-books-view.fxml");
    }


    @FXML
    public void searchBooks(){
        if(searchBookTF.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Upozorenje");
            alert.setContentText("Molim unesite naziv knjige.");
            alert.showAndWait();
            return;
        }

        String input = searchBookTF.getText();

        try{

            Connection connection = Database.CONNECTION;

            String query = "SELECT b.id, b.title, b.author, b.isbn, b.publisher, b.edition, b.category_id, b.stock_number" +
                    " FROM books b INNER JOIN issuedbooks ib ON ib.books_id = b.id  WHERE b.title = ?";

            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1,input);

            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {

                Long id = resultSet.getLong(1);
                String title = resultSet.getString(2);
                String author = resultSet.getString(3);
                String isbn = resultSet.getString(4);
                String publisher = resultSet.getString(5);
                Integer edition = resultSet.getInt(6);
                Long categoryId = resultSet.getLong(7);
                Integer stockNumber = resultSet.getInt(8);

                String categoryName = "";
                if(categoryId != null) {
                    String categoryQuery = "SELECT name FROM category WHERE id = ?";
                    PreparedStatement preparedStatement = connection.prepareStatement(categoryQuery);
                    preparedStatement.setLong(1, categoryId);


                    ResultSet categoryResultSet = preparedStatement.executeQuery();
                    while (categoryResultSet.next()) {
                        categoryName = categoryResultSet.getString(1);
                    }
                }

                Books books = new Books();
                books.setId(id);
                books.setTitle(title);
                books.setIsbn(isbn);
                books.setPublisher(publisher);
                books.setEdition(edition);
                books.setCategory_id(categoryId);
                books.setStock_number(stockNumber);
                selectedCategoryName= categoryName;
                books.setAuthor(author);
                selectedBook = books;

                this.bookTitle.setText(title);
                this.bookIsbn.setText(isbn);
                this.bookPublisher.setText(publisher);
                this.bookEdition.setText(String.valueOf(edition));
                this.bookCategory.setText(categoryName);
                this.bookNumber.setText(String.valueOf(stockNumber));
                this.bookAuthor.setText(author);

            }

            if(selectedBook == null){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Upozorenje");
                alert.setContentText("Molim unesite ispravan naziv knjige.");
                alert.showAndWait();
            }

        }catch (Exception e){
            System.out.println("Greška prilikom traženja knjige s nazivom " + input);

        }

    }

    @FXML
    public void searchUsers(){
        if(searchUserTF.getText().isEmpty() || searchUserTF.getText().length() != 11){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Upozorenje");
            alert.setContentText("Molim unesite OIB korisnika.");
            alert.showAndWait();
            return;
        }

        String input = searchUserTF.getText();

        try{
            Connection c = Database.CONNECTION;


            String query = "SELECT u.id, u.first_name, u.last_name, u.oib, u.username, u.email, u.year, u.user_type_id " +
                    "FROM user u INNER JOIN issuedbooks ib ON ib.user_id = u.id " +
                    "WHERE u.oib = ?";

            PreparedStatement ps = c.prepareStatement(query);
            ps.setString(1, input);

            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {

                Long id = resultSet.getLong(1);
                String firstName = resultSet.getString(2);
                String lastName = resultSet.getString(3);
                String oib = resultSet.getString(4);
                String username = resultSet.getString(5);
                String email = resultSet.getString(6);
                Integer year = resultSet.getInt(7);
                Long userTypeId = resultSet.getLong(8);

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

                User user = new User();
                user.setId(id);
                user.setFirst_name(firstName);
                user.setLast_name(lastName);
                user.setOib(oib);
                user.setUsername(username);
                user.setEmail(email);
                user.setYear(year);

                selectedUser = user;
                selectedUserType = userTypeName;

                this.userFirstName.setText(firstName);
                this.userLastName.setText(lastName);
                this.userOib.setText(oib);
                this.userUsername.setText(username);
                this.userEmail.setText(email);
                this.userType.setText(userTypeName);
                this.userYear.setText(String.valueOf(year));
            }

            if(selectedUser == null){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Upozorenje");
                alert.setContentText("Korisnik sa zadanim OIB-om nije pronađen u bazi.");
                alert.showAndWait();
            }

        }catch(Exception e){
            System.out.println(e);
            System.out.println("Greška prilikom dohvaćanja korisnika s OIB-om " + input);
        }
    }

    @FXML
    public void returnBook(){

        if(selectedBook == null || selectedUser == null || returnDate.getValue() == null){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Upozorenje");
            alert.setContentText("Korisnik, knjiga i datum povratka moraju biti odabrani.");
            alert.showAndWait();
            return;
        }

        try{
            Connection connection = Database.CONNECTION;

            String checkQuery = "SELECT id FROM returnedbooks WHERE books_id = ? AND user_id = ?";
            PreparedStatement ps1 = connection.prepareStatement(checkQuery);
            ps1.setLong(1, selectedBook.getId());
            ps1.setLong(2, selectedUser.getId());

            ResultSet rs = ps1.executeQuery();

            if(rs.next()){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Upozorenje");
                alert.setContentText("Korisnik je već vratio odabranu knjigu.");
                alert.showAndWait();
                return;
            }

            String query = "INSERT INTO returnedbooks(books_id, user_id, returned_date) VALUES(?, ?, ?)";

            LocalDate selectedDate = returnDate.getValue();
            java.sql.Date date = new Date(selectedDate.getYear()-1900, selectedDate.getMonthValue(), selectedDate.getDayOfMonth());

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, selectedBook.getId());
            preparedStatement.setLong(2, selectedUser.getId());
            preparedStatement.setDate( 3, date);
            preparedStatement.execute();

            System.out.println("ID knjige: " + selectedBook.getId());
            System.out.println("Trenutna količina: " + selectedBook.getStock_number());
            selectedBook.setStock_number(selectedBook.getStock_number()+1);
            System.out.println("Azurirana količina: " + selectedBook.getStock_number());

            String bookQuery = "UPDATE books SET stock_number = ? WHERE id = ?";
            PreparedStatement ps = connection.prepareStatement(bookQuery);
            ps.setInt(1, selectedBook.getStock_number());
            ps.setLong(2, selectedBook.getId());
            ps.execute();

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Upozorenje");
            alert.setContentText("Knjiga uspješno vraćena.");
            alert.showAndWait();

        }catch (Exception e){
            System.out.println("Greška prilikom spremanja povrata");
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.bookTitle.setEditable(false);
        this.bookIsbn.setEditable(false);
        this.bookPublisher.setEditable(false);
        this.bookEdition.setEditable(false);
        this.bookCategory.setEditable(false);
        this.bookNumber.setEditable(false);
        this.bookAuthor.setEditable(false);

        this.userFirstName.setEditable(false);
        this.userLastName.setEditable(false);
        this.userOib.setEditable(false);
        this.userUsername.setEditable(false);
        this.userEmail.setEditable(false);
        this.userType.setEditable(false);
        this.userYear.setEditable(false);
    }
}
