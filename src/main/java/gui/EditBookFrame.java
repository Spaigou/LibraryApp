package gui;
import for_req.BookReq;
import for_bd.DBConnection;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.ArrayList;

public class EditBookFrame extends JPanel implements ActionListener {
    private final int width = 600, height = 400;
    private JPanel main_panel, text_panel, input_panel;
    private JLabel author_label, publication_label, publishing_house_label, year_public_label, pages_label, year_write_label, weight_label, id_place_label;
    private JTextField author_text, publication_text, publishing_house_text, year_public_text, pages_text, year_write_text, weight_text, id_place_text;
    private JButton edit_button;
    private String to_change[];

    public EditBookFrame(String[] data){
        super();
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        setPreferredSize(new Dimension(width, height));
        to_change = data;
        main_panel = new JPanel();
        main_panel.setPreferredSize(new Dimension(width, height - 50));
        main_panel.setLayout(new BoxLayout(main_panel, BoxLayout.LINE_AXIS));
        input_panel = new JPanel();
        input_panel.setPreferredSize(new Dimension(2*width/3, height-70));
        input_panel.setLayout(new GridLayout(9,1));
        input_panel.setBorder(BorderFactory.createBevelBorder(1));
        text_panel = new JPanel();
        text_panel.setPreferredSize(new Dimension(width/3, height - 70));
        text_panel.setLayout(new GridLayout(9,1));
        text_panel.setBorder(BorderFactory.createBevelBorder(1));
        author_label = new JLabel("Автор");
        publication_label = new JLabel("Издание");
        publishing_house_label = new JLabel("Издательство");
        year_public_label = new JLabel("Год издания");
        pages_label = new JLabel("Кол-во страниц");
        year_write_label = new JLabel("Год написания");
        weight_label = new JLabel("Вес");
        id_place_label = new JLabel("ID библиотеки");
        author_text = new JTextField(to_change[1]);
        publication_text = new JTextField(to_change[2]);
        publishing_house_text = new JTextField(to_change[3]);
        year_public_text = new JTextField(to_change[4]);
        pages_text = new JTextField(to_change[5]);
        year_write_text = new JTextField(to_change[6]);
        weight_text = new JTextField(to_change[7]);
        id_place_text = new JTextField(to_change[8]);
        edit_button = new JButton("Изменить");
        edit_button.addActionListener(this);
        text_panel.add(author_label);
        input_panel.add(author_text);
        text_panel.add(publication_label);
        input_panel.add(publication_text);
        text_panel.add(publishing_house_label);
        input_panel.add(publishing_house_text);
        text_panel.add(year_public_label);
        input_panel.add(year_public_text);
        text_panel.add(pages_label);
        input_panel.add(pages_text);
        text_panel.add(year_write_label);
        input_panel.add(year_write_text);
        text_panel.add(weight_label);
        input_panel.add(weight_text);
        text_panel.add(id_place_label);
        input_panel.add(id_place_text);
        input_panel.add(edit_button);
        main_panel.add(text_panel);
        main_panel.add(input_panel);
        add(main_panel);
    }

    public static boolean checking(String str) {
        try {
            int x = Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public int get_width(){
        return width;
    }
    public int get_height(){
        return height;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        BookReq obj = new BookReq();
        if("Изменить".equals(e.getActionCommand())){
            ArrayList<String> arrayList = new ArrayList<>();
            arrayList.add(author_text.getText().trim());
            arrayList.add(publication_text.getText().trim());
            arrayList.add(publishing_house_text.getText().trim());
            arrayList.add(year_public_text.getText().trim());
            arrayList.add(pages_text.getText().trim());
            arrayList.add(year_write_text.getText().trim());
            arrayList.add(weight_text.getText().trim());
            arrayList.add(id_place_text.getText().trim());
            arrayList.add(to_change[0]);
            if (arrayList.contains("")){
                JOptionPane.showMessageDialog(this, "Введено пустое значение!", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (checking(year_public_text.getText().trim()) && checking(pages_text.getText().trim()) && checking(year_write_text.getText().trim()) && checking(weight_text.getText().trim()) && checking(id_place_text.getText().trim())){
                try{
                    DBConnection.get_connection().setAutoCommit(false);
                    obj.edit_book(arrayList);
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
