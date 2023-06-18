module fpmoz.sum.ba.school_library.school_library {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.sql;


    opens fpmoz.sum.ba.school_library.school_library to javafx.fxml;
    exports fpmoz.sum.ba.school_library.school_library;
    exports fpmoz.sum.ba.school_library.school_library.controller;
    opens fpmoz.sum.ba.school_library.school_library.controller to javafx.fxml;
    exports fpmoz.sum.ba.school_library.school_library.model;
    opens fpmoz.sum.ba.school_library.school_library.model to javafx.fxml;
}