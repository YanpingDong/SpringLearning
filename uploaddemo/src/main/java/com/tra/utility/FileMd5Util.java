package com.tra.utility;

import sun.rmi.runtime.NewThreadAction;

import java.io.File;
import java.io.FileInputStream;
import java.math.BigInteger;
import java.security.MessageDigest;


public class FileMd5Util {

    public static String getFileMD5(File file) {
        if (!file.exists() || !file.isFile()) {
            return null;
        }
        MessageDigest digest = null;
        FileInputStream in = null;
        byte buffer[] = new byte[1024];
        int len;
        try {
            digest = MessageDigest.getInstance("MD5");
            in = new FileInputStream(file);
            while ((len = in.read(buffer, 0, 1024)) != -1) {
                digest.update(buffer, 0, len);
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        BigInteger bigInt = new BigInteger(1, digest.digest());
        return bigInt.toString(16);
    }

    public static void main(String[] args)
    {
        System.out.println(getFileMD5(new File("/home/learlee/Downloads/multipartwu_1djbfq5of1n7d1nb21p6udqpi7e0/wu_1djbfq5of1n7d1nb21p6udqpi7e0_0.part")));
    }
}
