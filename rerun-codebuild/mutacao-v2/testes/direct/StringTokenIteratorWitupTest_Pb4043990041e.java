/*
 * @test
 * @summary Test sun.util.locale.StringTokenIterator.setStart(int) method
 * @run main StringTokenIteratorWitupTest_Pb4043990041e
 */

public class StringTokenIteratorWitupTest_Pb4043990041e {
    public static void main(String[] args) {
        testSetStartWithinBounds();
        testSetStartAtTextLength();
        testSetStartThrowsIndexOutOfBoundsException();
    }

    private static void testSetStartWithinBounds() {
        String text = "a,b,c";
        StringTokenIterator it = new StringTokenIterator(text, ",");
        it.setStart(2); // start at 'b'
        String token = it.current();
        int start = it.currentStart();
        int end = it.currentEnd();
        boolean done = it.isDone();

        if (!"b".equals(token)) {
            throw new RuntimeException("Expected token 'b' but got '" + token + "'");
        }
        if (start != 2) {
            throw new RuntimeException("Expected start 2 but got " + start);
        }
        if (end != 3) {
            throw new RuntimeException("Expected end 3 but got " + end);
        }
        if (done) {
            throw new RuntimeException("Expected done false but got true");
        }
    }

    private static void testSetStartAtTextLength() {
        String text = "abc";
        StringTokenIterator it = new StringTokenIterator(text, ",");
        it.setStart(text.length()); // start at end of text
        String token = it.current();
        int start = it.currentStart();
        int end = it.currentEnd();
        boolean done = it.isDone();

        if (!"".equals(token)) {
            throw new RuntimeException("Expected empty token but got '" + token + "'");
        }
        if (start != text.length()) {
            throw new RuntimeException("Expected start " + text.length() + " but got " + start);
        }
        if (end != text.length()) {
            throw new RuntimeException("Expected end " + text.length() + " but got " + end);
        }
        if (done) {
            throw new RuntimeException("Expected done false but got true");
        }
    }

    private static void testSetStartThrowsIndexOutOfBoundsException() {
        String text = "abc";
        StringTokenIterator it = new StringTokenIterator(text, ",");
        boolean thrown = false;
        try {
            it.setStart(text.length() + 1); // offset > text.length()
        } catch (IndexOutOfBoundsException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected IndexOutOfBoundsException for offset > text.length()");
        }
    }
}
