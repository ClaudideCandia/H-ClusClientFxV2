module com.hclusclientfxv2 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens com.hclusclientfxv2 to javafx.fxml;
    exports com.hclusclientfxv2;
}