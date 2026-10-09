/* @test    @summary Test setStart method of StringTokenIterator for exception paths    @run main StringTokenIteratorWitupTest_P36c0c0994824 */

import sun.util.locale.StringTokenIterator;

public class StringTokenIteratorWitupTest_P36c0c0994824 {

    public static void main(String[] args) {
        testSetStartWithOffsetGreaterThanTextLength();
        testSetStartWithInvalidBounds();
    }

    private static void testSetStartWithOffsetGreaterThanTextLength() {
        String text = "abc";
        String dlms = ",";
        StringTokenIterator iterator = new StringTokenIterator(text, dlms);
        try {
            iterator.setStart(10); // offset greater than text length
            throw new AssertionError("Expected IndexOutOfBoundsException not thrown");
        } catch (IndexOutOfBoundsException e) {
            // Expected exception
        } catch (Throwable t) {
            throw new AssertionError("Unexpected exception: " + t);
        }
    }

    private static void testSetStartWithInvalidBounds() {
        String text = "abc";
        String dlms = ",";
        StringTokenIterator iterator = new StringTokenIterator(text, dlms);
        try {
            // Set start to a negative value to trigger StringIndexOutOfBoundsException
            iterator.setStart(-1);
            throw new AssertionError("Expected IndexOutOfBoundsException not thrown");
        } catch (IndexOutOfBoundsException e) {
            // Expected exception
        } catch (Throwable t) {
            throw new AssertionError("Unexpected exception: " + t);
        }
    }
}
