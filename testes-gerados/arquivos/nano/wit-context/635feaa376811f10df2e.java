/* @test    @summary Test for URLDecoder.decode(String s, String enc) handling empty encoding string    @run main URLDecoderWitupTest */

import java.net.URLDecoder;
import java.io.UnsupportedEncodingException;

public class URLDecoderWitupTest {

    public static void main(String[] args) {
        testDecodeWithEmptyEncoding();
        testDecodeWithValidEncoding();
        testDecodeWithInvalidEncoding();
    }

    private static void testDecodeWithEmptyEncoding() {
        String input = "hello%20world";
        String encoding = "";
        try {
            URLDecoder.decode(input, encoding);
            System.out.println("PASS: decode with empty encoding should throw UnsupportedEncodingException");
        } catch (UnsupportedEncodingException e) {
            System.out.println("PASS: Caught expected UnsupportedEncodingException for empty encoding");
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception for empty encoding: " + e);
        }
    }

    private static void testDecodeWithValidEncoding() {
        String input = "hello%20world";
        String encoding = "UTF-8";
        try {
            String result = URLDecoder.decode(input, encoding);
            if ("hello world".equals(result)) {
                System.out.println("PASS: decode with valid encoding returns expected result");
            } else {
                System.out.println("FAIL: decode with valid encoding returned: " + result);
            }
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception for valid encoding: " + e);
        }
    }

    private static void testDecodeWithInvalidEncoding() {
        String input = "hello%20world";
        String encoding = "Invalid-Charset";
        try {
            URLDecoder.decode(input, encoding);
            System.out.println("FAIL: decode with invalid encoding should throw UnsupportedEncodingException");
        } catch (UnsupportedEncodingException e) {
            System.out.println("PASS: Caught expected UnsupportedEncodingException for invalid encoding");
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception for invalid encoding: " + e);
        }
    }
}
