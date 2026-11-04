/*
 * @test
 * @summary Test ReplaceableUCharacterIterator(String) constructor for null argument and normal usage
 * @run main ReplaceableUCharacterIteratorWitupTest
 */

public class ReplaceableUCharacterIteratorWitupTest {
    public static void main(String[] args) {
        testConstructorWithNull();
        testConstructorWithValidString();
    }

    private static void testConstructorWithNull() {
        try {
            new sun.text.normalizer.ReplaceableUCharacterIterator((String) null);
            throw new RuntimeException("Expected IllegalArgumentException not thrown");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }

    private static void testConstructorWithValidString() {
        String testStr = "abc";
        sun.text.normalizer.ReplaceableUCharacterIterator it = new sun.text.normalizer.ReplaceableUCharacterIterator(testStr);
        int length = it.getLength();
        if (length != testStr.length()) {
            throw new RuntimeException("Length mismatch: expected " + testStr.length() + " but got " + length);
        }
        int currentChar = it.current();
        if (currentChar != testStr.charAt(0)) {
            throw new RuntimeException("Current char mismatch: expected '" + testStr.charAt(0) + "' but got '" + (char) currentChar + "'");
        }
    }
}
