/*
 * @test
 * @summary Test for StringTokenIterator.setStart(int)
 * @run main StringTokenIteratorWitupTest_P7d624a0a5243
 */

import sun.util.locale.StringTokenIterator;

public class StringTokenIteratorWitupTest_P7d624a0a5243 {

    public static void main(String[] args) {
        testSetStartWithValidOffset();
        testSetStartWithOffsetGreaterThanTextLength();
        testSetStartWithNegativeOffset();
        testSetStartWithNegativeOffsetAndDelimiter();
    }

    private static void testSetStartWithValidOffset() {
        String text = "abc,def,ghi";
        String dlms = ",";
        StringTokenIterator iterator = new StringTokenIterator(text, dlms);
        try {
            iterator.setStart(4); // 'd' in "abc,def,ghi"
            String currentToken = iterator.current();
            String expected = "def";
            if (!expected.equals(currentToken)) {
                System.err.println("FAIL: Expected token '" + expected + "', but got '" + currentToken + "'");
            } else {
                System.out.println("PASS: setStart with valid offset")
            }
        } catch (Exception e) {
            System.err.println("FAIL: Unexpected exception: " + e);
        }
    }

    private static void testSetStartWithOffsetGreaterThanTextLength() {
        String text = "abc";
        String dlms = ",";
        StringTokenIterator iterator = new StringTokenIterator(text, dlms);
        try {
            iterator.setStart(10); // beyond text length
            System.err.println("FAIL: Expected IndexOutOfBoundsException not thrown");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("PASS: setStart with offset greater than text length throws IndexOutOfBoundsException");
        } catch (Exception e) {
            System.err.println("FAIL: Unexpected exception: " + e);
        }
    }

    private static void testSetStartWithNegativeOffset() {
        String text = "abc";
        String dlms = ",";
        StringTokenIterator iterator = new StringTokenIterator(text, dlms);
        try {
            iterator.setStart(-1); // negative offset
            String current = iterator.current();
            if (current != null && current.equals("abc")) {
                System.err.println("FAIL: Negative offset should not set token without exception");
            } else {
                System.out.println("PASS: setStart with negative offset sets token appropriately or throws exception")
            }
        } catch (IndexOutOfBoundsException e) {
            System.out.println("PASS: setStart with negative offset throws IndexOutOfBoundsException");
        } catch (Exception e) {
            System.err.println("FAIL: Unexpected exception: " + e);
        }
    }

    private static void testSetStartWithNegativeOffsetAndDelimiter() {
        String text = "abc,def";
        String dlms = ",";
        StringTokenIterator iterator = new StringTokenIterator(text, dlms);
        try {
            iterator.setStart(-5); // negative offset
            String current = iterator.current();
            if (current != null && current.equals("abc,def")) {
                System.err.println("FAIL: Negative offset should not set token without exception");
            } else {
                System.out.println("PASS: setStart with negative offset sets token appropriately or throws exception")
            }
        } catch (IndexOutOfBoundsException e) {
            System.out.println("PASS: setStart with negative offset throws IndexOutOfBoundsException");
        } catch (Exception e) {
            System.err.println("FAIL: Unexpected exception: " + e);
        }
    }
}
