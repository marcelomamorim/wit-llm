/* @test
   @summary Tests the setStart method of StringTokenIterator for boundary conditions.
   @run main StringTokenIteratorWitupTest_Pded45c072c8c
 */
import java.util.Objects;
import sun.util.locale.StringTokenIterator;

public class StringTokenIteratorWitupTest_Pded45c072c8c {
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
            // expected
        }
    }

    private static void testSetStartWithInvalidBounds() {
        String text = "abc";
        String dlms = ",";
        StringTokenIterator iterator = new StringTokenIterator(text, dlms);
        // Set start to a valid position
        iterator.setStart(1);
        // Manually manipulate internal state to invalid bounds for testing
        // Since internal fields are private, we test via public API
        // We simulate invalid bounds by calling substring with invalid start/end
        // but since substring is called internally, we test setStart with invalid start
        try {
            iterator.setStart(-1); // negative start
            throw new AssertionError("Expected IndexOutOfBoundsException not thrown for negative start");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
        // Also test with start beyond end
        try {
            iterator.setStart(4); // beyond text length
            throw new AssertionError("Expected IndexOutOfBoundsException not thrown for start beyond text length");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }
}
