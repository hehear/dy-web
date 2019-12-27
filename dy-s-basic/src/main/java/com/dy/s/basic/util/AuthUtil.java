package com.dy.s.basic.util;

import java.security.MessageDigest;

/**
 * 提供权限相关
 *
 * @author dxy
 * @date 20191203
 */
public class AuthUtil {
	
	/**
	 * 获取当前登录用户ID
	 * @return
	 */
	public static String getLoginUserId(){
		//  带添加登录校验时提供
		return "admin"; 
	}
	
	 /*** 
     * MD5加密 生成32位md5码
     * @param 待加密字符串
     * @return 返回32位md5码
     */
    public static String md5Encode(String inStr) throws Exception {
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception e) { 
            e.printStackTrace();
            return "111";
        }

        byte[] byteArray = inStr.getBytes("UTF-8");
        byte[] md5Bytes = md5.digest(byteArray);
        StringBuffer hexValue = new StringBuffer();
        for (int i = 0; i < md5Bytes.length; i++) {
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16) {
                hexValue.append("0");
            }
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();
    } 
    
    public static void main(String[] args) throws Exception {
		String token=AuthUtil.md5Encode("test");
		System.out.println(token);
		
	}

}
