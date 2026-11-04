/* @test    @summary Test for StringUTF16.checkBoundsOffCount method to verify exception path handling.    @run main StringUTF16WitupTest */

import java.lang.StringUTF16;

public class StringUTF16WitupTest {

    public static void main(String[] args) {
        testCheckBoundsOffCountValid();
        testCheckBoundsOffCountNegativeOffset();
        testCheckBoundsOffCountNegativeCount();
        testCheckBoundsOffCountOffsetExceeds();
    }

    static void testCheckBoundsOffCountValid() {
        byte[] validArray = new byte[10];
        try {
            StringUTF16.checkBoundsOffCount(0, 5, validArray);
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for valid input", e);
        }
    }

    static void testCheckBoundsOffCountNegativeOffset() {
        byte[] array = new byte[10];
        try {
            StringUTF16.checkBoundsOffCount(-1, 5, array);
            throw new AssertionError("Expected exception not thrown for negative offset");
        } catch (IndexOutOfBoundsException e) {
            // Expected exception
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type", e);
        }
    }

    static void testCheckBoundsOffCountNegativeCount() {
        byte[] array = new byte[10];
        try {
            StringUTF16.checkBoundsOffCount(0, -1, array);
            throw new AssertionError("Expected exception not thrown for negative count");
        } catch (IndexOutOfBoundsException e) {
            // Expected exception
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type", e);
        }
    }

    static void testCheckBoundsOffCountOffsetExceeds() {
        byte[] array = new byte[10];
        try {
            StringUTF16.checkBoundsOffCount(6, 5, array); // 6 + 5 > 10
            throw new AssertionError("Expected exception not thrown for offset exceeding bounds");
        } catch (IndexOutOfBoundsException e) {
            // Expected exception
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type", e);
        }
    }
}
