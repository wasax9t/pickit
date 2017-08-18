package util;

import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by Administrator on 2017/8/16.
 * 七牛相关方法
 */
public class QiniuHelper {
    /**
     * 返回{ak, sk, bucket_name}
     */
    static String[] readIni(){
        File file = new File("qiniu.ini");
        BufferedReader reader = null;
        String[] ini = new String[3];
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            int line = 1;
            while ((tempString = reader.readLine()) != null) {
                //System.out.println("line " + line + ": " + tempString);
                ini[line-1] = tempString;
                line++;
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
        return ini;
    }

    /**
     * 上传图片到七牛云
     * ak,sk,bucket_name都从ini文件中读出
     * @param imgPath 文件位置
     * @param imgName 最终文件名
     * @return success?
     */
    public static boolean uploadImg(String imgPath, String imgName) {
        String[] ini = readIni();
        String ak = ini[0];
        String sk = ini[1];
        String bucket = ini[2];
        Auth auth = Auth.create(ak, sk);
        Zone z = Zone.zone0();
        Configuration c = new Configuration(z);
        UploadManager uploadManager = new UploadManager(c);
        String token = auth.uploadToken(bucket);
        String url = null;
        try {
            //调用put方法上传
            Response res = uploadManager.put(imgPath, imgName, token);
            //打印返回的信息
            System.out.println(res.bodyString());
            return true;
        } catch (QiniuException e) {
            Response r = e.response;
            // 请求失败时打印的异常的信息
            System.out.println(r.toString());
            try {
                //响应的文本信息
                System.out.println(r.bodyString());
            } catch (QiniuException e1) {
                //ignore
            }
        }
        return false;
    }

    public static void main(String[] args) {
        readIni();
        //System.out.println(uploadImg("test.jpg", "rename.jpg"));
    }
}
