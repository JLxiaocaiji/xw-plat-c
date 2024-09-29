package com.ruoyi.common.util;

import java.util.Collection;
import java.util.Map;

public class StringUtil extends org.apache.commons.lang3.StringUtils {
    /** 空字符串 */
    private static final String NULLSTR = "";

    /** 下划线 */
    private static final String SEPARATOR = "_";

    /**
     * 获取参数不为空值
     * @param value
     * @param defaultValue
     * @return
     * @param <T>
     */
    // nvl 方法被用来安全地处理可能为 null 的值. 使用了 Java 泛型 <T>，这意味着 nvl 方法可以接受任何类型的参数
    // <T>：这是泛型类型的声明
    // T：这是方法的返回类型
    public static <T> T nvl(T value, T defaultValue) {
        return value != null ? value : defaultValue;
    }

    /**
     * 判断一个对象是否为空
     * @param object
     * @return
     */
    public static boolean isNull(Object object) { return object == null; }

    /**
     * 判断一个对象是否非空isNotNull
     * @param object
     * @return
     */
    public static boolean isNotNull(Object object) {
        return !isNull(object);
    }

    /**
     * 判断一个Collection是否为空， 包含List，Set，Queue
     * @param coll
     * @return
     */
    // Collection<?>： collection 类型，广泛意义上的集合，包括对象类型
    public static boolean isEmpty(Collection<?> coll) {
        return isNull(coll) || coll.isEmpty();
    }

    /**
     * 判断一个对象数组是否为空
     * @param objects
     * @return
     */
    public static boolean isEmpty(Object[] objects) {
        return isNull(objects) || ( objects.length == 0);
    }

    /**
     * 判断一个Map是否为空
     * @param map
     * @return
     */
    // Map<?, ?> map：这是一个泛型参数，Map<?, ?> 表示这个方法可以接受任何键值对类型的 Map 对象。? 是一个通配符，表示 map 可以是任何类型的键和值的 Map 实例
    public static boolean isEmpty(Map<?, ?> map) {
        return isNull(map) || map.isEmpty();
    }

    /**
     * 判断一个字符串是否为空串
     * @param str
     * @return
     */
    public static boolean isEmpty(String str) {
        // trim(): 删除任何前导和尾随空格
        return isNull(str) || NULLSTR.equals(str.trim());
    }

    /**
     * 判断一个Collection是否非空，包含List，Set，Queue
     * @param coll
     * @return
     */
    public static boolean isNotEmpty(Collection<?> coll) {
        return !isEmpty(coll);
    }


    /**
     * 判断一个对象数组是否非空
     * @param objects
     * @return
     */
    public static boolean isNotEmpty(Object[] objects) {
        return !isEmpty(objects);
    }

    /**
     * 判断一个Map是否为空
     * @param map
     * @return
     */
    public static boolean isNotEmpty(Map<?, ?> map) {
        return !isEmpty(map);
    }

    /**
     * 判断一个字符串是否为非空串
     * @param str
     * @return
     */
    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }
}
