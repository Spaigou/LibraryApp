package for_req;

import for_bd.DBConnection;
import java.sql.*;
import java.util.ArrayList;

public class BookReq {

    private ArrayList<Object[]> prepare_to_send(ResultSet resultSet) throws SQLException{
        ArrayList<Object[]> res = new ArrayList<>();
        while (resultSet.next()){
            res.add(new Object[] {resultSet.getInt("id"),resultSet.getString("author"),resultSet.getString("publication"),resultSet.getString("publishing_house"),resultSet.getInt("year_public"),resultSet.getInt("pages"),resultSet.getInt("year_write"), resultSet.getInt("weight"),resultSet.getInt("place_id")});
        }
        return res;
    }

    public ArrayList<Object[]> get_book(String a_name) throws SQLException {
        PreparedStatement st = DBConnection.get_connection().prepareStatement("SELECT * FROM book WHERE author = ?");
        st.setString(1, a_name);
        ResultSet resultSet = st.executeQuery();
        return prepare_to_send(resultSet);
    }

    public void add_book(ArrayList<String> array) throws SQLException{
        PreparedStatement st = DBConnection.get_connection().prepareStatement("INSERT INTO book (author, publication, publishing_house, year_public, pages, year_write, weight, place_id) VALUES (?,?,?,?,?,?,?,?)");
        st.setString(1, array.get(0));
        st.setString(2, array.get(1));
        st.setString(3, array.get(2));
        st.setInt(4, Integer.parseInt(array.get(3)));
        st.setInt(5, Integer.parseInt(array.get(4)));
        st.setInt(6, Integer.parseInt(array.get(5)));
        st.setInt(7, Integer.parseInt(array.get(6)));
        st.setInt(8, Integer.parseInt(array.get(7)));
        st.executeUpdate();
        st.close();
    }

    public void edit_book(ArrayList<String> array) throws SQLException{
        PreparedStatement st = DBConnection.get_connection().prepareStatement("UPDATE book SET author = ?, publication = ?, publishing_house = ?, year_public = ?, pages = ?, year_write = ?, weight = ?, place_id = ? WHERE id = ?");
        st.setString(1, array.get(0));
        st.setString(2, array.get(1));
        st.setString(3, array.get(2));
        st.setInt(4, Integer.parseInt(array.get(3)));
        st.setInt(5, Integer.parseInt(array.get(4)));
        st.setInt(6, Integer.parseInt(array.get(5)));
        st.setInt(7, Integer.parseInt(array.get(6)));
        st.setInt(8, Integer.parseInt(array.get(7)));
        st.setInt(9, Integer.parseInt(array.get(8)));
        st.executeUpdate();
        st.close();
    }

    public ArrayList<Object[]> pub_h_wardrobe(int wardrobe) throws SQLException{
        PreparedStatement st = DBConnection.get_connection().prepareStatement("SELECT publishing_house, wardrobe, publication FROM book INNER JOIN place ON place_id = place.id WHERE wardrobe = ? ORDER BY publishing_house");
        st.setInt(1, wardrobe);
        ResultSet resultSet = st.executeQuery();
        ArrayList<Object[]> res = new ArrayList<>();
        while (resultSet.next()){
            res.add(new Object[] {resultSet.getString("publishing_house"),resultSet.getInt("wardrobe"),resultSet.getString("publication")});
        }
        return res;
    }

    public ArrayList<Object[]> max_min_page_floor(int floor) throws SQLException{
        PreparedStatement st = DBConnection.get_connection().prepareStatement("SELECT MAX(book.pages), MIN(book.pages), place.floor FROM book INNER JOIN place ON book.place_id = place.id WHERE place.floor = ? GROUP BY place.floor");
        st.setInt(1, floor);
        ResultSet resultSet = st.executeQuery();
        ArrayList<Object[]> res = new ArrayList<>();
        while (resultSet.next()){
            int[] mass = new int[3];
            for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++){
                mass[i-1] = resultSet.getInt(i);
            }
            res.add(new Object[] {mass[0], mass[1], mass[2]});
        }
        return res;
    }

    public ArrayList<Object[]> get_allbook() throws SQLException {
        PreparedStatement st = DBConnection.get_connection().prepareStatement("SELECT * FROM book");
        ResultSet resultSet = st.executeQuery();
        return prepare_to_send(resultSet);
    }

    public void delete(int id_arg) throws SQLException{
        PreparedStatement st = DBConnection.get_connection().prepareStatement("DELETE FROM book WHERE id = ?");
        st.setInt(1, id_arg);
        st.executeUpdate();
        st.close();
    }

}
