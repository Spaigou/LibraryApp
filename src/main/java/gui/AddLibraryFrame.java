package gui;
import for_req.BookReq;
import for_req.LibReq;
import for_bd.DBConnection;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.ArrayList;

public class AddLibraryFrame extends JPanel implements ActionListener{
    private final int width = 400, height = 200;
    private JPanel main_panel, text_panel, input_panel;
    private JLabel floor_label, wardrobe_label, shelf_label;
    private JTextField floor_text, wardrobe_text, shelf_text;
    private JButton add_button;

    public AddLibraryFrame(){
        super();
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        setPreferredSize(new Dimension(width, height));
        main_panel = new JPanel();
        main_panel.setPreferredSize(new Dimension(width, height - 50));
        main_panel.setLayout(new BoxLayout(main_panel, BoxLayout.LINE_AXIS));
        input_panel = new JPanel();
        input_panel.setPreferredSize(new Dimension(2*width/3, height-70));
        input_panel.setLayout(new GridLayout(4,1));
        input_panel.setBorder(BorderFactory.createBevelBorder(1));
        text_panel = new JPanel();
        text_panel.setPreferredSize(new Dimension(width/3, height - 70));
        text_panel.setLayout(new GridLayout(4,1));
        text_panel.setBorder(BorderFactory.createBevelBorder(1));
        floor_label = new JLabel("Этаж");
        wardrobe_label = new JLabel("Шкаф");
        shelf_label = new JLabel("Полка");
        floor_text = new JTextField();
        wardrobe_text = new JTextField();
        shelf_text = new JTextField();
        add_button = new JButton("Добавить");
        add_button.addActionListener(this);
        text_panel.add(floor_label);
        text_panel.add(wardrobe_label);
        text_panel.add(shelf_label);
        input_panel.add(floor_text);
        input_panel.add(wardrobe_text);
        input_panel.add(shelf_text);
        input_panel.add(add_button);
        main_panel.add(text_panel);
        main_panel.add(input_panel);
        add(main_panel);
    }

    public int get_width(){
        return width;
    }
    public int get_height(){
        return height;
    }

    public static boolean checking(String str) {
        try {
            int x = Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        LibReq obj = new LibReq();
        if("Добавить".equals(e.getActionCommand())){
            ArrayList<String> arrayList = new ArrayList<>();
            arrayList.add(floor_text.getText().trim());
            arrayList.add(wardrobe_text.getText().trim());
            arrayList.add(shelf_text.getText().trim());
            if (arrayList.contains("")){
                JOptionPane.showMessageDialog(this, "Введено пустое значение!", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (checking(floor_text.getText().trim()) && checking(wardrobe_text.getText().trim()) && checking(shelf_text.getText().trim())){
                try{
                    DBConnection.get_connection().setAutoCommit(false);
                    obj.add_place(arrayList);
                    DBConnection.get_connection().commit();
                    DBConnection.get_connection().setAutoCommit(true);
                } catch (SQLException exc){
                    throw new RuntimeException(exc);
                }
            } else{
                JOptionPane.showMessageDialog(this, "Введено не число!", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

    }

}
