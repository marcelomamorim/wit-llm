/*
 * @test
 * @summary Test java.lang.String.charAt(int) for valid and invalid indices
 * @run main StringWitupTest
 */

public class StringWitupTest {
    public static void main(String[] args) {
        testCharAtValid();
        testCharAtInvalid();
    }

    private static void testCharAtValid() {
        String s = "abc";
        // Test valid indices
        if (s.charAt(0) != 'a') throw new RuntimeException("charAt(0) failed");
        if (s.charAt(1) != 'b') throw new RuntimeException("charAt(1) failed");
        if (s.charAt(2) != 'c') throw new RuntimeException("charAt(2) failed");
    }

    private static void testCharAtInvalid() {
        String s = "abc";
        // Test index < 0 returns StringIndexOutOfBoundsException
        try {
            s.charAt(-1);
            throw new RuntimeException("Expected StringIndexOutOfBoundsException for index -1");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }

        // Test index == length returns StringIndexOutOfBoundsException
        try {
            s.charAt(s.length());
            throw new RuntimeException("Expected StringIndexOutOfBoundsException for index length");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }

        // Test index > length returns StringIndexOutOfBoundsException
        try {
            s.charAt(s.length() + 1);
            throw new RuntimeException("Expected StringIndexOutOfBoundsException for index > length");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
    }
}
