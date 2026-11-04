/*
 * @test
 * @summary Verifies String.subSequence returns the same content as substring and rejects invalid bounds
 * @run main StringWitupTest
 */

public class StringWitupTest {
    public static void main(String[] args) {
        String s = "abcdef";

        check("bcd".equals(s.subSequence(1, 4).toString()), "middle range");
        check("".equals(s.subSequence(2, 2).toString()), "empty range");
        check(s.equals(s.subSequence(0, s.length()).toString()), "full range");
        check(s.substring(3, 5).equals(s.subSequence(3, 5).toString()), "matches substring");

        CharSequence cs = s.subSequence(0, 1);
        check(cs.length() == 1 && cs.charAt(0) == 'a', "length and charAt");

        boolean thrown = false;
        try {
            s.subSequence(4, 2);
        } catch (StringIndexOutOfBoundsException e) {
            thrown = true;
        }
        check(thrown, "begin greater than end must throw");

        thrown = false;
        try {
            s.subSequence(-1, 2);
        } catch (StringIndexOutOfBoundsException e) {
            thrown = true;
        }
        check(thrown, "negative begin must throw");

        thrown = false;
        try {
            s.subSequence(0, 7);
        } catch (StringIndexOutOfBoundsException e) {
            thrown = true;
        }
        check(thrown, "end beyond length must throw");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException("Failed: " + message);
        }
    }
}
