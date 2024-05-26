package gui;
import for_req.BookReq;
import for_bd.DBConnection;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.ArrayList;

public class MaxMinFrame extends JPanel{
    private final int width = 600, height = 400;
    private JPanel main_panel;
    private DefaultTableModel table_model;
    private JTable table;
    private int floor;

    public MaxMinFrame(int fl){
        super();
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        setPreferredSize(new Dimension(width, height));
        main_panel = new JPanel();
        main_panel.setPreferredSize(new Dimension(width,height));
        main_panel.setLayout(new BoxLayout(main_panel, BoxLayout.Y_AXIS));
        floor = fl;
        table_model = new DefaultTableModel(new Object[]{"Максимум страниц", "Минимум страниц", "Этаж"},0){
            @Override
            public boolean isCellEditable(int row, int column){
                return false;
            };
        };
        table = new JTable();
        table.setModel(table_model);
        BookReq obj = new BookReq();
        try{
            ArrayList<Object[]> res = obj.max_min_page_floor(floor);
            for (Object[] array: res){
                table_model.addRow(array);
            }
        } catch (SQLException exc){
            throw new RuntimeException(exc);
        }
        main_panel.add(new JScrollPane(table));
        add(main_panel);
    }
    public int get_width(){
        return width;
    }
    public int get_height(){
        return height;
    }
}
