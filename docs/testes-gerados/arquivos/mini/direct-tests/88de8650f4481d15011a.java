/*
 * @test
 * @summary Test String.subSequence(int, int) method
 * @run main StringWitupTest
 */

public class StringWitupTest {
    public static void main(String[] args) {
        testSubSequenceBasic();
        testSubSequenceFullString();
        testSubSequenceEmpty();
        testSubSequenceSameIndices();
        testSubSequenceInvalidIndices();
    }

    private static void testSubSequenceBasic() {
        String s = "Hello, World!";
        CharSequence cs = s.subSequence(7, 12);
        if (!"World".contentEquals(cs)) {
            throw new RuntimeException("Expected 'World' but got '" + cs + "'");
        }
    }

    private static void testSubSequenceFullString() {
        String s = "abc";
        CharSequence cs = s.subSequence(0, s.length());
        if (!s.contentEquals(cs)) {
            throw new RuntimeException("Expected full string but got '" + cs + "'");
        }
    }

    private static void testSubSequenceEmpty() {
        String s = "abc";
        CharSequence cs = s.subSequence(1, 1);
        if (!"".contentEquals(cs)) {
            throw new RuntimeException("Expected empty string but got '" + cs + "'");
        }
    }

    private static void testSubSequenceSameIndices() {
        String s = "test";
        CharSequence cs = s.subSequence(2, 2);
        if (!"".contentEquals(cs)) {
            throw new RuntimeException("Expected empty string but got '" + cs + "'");
        }
    }

    private static void testSubSequenceInvalidIndices() {
        String s = "abc";
        boolean npeThrown = false;
        boolean ioobeThrown = false;
        try {
            s.subSequence(-1, 2);
        } catch (IndexOutOfBoundsException e) {
            ioobeThrown = true;
        }
        try {
            s.subSequence(1, 5);
        } catch (IndexOutOfBoundsException e) {
            ioobeThrown = ioobeThrown && true;
        }
        try {
            s.subSequence(2, 1);
        } catch (IndexOutOfBoundsException e) {
            ioobeThrown = ioobeThrown && true;
        }
        if (!ioobeThrown) {
            throw new RuntimeException("Expected IndexOutOfBoundsException for invalid indices");
        }
    }
}
