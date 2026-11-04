/*
 * @test
 * @summary Test insert method of StringBuffer with boolean argument for exception behavior
 * @run main StringBufferWitupTest
 */

import java.lang.StringBuffer;

public class StringBufferWitupTest {

    public static void main(String[] args) {
        testInsertBooleanValid();
        testInsertBooleanOutOfBounds();
    }

    private static void testInsertBooleanValid() {
        StringBuffer sb = new StringBuffer("start");
        try {
            sb.insert(2, true);
            String result = sb.toString();
            if (!"sttrueart".equals(result)) {
                throw new AssertionError("Unexpected result: " + result);
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception: " + e);
        }
    }

    private static void testInsertBooleanOutOfBounds() {
        StringBuffer sb = new StringBuffer("test");
        int invalidOffset = -1;
        try {
            sb.insert(invalidOffset, false);
            throw new AssertionError("Expected StringIndexOutOfBoundsException not thrown");
        } catch (StringIndexOutOfBoundsException e) {
            // Expected exception
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception: " + e);
        }
        int tooLargeOffset = sb.length() + 10;
        try {
            sb.insert(tooLargeOffset, false);
            throw new AssertionError("Expected StringIndexOutOfBoundsException not thrown");
        } catch (StringIndexOutOfBoundsException e) {
            // Expected exception
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception: " + e);
        }
    }
}
