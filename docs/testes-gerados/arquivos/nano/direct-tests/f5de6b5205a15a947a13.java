/* @test    @summary Test StringCharacterIterator.setText method for normal and null input    @run main StringCharacterIteratorWitupTest */

import java.text.StringCharacterIterator;

public class StringCharacterIteratorWitupTest {

    public static void main(String[] args) {
        testSetTextWithValidString();
        testSetTextWithEmptyString();
        testSetTextWithNull();
    }

    private static void testSetTextWithValidString() {
        StringCharacterIterator iterator = new StringCharacterIterator("initial");
        String newText = "new text";
        iterator.setText(newText);
        assert iterator.text.equals(newText) : "Text should be updated to new text";
        assert iterator.begin == 0 : "Begin should be reset to 0";
        assert iterator.end == newText.length() : "End should be reset to new text length";
        assert iterator.pos == 0 : "Position should be reset to 0";
    }

    private static void testSetTextWithEmptyString() {
        StringCharacterIterator iterator = new StringCharacterIterator("initial");
        String newText = "";
        iterator.setText(newText);
        assert iterator.text.equals(newText) : "Text should be updated to empty string";
        assert iterator.begin == 0 : "Begin should be 0";
        assert iterator.end == 0 : "End should be 0";
        assert iterator.pos == 0 : "Position should be 0";
    }

    private static void testSetTextWithNull() {
        StringCharacterIterator iterator = new StringCharacterIterator("initial");
        boolean exceptionThrown = false;
        try {
            iterator.setText(null);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        assert exceptionThrown : "NullPointerException should be thrown when setting null text";
    }
}
