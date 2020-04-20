package com.smartweb.admin.common;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Adminsha
{
  /**
   * 암호화 : SHA-256
 * @param value
 * @return
 */ 
public static String getshavalue(String value)
  {
    String password = value;  

    StringBuffer hexString = null;
    try {
      MessageDigest md = MessageDigest.getInstance("SHA-256");

      md.update(password.getBytes());

      byte[] byteData = md.digest();

      StringBuffer sb = new StringBuffer();
      for (int i = 0; i < byteData.length; i++) {
        sb.append(Integer.toString((byteData[i] & 0xFF) + 256, 16).substring(1));
      }

      hexString = new StringBuffer();
      for (int i = 0; i < byteData.length; i++) {
        String hex = Integer.toHexString(0xFF & byteData[i]);
        if (hex.length() == 1) hexString.append('0');
        hexString.append(hex);
      }
    }
    catch (NoSuchAlgorithmException e)
    {
      e.printStackTrace();
    }
    return hexString.toString();
  }
}
