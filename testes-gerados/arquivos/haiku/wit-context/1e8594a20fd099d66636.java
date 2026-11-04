/*
 * @test
 * @summary Verify StringLatin1.charAt bounds checking and byte-to-char mapping through the public String.charAt API
 * @run main StringLatin1WitupTest
 */

public class StringLatin1WitupTest {

    public static void main(String[] args) {
        String s = "abc";
        check(s.charAt(0) == 'a');
        check(s.charAt(2) == 'c');

        String high = new String(new char[] { (char) 0xff });
        check(high.length() == 1);
        check(high.charAt(0) == (char) 0xff);

        expectSIOOBE(s, -1);
        expectSIOOBE(s, 3);
        expectSIOOBE(s, 100);
        expectSIOOBE("", 0);
        expectSIOOBE(high, 1);
    }

    static void expectSIOOBE(String s, int index) {
        try {
            char c = s.charAt(index);
            throw new RuntimeException("No exception for index " + index + ", got " + c);
        } catch (StringIndexOutOfBoundsException expected) {
        }
    }

    static void check(boolean condition) {
        if (!condition) {
            throw new RuntimeException("Assertion failed");
        }
    }
}
