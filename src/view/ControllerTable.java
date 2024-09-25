package view;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;

import databaseconnect.JDBCConnect;
import databaseconnect.dataload;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.ThanhVien;

public class ControllerTable {
	@FXML
	private TextField searchField;
	@FXML
	private TableView<ThanhVien> tableView;
	@FXML
	private TableColumn<ThanhVien, String> anh;

	@FXML
	private TableColumn<ThanhVien, String> gioitinh;

	@FXML
	private TableColumn<ThanhVien, String> idBo;

	@FXML
	private TableColumn<ThanhVien, String> idMe;

	@FXML
	private TableColumn<ThanhVien, String> idTV;

	@FXML
	private TableColumn<ThanhVien, String> idVc;

	@FXML
	private TableColumn<ThanhVien, Integer> namSinh;

	@FXML
	private TableColumn<ThanhVien, String> ten;

	@FXML
	private TableColumn<ThanhVien, String> tenBo;

	@FXML
	private TableColumn<ThanhVien, String> tenMe;
	@FXML
	private TableColumn<ThanhVien, String> tenVc;
	@FXML
	private TableColumn<ThanhVien, Void> actionColumn;
	@FXML
	private Button addbtn;
	@FXML
	private Button search;
	@FXML
	private Button show;
    @FXML
    private Button relations;
	@FXML
	private Button update;
	// Khởi tạo dữ liệu
	public void initialize() {
		idTV.setCellValueFactory(cellData -> cellData.getValue().idTVProperty());
		idBo.setCellValueFactory(cellData -> cellData.getValue().idBoProperty());
		idMe.setCellValueFactory(cellData -> cellData.getValue().idMeProperty());
		idVc.setCellValueFactory(cellData -> cellData.getValue().idVcProperty());
		tenBo.setCellValueFactory(cellData -> cellData.getValue().tenBoProperty());
		tenMe.setCellValueFactory(cellData -> cellData.getValue().tenMeProperty());
		ten.setCellValueFactory(cellData -> cellData.getValue().tenProperty());
		tenVc.setCellValueFactory(new PropertyValueFactory<ThanhVien, String>("tenVc"));
		namSinh.setCellValueFactory(new PropertyValueFactory<ThanhVien, Integer>("namSinh"));
		gioitinh.setCellValueFactory(cellData -> cellData.getValue().gioiTinhProperty());
		actionColumn.setCellFactory(ActionButtonTableCell.forTableColumn());
		dataload dt = new dataload();
		ObservableList<ThanhVien> data = FXCollections.observableArrayList();
	    for (ThanhVien v : dt.GetAllTV()) {
	        data.add(v);
	    }
	    // Đặt dữ liệu vào TableView
	    tableView.setItems(data);
		
		FilteredList<ThanhVien> filteredData = new FilteredList<>(data, p -> true);

		// Lắng nghe sự kiện thay đổi trong TextField và lọc dữ liệu tương ứng
		searchField.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(thanhVien -> {
				// Nếu từ khóa tìm kiếm là rỗng, hiển thị tất cả các hàng
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}

				// Chuyển đổi tên thành viên và từ khóa tìm kiếm thành chữ thường để so sánh
				// không phân biệt hoa thường
				String lowerCaseFilter = newValue.toLowerCase();
				String tenTV = thanhVien.getTen().toLowerCase();

				// So sánh tên thành viên với từ khóa tìm kiếm
				return tenTV.contains(lowerCaseFilter);
			});
		});

		// Gán dữ liệu đã lọc vào TableView
		tableView.setItems(filteredData);

	}

	static class ActionButtonTableCell extends TableCell<ThanhVien, Void> {
		private final Button editButton = new Button("Sửa");
		private final Button deleteButton = new Button("Xóa");

		public ActionButtonTableCell() {
			// Xử lý sự kiện click cho nút "Sửa"
			editButton.setOnAction(event -> {
				// Lấy dữ liệu của hàng tương ứng
				ThanhVien thanhVien = getTableView().getItems().get(getIndex());
				if (thanhVien != null) {
					try {
						// Tải tệp FXML cho Scene mới
						FXMLLoader loader = new FXMLLoader(getClass().getResource("editForm.fxml"));
						Parent root = loader.load();

						// Tạo Scene mới
						Scene newScene = new Scene(root);

						// Lấy Stage hiện tại từ sự kiện (event)
						Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

						// Thiết lập Scene mới cho Stage hiện tại
						currentStage.setScene(newScene);
						ControllerEdit edit = loader.getController();
						dataload dt = new dataload();
						ThanhVien Bo = new ThanhVien(), Me = new ThanhVien(), Vc = new ThanhVien();

						for (ThanhVien v : dt.GetAllTV()) {

							if (v.getIdTV().equals(thanhVien.getIdBo())) {
								Bo = v;
							}
							if (v.getIdTV().equals(thanhVien.getIdMe())) {
								Me = v;
							}
							if (v.getIdTV().equals(thanhVien.getIdVc())) {
								Vc = v;
							}
						}
						SpinnerValueFactory<Integer> spinnerFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(
								1800, 2024, thanhVien.getNamSinh());

						edit.setTextField(thanhVien.getTen(), Bo, Me, Vc, thanhVien.getGioiTinh(), spinnerFactory,
								thanhVien.getAnh());
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			});

			// Xử lý sự kiện click cho nút "Xóa"
			deleteButton.setOnAction(event -> {
				// Lấy dữ liệu của hàng tương ứng
			    ThanhVien thanhVien = getTableView().getItems().get(getIndex());
			    dataload dt = new dataload();
			  
	            if (thanhVien != null) {
			        // Tạo một cửa sổ xác nhận (alert)
			        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
			        alert.setTitle("Xác nhận xóa");
			        alert.setHeaderText("Bạn có chắc chắn muốn xóa?");
			        alert.setContentText("Hành động này không thể hoàn tác!");

			        // Tùy chọn nút xác nhận và hủy
			        ButtonType buttonTypeYes = new ButtonType("Có", ButtonBar.ButtonData.YES);
			        ButtonType buttonTypeNo = new ButtonType("Không", ButtonBar.ButtonData.NO);

			        // Thêm các nút vào cửa sổ xác nhận
			        alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

			        // Hiển thị cửa sổ xác nhận và chờ người dùng phản hồi
			        Optional<ButtonType> result = alert.showAndWait();

			        // Xử lý hành động tương ứng với phản hồi từ người dùng
			        if (result.isPresent() && result.get() == buttonTypeYes) {
			            // Người dùng đã xác nhận xóa, thực hiện hành động xóa
			            
			            dt.deleteTV(thanhVien.getIdTV());
			            ObservableList<ThanhVien> data = FXCollections.observableArrayList();
			    	    for (ThanhVien v : dt.GetAllTV()) {
			    	        data.add(v);
			    	    }
			    	    // Đặt dữ liệu vào TableView
			    	    getTableView().setItems(data);
			    
			        }
			    }

			});
			editButton.getStyleClass().add("edit-button");
        	deleteButton.getStyleClass().add("delete-button");
            setGraphic(new HBox(editButton, deleteButton));
            setAlignment(Pos.CENTER);
		}

		@Override
		protected void updateItem(Void item, boolean empty) {
			super.updateItem(item, empty);
			if (empty) {
				setGraphic(null);
			} else {
				setGraphic(new HBox(editButton, deleteButton));
			}
		}

		// Factory method để tạo ra các ô dữ liệu của cột có hai nút "Sửa" và "Xóa"
		public static Callback<TableColumn<ThanhVien, Void>, TableCell<ThanhVien, Void>> forTableColumn() {
			return param -> new ActionButtonTableCell();
		}
	}

	@FXML
	void showDiagram(ActionEvent event) {
		try {
			// Tải tệp FXML cho Scene mới
			FXMLLoader loader = new FXMLLoader(getClass().getResource("diagram.fxml"));
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
	private void changeScene(ActionEvent event) {
		try {
			// Tải tệp FXML cho Scene mới
			FXMLLoader loader = new FXMLLoader(getClass().getResource("addForm.fxml"));
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
	void updatedata() {
		ObservableList<ThanhVien> data = FXCollections.observableArrayList();
	    dataload dt = new dataload();
	    for (ThanhVien v : dt.GetAllTV()) {
	        data.add(v);
	    }
	    // Đặt dữ liệu vào TableView
	    tableView.setItems(data);

	    // Hiển thị cảnh báo đã cập nhật
	    Alert alert = new Alert(AlertType.INFORMATION);
	    alert.setTitle("Cập nhật thành công");
	    alert.setHeaderText(null);
	    alert.setContentText("Dữ liệu đã được cập nhật thành công.");
	    alert.showAndWait();
	}
    @FXML
    void relations_click(ActionEvent event) {
    	try {
			// Tải tệp FXML cho Scene mới
			FXMLLoader loader = new FXMLLoader(getClass().getResource("relationsForm.fxml"));
			Parent root = loader.load();

			// Tạo Scene mới
			Scene newScene = new Scene(root);
			Stage newStage= new Stage();
			newStage.setTitle("Quan hệ giữa các thành viên");
			newStage.setScene(newScene);
			  newStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

}
