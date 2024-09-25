package view;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import databaseconnect.dataload;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import model.ThanhVien;

public class ControllerDiagram {

	@FXML
	private Canvas canvas;
	// Khoảng cách giữa các nút trên trục ngang và trục dọc
    private static final double HORIZONTAL_GAP = 300;
    private static final double VERTICAL_GAP = 100;

    // Chiều rộng và chiều cao của mỗi nút
    private static final double NODE_WIDTH = 120;
    private static final double NODE_HEIGHT = 120;
    dataload dt= new dataload();
	// Phương thức để khởi tạo
	public void initialize() {
		GraphicsContext gc = canvas.getGraphicsContext2D();
		LinearGradient linearGradient = new LinearGradient(
                0, 0, // điểm bắt đầu của gradient (0.0, 0.0)
                1, 1, // điểm kết thúc của gradient (1.0, 1.0) tương ứng với góc từ trên cùng bên trái đến dưới cùng bên phải của canvas
                true, // chu kỳ gradient (true cho phép lặp lại)
                CycleMethod.REPEAT, // chế độ lặp lại
                new Stop(0, Color.web("#DFD0B8")), // màu xanh dương nhạt ở vị trí bắt đầu
                new Stop(1, Color.web("#A3FFD6")) // màu đỏ tươi ở vị trí kết thúc
        );
        gc.setFill(linearGradient);
        
        // Vẽ hình chữ nhật che phủ toàn bộ Canvas
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        // Tạo cây gia phả (ví dụ)
        ThanhVien root = dt.GetAllTV().get(0);

        // Vẽ sơ đồ gia phả
        drawFamilyTree(gc, root, 500, 10);
	}

	private void drawFamilyTree(GraphicsContext gc, ThanhVien member, double x, double y) {
        if (member == null) {
            return;
        }

        // Vẽ node thành viên
        drawNode(gc, member, x, y);
        if (member.getIdVc()!=null && member.getIdVc()!="" ) {
        	for (ThanhVien tv : dt.GetAllTV()) {
        		if (member.getIdVc().equals(tv.getIdTV())){
        			drawNode(gc,tv,x+140,y);
        			break;
        		}
        	}
        }
        // Vẽ các đường dẫn đến con cái (nếu có)
        double childX = x - (member.getDSCon().size() - 1) * (HORIZONTAL_GAP / 2);
        for (ThanhVien child : member.getDSCon()) {
            // Vẽ đường dẫn
            gc.strokeLine(x+60, y + NODE_HEIGHT, childX+60, y + VERTICAL_GAP+120);

            // Đệ quy vẽ cây con
            drawFamilyTree(gc, child, childX, y + VERTICAL_GAP + NODE_HEIGHT);

            // Cập nhật tọa độ cho con cái tiếp theo
            childX += HORIZONTAL_GAP;
        }
    }
	// Phương thức để vẽ node thành viên
    private void drawNode(GraphicsContext gc, ThanhVien member, double x, double y) {
        // Vẽ ảnh
        if (!member.getAnh().isEmpty()) {
            Image image = new Image(member.getAnh());
            gc.drawImage(image, x+15, y, NODE_WIDTH-30, NODE_HEIGHT-30);
            gc.strokeRect(x+15, y, NODE_WIDTH-30, NODE_HEIGHT-30);
        }

        // Vẽ tên và năm sinh
        gc.setFont(new Font(12));
        gc.strokeText(member.getTen(), x + 20, y + 103);
        gc.strokeText("Năm sinh: " + member.getNamSinh(), x + 18, y + 116);

        // Vẽ viền cho node
        gc.strokeRect(x, y, NODE_WIDTH, NODE_HEIGHT);
    }

    @FXML
    private Button outbtn;

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

}
