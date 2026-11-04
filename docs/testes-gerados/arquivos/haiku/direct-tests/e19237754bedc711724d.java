/*
 * @test
 * @summary Black-box checks of CRC32C.update(byte[], int, int) argument validation and checksum values
 * @run main CRC32CWitupTest
 */

public class CRC32CWitupTest {

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    static byte[] digits() {
        byte[] data = new byte[9];
        for (int i = 0; i < data.length; i++) {
            data[i] = (byte) ('1' + i);
        }
        return data;
    }

    static void testCheckValue() {
        byte[] data = digits();
        CRC32C c = new CRC32C();
        c.update(data, 0, data.length);
        check(c.getValue() == 0xE3069283L, "CRC-32C check value mismatch: " + Long.toHexString(c.getValue()));
    }

    static void testChunkingInvariance() {
        byte[] data = digits();
        CRC32C whole = new CRC32C();
        whole.update(data, 0, data.length);

        CRC32C parts = new CRC32C();
        parts.update(data, 0, 4);
        parts.update(data, 4, 5);
        check(whole.getValue() == parts.getValue(), "chunked update differs from whole update");
    }

    static void testZeroLengthKeepsState() {
        byte[] data = digits();
        CRC32C c = new CRC32C();
        c.update(data, 0, 0);
        check(c.getValue() == 0L, "zero-length update changed CRC from initial value");

        c.update(data, 9, 0);
        check(c.getValue() == 0L, "zero-length update at end changed CRC");
    }

    static void testResetAfterUpdate() {
        byte[] data = digits();
        CRC32C c = new CRC32C();
        c.update(data, 0, data.length);
        c.reset();
        check(c.getValue() == 0L, "reset did not restore initial value");
    }

    static void testNullArray() {
        CRC32C c = new CRC32C();
        boolean thrown = false;
        try {
            c.update(null, 0, 0);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "null array did not throw NullPointerException");
    }

    static void expectAioobe(byte[] b, int off, int len, String name) {
        CRC32C c = new CRC32C();
        boolean thrown = false;
        try {
            c.update(b, off, len);
        } catch (ArrayIndexOutOfBoundsException e) {
            thrown = true;
        }
        check(thrown, "expected ArrayIndexOutOfBoundsException for " + name);
    }

    static void testBadOffsetsAndLengths() {
        byte[] data = digits();
        expectAioobe(data, -1, 1, "negative offset");
        expectAioobe(data, 0, -1, "negative length");
        expectAioobe(data, 5, 5, "off + len past end (5,5)");
        expectAioobe(data, 9, 1, "off past end (9,1)");
    }

    public static void main(String[] args) throws Exception {
        testCheckValue();
        testChunkingInvariance();
        testZeroLengthKeepsState();
        testResetAfterUpdate();
        testNullArray();
        testBadOffsetsAndLengths();
    }
}
