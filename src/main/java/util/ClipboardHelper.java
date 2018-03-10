package util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.datatransfer.*;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by Administrator on 2017/8/16.
 * 目测需要两个方法，一个是获得图片文件，一个是返回url
 */
public class ClipboardHelper {
    /**
     * 从系统剪贴板获得图片文件
     */
    public static Image getImage(Clipboard sysClipboard) throws IOException, UnsupportedFlavorException {
        Transferable cc = sysClipboard.getContents(null);
        if (cc == null)
            return null;
        else if(cc.isDataFlavorSupported(DataFlavor.imageFlavor)) {
            return (Image) cc.getTransferData(DataFlavor.imageFlavor);
        }
        return null;
    }

    /**
     * 往剪切板写文本数据
     */
    public static void setText(Clipboard sysClipboard, String writeMe) {
        Transferable tText = new StringSelection(writeMe);
        sysClipboard.setContents(tText, null);
    }

    /**
     * 将图片存到本地
     * @param image 图片
     * @param pathToSave 欲存位置，包含文件名
     */
    public static void saveImg(Image image, String pathToSave) throws IOException {
        File file= new File(pathToSave);
        BufferedImage bufferedImage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = bufferedImage.createGraphics();
        g.drawImage(image, null, null);
        ImageIO.write(bufferedImage, "png", file);
    }

    public static Clipboard getClipboard() {
        return Toolkit.getDefaultToolkit().getSystemClipboard();
    }

    //测试
    public static void main(String[] args) throws Exception {
//        setText(getClipboard(), "can it work?");

        Image image = getImage(getClipboard());
        saveImg(image, "12.jpg");

    }
}
