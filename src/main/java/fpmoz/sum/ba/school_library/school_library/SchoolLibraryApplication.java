package fpmoz.sum.ba.school_library.school_library;

import fpmoz.sum.ba.school_library.school_library.model.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class SchoolLibraryApplication extends Application {

    private static final String APP_TITLE = "Školska Knjižnica";

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SchoolLibraryApplication.class.getResource("login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 660, 431);
        stage.setTitle(APP_TITLE);
        stage.setScene(scene);
        stage.show();
    }

    public static void swapScene(Stage stage, String viewName) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(SchoolLibraryApplication.class.getResource(viewName));
            Scene scene = new Scene(fxmlLoader.load(), 660, 450);
            stage.setTitle(APP_TITLE);
            stage.setScene(scene);
            stage.show();
        }catch (IOException e){
            System.out.println("Greska prilikom otvaranja scene " + e.getMessage());
        }
    }

    public static void main(String[] args) throws SQLException {
      /*  Table.create(UserType.class);
        Table.create(Category.class);
        Table.create(User.class);
        Table.create(Books.class);
        Table.create(IssuedBooks.class);
        Table.create(ReturnedBooks.class);*/
        launch();
    }
}