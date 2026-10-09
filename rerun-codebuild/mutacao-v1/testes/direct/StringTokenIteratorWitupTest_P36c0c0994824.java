/* @test    @summary Test StringTokenIterator.setStart method for valid and invalid inputs    @run main StringTokenIteratorWitupTest_P36c0c0994824 */

import sun.util.locale.StringTokenIterator;

public class StringTokenIteratorWitupTest_P36c0c0994824 {

    public static void main(String[] args) {
        testSetStartValid();
        testSetStartAtTextEnd();
        testSetStartBeyondTextEnd();
    }

    private static void testSetStartValid() {
        String text = "a,b,c";
        String dlms = ",";
        StringTokenIterator iterator = new StringTokenIterator(text, dlms);
        try {
            iterator.setStart(2);
            String currentToken = iterator.current();
            if (!"b".equals(currentToken)) {
                throw new AssertionError("Expected token 'b', but got: " + currentToken);
            }
            if (iterator.currentStart() != 2) {
                throw new AssertionError("Expected start index 2, but got: " + iterator.currentStart());
            }
            if (iterator.currentEnd() != 3) {
                throw new AssertionError("Expected end index 3, but got: " + iterator.currentEnd());
            }
            if (iterator.isDone()) {
                throw new AssertionError("Iterator should not be done after valid setStart");
            }
        } catch (Exception e) {
            throw new RuntimeException("testSetStartValid failed", e);
        }
    }

    private static void testSetStartAtTextEnd() {
        String text = "abc";
        String dlms = ",";
        StringTokenIterator iterator = new StringTokenIterator(text, dlms);
        try {
            iterator.setStart(3); // at text length
            String currentToken = iterator.current();
            if (!"".equals(currentToken)) {
                throw new AssertionError("Expected empty token at text end, but got: '" + currentToken + "'");
            }
            if (iterator.currentStart() != 3) {
                throw new AssertionError("Expected start index 3, but got: " + iterator.currentStart());
            }
            if (iterator.currentEnd() != 3) {
                throw new AssertionError("Expected end index 3, but got: " + iterator.currentEnd());
            }
            if (iterator.isDone()) {
                throw new AssertionError("Iterator should not be done at text end after setStart");
            }
        } catch (Exception e) {
            throw new RuntimeException("testSetStartAtTextEnd failed", e);
        }
    }

    private static void testSetStartBeyondTextEnd() {
        String text = "abc";
        String dlms = ",";
        StringTokenIterator iterator = new StringTokenIterator(text, dlms);
        try {
            iterator.setStart(4); // beyond text length
            throw new AssertionError("Expected IndexOutOfBoundsException for setStart(4)");
        } catch (IndexOutOfBoundsException e) {
            // expected
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception in testSetStartBeyondTextEnd", e);
        }
    }
}
