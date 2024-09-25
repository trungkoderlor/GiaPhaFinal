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
import javafx.scene.control.Alert;
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
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.ThanhVien;
import javafx.util.StringConverter;

public class ControllerRelation {
    @FXML
    private Text birth1;

    @FXML
    private Text birth2;

    @FXML
    private Text name1;

    @FXML
    private Text name2;

    @FXML
    private Text result;

    @FXML
    private Text sex1;

    @FXML
    private Text sex2;

    @FXML
    private Button show_btn;

    @FXML
    private Text show_txt;

    @FXML
    private ChoiceBox<ThanhVien> choiceP1;

    @FXML
    private ChoiceBox<ThanhVien> choiceP2;
    @FXML
    private Pane p1;

    @FXML
    private Pane p2;
    ObservableList<ThanhVien> data = FXCollections.observableArrayList();
	
    public void initialize() {
    	
    	dataload dt = new dataload();
		for (ThanhVien v : dt.GetAllTV()) data.add(v);
		choiceP1.setItems(data);
		choiceP2.setItems(data);	
		
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
		choiceP1.setConverter(converter);
		choiceP2.setConverter(converter);
		result.setVisible(false);
		p1.setVisible(false);
		p2.setVisible(false);
    }
    @FXML
    void checkRelationship(ActionEvent event) {
        ThanhVien p1 = choiceP1.getValue();
        ThanhVien p2 = choiceP2.getValue();
        if (p1 == null || p2 == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Thông tin không hợp lệ");
            alert.setHeaderText(null);
            alert.setContentText("Vui lòng chọn cả 2 thành viên.");
            alert.showAndWait();
            return;
        }
        else if(p1 == p2) {
        	Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Thông tin không hợp lệ");
            alert.setHeaderText(null);
            alert.setContentText("Vui lòng chọn cả 2 thành viên khác nhau.");
            alert.showAndWait();
            return;
        }
        else  {	
        	this.p1.setVisible(true);
        	this.p2.setVisible(true);
        	name1.setText(p1.getTen());
        	name2.setText(p2.getTen());
        	birth1.setText(p1.getNamSinh()+"");
        	birth2.setText(p2.getNamSinh()+"");
        	sex1.setText(p1.getGioiTinh());
        	sex2.setText(p2.getGioiTinh());
        	String relationship = findRelationship(p1, p2);
        	result.setText(relationship);
        	centerResultText();
        	result.setVisible(true);
        	LinearGradient gradient = new LinearGradient(
        		    0, 0, 1, 0, true, // proportional
        		    CycleMethod.NO_CYCLE, // không lặp lại
        		    new Stop(0, Color.RED), // từ màu đỏ
        		    new Stop(1, Color.BLUE) // đến màu xanh
        		);
        	result.setFill(gradient);
        	
       }
    }
    private void centerResultText() {
        // Get parent width
        double parentWidth = ((Pane) result.getParent()).getWidth();
        // Get result text width
        double resultWidth = result.getLayoutBounds().getWidth();
        // Set layout X to center the result
        result.setLayoutX((parentWidth - resultWidth) / 2);
    }

    private String findRelationship(ThanhVien person1, ThanhVien person2) {
    	if (isConjugal(person1, person2)) {
            return "Vợ và Chồng";
        }
        if (isSibling(person1, person2)) {
            return "Anh/Chị và Em";
        }
        if(isChiDau(person1,person2) || isChiDau(person2,person1)) {
        	return "Em Chồng và Chị Dâu hoặc Anh Chồng và Em Dâu";
        }
        // Kiểm tra quan hệ cha mẹ con cái
        if (isBo(person1, person2) || isBo(person2, person1) ) {
            return "Bố và Con";
        }
        if (isMe(person1, person2) || isMe(person2, person1) ) {
            return "Mẹ và Con";
        }
        if (isBochong(person1, person2) || isBochong(person2, person1) ) {
            return "Bố Chồng và Con Dâu";
        }
        if (isMechong(person1, person2) || isMechong(person2, person1) ) {
            return "Mẹ Chồng và con Dâu";
        }

        if (isOngBa(person1,person2) || isOngBa(person2,person1)) {
        	return "Ông/Bà và Cháu";
        }
        // Kiểm tra bác/chú/mợ
        if (isChuBac(person1,person2) || isChuBac(person2,person1)) {
        	return "Bác/Cô/Chú và Cháu";
        }
        if (isMo(person1,person2) || isMo(person2,person1)) {
        	return "Mợ và Cháu";
        }

        return "Họ hàng xa";
    }
    private boolean isConjugal(ThanhVien person1, ThanhVien person2) {
        return (person1.getIdTV() != null && person1.getIdTV().equals(person2.getIdVc()));
             }
    private boolean isSibling(ThanhVien person1, ThanhVien person2) {
        return (person1.getIdBo() != null && person1.getIdBo().equals(person2.getIdBo())) ;
  
    }
    private boolean isChiDau(ThanhVien person1, ThanhVien person2) {
    	ThanhVien chong = new ThanhVien();
        if (person1.getIdVc()!= null ) {
        	
        	for (ThanhVien tv:data) {
        		if (person1.getIdVc().equals(tv.getIdTV())) chong=tv;}	
        		
        }
        return (isSibling(chong,person2));
               
    }
    private boolean isBochong(ThanhVien person1, ThanhVien person2) {
    	ThanhVien chong = new ThanhVien();
        if (person1.getIdVc()!= null ) {
        	
        	for (ThanhVien tv:data) {
        		if (person1.getIdVc().equals(tv.getIdTV())) chong=tv;}	
        }
        return (isBo(chong,person2));
  
    }
    private boolean isMechong(ThanhVien person1, ThanhVien person2) {
    	ThanhVien chong = new ThanhVien();
        if (person1.getIdVc()!= null ) {
        	
        	for (ThanhVien tv:data) {
        		if (person1.getIdVc().equals(tv.getIdTV())) chong=tv;}	
        }
        return (isMe(chong,person2));
  
    }
    private boolean isBo( ThanhVien child,ThanhVien parent) {
        return (child.getIdBo() != null && child.getIdBo().equals(parent.getIdTV())) ;
             
    }
    private boolean isMe( ThanhVien child,ThanhVien parent) {
        return (child.getIdMe() != null && child.getIdMe().equals(parent.getIdTV())) ;
             
    }
    private boolean isOngBa(ThanhVien person1, ThanhVien person2) {
    	ThanhVien Bo = new ThanhVien();
        	for (ThanhVien tv:data) {
        		if (isBo(person1,tv) || isBo(tv,person1) || isBochong(person1,tv) || isBochong(tv,person1)) Bo=tv;	
        	}
        return (isBo(Bo,person2) || isMe(Bo,person2));
  
    }
    
    private boolean isChuBac( ThanhVien person1, ThanhVien person2) {
    	ThanhVien Bo = new ThanhVien();
    	for (ThanhVien tv:data) {
    		if (isBo(person1,tv) || isBo(tv,person1) || isBochong(person1,tv) || isBochong(tv,person1)) Bo=tv;	
    	}
    	return (isSibling(Bo,person2));
    }
    private boolean isMo( ThanhVien person1, ThanhVien person2) {
    	ThanhVien Bo = new ThanhVien();
    	for (ThanhVien tv:data) {
    		if (isBo(person1,tv) || isBo(tv,person1) || isBochong(person1,tv) || isBochong(tv,person1)) Bo=tv;	
    	}
    	return (isChiDau(Bo,person2));
    }

}

