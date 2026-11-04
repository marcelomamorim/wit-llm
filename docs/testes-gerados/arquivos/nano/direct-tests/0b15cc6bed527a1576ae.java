/* @test    @summary Test the encode method of java.util.Base64 for normal and edge cases    @run main Base64WitupTest */

import java.util.Base64;

public class Base64WitupTest {

    public static void main(String[] args) {
        testEncodeNormal();
        testEncodeEmpty();
        testEncodeSmallInput();
        testEncodeLargeInput();
        testEncodeNullInput();
    }

    static void testEncodeNormal() {
        byte[] input = "hello".getBytes(StandardCharsets.UTF_8);
        byte[] output = new byte[Base64.getEncoder().encode(input, new byte[10])];
        int encodedLength = Base64.getEncoder().encode(input, output);
        String encodedStr = new String(output, 0, encodedLength, StandardCharsets.US_ASCII);
        assert "aGVsbG8=".equals(encodedStr) : "Normal encoding failed";
    }

    static void testEncodeEmpty() {
        byte[] input = new byte[0];
        byte[] output = new byte[0];
        int result = Base64.getEncoder().encode(input, output);
        assert result == 0 : "Encoding empty array should return 0";
    }

    static void testEncodeSmallInput() {
        byte[] input = {1, 2, 3};
        byte[] output = new byte[10];
        int len = Base64.getEncoder().encode(input, output);
        String encoded = new String(output, 0, len, StandardCharsets.US_ASCII);
        assert "AQID".equals(encoded) : "Small input encoding failed";
    }

    static void testEncodeLargeInput() {
        byte[] input = new byte[1000];
        for (int i = 0; i < input.length; i++) {
            input[i] = (byte) (i % 256);
        }
        byte[] output = new byte[1500];
        int len = Base64.getEncoder().encode(input, output);
        assert len > 0 : "Large input encoding returned non-positive length";
    }

    static void testEncodeNullInput() {
        try {
            Base64.getEncoder().encode(null, new byte[10]);
            assert false : "Expected NullPointerException for null src";
        } catch (NullPointerException e) {
            // expected
        }
        try {
            Base64.getEncoder().encode(new byte[10], null);
            assert false : "Expected NullPointerException for null dst";
        } catch (NullPointerException e) {
            // expected
        }
    }
}
