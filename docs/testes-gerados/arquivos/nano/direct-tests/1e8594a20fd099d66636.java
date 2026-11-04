/*
 * @test
 * @summary Test StringLatin1.charAt(byte[], int) for valid and invalid indices
 * @run main StringLatin1WitupTest
 */

import java.lang.StringLatin1;

public class StringLatin1WitupTest {

    public static void main(String[] args) {
        testValidIndex();
        testNegativeIndex();
        testIndexEqualToLength();
    }

    static void testValidIndex() {
        byte[] data = {10, 20, 30, 40, 50};
        int index = 2;
        char result = StringLatin1.charAt(data, index);
        if (result != (char)(data[index] & 0xff)) {
            throw new AssertionError("Expected " + (char)(data[index] & 0xff) + " but got " + result);
        }
    }

    static void testNegativeIndex() {
        byte[] data = {1, 2, 3};
        try {
            StringLatin1.charAt(data, -1);
            throw new AssertionError("Expected StringIndexOutOfBoundsException for negative index");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
    }

    static void testIndexEqualToLength() {
        byte[] data = {1, 2, 3};
        try {
            StringLatin1.charAt(data, data.length);
            throw new AssertionError("Expected StringIndexOutOfBoundsException for index equal to length");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
    }
}
