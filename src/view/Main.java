package view;

import databaseconnect.dataload;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.ThanhVien;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        // Tải tệp FXML
        Parent root = FXMLLoader.load(getClass().getResource("table.fxml"));
        
        // Tại Scene và thiết lập nó cho Stage chính
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
        //Trần Quang Tùng
        //Đinh Thị Linh
        //Trần Dần
    }

    public static void main(String[] args) {
        launch(args);
    }
}
