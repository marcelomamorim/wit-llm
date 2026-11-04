/*
 * @test
 * @summary Test sun.util.locale.StringTokenIterator.setText(String) method
 * @run main StringTokenIteratorWitupTest
 */

public class StringTokenIteratorWitupTest {
    public static void main(String[] args) {
        testSetTextBasic();
        testSetTextEmptyString();
        testSetTextNullBehavior();
    }

    private static void testSetTextBasic() {
        StringTokenIterator it = new StringTokenIterator("a,b,c", ",");
        String firstToken = it.first();
        if (!"a".equals(firstToken)) {
            throw new RuntimeException("Expected first token 'a', got: " + firstToken);
        }
        it.setText("x;y;z");
        String newFirst = it.first();
        if (!"x".equals(newFirst)) {
            throw new RuntimeException("After setText, expected first token 'x', got: " + newFirst);
        }
    }

    private static void testSetTextEmptyString() {
        StringTokenIterator it = new StringTokenIterator("abc", ",");
        it.setText("");
        if (!"".equals(it.first())) {
            throw new RuntimeException("Expected first token to be empty string for empty text");
        }
        if (!it.isDone()) {
            throw new RuntimeException("Expected isDone() true for empty text after first()");
        }
    }

    private static void testSetTextNullBehavior() {
        // The constructor and setText do not allow null text, but no explicit check is present.
        // Passing null will cause NullPointerException on setStart(0) -> nextDelimiter(0) -> text.length()
        // We test that NPE is thrown to confirm current behavior.
        boolean npeThrown = false;
        try {
            StringTokenIterator it = new StringTokenIterator("abc", ",");
            it.setText(null);
        } catch (NullPointerException e) {
            npeThrown = true;
        }
        if (!npeThrown) {
            throw new RuntimeException("Expected NullPointerException when setting null text");
        }
    }
}
