/*
 * @test
 * @summary Witup generated tests for DerOutputStream.putSet
 * @modules java.base/sun.security.util
 * @run main DerOutputStreamWitupTest
 */

import java.io.IOException;
import java.util.Arrays;
import sun.security.util.DerOutputStream;
import sun.security.util.DerValue;

public class DerOutputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        testEmptySet();
        testSingleInteger();
        testOrderPreserved();
        testAppendsAfterExistingContent();
        testNullArrayThrowsNPE();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void assertBytes(byte[] actual, int... expected) {
        byte[] exp = new byte[expected.length];
        for (int i = 0; i < expected.length; i++) {
            exp[i] = (byte) expected[i];
        }
        check(Arrays.equals(actual, exp),
              "unexpected bytes: " + Arrays.toString(actual)
              + " expected: " + Arrays.toString(exp));
    }

    static void testEmptySet() throws IOException {
        DerOutputStream out = new DerOutputStream();
        out.putSet(new DerValue[0]);
        assertBytes(out.toByteArray(), 0x31, 0x00);
    }

    static void testSingleInteger() throws IOException {
        DerOutputStream out = new DerOutputStream();
        DerValue v = new DerValue(DerValue.tag_Integer, new byte[] {5});
        out.putSet(new DerValue[] {v});
        assertBytes(out.toByteArray(), 0x31, 0x03, 0x02, 0x01, 0x05);
    }

    static void testOrderPreserved() throws IOException {
        DerOutputStream out = new DerOutputStream();
        DerValue a = new DerValue(DerValue.tag_Integer, new byte[] {1});
        DerValue b = new DerValue(DerValue.tag_Integer, new byte[] {2});
        out.putSet(new DerValue[] {a, b});
        assertBytes(out.toByteArray(), 0x31, 0x06, 0x02, 0x01, 0x01, 0x02, 0x01, 0x02);
    }

    static void testAppendsAfterExistingContent() throws IOException {
        DerOutputStream out = new DerOutputStream();
        out.write(0x05);
        out.putSet(new DerValue[0]);
        assertBytes(out.toByteArray(), 0x05, 0x31, 0x00);
    }

    static void testNullArrayThrowsNPE() {
        DerOutputStream out = new DerOutputStream();
        try {
            out.putSet(null);
        } catch (NullPointerException e) {
            return;
        } catch (IOException e) {
            throw new RuntimeException("unexpected IOException", e);
        }
        throw new RuntimeException("expected NullPointerException for null set");
    }
}
