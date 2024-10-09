package com.ruoyi.common.util.ip;

import javax.servlet.http.HttpServletRequest;
import com.ruoyi.common.util.StringUtil;

/**
 * 获取IP方法
 */
public class IpUtils {

    /**
     * 获取客户端IP
     *
     * @param request 请求对象
     * @return IP地址
     */
    public static String getIpAddr(HttpServletRequest request) {
        if (request == null) {
            return "unknow";
        }
        /**
         * 目的是尝试从HTTP请求中获取客户端的真实IP地址
         * 由于在Web应用程序可能部署在反向代理或负载均衡器后面，直接从HttpServletRequest对象获取的IP地址可能不是客户端的真实地址。因此，这段代码尝试从多个HTTP头部中获取IP地址，这些头部通常由代理服务器设置以传递原始请求者的IP地址
         */
        // 尝试从请求头中获取x-forwarded-for字段的值。这个头部通常用于记录经过的代理服务器的IP地址列表，客户端的真实IP通常位于列表的第一位
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            // 尝试从请求头中获取Proxy-Client-IP字段的值。这是另一个可能由代理服务器设置的头部
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            // 尝试获取X-Forwarded-For头部的值。这可能是对第一个尝试的重复，但有些代理可能使用不同的大小写
            ip = request.getHeader("X-Forwarded-For");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
        {
            // 从请求头中获取WL-Proxy-Client-IP字段的值。这个头部是WebLogic服务器特有的
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
        {
            // 从请求头中获取X-Real-IP字段的值。这个头部通常由Nginx代理服务器设置
            ip = request.getHeader("X-Real-IP");
        }
        // 果所有这些尝试都失败了，最后使用request.getRemoteAddr(): 直接从HttpServletRequest对象获取远程地址。这个地址可能是客户端的IP，但如果应用部署在代理或负载均衡器后面，则可能是代理服务器的IP
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
        {
            ip = request.getRemoteAddr();
        }

        return "0:0:0:0:0:0:0:1".equals(ip) ? "127.0.0.1" : getMultistageReverseProxyIp(ip);
    }

    /**
     * 从多级反向代理中获得第一个非unknown IP地址
     *
     * @param ip 获得的IP地址
     * @return 第一个非unknown IP地址
     */
    public static String getMultistageReverseProxyIp(String ip) {
        // 多级反向代理检测
        // ip不为null并且ip字符串中包含逗号","
        if (ip != null && ip.indexOf(",") > 0) {
            final String[] ips = ip.trim().split(",");
            // 遍历ips数组中的每个元素subIp
            for( String subIp: ips) {
                if (false == isUnknown(subIp)) {
                // if (!isUnknown(subIp)) {
                    ip = subIp;
                    break;
                }
            }
        }
        return ip;
    }

    /**
     * 检测给定字符串是否为未知，多用于检测HTTP请求相关
     *
     * @param checkString 被检测的字符串
     * @return 是否未知
     */
    public static boolean isUnknown(String checkString) {
        // Blank: 空白
        // 判断是否为 空（null或者空字符串）或 “unknown”
        return StringUtil.isBlank(checkString) || "unknown".equalsIgnoreCase(checkString);
    }

    /**
     * 检查是否为内部IP地址
     *
     * @param ip IP地址
     * @return 结果
     */
    public static boolean internalIp(String ip) {
        byte[] addr = textToNumbericFormatV4(ip);
        return internalIp(addr) || "127.0.0.1".equals(ip);
    }

    /**
     * 将IPv4地址转换成字节
     *
     * @param text IPv4地址
     * @return byte 字节
     */
    public static byte[] textToNumbericFormatV4(String text) {
        if (text.length() == 0) {
            return null;
        }

        byte[] bytes = new byte[4];
        // split 的 第二个参数 是结果数组的最大长度。如果 limit 大于0，则最多返回 limit 个结果，最后一个元素包含所有超出 limit 的剩余部分。如果 limit 小于0，则方法返回所有可能的拆分，忽略 limit 的限制
        // 参数 -1 确保即使字符串末尾有点号，也能保留空字符串
        String[] elements = text.split("\\.", -1);
        try {
            long l;
            int i;
            switch( elements.length) {
                // 如果只有一个元素，这意味着输入的IP地址格式类似于 “A”，其中A是一个介于0到4294967295之间的数字。将这个数字解析为长整型 long，然后将其转换为4个字节并存储到 bytes 数组中
                case 1:
                    l = Long.parseLong(elements[0]);
                    // 不是一个有效的IPv4地址
                    if ( (l < 0L) || (l > 4294967295L)){
                        return null;
                    }
                    // 将数字 l 右移24位，然后与0xFF进行位与操作，得到IP地址的第一个八位组，并将其转换为byte类型
                    bytes[0] = (byte) (int) (l >> 24 & 0xFF);
                    // 将数字 l 与0xFFFFFF进行位与操作，以清除最高8位，然后右移16位，再与0xFF进行位与操作，得到IP地址的第二个八位组，并转换为byte类型
                    bytes[1] = (byte) (int) ((l & 0xFFFFFF) >> 16 & 0xFF);
                    // 将数字 l 与0xFFFF进行位与操作，以清除最高16位，然后右移8位，再与0xFF进行位与操作，得到IP地址的第三个八位组，并转换为byte类型
                    bytes[2] = (byte) (int) ((l & 0xFFFF) >> 8 & 0xFF);
                    // 将数字 l 与0xFF进行位与操作，得到IP地址的第四个八位组，并转换为byte类型
                    bytes[3] = (byte) (int) (l & 0xFF);
                    break;

                // 有两个元素，格式类似于 “A.B”，其中A是一个介于0到255之间的数字，B是一个介于0到16777215之间的数字。将这两个数字解析为整型 int，然后转换为4个字节
                case 2 :
                    l = Integer.parseInt(elements[0]);
                    if (( l < 0L) || (l > 255L)) {
                        return null;
                    }
                    bytes[0] = (byte)(int)( l & 0xFF);

                    l = Integer.parseInt(elements[1]);
                    if ((l< 0L) || (l> 16777215L)) {
                        return null;
                    }
                    bytes[1] = (byte)(int)(l >> 16 & 0xFF);
                    bytes[2] = (byte)(int)((l & 0xFFFF) >> 8 & 0xFF);
                    bytes[3] = (byte)(int)(l & 0xFF);
                    break;

                case 3:
                    for( i = 0; i< 2; i++) {
                        l = Integer.parseInt(elements[i]);

                        if((l < 0L) || (l > 255L)) {
                            return null;
                        }
                        bytes[i] = (byte)(int)(l & 0xFF);
                    }
                    l = Integer.parseInt(elements[2]);
                    if ((l < 0L) || (l > 65535L)) {
                        return null;
                    }
                    bytes[2] = (byte)(int)( l >> 8 & 0xFF);
                    bytes[3] = (byte)(int)(l & 0xFF);
                    break;

                case 4:
                    for( i = 0; i< 4; ++i) {
                        l = Integer.parseInt(elements[i]);
                        if ((l < 0L) || (l > 255L)) {
                            return null;
                        }
                        bytes[i] = (byte)(int)(l & 0xFF);
                    }
                    break;

                default:
                    return null;
            }
        }
        catch (NumberFormatException e) {
            return null;
        }
        return bytes;
    }

    /**
     * 检查是否为内部IP地址
     *
     * @param addr byte地址
     * @return 结果
     */
    private static boolean internalIp(byte[] addr) {
        if (StringUtil.isNull(addr) || addr.length < 2) {
            return true;
        }
        final byte b0 = addr[0];
        final byte b1 = addr[1];
        // 10.x.x.x/8
        final byte SECTION_1 = 0x0A;
        // 172.16.x.x/12
        final byte SECTION_2 = (byte) 0xAC;
        final byte SECTION_3 = (byte) 0x10;
        final byte SECTION_4 = (byte) 0x1F;
        // 192.168.x.x/16
        final byte SECTION_5 = (byte) 0xC0;
        final byte SECTION_6 = (byte) 0xA8;

        switch(b0) {
            case SECTION_1:
                return true;
            case SECTION_2:
                if(b1 >= SECTION_3 && b1 <= SECTION_4) {
                    return true;
                }
            case SECTION_5:
                switch(b1) {
                    case SECTION_6:
                        return true;
                }

            default:
                return false;
        }
    }
}
