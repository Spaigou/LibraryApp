import gui.MainFrame;
import for_bd.DBConnection;
public class Main {
    public static void main(String[] args) {
        try{
            DBConnection.connect();
        } catch (Exception exc){
            //System.out.println("Ошибка подключения!");
            exc.printStackTrace(System.err);
            return;
        }
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                MainFrame frame = new MainFrame();
                frame.show_frame();
            }
        });
    }
}


