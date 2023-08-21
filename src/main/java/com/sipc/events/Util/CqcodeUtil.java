package com.sipc.events.Util;

public class CqcodeUtil {
    public static String getImageCqcode(String fileName,String pictureUrl){
        return "[CQ:image,file=" + fileName + ",subType=0,url=" + pictureUrl + "]";
    }
}
