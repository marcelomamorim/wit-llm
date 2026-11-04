/*
 * @test
 * @summary Test for StringUTF16.compress method, focusing on boundary conditions and exception paths.
 * @run main StringUTF16WitupTest
 */

import java.lang.StringUTF16;

public class StringUTF16WitupTest {

    public static void main(String[] args) {
        testCompressWithInvalidBounds();
        testCompressWithValidInput();
    }

    private static void testCompressWithInvalidBounds() {
        byte[] src = new byte[10];
        byte[] dst = new byte[10];
        int srcOff = -1;
        int dstOff = 0;
        int len = 5;
        try {
            StringUTF16.compress(src, srcOff, dst, dstOff, len);
            System.out.println("FAIL: Expected StringIndexOutOfBoundsException for negative srcOff");
        } catch (StringIndexOutOfBoundsException e) {
            System.out.println("PASS: Caught expected exception for negative srcOff");
        }

        srcOff = 0;
        len = -1;
        try {
            StringUTF16.compress(src, srcOff, dst, dstOff, len);
            System.out.println("FAIL: Expected StringIndexOutOfBoundsException for negative len");
        } catch (StringIndexOutOfBoundsException e) {
            System.out.println("PASS: Caught expected exception for negative len");
        }

        srcOff = 8;
        len = 5;
        try {
            StringUTF16.compress(src, srcOff, dst, dstOff, len);
            System.out.println("FAIL: Expected StringIndexOutOfBoundsException for srcOff + len > src.length");
        } catch (StringIndexOutOfBoundsException e) {
            System.out.println("PASS: Caught expected exception for srcOff + len > src.length");
        }
    }

    private static void testCompressWithValidInput() {
        byte[] src = new byte[10];
        byte[] dst = new byte[10];
        // Fill src with valid data
        for (int i = 0; i < 5; i++) {
            src[i * 2] = (byte) (i + 1); // arbitrary data
            src[i * 2 + 1] = (byte) (i + 1);
        }
        int srcOff = 0;
        int dstOff = 0;
        int len = 5;
        int result = StringUTF16.compress(src, srcOff, dst, dstOff, len);
        if (result == len) {
            System.out.println("PASS: compress returned expected length");
        } else {
            System.out.println("FAIL: compress returned unexpected length: " + result);
        }
        // Verify dst content
        boolean contentValid = true;
        for (int i = 0; i < len; i++) {
            if (dst[i] != src[i * 2]) {
                contentValid = false;
                break;
            }
        }
        if (contentValid) {
            System.out.println("PASS: dst contains expected data");
        } else {
            System.out.println("FAIL: dst does not contain expected data");
        }
    }
}
