/* @test    @summary Test the read() method of java.util.Base64 class    @run main Base64WitupTest */

import java.io.IOException;
import java.util.Base64;

public class Base64WitupTest {

    public static void main(String[] args) throws IOException {
        testReadReturnsByteValue();
        testReadReturnsMinusOneAtEnd();
    }

    private static void testReadReturnsByteValue() throws IOException {
        Base64 base64 = Base64.getEncoder();
        byte[] data = "test".getBytes(StandardCharsets.UTF_8);
        byte[] encoded = base64.encode(data);
        String encodedStr = new String(encoded, StandardCharsets.US_ASCII);
        InputStream is = new java.io.ByteArrayInputStream(encoded);
        java.util.Base64.Decoder decoder = Base64.getDecoder();
        byte[] decodedBytes = decoder.decode(encodedStr);
        InputStream inputStream = new java.io.ByteArrayInputStream(decodedBytes);
        java.util.Base64.Base64InputStream bis = new java.util.Base64.Base64InputStream(inputStream, java.util.Base64.getDecoder());
        int result = bis.read();
        assert result != -1 : "read() should not return -1 for non-empty stream";
        assert (result & 0xff) == (decodedBytes[0] & 0xff) : "read() should return first byte of decoded data";
        bis.close();
    }

    private static void testReadReturnsMinusOneAtEnd() throws IOException {
        byte[] emptyData = new byte[0];
        java.io.ByteArrayInputStream emptyStream = new java.io.ByteArrayInputStream(emptyData);
        java.util.Base64.Base64InputStream bis = new java.util.Base64.Base64InputStream(emptyStream, java.util.Base64.getDecoder());
        int result = bis.read();
        assert result == -1 : "read() should return -1 for empty stream";
        bis.close();
    }
}
