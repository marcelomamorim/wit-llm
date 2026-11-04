/*
 * @test
 * @summary Test ReplaceableUCharacterIterator(String) constructor behavior
 * @run main ReplaceableUCharacterIteratorWitupTest
 */

public class ReplaceableUCharacterIteratorWitupTest {
    public static void main(String[] args) {
        testConstructorWithValidString();
        testConstructorWithNullString();
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
            throw new RuntimeException("Current char mismatch: expected " + (int)testStr.charAt(0) + " but got " + currentChar);
        }
    }

    private static void testConstructorWithNullString() {
        try {
            new sun.text.normalizer.ReplaceableUCharacterIterator((String) null);
            throw new RuntimeException("Expected IllegalArgumentException for null input string");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
