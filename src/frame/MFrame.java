package frame;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

/**
 * swing程序最小化至系统托盘
 *
 * @author seara
 */
public class MFrame {

    public static final String ICO_LOCATION = "icon.png";

    public static void main(String[] args) {
        try {
            createTrayIcon();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void createTrayIcon() throws IOException {

        TrayIcon trayIcon = null;
        if (SystemTray.isSupported()) // 判断系统是否支持系统托盘
        {
            SystemTray tray = SystemTray.getSystemTray(); // 创建系统托盘
            Image image = ImageIO.read(new File(ICO_LOCATION));

            ActionListener listener = event -> {
                JFrame frame = new JFrame();
                frame.setBounds(400, 400, 200, 200);
                frame.setVisible(true);
            };
            // 创建弹出菜单
            PopupMenu popup = new PopupMenu();
            //主界面选项
            MenuItem mainFrameItem = new MenuItem("主界面");
            mainFrameItem.addActionListener(listener);

            //退出程序选项
            MenuItem exitItem = new MenuItem("退出程序");
            exitItem.addActionListener(event -> {
                if (JOptionPane.showConfirmDialog(null, "确定退出系统") == 0) {
                    System.exit(0);
                }
            });

            popup.add(mainFrameItem);
            popup.add(exitItem);

            trayIcon = new TrayIcon(image, "qiniuTool", popup);// 创建trayIcon
            trayIcon.setImageAutoSize(true);
            trayIcon.addActionListener(listener);
            try {
                tray.add(trayIcon);
            } catch (AWTException e1) {
                e1.printStackTrace();
            }
        }
    }
}
