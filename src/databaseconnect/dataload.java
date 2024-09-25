package databaseconnect;



import java.util.ArrayList;
import java.util.List;
import model.*;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Huy Trung
 */
public class dataload {

    public List<ThanhVien> GetAllTV() {
        Connection connect = JDBCConnect.getJDBCConnect();

        String sql = "select * from ThanhVien";
        try {
            List<ThanhVien> TVs = new ArrayList<ThanhVien>();
            Statement st = connect.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                ThanhVien tv = new ThanhVien();
                tv.setIdTV(rs.getString("idThanhVien"));
                tv.setTen(rs.getString("tenTV"));
                tv.setNamSinh(rs.getInt("namsinh"));
                tv.setGioiTinh(rs.getString("gioitinh"));
                tv.setAnh(rs.getString("anh"));
                tv.setIdBo(rs.getString("idbo"));
                tv.setIdMe(rs.getString("idme"));
                tv.setIdVc(rs.getString("idvc"));
                TVs.add(tv);
            }
            for (ThanhVien k : TVs) {
                for (ThanhVien t : TVs) {
                    if (k.getIdTV().equals(t.getIdBo())) {
                        k.getDSCon().add(t);
                        t.setTenBo(k.getTen());
                    }
                    if (k.getIdTV().equals(t.getIdMe())) {
                        t.setTenMe(k.getTen());
                    }
                    if (k.getIdTV().equals(t.getIdVc())) {
                    	k.setTenVc(t.getTen());
                    	t.setTenVc(k.getTen());
                        k.setIdVc(t.getIdTV());
                        t.setIdVc(k.getIdTV());
                    }
                }
            }
            return TVs;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }
    public void addTV(ThanhVien tv) {
        Connection connect = JDBCConnect.getJDBCConnect();
        String sql = "DECLARE @newID CHAR(5);"
        		+ "EXEC GenerateIDThanhVien @newID OUTPUT;"
        		+ "INSERT INTO ThanhVien (idThanhvien, tenTV, namsinh, gioitinh, anh, idbo, idme, idvc) VALUES (@newID,?,?,?,?,?,?,?)";
        try {
            PreparedStatement st = connect.prepareStatement(sql);
            st.setString(1, tv.getTen());
            st.setInt(2, tv.getNamSinh());
            st.setString(3, tv.getGioiTinh());
            st.setString(4, tv.getAnh());
            st.setString(5, tv.getIdBo());
            st.setString(6, tv.getIdMe());
            st.setString(7, tv.getIdVc());

            int rs = st.executeUpdate();
            System.out.print(rs);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateTV(ThanhVien tv){
         Connection connect = JDBCConnect.getJDBCConnect();
        String sql = "update ThanhVien set tenTV=? , namsinh=?, gioitinh=?, anh=?, idbo=?, idme=?,idVoChong=? where idThanhVien=? ";
        try {
            PreparedStatement st = connect.prepareStatement(sql);
            st.setString(1, tv.getTen());
            st.setInt(2, tv.getNamSinh());
            st.setString(3, tv.getGioiTinh());
            st.setString(4, tv.getAnh());
            st.setString(5, tv.getIdBo());
            st.setString(6, tv.getIdMe());
            st.setString(7, tv.getIdVc());
            st.setString(8, tv.getIdTV());
            int rs = st.executeUpdate();
            System.out.print(rs);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void deleteTV(String id){
        Connection connect= JDBCConnect.getJDBCConnect();
        String sql="delete from ThanhVien where idThanhVien=?";
        try {
            PreparedStatement st = connect.prepareStatement(sql);
            st.setString(1,id);
            int rs = st.executeUpdate();
            System.out.print(rs);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}


