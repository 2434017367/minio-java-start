package org.example.minio.start.utils;

import cn.hutool.core.date.DateUtil;
import org.example.minio.start.utils.entity.EncryptEntity;

import java.util.Date;

/**
 * @author zhy
 * @email 2434017367@qq.com
 * @date 2022/6/19 12:35
 */
public class AppKeyUtil {

    /**
     * 密码本
     */
    private final static String[] CODEBOOK = new String[]{"o", "8", "L", "A", "e", "5", "K", "i", "y", "1"};

    private final static String TIME_FORMAT = "ssyyHHmmMMdd";

    /**
     * 加密
     * @param appKey
     * @return
     */
    public static EncryptEntity encrypt(String appKey) {
        String s = DateUtil.format(new Date(), TIME_FORMAT);
        StringBuffer sb = new StringBuffer();

        // 偏移量
        int pyl = 0;
        for (int i = 0; i < s.length(); i++) {
            Integer index = Integer.parseInt(s.substring(i, i + 1));
            if (i == 0) {
                pyl = index;
            }
            sb.append(CODEBOOK[index]);
        }
        String sjc = sb.toString();

        String m = appKey.substring(0, pyl) + sjc + appKey.substring(pyl);
        StringBuffer mj = new StringBuffer();
        for (int i = 0; i < m.length(); i++) {
            char c = (char)((int)m.charAt(i) + pyl);
            mj.append(c);
        }

        EncryptEntity encryptEntity = new EncryptEntity(mj.toString(), sjc);

        return encryptEntity;
    }

}
