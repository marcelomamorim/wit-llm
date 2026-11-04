/* @test    @summary Test for StringUTF16.compress method to ensure it correctly processes input data and handles edge cases.    @run main StringUTF16WitupTest */

import java.lang.StringUTF16;

public class StringUTF16WitupTest {

    public static void main(String[] args) {
        testCompressNormalCase();
        testCompressEmptyInput();
        testCompressNullInput();
        testCompressDstNull();
        testCompressSrcOffsetAndLength();
    }

    static void testCompressNormalCase() {
        byte[] src = new byte[] {0x00, 0x41, 0x00, 0x42, 0x00, 0x43}; // 'A', 'B', 'C'
        byte[] dst = new byte[3];
        int result = StringUTF16.compress(src, 0, dst, 0, 3);
        assert result == 3 : "Expected result 3, got " + result;
        assert dst[0] == 0x00 : "Expected 0x00, got " + dst[0];
        assert dst[1] == 0x41 : "Expected 0x41, got " + dst[1];
        assert dst[2] == 0x00 : "Expected 0x00, got " + dst[2];
    }

    static void testCompressEmptyInput() {
        byte[] src = new byte[0];
        byte[] dst = new byte[0];
        int result = StringUTF16.compress(src, 0, dst, 0, 0);
        assert result == 0 : "Expected result 0, got " + result;
    }

    static void testCompressNullInput() {
        try {
            StringUTF16.compress(null, 0, new byte[1], 0, 1);
            assert false : "Expected NullPointerException";
        } catch (NullPointerException e) {
            // expected
        }
    }

    static void testCompressDstNull() {
        byte[] src = new byte[] {0x00, 0x41};
        try {
            StringUTF16.compress(src, 0, null, 0, 1);
            assert false : "Expected NullPointerException";
        } catch (NullPointerException e) {
            // expected
        }
    }

    static void testCompressSrcOffsetAndLength() {
        byte[] src = new byte[] {0x00, 0x41, 0x00, 0x42, 0x00, 0x43};
        byte[] dst = new byte[2];
        int result = StringUTF16.compress(src, 2, dst, 0, 2);
        assert result == 2 : "Expected result 2, got " + result;
        assert dst[0] == 0x00 : "Expected 0x00, got " + dst[0];
        assert dst[1] == 0x42 : "Expected 0x42, got " + dst[1];
    }
}
