/*
 * @test
 * @summary Test for String.getBytes(Charset charset) method, focusing on null input.
 * @run main StringWitupTest
 */

import java.lang.String;
import java.nio.charset.Charset;

public class StringWitupTest {

    public static void main(String[] args) {
        testGetBytesWithNullCharset();
        testGetBytesWithValidCharset();
    }

    public static void testGetBytesWithNullCharset() {
        String str = "test";
        try {
            str.getBytes((Charset) null);
            System.out.println("FAIL: Expected NullPointerException for null charset");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown as expected");
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception: " + e);
        }
    }

    public static void testGetBytesWithValidCharset() {
        String str = "abc";
        try {
            byte[] bytes = str.getBytes(Charset.forName("UTF-8"));
            if (bytes != null && bytes.length == 3) {
                System.out.println("PASS: getBytes with UTF-8 succeeded");
            } else {
                System.out.println("FAIL: getBytes with UTF-8 returned unexpected result");
            }
        } catch (Exception e) {
            System.out.println("FAIL: Exception during getBytes with valid charset: " + e);
        }
    }
}
