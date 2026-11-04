/*
 * @test
 * @summary Test for StringUTF16.charAt(byte[], int) method, including boundary conditions.
 * @run main StringUTF16WitupTest
 */

import java.lang.StringUTF16;

public class StringUTF16WitupTest {

    public static void main(String[] args) {
        testCharAtValidIndex();
        testCharAtNegativeIndex();
        testCharAtIndexEqualToLength();
        testCharAtIndexGreaterThanLength();
    }

    private static void testCharAtValidIndex() {
        byte[] data = new byte[] {0, 65, 0, 66}; // Represents 'AB'
        try {
            char c = StringUTF16.charAt(data, 0);
            if (c != 'A') {
                System.err.println("Failed: Expected 'A' at index 0, got: " + c);
            }
            c = StringUTF16.charAt(data, 2);
            if (c != 'B') {
                System.err.println("Failed: Expected 'B' at index 2, got: " + c);
            }
        } catch (Exception e) {
            System.err.println("Unexpected exception in valid index test: " + e);
        }
    }

    private static void testCharAtNegativeIndex() {
        byte[] data = new byte[] {0, 65, 0, 66};
        try {
            StringUTF16.charAt(data, -1);
            System.err.println("Failed: Expected StringIndexOutOfBoundsException for negative index");
        } catch (StringIndexOutOfBoundsException e) {
            // Expected
        } catch (Exception e) {
            System.err.println("Unexpected exception type for negative index: " + e);
        }
    }

    private static void testCharAtIndexEqualToLength() {
        byte[] data = new byte[] {0, 65, 0, 66};
        int length = data.length;
        try {
            StringUTF16.charAt(data, length);
            System.err.println("Failed: Expected StringIndexOutOfBoundsException for index == length");
        } catch (StringIndexOutOfBoundsException e) {
            // Expected
        } catch (Exception e) {
            System.err.println("Unexpected exception type for index == length: " + e);
        }
    }

    private static void testCharAtIndexGreaterThanLength() {
        byte[] data = new byte[] {0, 65, 0, 66};
        int index = data.length + 1;
        try {
            StringUTF16.charAt(data, index);
            System.err.println("Failed: Expected StringIndexOutOfBoundsException for index > length");
        } catch (StringIndexOutOfBoundsException e) {
            // Expected
        } catch (Exception e) {
            System.err.println("Unexpected exception type for index > length: " + e);
        }
    }
}
