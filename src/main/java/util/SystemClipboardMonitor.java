package util;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import javax.swing.JFrame;
/**
 * 探索历程，未使用
 * 剪贴板监控器
 * 负责对剪贴板的监控和操作
 * 由于监控需要一个对象作为ClipboardOwner，故不能用静态类
 * 不用FlavorListener是因为它仅监控剪贴板中数据类型的变化
 */
public class SystemClipboardMonitor implements ClipboardOwner{
    private Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
    private boolean going; //控制开关

    public void begin(){
        going = true;
        //将剪贴板中内容的ClipboardOwner设置为自己
        //这样当其中内容变化时，就会触发lostOwnership事件
        clipboard.setContents(clipboard.getContents(null), this);
    }

    public void stop(){
        going = false;
    }

    @Override
    public void lostOwnership(Clipboard clipboard, Transferable contents) {
        if (going){ //如果是进行中状态，则操作
            // 如果不暂停一下，经常会抛出IllegalStateException
            // 猜测是操作系统正在使用系统剪切板，故暂时无法访问
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // 取出文本并进行一次文本处理
            // 如果剪贴板中有文本:
            if (clipboard.isDataFlavorAvailable(DataFlavor.stringFlavor)){
                try {
                    String text = (String)clipboard.getData(DataFlavor.stringFlavor);
                    // 存入剪贴板，并注册自己为所有者
                    // 这样下次剪贴板内容改变时，仍然可以触发此事件
                    System.out.println(text);
                    StringSelection tmp = new StringSelection(text);
                    clipboard.setContents(tmp, this);
                } catch (UnsupportedFlavorException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                // 如果剪贴板中没有文本，仍然将自己设置为它的ClipboardOwner
                clipboard.setContents(clipboard.getContents(null), this);
            }
        }
    }
}