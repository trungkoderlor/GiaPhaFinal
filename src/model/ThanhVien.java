package model;

import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
public class ThanhVien {

    //thuộc tính

    private SimpleStringProperty idTV=new SimpleStringProperty("tv000");
    private SimpleStringProperty ten;
    private SimpleIntegerProperty namSinh;
    private SimpleStringProperty gioiTinh;
    private SimpleStringProperty anh;
    private SimpleStringProperty idBo;
    private SimpleStringProperty tenBo;
    private SimpleStringProperty idMe;
    private SimpleStringProperty tenMe;
    private SimpleStringProperty idVc;
    private SimpleStringProperty tenVc=new SimpleStringProperty("");;
    private List<ThanhVien> DSCon;

    public SimpleStringProperty tenVcProperty() {
        return tenVc;
    }


	public void setTenVc(String tenVc) {
		this.tenVc.set(tenVc);
	}
	public String getTenVc() {
        return tenVc.get();
    }
	// Constructor
    public ThanhVien(String idTV, String ten, int namSinh, String gioiTinh, String anh, String idBo, String tenBo, String idMe, String tenMe, String idVc,String tenVc) {
        this.idTV = new SimpleStringProperty(idTV);
        this.ten = new SimpleStringProperty(ten);
        this.namSinh = new SimpleIntegerProperty(namSinh);
        this.gioiTinh = new SimpleStringProperty(gioiTinh);
        this.anh = new SimpleStringProperty(anh);
        this.idBo = new SimpleStringProperty(idBo);
        this.tenBo = new SimpleStringProperty(tenBo);
        this.idMe = new SimpleStringProperty(idMe);
        this.tenMe = new SimpleStringProperty(tenMe);
        this.idVc = new SimpleStringProperty(idVc);
        this.tenVc = new SimpleStringProperty(tenVc);
        this.DSCon = new ArrayList<>();
    }

    //phương thức
    //hàm khởi tạo
    public ThanhVien() {
        DSCon= new ArrayList<>();
        this.idTV = new SimpleStringProperty("");
        this.ten = new SimpleStringProperty("");
        this.namSinh = new SimpleIntegerProperty(0);
        this.gioiTinh = new SimpleStringProperty("");
        this.anh = new SimpleStringProperty("");
        this.idBo = new SimpleStringProperty("");
        this.tenBo = new SimpleStringProperty("");
        this.idMe = new SimpleStringProperty("");
        this.tenMe = new SimpleStringProperty("");
        this.idVc = new SimpleStringProperty("");
        this.tenVc = new SimpleStringProperty("");
        this.DSCon = new ArrayList<>();

    }

    public String getIdTV() {
        return idTV.get();
    }

    public SimpleStringProperty idTVProperty() {
        return idTV;
    }

    public void setIdTV(String idTV) {
        this.idTV.set(idTV);
    }

    public String getTen() {
        return ten.get();
    }

    public SimpleStringProperty tenProperty() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten.set(ten);
    }

    public int getNamSinh() {
        return namSinh.get();
    }

    public SimpleIntegerProperty namSinhProperty() {
        return namSinh;
    }

    public void setNamSinh(int namSinh) {
        this.namSinh.set(namSinh);
    }

    public String getGioiTinh() {
        return gioiTinh.get();
    }

    public SimpleStringProperty gioiTinhProperty() {
        return gioiTinh;
    }

    public void setGioiTinh(String gioiTinh) {
        this.gioiTinh.set(gioiTinh);
    }

    public String getAnh() {
        return anh.get();
    }

    public SimpleStringProperty anhProperty() {
        return anh;
    }

    public void setAnh(String anh) {
        this.anh.set(anh);
    }

    public String getIdBo() {
        return idBo.get();
    }

    public SimpleStringProperty idBoProperty() {
        return idBo;
    }

    public void setIdBo(String idBo) {
        this.idBo.set(idBo);
    }

    public String getTenBo() {
        return tenBo.get();
    }

    public SimpleStringProperty tenBoProperty() {
        return tenBo;
    }

    public void setTenBo(String tenBo) {
        this.tenBo.set(tenBo);
    }

    public String getIdMe() {
        return idMe.get();
    }

    public SimpleStringProperty idMeProperty() {
        return idMe;
    }

    public void setIdMe(String idMe) {
        this.idMe.set(idMe);
    }

    public String getTenMe() {
        return tenMe.get();
    }

    public SimpleStringProperty tenMeProperty() {
        return tenMe;
    }

    public void setTenMe(String tenMe) {
        this.tenMe.set(tenMe);
    }

    public String getIdVc() {
        return idVc.get();
    }

    public SimpleStringProperty idVcProperty() {
        return idVc;
    }

    public void setIdVc(String idVc) {
        this.idVc.set(idVc);
    }

    public List<ThanhVien> getDSCon() {
        return DSCon;
    }

    public void setDSCon(List<ThanhVien> DSCon) {
        this.DSCon = DSCon;
    }
}
