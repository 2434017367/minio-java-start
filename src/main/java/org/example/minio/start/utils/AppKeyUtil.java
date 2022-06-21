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

        int pyl = 0;
        for (int i = 0; i < s.length(); i++) {
            Integer index = Integer.parseInt(s.substring(i, i + 1));
            if (i == 1) {
                pyl = index % 5 + 1;
            }
            sb.append(CODEBOOK[index]);
        }
        String sjc = sb.toString();

        String m = appKey.substring(0, pyl) + sjc + appKey.substring(pyl);
        StringBuffer mj = new StringBuffer();
        for (int i = 0; i < m.length(); i++) {
            int c = (int) m.charAt(i);
            int y = c + pyl;
            if (y < 48) {
                y = 123 - (48 - y);
            } else if (y > 57 && y < 65) {
                y += 7;
            } else if (y > 90 && y < 97) {
                y += 6;
            } else if (y > 122) {
                y = y - 122 + 47;
            }
            mj.append((char) y);
        }

        EncryptEntity encryptEntity = new EncryptEntity(mj.toString(), sjc);

        return encryptEntity;
    }

}
