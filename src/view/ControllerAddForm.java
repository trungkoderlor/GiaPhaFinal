package view;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


import databaseconnect.JDBCConnect;
import databaseconnect.dataload;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Labeled;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.ThanhVien;
import javafx.util.StringConverter;

public class ControllerAddForm {
	@FXML
	private ChoiceBox<ThanhVien> choiceBoxBo;

	@FXML
	private ChoiceBox<ThanhVien> choiceBoxMe;

	@FXML
	private ChoiceBox<ThanhVien> choiceBoxVC;
	@FXML
	private ChoiceBox<String> choiceBoxGT;
	@FXML
	private Spinner<Integer> spinerNameSinh;
	@FXML
	private Button outbtn;
	@FXML
	private Button confirmbtn;
	@FXML
	private TextField txt_link;

	@FXML
	private TextField txt_name;
    @FXML
    private ImageView avatar;

    @FXML
    private Text no_img;
    @FXML
    private Text chamthan;
    @FXML
    private Text note;

	public void initialize() {
		ObservableList<ThanhVien> data = FXCollections.observableArrayList(), data_man = FXCollections.observableArrayList(), data_wman = FXCollections.observableArrayList();
		dataload dt = new dataload();

		for (ThanhVien v : dt.GetAllTV()) {
			data.add(v);
			if (v.getGioiTinh()!= null && v.getGioiTinh().contains("Nam")) data_man.add(v);
			else data_wman.add(v);
		}
		ObservableList<String> newItems = FXCollections.observableArrayList("Nam", "Nữ");
		choiceBoxGT.setItems(newItems);
		choiceBoxBo.setItems(data_man);
		choiceBoxMe.setItems(data_wman);
		choiceBoxVC.setItems(data);
       
		txt_link.textProperty().addListener((observable, oldValue, newValue) -> {
		    if (!newValue.isEmpty()) {
		        try {
		            // Thử tải ảnh từ đường dẫn mới
		            Image image = new Image(newValue);
		            
		            // Kiểm tra xem ảnh đã tải thành công chưa
		            if (image.isError()) {
		                // Xóa ảnh nếu không tải thành công
		                avatar.setImage(null);
		                // Hiển thị placeholderText
		                no_img.setVisible(true);
		                chamthan.setVisible(true);
		            } else {
		                // Hiển thị ảnh trong ImageView
		                avatar.setImage(image);
		                // Ẩn placeholderText
		                no_img.setVisible(false);
		                chamthan.setVisible(false);
		            }
		        } catch (Exception e) {
		            // Xóa ảnh nếu gặp lỗi khi tải
		            avatar.setImage(null);
		            // Hiển thị placeholderText
		            no_img.setVisible(true);
		            chamthan.setVisible(true);
		        }
		    } else {
		        // Xóa ảnh nếu không có đường dẫn
		        avatar.setImage(null);
		        // Hiển thị placeholderText
		        no_img.setVisible(true);
		        chamthan.setVisible(true);
		    }
		});

		class ThanhVienStringConverter extends StringConverter<ThanhVien> {

			public String toString(ThanhVien thanhVien) {
				// Chuyển đổi ThanhVien thành chuỗi (sử dụng thuộc tính "ten" của ThanhVien)
				return thanhVien != null ? thanhVien.getTen() : "";
			}

			public ThanhVien fromString(String string) {
				// Chuyển đổi từ chuỗi sang đối tượng ThanhVien (nếu cần thiết)
				// Nếu không cần từ chuỗi sang ThanhVien, bạn có thể để trống
				return null; // Hoặc trả về giá trị mặc định
			}
		}
		// Khởi tạo một đối tượng ThanhVienStringConverter
		ThanhVienStringConverter converter = new ThanhVienStringConverter();

		// Thiết lập converter cho ChoiceBox
		choiceBoxBo.setConverter(converter);
		choiceBoxMe.setConverter(converter);
		choiceBoxVC.setConverter(converter);

		SpinnerValueFactory.IntegerSpinnerValueFactory valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(
				1800, 2024);

		// Đặt giá trị mặc định nếu cần (ví dụ: năm hiện tại)
		valueFactory.setValue(2024);

		// Thiết lập SpinnerValueFactory cho Spinner
		spinerNameSinh.setValueFactory(valueFactory);
		chamthan.setOnMouseEntered(event -> {
			chamthan.setScaleX(1.2); // Phóng to văn bản khi hover
			chamthan.setScaleY(1.2);
			chamthan.setFill(Color.web("#7F27FF"));
			note.setVisible(true);
        });

		chamthan.setOnMouseExited(event -> {
			chamthan.setScaleX(1.0); // Thu nhỏ văn bản khi rời khỏi
			chamthan.setScaleY(1.0);
			chamthan.setFill(Color.web("#E72929"));
			note.setVisible(false);
        });


	}

	@FXML
	void outclick(ActionEvent event) {
		try {
			// Tải tệp FXML cho Scene mới
			FXMLLoader loader = new FXMLLoader(getClass().getResource("table.fxml"));
			Parent root = loader.load();

			// Tạo Scene mới
			Scene newScene = new Scene(root);

			// Lấy Stage hiện tại từ sự kiện (event)
			Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

			// Thiết lập Scene mới cho Stage hiện tại
			currentStage.setScene(newScene);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	void confirmclick(ActionEvent event) {
		Connection connect = JDBCConnect.getJDBCConnect();
		String sql = "DECLARE @newID CHAR(5);"
				+ "EXEC GenerateIDThanhVien @newID OUTPUT;"
				+ "INSERT INTO ThanhVien (idThanhvien, tenTV, namsinh, gioitinh, anh, idbo, idme, idvc) VALUES (@newID,?,?,?,?,?,?,?)";
		try {
			PreparedStatement st = connect.prepareStatement(sql);
			st.setString(1, txt_name.getText());
			st.setInt(2, spinerNameSinh.getValue());
			st.setString(3, choiceBoxGT.getValue());
			st.setString(4, txt_link.getText());
			if (choiceBoxBo.getValue() != null) {
				st.setString(5, choiceBoxBo.getValue().getIdTV());
			}
			else {
				st.setString(5, null);
			}
			if (choiceBoxMe.getValue() != null) {
				st.setString(6, choiceBoxMe.getValue().getIdTV());
			}
			else {
				st.setString(6, null);
			}
			if (choiceBoxVC.getValue() != null) {
				st.setString(7, choiceBoxVC.getValue().getIdTV());
			}
			else {
				st.setString(7, null);
			}

			int rs = st.executeUpdate();
			outclick(event);

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
}
