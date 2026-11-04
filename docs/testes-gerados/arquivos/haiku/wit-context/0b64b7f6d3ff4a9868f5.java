/*
 * @test
 * @summary Verifies that DerOutputStream.putNull() emits a DER NULL value (tag 0x05, length 0x00)
 * @modules java.base/sun.security.util
 * @run main DerOutputStreamWitupTest
 */

import java.util.Arrays;
import sun.security.util.DerOutputStream;

public class DerOutputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        testSingleNullEncoding();
        testNullEncodingAppendsSequentially();
        testNullEncodingWithPresizedBuffer();
    }

    private static void testSingleNullEncoding() throws Exception {
        DerOutputStream out = new DerOutputStream();
        out.putNull();
        byte[] expected = {0x05, 0x00};
        check(Arrays.equals(expected, out.toByteArray()), "single putNull must encode as 05 00");
        check(out.size() == 2, "single putNull must write exactly 2 bytes");
    }

    private static void testNullEncodingAppendsSequentially() throws Exception {
        DerOutputStream out = new DerOutputStream();
        out.putNull();
        out.putNull();
        byte[] expected = {0x05, 0x00, 0x05, 0x00};
        check(Arrays.equals(expected, out.toByteArray()), "two putNull calls must append two NULL encodings");
    }

    private static void testNullEncodingWithPresizedBuffer() throws Exception {
        DerOutputStream out = new DerOutputStream(1);
        out.putNull();
        byte[] expected = {0x05, 0x00};
        check(Arrays.equals(expected, out.toByteArray()), "putNull must behave identically with a presized buffer");
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
