package com.minmai.wallet.common.uitl;

import android.content.Context;
import android.os.Environment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class FileUtil {
    public static File getSaveFile(Context context,String type) {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File externalFile = context.getExternalFilesDir("/idcard/");
            String filePath = externalFile.getAbsolutePath();
            File path = new File(filePath);
            if (!path.exists()) {
                path.mkdirs();
            }
            File file = new File(path, type + "_user.jpg");
            return file;
        }else {
            return null;
        }

    }
    public static File getSaveBankcardFile(Context context,String type) {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File externalFile = context.getExternalFilesDir("/bankcard/");
            String filePath = externalFile.getAbsolutePath();
            File path = new File(filePath);
            if (!path.exists()) {
                path.mkdirs();
            }
            File file = new File(path, type + "_bankcard.jpg");
            return file;
        }else {
            return null;
        }

    }


    /**
     * 保存轮播图信息
     * @param result
     */
    public static void savaBannerData(String result){
        String state = Environment.getExternalStorageState();
        if (!Environment.MEDIA_MOUNTED.equals(state)) {
            return;
        } else {
            FileOutputStream fos = null;
            File sdDir = Environment.getExternalStorageDirectory();
            try {
                File file = new File(sdDir.getCanonicalPath() + "/" + "myxkzf.txt");
                if (file.exists()){

                }
                fos = new FileOutputStream(file);

                fos.write(result.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (fos != null){

                        fos.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


    }


    /**
     * 查看最后一笔批结算
     * @return
     */
    public static String getBannerData(){
        String state = Environment.getExternalStorageState();
        if (!Environment.MEDIA_MOUNTED.equals(state)) {
            return null;
        } else {
            StringBuffer buffer = null;
            BufferedReader reader = null;
            try {
                File sdDir = Environment.getExternalStorageDirectory();
                File file = new File(sdDir.getCanonicalPath() + "/" + "myxkzf.txt");
                if (file.exists() ) {
                    FileInputStream fis = new FileInputStream(file);
                    reader = new BufferedReader(new InputStreamReader(fis));
                    String row = "";
                    buffer = new StringBuffer();
                    while ((row = reader.readLine()) != null) {
                        buffer.append(row);
                    }
                }else {
                    return null;
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (reader!=null){
                    try {
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (buffer != null){
                    return buffer.toString();
                }else
                    return null;
            }
        }

    }
}
