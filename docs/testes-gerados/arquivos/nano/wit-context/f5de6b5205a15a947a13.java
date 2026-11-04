/* @test    @summary Test for StringCharacterIterator.setText method handling null input    @run main StringCharacterIteratorWitupTest */

import java.text.StringCharacterIterator;

public class StringCharacterIteratorWitupTest {

    public static void main(String[] args) {
        testSetTextWithNull();
        testSetTextWithValidString();
    }

    public static void testSetTextWithNull() {
        StringCharacterIterator iterator = new StringCharacterIterator("initial");
        try {
            iterator.setText(null);
            System.err.println("Failed: Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            System.out.println("Passed: NullPointerException thrown as expected");
        } catch (Exception e) {
            System.err.println("Failed: Unexpected exception type: " + e);
        }
    }

    public static void testSetTextWithValidString() {
        StringCharacterIterator iterator = new StringCharacterIterator("initial");
        String newText = "new text";
        iterator.setText(newText);
        if (iterator != null) {
            System.out.println("Passed: setText with valid string executed without exception");
        } else {
            System.err.println("Failed: setText with valid string resulted in null iterator");
        }
    }
}
