import frame.MFrame;
import util.ClipboardHelper;
import util.QiniuHelper;

import java.awt.*;
import java.awt.datatransfer.*;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Main {

    public static final String TMP_PATH = "E:\\temp";
    public static final String QINIU_URL = "http://or4o97iuj.bkt.clouddn.com";
    public static final String NAME_PREFIX = "prtimg";

    public Main() {
        try {
            MFrame.createTrayIcon();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        FlavorListener lis = e -> handleAction(clipboard, TMP_PATH);
        clipboard.addFlavorListener(lis);
    }

    public static void main(String[] args) {

//        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
//        clipboard.setContents(new StringSelection("work?"), null);

        new Main();
    }

    public static void handleAction(Clipboard clipboard, String tmpPath) {
        if(clipboard.isDataFlavorAvailable(DataFlavor.imageFlavor)) {
            try {
                Image image = ClipboardHelper.getImage(clipboard);
                String dateSting = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                String fileName = NAME_PREFIX + dateSting + ".png";
                String imgLocation = tmpPath + "\\" + fileName;
                System.out.println(imgLocation);

                ClipboardHelper.saveImg(image, imgLocation);

                QiniuHelper.uploadImg(imgLocation, fileName);
                String imgUrl = QINIU_URL + "/" + fileName;

                ClipboardHelper.setText(clipboard, imgUrl);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
