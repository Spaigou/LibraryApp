package for_req;

import for_bd.DBConnection;
import java.sql.*;
import java.util.ArrayList;

public class LibReq {
    public void add_place(ArrayList<String> array) throws SQLException{
        PreparedStatement st = DBConnection.get_connection().prepareStatement("INSERT INTO place (floor, wardrobe, shelf) VALUES (?,?,?)");
        st.setInt(1, Integer.parseInt(array.get(0)));
        st.setInt(2, Integer.parseInt(array.get(1)));
        st.setInt(3, Integer.parseInt(array.get(2)));
        st.executeUpdate();
        st.close();
    }

    private ArrayList<Object[]> prepare_to_send(ResultSet resultSet) throws SQLException{
        ArrayList<Object[]> res = new ArrayList<>();
        while (resultSet.next()){
            res.add(new Object[] {resultSet.getInt("id"),resultSet.getInt("floor"),resultSet.getInt("wardrobe"),resultSet.getInt("shelf")});
        }
        return res;
    }

    public ArrayList<Object[]> get_place(int fl) throws SQLException {
        PreparedStatement st = DBConnection.get_connection().prepareStatement("SELECT * FROM place WHERE floor = ?");
        st.setInt(1, fl);
        ResultSet resultSet = st.executeQuery();
        return prepare_to_send(resultSet);
    }

    public void edit_place(ArrayList<String> array) throws SQLException{
        PreparedStatement st = DBConnection.get_connection().prepareStatement("UPDATE place SET floor = ?, wardrobe = ?, shelf = ? WHERE id = ?");
        st.setInt(1, Integer.parseInt(array.get(0)));
        st.setInt(2, Integer.parseInt(array.get(1)));
        st.setInt(3, Integer.parseInt(array.get(2)));
        st.setInt(4, Integer.parseInt(array.get(3)));
        st.executeUpdate();
        st.close();
    }

    public ArrayList<Object[]> get_wardrobes() throws SQLException{
        PreparedStatement st = DBConnection.get_connection().prepareStatement("SELECT * FROM place ORDER BY floor");
        ResultSet resultSet = st.executeQuery();
        return prepare_to_send(resultSet);
    }
    public ArrayList<Object[]> get_allplace() throws SQLException {
        PreparedStatement st = DBConnection.get_connection().prepareStatement("SELECT * FROM place");
        ResultSet resultSet = st.executeQuery();
        return prepare_to_send(resultSet);
    }

    public void delete(int id_arg) throws SQLException{
        PreparedStatement st = DBConnection.get_connection().prepareStatement("DELETE FROM place WHERE id = ?");
        st.setInt(1, id_arg);
        st.executeUpdate();
        st.close();
    }

}
