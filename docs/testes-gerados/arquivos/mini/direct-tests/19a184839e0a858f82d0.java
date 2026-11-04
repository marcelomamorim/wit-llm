/*
 * @test
 * @summary Test sun.util.locale.StringTokenIterator.first() method behavior
 * @run main StringTokenIteratorWitupTest
 */
public class StringTokenIteratorWitupTest {
    public static void main(String[] args) {
        testFirstWithSingleCharDelimiter();
        testFirstWithMultipleCharDelimiters();
        testFirstWithEmptyText();
        testFirstWithTextWithoutDelimiter();
        testSetStartThrowsIndexOutOfBounds();
    }

    private static void testFirstWithSingleCharDelimiter() {
        String text = "a,b,c";
        StringTokenIterator it = new StringTokenIterator(text, ",");
        String first = it.first();
        if (!"a".equals(first)) {
            throw new RuntimeException("Expected first token 'a', got: " + first);
        }
        if (it.currentStart() != 0) {
            throw new RuntimeException("Expected currentStart 0, got: " + it.currentStart());
        }
        if (it.isDone()) {
            throw new RuntimeException("Expected isDone false at start");
        }
    }

    private static void testFirstWithMultipleCharDelimiters() {
        String text = "a,b;c";
        StringTokenIterator it = new StringTokenIterator(text, ",;");
        String first = it.first();
        if (!"a".equals(first)) {
            throw new RuntimeException("Expected first token 'a', got: " + first);
        }
        if (it.currentStart() != 0) {
            throw new RuntimeException("Expected currentStart 0, got: " + it.currentStart());
        }
        if (it.isDone()) {
            throw new RuntimeException("Expected isDone false at start");
        }
    }

    private static void testFirstWithEmptyText() {
        String text = "";
        StringTokenIterator it = new StringTokenIterator(text, ",");
        String first = it.first();
        if (!"".equals(first)) {
            throw new RuntimeException("Expected first token empty string, got: " + first);
        }
        if (it.currentStart() != 0) {
            throw new RuntimeException("Expected currentStart 0, got: " + it.currentStart());
        }
        if (it.isDone()) {
            throw new RuntimeException("Expected isDone false at start with empty text");
        }
    }

    private static void testFirstWithTextWithoutDelimiter() {
        String text = "abc";
        StringTokenIterator it = new StringTokenIterator(text, ",");
        String first = it.first();
        if (!"abc".equals(first)) {
            throw new RuntimeException("Expected first token 'abc', got: " + first);
        }
        if (it.currentStart() != 0) {
            throw new RuntimeException("Expected currentStart 0, got: " + it.currentStart());
        }
        if (it.isDone()) {
            throw new RuntimeException("Expected isDone false at start");
        }
    }

    private static void testSetStartThrowsIndexOutOfBounds() {
        String text = "a,b,c";
        StringTokenIterator it = new StringTokenIterator(text, ",");
        boolean thrown = false;
        try {
            it.setStart(text.length() + 1);
        } catch (IndexOutOfBoundsException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected IndexOutOfBoundsException when setStart with offset > text.length()");
        }
    }
}
