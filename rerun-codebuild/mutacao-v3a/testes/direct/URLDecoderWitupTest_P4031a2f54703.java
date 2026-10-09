/* @test    @summary Test URLDecoder.decode with various inputs   @run main URLDecoderWitupTest_P4031a2f54703 */

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

public class URLDecoderWitupTest_P4031a2f54703 {

    public static void main(String[] args) {
        testDecodeSimplePlus();
        testDecodePercentHex();
        testDecodeMultiplePercentSequences();
        testDecodeIncompletePercentSequence();
        testDecodeIllegalHex();
        testDecodeNoChange();
    }

    static void testDecodeSimplePlus() {
        String input = "hello+world";
        String expected = "hello world";
        String result = URLDecoder.decode(input, StandardCharsets.UTF_8);
        if (!result.equals(expected)) {
            throw new AssertionError("Failed testDecodeSimplePlus: expected '" + expected + "', got '" + result + "'");
        }
    }

    static void testDecodePercentHex() {
        String input = "hello%20world";
        String expected = "hello world";
        String result = URLDecoder.decode(input, StandardCharsets.UTF_8);
        if (!result.equals(expected)) {
            throw new AssertionError("Failed testDecodePercentHex: expected '" + expected + "', got '" + result + "'");
        }
    }

    static void testDecodeMultiplePercentSequences() {
        String input = "%48%65%6C%6C%6F";
        String expected = "Hello";
        String result = URLDecoder.decode(input, StandardCharsets.UTF_8);
        if (!result.equals(expected)) {
            throw new AssertionError("Failed testDecodeMultiplePercentSequences: expected '" + expected + "', got '" + result + "'");
        }
    }

    static void testDecodeIncompletePercentSequence() {
        String input = "hello%2";
        try {
            URLDecoder.decode(input, StandardCharsets.UTF_8);
            throw new AssertionError("Expected IllegalArgumentException for incomplete percent sequence");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    static void testDecodeIllegalHex() {
        String input = "hello%ZZ";
        try {
            URLDecoder.decode(input, StandardCharsets.UTF_8);
            throw new AssertionError("Expected IllegalArgumentException for illegal hex characters");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    static void testDecodeNoChange() {
        String input = "plaintext";
        String result = URLDecoder.decode(input, StandardCharsets.UTF_8);
        if (!result.equals(input)) {
            throw new AssertionError("Failed testDecodeNoChange: expected '" + input + "', got '" + result + "'");
        }
    }
}
