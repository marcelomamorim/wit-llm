/*
 * @test
 * @summary Test StringCharacterIterator.setText(String) for null input and normal behavior
 * @run main StringCharacterIteratorWitupTest
 */

public class StringCharacterIteratorWitupTest {
    public static void main(String[] args) {
        testSetTextNull();
        testSetTextNormal();
    }

    private static void testSetTextNull() {
        StringCharacterIterator it = new StringCharacterIterator("abc");
        try {
            it.setText(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testSetTextNormal() {
        StringCharacterIterator it = new StringCharacterIterator("initial");
        it.setText("newtext");
        if (!"newtext".equals(it.getText())) {
            throw new RuntimeException("Text not set correctly");
        }
        if (it.getBeginIndex() != 0) {
            throw new RuntimeException("Begin index not reset to 0");
        }
        if (it.getEndIndex() != "newtext".length()) {
            throw new RuntimeException("End index not reset to text length");
        }
        if (it.getIndex() != 0) {
            throw new RuntimeException("Position not reset to 0");
        }
    }
}
