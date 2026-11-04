/*
 * @test
 * @summary Tests the StringUTF16.charAt method for correct behavior.
 * @run main StringUTF16WitupTest
 */

import java.lang.StringUTF16;

public class StringUTF16WitupTest {

    public static void main(String[] args) {
        testCharAtValidIndex();
        testCharAtNegativeIndex();
        testCharAtIndexEqualToLength();
        testCharAtEmptyArray();
    }

    static void testCharAtValidIndex() {
        byte[] data = new byte[] {0, 65, 0, 66, 0, 67}; // Represents 'A', 'B', 'C'
        char c0 = StringUTF16.charAt(data, 0); // 'A'
        char c1 = StringUTF16.charAt(data, 2); // 'B'
        char c2 = StringUTF16.charAt(data, 4); // 'C'
        assert c0 == 'A' : "Expected 'A', got " + c0;
        assert c1 == 'B' : "Expected 'B', got " + c1;
        assert c2 == 'C' : "Expected 'C', got " + c2;
    }

    static void testCharAtNegativeIndex() {
        byte[] data = new byte[] {0, 65};
        try {
            StringUTF16.charAt(data, -1);
            assert false : "Expected ArrayIndexOutOfBoundsException for negative index";
        } catch (ArrayIndexOutOfBoundsException e) {
            // Expected exception
        }
    }

    static void testCharAtIndexEqualToLength() {
        byte[] data = new byte[] {0, 65};
        try {
            StringUTF16.charAt(data, data.length / 2); // index equal to length / 2, valid
            // Valid index, should not throw
        } catch (ArrayIndexOutOfBoundsException e) {
            assert false : "Did not expect exception at valid index";
        }
        try {
            StringUTF16.charAt(data, data.length / 2 + 1); // index beyond length
            assert false : "Expected ArrayIndexOutOfBoundsException for index beyond length";
        } catch (ArrayIndexOutOfBoundsException e) {
            // Expected exception
        }
    }

    static void testCharAtEmptyArray() {
        byte[] data = new byte[0];
        try {
            StringUTF16.charAt(data, 0);
            assert false : "Expected ArrayIndexOutOfBoundsException for empty array";
        } catch (ArrayIndexOutOfBoundsException e) {
            // Expected exception
        }
    }
}
