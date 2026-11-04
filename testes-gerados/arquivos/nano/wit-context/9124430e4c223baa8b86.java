/*
 * @test
 * @summary Tests for StringLatin1.compareToUTF16 method.
 * @run main StringLatin1WitupTest
 */

import java.lang.StringLatin1;

public class StringLatin1WitupTest {

    public static void main(String[] args) {
        testCompareToUTF16_NormalCase();
        testCompareToUTF16_OutOfBounds();
        testCompareToUTF16_NegativeLength();
    }

    private static void testCompareToUTF16_NormalCase() {
        byte[] value = {0x00, 0x01, 0x02};
        byte[] other = {0x03, 0x04, 0x05};
        int len1 = 3;
        int len2 = 3;
        int result = StringLatin1.compareToUTF16(value, other, len1, len2);
        System.out.println("Normal case result: " + result);
    }

    private static void testCompareToUTF16_OutOfBounds() {
        byte[] value = {0x00, 0x01};
        byte[] other = {0x02, 0x03};
        int len1 = 5; // exceeds value.length
        int len2 = 2;
        try {
            StringLatin1.compareToUTF16(value, other, len1, len2);
            System.out.println("Out of bounds test failed: no exception thrown");
        } catch (StringIndexOutOfBoundsException e) {
            System.out.println("Out of bounds test passed: " + e.getMessage());
        }
    }

    private static void testCompareToUTF16_NegativeLength() {
        byte[] value = {0x00, 0x01, 0x02};
        byte[] other = {0x03, 0x04, 0x05};
        int len1 = -1;
        int len2 = 3;
        try {
            StringLatin1.compareToUTF16(value, other, len1, len2);
            System.out.println("Negative length test failed: no exception thrown");
        } catch (StringIndexOutOfBoundsException e) {
            System.out.println("Negative length test passed: " + e.getMessage());
        }
    }
}
