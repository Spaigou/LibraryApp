package gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;

import for_req.BookReq;
import for_req.LibReq;
import for_bd.DBConnection;
import java.io.FileReader;
import java.util.*;

public class MainFrame extends JPanel implements ActionListener{

    private JPanel panel_control, panel_search_book, panel_book_table, panel_search_place, panel_place_table;
    private JButton reset_button, delete_book_button, delete_place_button, edit_library_button, edit_book_button, add_library_button, add_book_button, show_cupboards_button, show_publishing_house_button, show_pages_button, sb_button, sp_button, show_books_button, show_places_button;
    private final int width = 700;
    private static JFrame main_frame = null;
    private JTextField sb_text, sp_text;
    private Object[][] data;
    private DefaultTableModel tb_book_model, tb_place_model;
    private JTable tb_book, tb_place;
    private JLabel count_tb_book, count_tb_place;

    public MainFrame(){
        super();
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        panel_control = new JPanel();
        panel_control.setPreferredSize(new Dimension(width, 130));
        panel_control.setBorder(BorderFactory.createTitledBorder("Панель управления"));
        panel_control.setLayout(new FlowLayout());
        reset_button = new JButton("Сброс значений");
        reset_button.addActionListener(this);
        delete_book_button = new JButton("Удаление книги");
        delete_book_button.addActionListener(this);
        delete_place_button = new JButton("Удаление библиотеки");
        delete_place_button.addActionListener(this);
        panel_control.add(reset_button);
        panel_control.add(delete_book_button);
        panel_control.add(delete_place_button);
        edit_library_button = new JButton("Редактирование домашней библиотеки");
        panel_control.add(edit_library_button);
        edit_library_button.addActionListener(this);
        edit_book_button = new JButton("Редактирование книги");
        panel_control.add(edit_book_button);
        edit_book_button.addActionListener(this);
        add_library_button = new JButton("Добавление домашней библиотеки");
        panel_control.add(add_library_button);
        add_library_button.addActionListener(this);
        show_books_button = new JButton("Вывод всех книг");
        panel_control.add(show_books_button);
        show_books_button.addActionListener(this);
        show_places_button = new JButton("Вывод всех библиотек");
        panel_control.add(show_places_button);
        show_places_button.addActionListener(this);
        add_book_button = new JButton("Добавление книги");
        panel_control.add(add_book_button);
        add_book_button.addActionListener(this);
        show_publishing_house_button = new JButton("Вывод издательств в шкафу");
        panel_control.add(show_publishing_house_button);
        show_publishing_house_button.addActionListener(this);
        show_pages_button = new JButton("Вывод самых коротких и длинных по кол-ву страниц на этаже");
        panel_control.add(show_pages_button);
        show_pages_button.addActionListener(this);
        add(panel_control);
        panel_search_book = new JPanel();
        panel_search_book.setPreferredSize(new Dimension(width, 50));
        panel_search_book.setBorder(BorderFactory.createTitledBorder("Поиск книг/издательств"));
        panel_search_book.setLayout(new GridLayout());
        sb_text = new JTextField();
        panel_search_book.add(sb_text);
        sb_button = new JButton("Поиск книг");
        panel_search_book.add(sb_button);
        sb_button.addActionListener(this);
        add(panel_search_book);
        panel_book_table = new JPanel();
        panel_book_table.setPreferredSize(new Dimension(width, 150));
        panel_book_table.setLayout(new BoxLayout(panel_book_table, BoxLayout.Y_AXIS));
        panel_book_table.setBorder(BorderFactory.createTitledBorder("Книги"));
        data = new Object[][]{};
        tb_book_model = new DefaultTableModel(new Object[]{"ID", "Автор", "Название книги", "Издательство", "Год публикации", "Кол-во страниц", "Год написания", "Вес", "ID места"}, 0){
            @Override
            public boolean isCellEditable(int row, int column){
              return false;
            };
        };
        tb_book = new JTable();
        tb_book.setModel(tb_book_model);
        panel_book_table.add(new JScrollPane((tb_book)));
        count_tb_book = new JLabel("Найдено записей -> 0");
        panel_book_table.add(count_tb_book);
        add(panel_book_table);

        panel_search_place = new JPanel();
        panel_search_place.setPreferredSize(new Dimension(width, 50));
        panel_search_place.setBorder(BorderFactory.createTitledBorder("Поиск домашней библиотеки"));
        panel_search_place.setLayout(new GridLayout());
        sp_text = new JTextField();
        panel_search_place.add(sp_text);
        sp_button = new JButton("Поиск домашней библиотеки");
        panel_search_place.add(sp_button);
        sp_button.addActionListener(this);
        show_cupboards_button = new JButton("Вывод шкафов в лекс.порядке");
        panel_search_place.add(show_cupboards_button);
        show_cupboards_button.addActionListener(this);
        add(panel_search_place);

        panel_place_table = new JPanel();
        panel_place_table.setPreferredSize(new Dimension(width, 150));
        panel_place_table.setLayout(new BoxLayout(panel_place_table, BoxLayout.Y_AXIS));
        panel_place_table.setBorder(BorderFactory.createTitledBorder("Домашние библиотеки"));
        tb_place_model = new DefaultTableModel(new Object[]{"ID", "Этаж", "Шкаф", "Полка"}, 0){
            @Override
            public boolean isCellEditable(int row, int column){
                return false;
            };
        };
        tb_place = new JTable();
        tb_place.setModel(tb_place_model);
        panel_place_table.add(new JScrollPane(tb_place));
        count_tb_place = new JLabel("Найдено записей -> 0");
        panel_place_table.add(count_tb_place);
        add(panel_place_table);
    }

    public static boolean checking(String str) {
        try {
            int x = Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }


    public void show_frame(){
        JFrame frame = new JFrame("Книги");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        main_frame = frame;
        JComponent component_panel = new MainFrame();
        frame.setContentPane(component_panel);
        frame.pack();
        frame.setVisible(true);
    }

    public void search_book(String find){
        tb_book_model.getDataVector().removeAllElements();
        BookReq obj = new BookReq();
        try{
            ArrayList<Object[]> res = obj.get_book(find);
            for (Object[] array: res){
                tb_book_model.addRow(array);
            }
            count_tb_book.setText("Найдено записей -> " + res.size());
        } catch (SQLException exc){
            throw new RuntimeException(exc);
        }
    }

    public void search_lib(String find){
        tb_place_model.getDataVector().removeAllElements();
        LibReq obj = new LibReq();
        if (checking(find)){
            int fl = Integer.parseInt(find);
            try{
                ArrayList<Object[]> res = obj.get_place(fl);
                for (Object[] array: res){
                    tb_place_model.addRow(array);
                }
                count_tb_place.setText("Найдено записей -> " + res.size());
            } catch (SQLException exc){
                throw new RuntimeException(exc);
            }
        }
    }

    public void search_wardrobes(){
        tb_place_model.getDataVector().removeAllElements();
        LibReq obj = new LibReq();
        try{
            ArrayList<Object[]> res = obj.get_wardrobes();
            for (Object[] array: res){
                tb_place_model.addRow(array);
            }
            count_tb_place.setText("Найдено записей -> " + res.size());
        } catch (SQLException exc){
            throw new RuntimeException(exc);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e){
        String[] book_data = new String[9];
        Arrays.fill(book_data, "");
        String[] place_data = new String[4];
        Arrays.fill(place_data, "");
        String command = e.getActionCommand();
        System.out.println(command);
        if("Сброс значений".equals(command)){
            try{
                FileReader filer = new FileReader("E:/labs/java/9/9/src/main/java/res.txt");
                Scanner scan = new Scanner(filer);
                String ids = scan.nextLine();
                if (ids.length() > 0){
                    String[] arr_id = ids.split(" ");
                    for (int i = 0; i <= arr_id.length - 1; i++) {
                        boolean check = false;
                        try{
                            PreparedStatement st = DBConnection.get_connection().prepareStatement("SELECT COUNT(*) FROM book where id = ?");
                            st.setInt(1, Integer.parseInt(arr_id[i]));
                            ResultSet resultSet = st.executeQuery();
                            resultSet.next();
                            if (resultSet.getInt(1) > 0){
                                System.out.println(resultSet.getInt(1));
                                check = true;
                            }
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                        if (check) {
                            BookReq obj = new BookReq();
                            obj.delete(Integer.parseInt(arr_id[i]));
                        } else {
                            System.out.println("Такой книги нет!");
                        }
                    }
                }
            } catch (Exception exc){
                System.out.println("Файл не найден!");
            }

        }
        if("Удаление книги".equals(command)){
            int select = tb_book.getSelectedRow();
            if (select == -1){
                JOptionPane.showMessageDialog(this, "Выберите книгу для удаления!", "Ошибка",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }
            try{
                DBConnection.get_connection().setAutoCommit(false);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            int id = Integer.parseInt(String.valueOf(tb_book_model.getValueAt(select, 0)));
            BookReq obj = new BookReq();
            try {
                obj.delete(id);
                DBConnection.get_connection().commit();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            try{
                DBConnection.get_connection().setAutoCommit(true);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
        if("Удаление библиотеки".equals(command)){
            int select = tb_place.getSelectedRow();
            if (select == -1){
                JOptionPane.showMessageDialog(this, "Выберите библиотеку для удаления!", "Ошибка",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            try{
                DBConnection.get_connection().setAutoCommit(false);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            int id = Integer.parseInt(String.valueOf(tb_place_model.getValueAt(select, 0)));
            boolean check = true;
            try{
                PreparedStatement st = DBConnection.get_connection().prepareStatement("SELECT COUNT(*) FROM book where place_id = ?");
                st.setInt(1, id);
                ResultSet resultSet = st.executeQuery();
                resultSet.next();
                if (resultSet.getInt(1) > 0){
                    System.out.println(resultSet.getInt(1));
                    check = false;
                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            if (check){
                LibReq obj = new LibReq();
                try{
                    obj.delete(id);
                    DBConnection.get_connection().commit();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
            else{
                JOptionPane.showMessageDialog(this, "Удалите все книги, связанные с данной библиотекой!", "Ошибка",
                        JOptionPane.WARNING_MESSAGE);
            }
            try{
                DBConnection.get_connection().setAutoCommit(true);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
        if("Редактирование домашней библиотеки".equals(command)){
            int select = tb_place.getSelectedRow();
            if (select == -1){
                JOptionPane.showMessageDialog(this, "Выберите библиотеку!", "Message",
                        JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            for (int i = 0; i < place_data.length; i++){
                book_data[i] = String.valueOf(tb_place_model.getValueAt(select, i));
            }
            JDialog dialog = new JDialog(main_frame, "Редактирование библиотеки", JDialog.DEFAULT_MODALITY_TYPE);
            EditLibraryFrame frame = new EditLibraryFrame(book_data);
            dialog.setBounds(20, 20, frame.get_width(), frame.get_height());
            dialog.add(frame);
            dialog.setVisible(true);
        }
        if("Редактирование книги".equals(command)){
            int select = tb_book.getSelectedRow();
            if (select == -1){
                JOptionPane.showMessageDialog(this, "Выберите книгу!", "Message",
                        JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            for (int i = 0; i < book_data.length; i++){
                book_data[i] = String.valueOf(tb_book_model.getValueAt(select, i));
            }
            JDialog dialog = new JDialog(main_frame, "Редактирование книги", JDialog.DEFAULT_MODALITY_TYPE);
            EditBookFrame frame = new EditBookFrame(book_data);
            dialog.setBounds(20, 20, frame.get_width(), frame.get_height());
            dialog.add(frame);
            dialog.setVisible(true);
        }
        if("Добавление домашней библиотеки".equals(command)){
            JDialog dialog = new JDialog(main_frame, "Добавление домашней библиотеки", JDialog.DEFAULT_MODALITY_TYPE);
            AddLibraryFrame frame = new AddLibraryFrame();
            dialog.setBounds(20, 20, frame.get_width(), frame.get_height());
            dialog.add(frame);
            dialog.setVisible(true);
        }
        if("Добавление книги".equals(command)){
            JDialog dialog = new JDialog(main_frame, "Добавление книги", JDialog.DEFAULT_MODALITY_TYPE);
            AddBookFrame frame = new AddBookFrame();
            dialog.setBounds(20, 20, frame.get_width(), frame.get_height());
            dialog.add(frame);
            dialog.setVisible(true);
        }
        if("Вывод шкафов в лекс.порядке".equals(command)){
            search_wardrobes();
            return;
        }
        if("Вывод издательств в шкафу".equals(command)){
            if (checking(sb_text.getText().trim())){
                JDialog dialog = new JDialog(main_frame,
                        "Вывод издательств в шкафу в лексикографическом порядке", JDialog.DEFAULT_MODALITY_TYPE);
                PubHouseFrame frame = new PubHouseFrame(Integer.parseInt(sb_text.getText().trim()));
                dialog.setBounds(20,20, frame.get_width(), frame.get_height());
                dialog.add(frame);
                dialog.setVisible(true);
            }

        }
        if("Вывод самых коротких и длинных по кол-ву страниц на этаже".equals(command)){
            if (checking(sb_text.getText().trim())){
                JDialog dialog = new JDialog(main_frame,
                        "Вывод самых коротких и длинных по кол-ву страниц на этаже", JDialog.DEFAULT_MODALITY_TYPE);
                MaxMinFrame frame = new MaxMinFrame(Integer.parseInt(sb_text.getText().trim()));
                dialog.setBounds(20,20, frame.get_width(), frame.get_height());
                dialog.add(frame);
                dialog.setVisible(true);
            }
        }
        if("Поиск домашней библиотеки".equals(command)){
            search_lib(sp_text.getText().trim());
            return;
        }
        if("Поиск книг".equals(command)){
            search_book(sb_text.getText().trim());
            return;
        }
        if("Вывод всех книг".equals(command)){
            tb_book_model.getDataVector().removeAllElements();
            BookReq obj = new BookReq();
            try{
                ArrayList<Object[]> res = obj.get_allbook();
                for (Object[] array: res){
                    tb_book_model.addRow(array);
                }
                count_tb_book.setText("Найдено записей -> " + res.size());
            } catch (SQLException exc){
                throw new RuntimeException(exc);
            }
        }
        if("Вывод всех библиотек".equals(command)){
            tb_place_model.getDataVector().removeAllElements();
            LibReq obj = new LibReq();
            try{
                ArrayList<Object[]> res = obj.get_allplace();
                for (Object[] array: res){
                    tb_place_model.addRow(array);
                }
                count_tb_place.setText("Найдено записей -> " + res.size());
            } catch (SQLException exc){
                throw new RuntimeException(exc);
            }
        }
    }

}
