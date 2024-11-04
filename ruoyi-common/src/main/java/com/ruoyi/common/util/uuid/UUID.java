package com.ruoyi.common.util.uuid;

import com.sun.xml.internal.ws.util.UtilException;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public final class UUID implements java.io.Serializable, Comparable<UUID>{
    private static final long serialVersionUID = -1185015143654744140L;

    /** 此UUID的最高64有效位 */
    private final long mostSigBits;

    /** 此UUID的最低64有效位 */
    private final long leastSigBits;

    /**
     * SecureRandom 的单例
     *
     */
    private static class Holder {
        static final SecureRandom numberGenerator = getSecureRandom();
    }

    /**
     * 获取{@link SecureRandom}，类提供加密的强随机数生成器 (RNG)
     * 获取一个基于 SHA1PRNG 算法的 SecureRandom 实例
     * @return {@link SecureRandom}
     */
    public static SecureRandom getSecureRandom() {
        try {
            return SecureRandom.getInstance("SHA1PRNG");
        }
        // 如果指定的算法 “SHA1PRNG” 在当前环境中不可用，会抛出 NoSuchAlgorithmException，这里通过 catch 块捕获该异常
        catch (NoSuchAlgorithmException e)
        {
            // 当捕获到 NoSuchAlgorithmException 异常时，将其包装在一个名为 UtilException 的自定义异常中并重新抛出
            throw new UtilException(e);
        }
    }

    /**
     * 私有构造
     *
     * @param data 数据
     */
    private UUID(byte[] data) {
        long msb = 0;  // 初始化最高有效位（most significant bits）
        long lsb = 0;  // 初始化最低有效位（least significant bits）
        assert data.length == 16 : "data must be 16 bytes in length";
        for ( int i = 0; i < 8; i++) {
            msb = (msb << 8) | (data[i] & 0xff);
        }
        for ( int i = 8; i< 16; i++) {
            lsb = (lsb << 8) | (data[i] & 0xff);
        }
        this.mostSigBits = msb;
        this.leastSigBits = lsb;
    }

    // 比较两个 UUID 实例
    public int compareTo(UUID val) {
        return (this.mostSigBits < val.mostSigBits ? -1 : //
                (this.mostSigBits > val.mostSigBits ? 1 : //
                        (this.leastSigBits < val.leastSigBits ? -1 : //
                                (this.leastSigBits > val.leastSigBits ? 1 : //
                                        0))));
    }

    /**
     * 获取类型 4（伪随机生成的）UUID 的静态工厂。 使用加密的本地线程伪随机数生成器生成该 UUID。
     *
     * @return 随机生成的 {@code UUID}
     */
    public static UUID fastUUID() {
        return randomUUID(false);
    }

    /**
     * 获取类型 4（伪随机生成的）UUID 的静态工厂。 使用加密的强伪随机数生成器生成该 UUID。
     *
     * @return 随机生成的 {@code UUID}
     */
    public static UUID randomUUID() {
        return randomUUID(true);
    }

    /**
     * 获取类型 4（伪随机生成的）UUID 的静态工厂。 使用加密的强伪随机数生成器生成该 UUID。
     *
     * @param isSecure 是否使用{@link SecureRandom}如果是可以获得更安全的随机码，否则可以得到更好的性能
     * @return 随机生成的 {@code UUID}
     */
    public static UUID randomUUID(boolean isSecure) {
        final Random ng = isSecure ? Holder.numberGenerator : getRandom();

        // 创建一个长度为16的字节数组 randomBytes，这是因为UUID是一个128位的值，通常表示为16个字节
        byte[] randomBytes = new byte[16];
        // 使用 ng 随机数生成器填充 randomBytes 数组，使得每个字节都是随机的
        ng.nextBytes(randomBytes);
        // 清除 randomBytes 数组中第7个字节（从0开始计数，因此索引为6）的版本位。这是通过与操作和掩码 0x0f 实现的，这个掩码在二进制表示中为 00001111，它会将第7个字节的高4位清零
        randomBytes[6] &= 0x0f; /* clear version */
        // 将 randomBytes 数组中第7个字节的版本位设置为4。这是通过或操作和掩码 0x40 实现的，这个掩码在二进制表示中为 01000000，它将第7个字节的高4位中的第2位设置为1，表示UUID的版本是4
        randomBytes[6] |= 0x40; /* set to version 4 */
        // 清除 randomBytes 数组中第9个字节（索引为8）的变体位。这是通过与操作和掩码 0x3f 实现的，这个掩码在二进制表示中为 00111111，它会将第9个字节的高2位清零
        randomBytes[8] &= 0x3f; /* clear variant */
        randomBytes[8] |= 0x80; /* set to IETF variant */
        return new UUID(randomBytes);
    }

    /**
     * 获取随机数生成器对象<br>
     * ThreadLocalRandom是JDK 7之后提供并发产生随机数，能够解决多个线程发生的竞争争夺。
     *
     * @return {@link ThreadLocalRandom}
     */
    public static ThreadLocalRandom getRandom() {
        return ThreadLocalRandom.current();
    }
}
