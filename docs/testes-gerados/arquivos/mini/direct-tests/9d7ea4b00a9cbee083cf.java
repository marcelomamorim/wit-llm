/*
 * @test
 * @summary Test java.util.Scanner.hasNextByte(int radix) method behavior
 * @run main java.util.ScannerWitupTest
 */

import java.util.Scanner;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

public class ScannerWitupTest {
    public static void main(String[] args) throws Exception {
        testHasNextByteValidDecimal();
        testHasNextByteValidHex();
        testHasNextByteInvalidNumberFormat();
        testHasNextByteOutOfRange();
        testHasNextByteEmptyInput();
    }

    private static void testHasNextByteValidDecimal() {
        Scanner sc = new Scanner("127");
        boolean hasNext = sc.hasNextByte(10);
        if (!hasNext) {
            throw new RuntimeException("Expected hasNextByte(10) to be true for '127'");
        }
        byte val = sc.nextByte(10);
        if (val != 127) {
            throw new RuntimeException("Expected nextByte(10) to return 127 but got " + val);
        }
        sc.close();
    }

    private static void testHasNextByteValidHex() {
        Scanner sc = new Scanner("7f");
        boolean hasNext = sc.hasNextByte(16);
        if (!hasNext) {
            throw new RuntimeException("Expected hasNextByte(16) to be true for '7f'");
        }
        byte val = sc.nextByte(16);
        if (val != 127) {
            throw new RuntimeException("Expected nextByte(16) to return 127 but got " + val);
        }
        sc.close();
    }

    private static void testHasNextByteInvalidNumberFormat() {
        Scanner sc = new Scanner("128"); // 128 is out of byte range
        boolean hasNext = sc.hasNextByte(10);
        if (hasNext) {
            throw new RuntimeException("Expected hasNextByte(10) to be false for '128' (out of byte range)");
        }
        sc.close();
    }

    private static void testHasNextByteOutOfRange() {
        Scanner sc = new Scanner("-129"); // -129 is out of byte range
        boolean hasNext = sc.hasNextByte(10);
        if (hasNext) {
            throw new RuntimeException("Expected hasNextByte(10) to be false for '-129' (out of byte range)");
        }
        sc.close();
    }

    private static void testHasNextByteEmptyInput() {
        Scanner sc = new Scanner("");
        boolean hasNext = sc.hasNextByte(10);
        if (hasNext) {
            throw new RuntimeException("Expected hasNextByte(10) to be false for empty input");
        }
        sc.close();
    }
}
