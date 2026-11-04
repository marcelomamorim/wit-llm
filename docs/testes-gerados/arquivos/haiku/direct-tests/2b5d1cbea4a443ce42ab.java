/*
 * @test
 * @summary Verifies DerInputStream.getBigInteger() decoding of INTEGER values and tag errors
 * @modules java.base/sun.security.util
 * @run main DerInputStreamWitupTest
 */

import java.io.IOException;
import java.math.BigInteger;
import sun.security.util.DerInputStream;

public class DerInputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        checkValue(new byte[]{0x02, 0x01, 0x05}, BigInteger.valueOf(5));
        checkValue(new byte[]{0x02, 0x01, (byte) 0xFF}, BigInteger.valueOf(-1));
        checkValue(new byte[]{0x02, 0x02, 0x01, 0x00}, BigInteger.valueOf(256));
        checkTagError(new byte[]{0x04, 0x01, 0x05}, "DER input, Integer tag error");
        checkTagError(new byte[]{0x30, 0x00}, "DER input, Integer tag error");
        checkEmptyFails();
    }

    static void checkValue(byte[] data, BigInteger expected) throws Exception {
        DerInputStream in = new DerInputStream(data);
        BigInteger actual = in.getBigInteger();
        if (!expected.equals(actual)) {
            throw new RuntimeException("Expected " + expected + " but got " + actual);
        }
    }

    static void checkTagError(byte[] data, String message) throws Exception {
        DerInputStream in = new DerInputStream(data);
        try {
            in.getBigInteger();
        } catch (IOException e) {
            if (!message.equals(e.getMessage())) {
                throw new RuntimeException("Unexpected message: " + e.getMessage());
            }
            return;
        }
        throw new RuntimeException("IOException expected for tag " + data[0]);
    }

    static void checkEmptyFails() throws Exception {
        try {
            DerInputStream in = new DerInputStream(new byte[0]);
            in.getBigInteger();
        } catch (IOException e) {
            return;
        }
        throw new RuntimeException("IOException expected for empty input");
    }
}
