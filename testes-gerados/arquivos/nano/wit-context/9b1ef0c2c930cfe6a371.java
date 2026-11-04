/* @test    @summary Test for StringTokenIterator.setText to cover StringIndexOutOfBoundsException scenarios    @run main StringTokenIteratorWitupTest */

import sun.util.locale.StringTokenIterator;

public class StringTokenIteratorWitupTest {

    public static void main(String[] args) {
        testSetTextWithEmptyString();
        testSetTextWithNull();
        testSetTextWithValidString();
        testSetTextWithStartOutOfBounds();
        testSetTextWithNegativeStart();
        testSetTextWithStartBeyondLength();
    }

    private static void testSetTextWithEmptyString() {
        StringTokenIterator iterator = new StringTokenIterator("abc", ",");
        try {
            iterator.setText("");
            assert "".equals(iterator.current()) : "Expected empty string as current token";
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for empty string", e);
        }
    }

    private static void testSetTextWithNull() {
        try {
            StringTokenIterator iterator = new StringTokenIterator("abc", ",");
            iterator.setText(null);
            assert iterator.current() == null : "Expected null as current token";
        } catch (NullPointerException e) {
            // Expected if setText does not handle null
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for null string", e);
        }
    }

    private static void testSetTextWithValidString() {
        StringTokenIterator iterator = new StringTokenIterator("abc,def", ",");
        try {
            iterator.setText("xyz,uvw");
            String token = iterator.first();
            assert "xyz".equals(token) : "First token should be 'xyz'";
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for valid string", e);
        }
    }

    private static void testSetTextWithStartOutOfBounds() {
        StringTokenIterator iterator = new StringTokenIterator("abc", ",");
        try {
            iterator.setStart(10); // start beyond text length
            throw new AssertionError("Expected IndexOutOfBoundsException for start beyond length");
        } catch (IndexOutOfBoundsException e) {
            // Expected
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for start beyond length", e);
        }
    }

    private static void testSetTextWithNegativeStart() {
        StringTokenIterator iterator = new StringTokenIterator("abc", ",");
        try {
            iterator.setStart(-1); // negative start
            throw new AssertionError("Expected IndexOutOfBoundsException for negative start");
        } catch (IndexOutOfBoundsException e) {
            // Expected
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for negative start", e);
        }
    }

    private static void testSetTextWithStartBeyondLength() {
        StringTokenIterator iterator = new StringTokenIterator("abc", ",");
        try {
            iterator.setStart(4); // start beyond text length
            throw new AssertionError("Expected IndexOutOfBoundsException for start beyond length");
        } catch (IndexOutOfBoundsException e) {
            // Expected
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for start beyond length", e);
        }
    }
}
