/*
 * @test
 * @summary Test StringLatin1.compareToUTF16 method for valid and invalid offsets
 * @run main StringLatin1WitupTest
 */

import java.lang.StringLatin1;
import java.lang.StringUTF16;

public class StringLatin1WitupTest {

    public static void main(String[] args) {
        testCompareToUTF16Valid();
        testCompareToUTF16InvalidLen1();
        testCompareToUTF16InvalidLen2();
    }

    private static void testCompareToUTF16Valid() {
        byte[] value = new byte[] {0x41, 0x42, 0x43}; // 'A','B','C'
        byte[] other = StringUTF16.newBytesFor(3);
        // Fill other with UTF16 bytes for 'A','B','D'
        other[0] = 0; other[1] = 65; // 'A'
        other[2] = 0; other[3] = 66; // 'B'
        other[4] = 0; other[5] = 68; // 'D'

        int len1 = value.length;
        int len2 = 3;

        int result = StringLatin1.compareToUTF16(value, other, len1, len2);

        // 'C' (67) < 'D' (68) so result should be negative
        if (result >= 0) {
            throw new AssertionError("Expected negative result for compareToUTF16 but got " + result);
        }
    }

    private static void testCompareToUTF16InvalidLen1() {
        byte[] value = new byte[] {0x41, 0x42};
        byte[] other = StringUTF16.newBytesFor(2);

        int invalidLen1 = value.length + 1; // invalid: len1 > value.length
        int len2 = 2;

        try {
            StringLatin1.compareToUTF16(value, other, invalidLen1, len2);
            throw new AssertionError("Expected StringIndexOutOfBoundsException for invalid len1");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }

        int negativeLen1 = -1; // invalid: len1 < 0
        try {
            StringLatin1.compareToUTF16(value, other, negativeLen1, len2);
            throw new AssertionError("Expected StringIndexOutOfBoundsException for negative len1");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
    }

    private static void testCompareToUTF16InvalidLen2() {
        byte[] value = new byte[] {0x41, 0x42};
        byte[] other = StringUTF16.newBytesFor(2);

        int len1 = value.length;
        int invalidLen2 = StringUTF16.length(other) + 1; // invalid: len2 > StringUTF16.length(other)

        try {
            StringLatin1.compareToUTF16(value, other, len1, invalidLen2);
            throw new AssertionError("Expected StringIndexOutOfBoundsException for invalid len2");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }

        int negativeLen2 = -1; // invalid: len2 < 0
        try {
            StringLatin1.compareToUTF16(value, other, len1, negativeLen2);
            throw new AssertionError("Expected StringIndexOutOfBoundsException for negative len2");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
    }
}
