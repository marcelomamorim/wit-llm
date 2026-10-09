/* @test    @summary Test for StringTokenIterator.setStart(int) to verify behavior with various offsets    @run main StringTokenIteratorWitupTest_Pa3d12df09fee */

import sun.util.locale.StringTokenIterator;

public class StringTokenIteratorWitupTest_Pa3d12df09fee {

    public static void main(String[] args) {
        testSetStartWithValidOffset();
        testSetStartWithOffsetGreaterThanTextLength();
        testSetStartWithNegativeOffset();
        testSetStartWithOffsetEqualToTextLength();
    }

    private static void testSetStartWithValidOffset() {
        String text = "abc,def,ghi";
        String dlms = ",";
        StringTokenIterator iterator = new StringTokenIterator(text, dlms);
        try {
            iterator.setStart(4); // valid offset
            String currentToken = iterator.current();
            if (!"def".equals(currentToken)) {
                throw new AssertionError("Expected token 'def', but got: " + currentToken);
            }
            System.out.println("testSetStartWithValidOffset passed.");
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in testSetStartWithValidOffset", e);
        }
    }

    private static void testSetStartWithOffsetGreaterThanTextLength() {
        String text = "abc";
        String dlms = ",";
        StringTokenIterator iterator = new StringTokenIterator(text, dlms);
        boolean exceptionThrown = false;
        try {
            iterator.setStart(10); // offset greater than text length
        } catch (IndexOutOfBoundsException e) {
            exceptionThrown = true;
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type", e);
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected IndexOutOfBoundsException for offset > text.length()");
        }
        System.out.println("testSetStartWithOffsetGreaterThanTextLength passed.");
    }

    private static void testSetStartWithNegativeOffset() {
        String text = "abc";
        String dlms = ",";
        StringTokenIterator iterator = new StringTokenIterator(text, dlms);
        boolean exceptionThrown = false;
        try {
            iterator.setStart(-1); // negative offset
        } catch (IndexOutOfBoundsException e) {
            exceptionThrown = true;
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type", e);
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected IndexOutOfBoundsException for negative offset");
        }
        System.out.println("testSetStartWithNegativeOffset passed.");
    }

    private static void testSetStartWithOffsetEqualToTextLength() {
        String text = "abc";
        String dlms = ",";
        StringTokenIterator iterator = new StringTokenIterator(text, dlms);
        try {
            iterator.setStart(text.length()); // offset equal to text length
            String currentToken = iterator.current();
            if (!"".equals(currentToken)) {
                throw new AssertionError("Expected empty token, but got: " + currentToken);
            }
            System.out.println("testSetStartWithOffsetEqualToTextLength passed.");
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in testSetStartWithOffsetEqualToTextLength", e);
        }
    }
}
