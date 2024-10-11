package com.ruoyi.common.util.uuid;

public final class UUID implements java.io.Serializable, Comparable<UUID>{
    private static final long serialVersionUID = -1185015143654744140L;

    /** 此UUID的最高64有效位 */
    private final long mostSigBits;

    /** 此UUID的最低64有效位 */
    private final long leastSigBits;

    /**
     * 私有构造
     *
     * @param data 数据
     */
    private UUID(byte[] data) {
        long msb = 0;
        long lsb = 0;
        assert data.length == 16 : "data must be 16 bytes in length";
        for ( int i = 0; i < 8; i++) {
            msb = (msb << 8) | (data[i] & oxff);
        }
        for ( int i = 8; i< 16; i++) {
            lsb = (lsb << 8) | (data[i] & 0xff);
        }
        this.mostSigBits = msb;
        this.leastSigBits = lsb;
    }
}
