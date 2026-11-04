/* @test    @summary Test StringUTF16.inflate method for correct byte array inflation    @run main StringUTF16WitupTest */

import java.lang.StringUTF16;

public class StringUTF16WitupTest {

    public static void main(String[] args) {
        testInflateBasic();
        testInflateEmpty();
        testInflateWithOffset();
        testInflateWithLargeData();
    }

    static void testInflateBasic() {
        byte[] src = {0x00, 0x41, 0x00, 0x42, 0x00, 0x43}; // 'A', 'B', 'C'
        byte[] dst = new byte[6];
        StringUTF16.inflate(src, 0, dst, 0, 3);
        assert dst.length == 6 : "Destination array length mismatch";
        assert StringUTF16.getChar(dst, 0) == 'A' : "First char mismatch";
        assert StringUTF16.getChar(dst, 2) == 'B' : "Second char mismatch";
        assert StringUTF16.getChar(dst, 4) == 'C' : "Third char mismatch";
    }

    static void testInflateEmpty() {
        byte[] src = {};
        byte[] dst = new byte[0];
        StringUTF16.inflate(src, 0, dst, 0, 0);
        assert dst.length == 0 : "Empty array test failed";
    }

    static void testInflateWithOffset() {
        byte[] src = {0x00, 0x41, 0x00, 0x42, 0x00, 0x43};
        byte[] dst = new byte[8];
        StringUTF16.inflate(src, 2, dst, 2, 3);
        assert StringUTF16.getChar(dst, 2) == 'A' : "Offset test char 1";
        assert StringUTF16.getChar(dst, 4) == 'B' : "Offset test char 2";
        assert StringUTF16.getChar(dst, 6) == 'C' : "Offset test char 3";
    }

    static void testInflateWithLargeData() {
        byte[] src = new byte[10];
        byte[] dst = new byte[20];
        for (int i = 0; i < 10; i++) {
            src[i] = (byte)(i + 65); // ASCII from 'A'
        }
        StringUTF16.inflate(src, 0, dst, 0, 5);
        for (int i = 0; i < 5; i++) {
            assert StringUTF16.getChar(dst, i * 2) == (char)(i + 65) : "Large data test char mismatch at index " + i;
        }
    }
}
