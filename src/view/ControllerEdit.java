package view;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import databaseconnect.JDBCConnect;
import databaseconnect.dataload;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import model.ThanhVien;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;

public class ControllerEdit {

    @FXML
    private ChoiceBox<ThanhVien> choiceBoxBo_edit;

    @FXML
    private ChoiceBox<String> choiceBoxGT_edit;

    @FXML
    private ChoiceBox<ThanhVien> choiceBoxMe_edit;

    @FXML
    private ChoiceBox<ThanhVien> choiceBoxVC_edit;

    @FXML
    private Button confirmbtn;

    @FXML
    private Button outbtn;

    @FXML
    private Spinner<Integer> spinerNameSinh_edit;

    @FXML
    private TextField txt_link_edit;

    @FXML
    private TextField txt_name_edit;
    @FXML
    private ImageView avatar_edit;
    @FXML
    private Text no_img_edit;

    @FXML
    private Text note_edit;
    @FXML
    private Text chamthan_edit;
    class ThanhVienStringConverter extends StringConverter<ThanhVien> {

		public String toString(ThanhVien thanhVien) {
			// Chuyển đổi ThanhVien thành chuỗi (sử dụng thuộc tính "ten" của ThanhVien)
			return thanhVien != null ? thanhVien.getTen() : "";
		}

		@Override
		public ThanhVien fromString(String arg0) {
			// TODO Auto-generated method stub
			return null;
		}}
	public void initialize() {
		ObservableList<ThanhVien> data = FXCollections.observableArrayList(), data_man = FXCollections.observableArrayList(), data_wman = FXCollections.observableArrayList();
		dataload dt = new dataload();

		for (ThanhVien v : dt.GetAllTV()) {
			data.add(v);
			if (v.getGioiTinh().contains("Nam")) data_man.add(v);
			else data_wman.add(v);
		}
		ObservableList<String> newItems = FXCollections.observableArrayList("Nam", "Nữ");
		choiceBoxGT_edit.setItems(newItems);
		choiceBoxBo_edit.setItems(data_man);
		choiceBoxMe_edit.setItems(data_wman);
		choiceBoxVC_edit.setItems(data);
		
		// Khởi tạo một đối tượng ThanhVienStringConverter
		ThanhVienStringConverter converter = new ThanhVienStringConverter();

		// Thiết lập converter cho ChoiceBox
		choiceBoxBo_edit.setConverter(converter);
		choiceBoxMe_edit.setConverter(converter);
		choiceBoxVC_edit.setConverter(converter);

		SpinnerValueFactory.IntegerSpinnerValueFactory valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(
				1800, 2024);

		// Đặt giá trị mặc định nếu cần (ví dụ: năm hiện tại)
		valueFactory.setValue(2024);

		// Thiết lập SpinnerValueFactory cho Spinner
		spinerNameSinh_edit.setValueFactory(valueFactory);
		txt_link_edit.textProperty().addListener((observable, oldValue, newValue) -> {
		    if (!newValue.isEmpty()) {
		        try {
		            // Thử tải ảnh từ đường dẫn mới
		            Image image = new Image(newValue);
		            
		            // Kiểm tra xem ảnh đã tải thành công chưa
		            if (image.isError()) {
		                // Xóa ảnh nếu không tải thành công
		                avatar_edit.setImage(null);
		                // Hiển thị placeholderText
		                no_img_edit.setVisible(true);
		                chamthan_edit.setVisible(true);
		            } else {
		                // Hiển thị ảnh trong ImageView
		                avatar_edit.setImage(image);
		                // Ẩn placeholderText
		                no_img_edit.setVisible(false);
		                chamthan_edit.setVisible(false);
		            }
		        } catch (Exception e) {
		            // Xóa ảnh nếu gặp lỗi khi tải
		            avatar_edit.setImage(null);
		            // Hiển thị placeholderText
		            no_img_edit.setVisible(true);
		            chamthan_edit.setVisible(true);
		        }
		    } else {
		        // Xóa ảnh nếu không có đường dẫn
		        avatar_edit.setImage(null);
		        // Hiển thị placeholderText
		        no_img_edit.setVisible(true);
		        chamthan_edit.setVisible(true);
		    }
		});
		chamthan_edit.setOnMouseEntered(event -> {
			chamthan_edit.setScaleX(1.2); // Phóng to văn bản khi hover
			chamthan_edit.setScaleY(1.2);
			note_edit.setVisible(true);
        });

		chamthan_edit.setOnMouseExited(event -> {
			chamthan_edit.setScaleX(1.0); // Thu nhỏ văn bản khi rời khỏi
			chamthan_edit.setScaleY(1.0);
			note_edit.setVisible(false);
        });

		

	}

    @FXML
    void confirm_edit(ActionEvent event) {
    	String id = null;
    	dataload dt=new dataload();
    	for (ThanhVien v: dt.GetAllTV()) {
    		if (v.getTen().equals(txt_name_edit.getText())) {
    			id=v.getIdTV();
    			break;
    		}
    	}
    	Connection connect = JDBCConnect.getJDBCConnect();
		String sql = "update ThanhVien set tenTV=? , namsinh=?, gioitinh=?, anh=?, idbo=?, idme=?,idvc=? where idThanhvien=?";
		try {
			PreparedStatement st = connect.prepareStatement(sql);
			st.setString(1, txt_name_edit.getText());
			st.setInt(2, spinerNameSinh_edit.getValue());
			st.setString(3, choiceBoxGT_edit.getValue());
			st.setString(4, txt_link_edit.getText());
			if (choiceBoxBo_edit.getValue() != null && choiceBoxBo_edit.getValue().getIdTV()!="") {
				st.setString(5, choiceBoxBo_edit.getValue().getIdTV());
			}
			else {
				st.setString(5, null);
			}
			if (choiceBoxMe_edit.getValue() != null  && choiceBoxMe_edit.getValue().getIdTV()!="" ) {
				st.setString(6, choiceBoxMe_edit.getValue().getIdTV());
			}
			else {
				st.setString(6, null);
			}
			if (choiceBoxVC_edit.getValue() != null && choiceBoxVC_edit.getValue().getIdTV()!="" ) {
				st.setString(7, choiceBoxVC_edit.getValue().getIdTV());
			}
			else {
				st.setString(7, null);
			}
			st.setString(8, id);

			int rs = st.executeUpdate();
			outclick(event);

		} catch (SQLException e) {
			e.printStackTrace();
		}

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
	public void setTextField(String ten ,ThanhVien Bo,ThanhVien Me,ThanhVien Vc,String GioiTinh, SpinnerValueFactory<Integer> NamSinh, String link) {
		txt_name_edit.setText(ten);
		txt_link_edit.setText(link);
		choiceBoxBo_edit.setValue(Bo);
		choiceBoxGT_edit.setValue(GioiTinh);
		choiceBoxMe_edit.setValue(Me);
		spinerNameSinh_edit.setValueFactory(NamSinh);;
		choiceBoxVC_edit.setValue(Vc);
		

	}

}
