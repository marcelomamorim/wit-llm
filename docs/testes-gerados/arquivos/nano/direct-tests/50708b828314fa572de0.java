/*
 * @test
 * @summary Test String.getBytes(Charset charset) for null and valid Charset
 * @run main StringWitupTest
 */

import java.lang.String;
import java.nio.charset.Charset;

public class StringWitupTest {

    public static void main(String[] args) {
        testGetBytesWithNullCharset();
        testGetBytesWithValidCharset();
    }

    static void testGetBytesWithNullCharset() {
        String str = "test";
        try {
            str.getBytes(null);
            System.out.println("Failed: Expected NullPointerException for null charset");
        } catch (NullPointerException e) {
            System.out.println("Passed: NullPointerException thrown for null charset");
        }
    }

    static void testGetBytesWithValidCharset() {
        String str = "abc";
        Charset utf8 = Charset.forName("UTF-8");
        byte[] bytes = str.getBytes(utf8);
        byte[] expected = {97, 98, 99}; // ASCII codes for 'a', 'b', 'c'
        boolean match = java.util.Arrays.equals(bytes, expected);
        if (match) {
            System.out.println("Passed: getBytes with UTF-8 returns expected byte array");
        } else {
            System.out.println("Failed: getBytes with UTF-8 does not return expected byte array");
        }
    }
}
