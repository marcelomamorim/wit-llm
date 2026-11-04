/* @test    @summary Test for Base64.read() method to observe behavior and exception handling.    @run main Base64WitupTest */

import java.util.Base64;
import java.io.IOException;

public class Base64WitupTest {

    public static void main(String[] args) {
        testReadNormal();
        testReadAfterClose();
    }

    static void testReadNormal() {
        Base64.Encoder encoder = Base64.getEncoder();
        String original = "test";
        byte[] encodedBytes = encoder.encode(original.getBytes(StandardCharsets.UTF_8));
        String encoded = new String(encodedBytes, StandardCharsets.UTF_8);
        byte[] decodedBytes = Base64.getDecoder().decode(encoded);
        InputStream is = new java.io.ByteArrayInputStream(decodedBytes);
        try {
            int result = ((java.util.Base64.DecInputStream) is).read();
            System.out.println("Read byte: " + result);
        } catch (IOException e) {
            System.out.println("IOException caught: " + e);
        }
    }

    static void testReadAfterClose() {
        Base64.Encoder encoder = Base64.getEncoder();
        String original = "test";
        byte[] encodedBytes = encoder.encode(original.getBytes(StandardCharsets.UTF_8));
        String encoded = new String(encodedBytes, StandardCharsets.UTF_8);
        byte[] decodedBytes = Base64.getDecoder().decode(encoded);
        java.io.ByteArrayInputStream is = new java.io.ByteArrayInputStream(decodedBytes);
        try {
            is.close();
            int result = ((java.util.Base64.DecInputStream) is).read();
            System.out.println("Read after close: " + result);
        } catch (IOException e) {
            System.out.println("IOException caught: " + e);
        } catch (NullPointerException e) {
            System.out.println("NullPointerException caught: " + e);
        }
    }
}
