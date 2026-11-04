/*
 * @test
 * @summary Behavioral tests for ByteArrayInputStream.readAllBytes()
 * @run main ByteArrayInputStreamWitupTest
 */

import java.io.ByteArrayInputStream;
import java.util.Arrays;

public class ByteArrayInputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        testReturnsWholeBufferOnFreshStream();
        testReturnsRemainingBytesAfterRead();
        testRespectsOffsetAndLengthConstructor();
        testEmptyBufferReturnsEmptyArray();
        testSecondCallReturnsEmptyArray();
        testPositionAdvancesToEnd();
        testReturnedArrayIsIndependentCopy();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testReturnsWholeBufferOnFreshStream() {
        byte[] data = {1, 2, 3};
        ByteArrayInputStream in = new ByteArrayInputStream(data);
        byte[] result = in.readAllBytes();
        check(Arrays.equals(new byte[]{1, 2, 3}, result), "expected whole buffer");
    }

    static void testReturnsRemainingBytesAfterRead() {
        byte[] data = {10, 20, 30, 40};
        ByteArrayInputStream in = new ByteArrayInputStream(data);
        int first = in.read();
        check(first == 10, "expected first byte 10 but was " + first);
        byte[] result = in.readAllBytes();
        check(Arrays.equals(new byte[]{20, 30, 40}, result), "expected remaining bytes after read()");
    }

    static void testRespectsOffsetAndLengthConstructor() {
        byte[] data = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
        ByteArrayInputStream in = new ByteArrayInputStream(data, 2, 3);
        byte[] result = in.readAllBytes();
        check(Arrays.equals(new byte[]{2, 3, 4}, result), "expected bytes limited by offset and length");
    }

    static void testEmptyBufferReturnsEmptyArray() {
        ByteArrayInputStream in = new ByteArrayInputStream(new byte[0]);
        byte[] result = in.readAllBytes();
        check(result != null, "result must not be null");
        check(result.length == 0, "expected empty array");
    }

    static void testSecondCallReturnsEmptyArray() {
        ByteArrayInputStream in = new ByteArrayInputStream(new byte[]{5, 6});
        in.readAllBytes();
        byte[] second = in.readAllBytes();
        check(second != null, "second result must not be null");
        check(second.length == 0, "second call should return empty array");
    }

    static void testPositionAdvancesToEnd() {
        ByteArrayInputStream in = new ByteArrayInputStream(new byte[]{7, 8, 9});
        in.readAllBytes();
        check(in.read() == -1, "read() after readAllBytes should return -1");
        check(in.available() == 0, "available() after readAllBytes should be 0");
    }

    static void testReturnedArrayIsIndependentCopy() {
        byte[] data = {1, 2, 3};
        ByteArrayInputStream in = new ByteArrayInputStream(data);
        byte[] result = in.readAllBytes();
        result[0] = 99;
        check(data[0] == 1, "modifying result must not change source buffer");
    }
}
