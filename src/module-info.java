module test {
	requires javafx.controls;
	requires java.sql;
	requires javafx.fxml;
	requires javafx.graphics;
	requires javafx.base;
	opens model to javafx.base;
	opens view to javafx.graphics, javafx.fxml,javafx.base;
}
