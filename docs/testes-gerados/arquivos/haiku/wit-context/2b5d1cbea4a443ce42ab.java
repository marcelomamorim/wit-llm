/*
 * @test
 * @summary Witup-derived checks for DerInputStream.getBigInteger
 * @modules java.base/sun.security.util
 * @run main DerInputStreamWitupTest
 */

import java.io.IOException;
import java.math.BigInteger;
import sun.security.util.DerInputStream;

public class DerInputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        check(new byte[]{0x02, 0x01, 0x05}, BigInteger.valueOf(5));
        check(new byte[]{0x02, 0x01, (byte) 0xFF}, BigInteger.valueOf(-1));
        check(new byte[]{0x02, 0x02, 0x01, 0x00}, BigInteger.valueOf(256));
        check(new byte[]{0x02, 0x01, (byte) 0x80}, BigInteger.valueOf(-128));
        expectIOException(new byte[]{0x04, 0x01, 0x05});
        expectIOException(new byte[0]);
    }

    static void check(byte[] data, BigInteger expected) throws IOException {
        DerInputStream in = new DerInputStream(data);
        BigInteger actual = in.getBigInteger();
        if (!expected.equals(actual)) {
            throw new RuntimeException("Expected " + expected + " but got " + actual);
        }
    }

    static void expectIOException(byte[] data) {
        boolean thrown = false;
        try {
            new DerInputStream(data).getBigInteger();
        } catch (IOException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected IOException was not thrown");
        }
    }
}
