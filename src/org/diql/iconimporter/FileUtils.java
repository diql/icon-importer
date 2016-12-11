package org.diql.iconimporter;

import java.io.*;
import java.nio.channels.FileChannel;

/**
 * Created by diql on 2016/12/9.
 */
public class FileUtils {
    private static final String TAG = "org.diql.iconimporter.FileUtils";
    public static void nioTransferCopy(String source, String target) {
        Log.i(TAG, "nioTransferCopy().source:" + source + ".target:" + target);
        if (StringUtils.isEmpty(source) || StringUtils.isEmpty(target)) {
            return;
        }
        nioTransferCopy(new File(source), new File(target));
    }


    public static void nioTransferCopy(File source, File target) {
        Log.i(TAG, "nioTransferCopy().source:" + source + ".target:" + target + ".start.");
        FileChannel in = null;
        FileChannel out = null;
        FileInputStream inStream = null;
        FileOutputStream outStream = null;
        try {
            inStream = new FileInputStream(source);
            outStream = new FileOutputStream(target);
            in = inStream.getChannel();
            out = outStream.getChannel();
            in.transferTo(0, in.size(), out);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            Log.i(TAG, "nioTransferCopy().source:" + source + ".target:" + target + ".finally.");
            try {
                inStream.close();
                in.close();
                outStream.close();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean makeDirs(String filePath) {
        if (StringUtils.isEmpty(filePath)) {
            return false;
        }
        File folder = new File(filePath);
        return (folder.exists() && folder.isDirectory()) ? true : folder.mkdirs();
    }
}
