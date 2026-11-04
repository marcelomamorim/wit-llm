/*
 * @test
 * @summary Test sun.util.locale.StringTokenIterator setText method behavior
 * @run main StringTokenIteratorWitupTest
 */
public class StringTokenIteratorWitupTest {
    public static void main(String[] args) {
        testSetTextWithSimpleDelimiter();
        testSetTextWithMultipleDelimiters();
        testSetTextEmptyString();
        testSetTextResetIteration();
    }

    private static void testSetTextWithSimpleDelimiter() {
        StringTokenIterator it = new StringTokenIterator("a,b,c", ",");
        String first = it.first();
        if (!"a".equals(first)) {
            throw new RuntimeException("Expected first token 'a', got: " + first);
        }
        it.setText("x,y,z");
        String newFirst = it.first();
        if (!"x".equals(newFirst)) {
            throw new RuntimeException("After setText, expected first token 'x', got: " + newFirst);
        }
    }

    private static void testSetTextWithMultipleDelimiters() {
        StringTokenIterator it = new StringTokenIterator("a|b,c", "|,");
        String first = it.first();
        if (!"a".equals(first)) {
            throw new RuntimeException("Expected first token 'a', got: " + first);
        }
        it.setText("x|y,z");
        String newFirst = it.first();
        if (!"x".equals(newFirst)) {
            throw new RuntimeException("After setText, expected first token 'x', got: " + newFirst);
        }
    }

    private static void testSetTextEmptyString() {
        StringTokenIterator it = new StringTokenIterator("abc", ",");
        it.setText("");
        if (!it.isDone()) {
            throw new RuntimeException("Expected isDone true for empty text after setText");
        }
        if (it.current() == null || !"".equals(it.current())) {
            throw new RuntimeException("Expected current token to be empty string for empty text");
        }
    }

    private static void testSetTextResetIteration() {
        StringTokenIterator it = new StringTokenIterator("a,b,c", ",");
        it.next(); // advance to second token
        it.setText("x,y,z");
        if (it.currentStart() != 0) {
            throw new RuntimeException("Expected currentStart to be reset to 0 after setText");
        }
        if (!"x".equals(it.current())) {
            throw new RuntimeException("Expected current token 'x' after setText, got: " + it.current());
        }
        if (it.isDone()) {
            throw new RuntimeException("Expected isDone false after setText");
        }
    }
}