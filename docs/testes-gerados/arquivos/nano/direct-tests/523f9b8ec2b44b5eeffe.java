/*
 * @test
 * @summary Test StringTokenIterator.setStart(int offset) for correct behavior and exception handling
 * @run main StringTokenIteratorWitupTest
 */

import sun.util.locale.StringTokenIterator;

public class StringTokenIteratorWitupTest {

    public static void main(String[] args) {
        testSetStartValid();
        testSetStartInvalid();
    }

    private static void testSetStartValid() {
        String text = "abc,def,ghi";
        String dlms = ",";
        StringTokenIterator iterator = new StringTokenIterator(text, dlms);
        // Move to second token
        iterator.next(); // first token
        iterator.next(); // second token
        // Set start to beginning of third token
        iterator.setStart(8); // position of 'g'
        String currentToken = iterator.current();
        if (!"ghi".equals(currentToken)) {
            throw new AssertionError("Expected 'ghi', but got: " + currentToken);
        }
        if (iterator.currentStart() != 8) {
            throw new AssertionError("Expected start index 8, but got: " + iterator.currentStart());
        }
        if (iterator.currentEnd() != 11) {
            throw new AssertionError("Expected end index 11, but got: " + iterator.currentEnd());
        }
        if (iterator.isDone()) {
            throw new AssertionError("Iterator should not be done after valid setStart.");
        }
        System.out.println("testSetStartValid passed.");
    }

    private static void testSetStartInvalid() {
        String text = "abc";
        String dlms = ",";
        StringTokenIterator iterator = new StringTokenIterator(text, dlms);
        boolean exceptionThrown = false;
        try {
            iterator.setStart(10); // beyond text length
        } catch (IndexOutOfBoundsException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected IndexOutOfBoundsException for offset 10.");
        }
        System.out.println("testSetStartInvalid passed.");
    }
}
