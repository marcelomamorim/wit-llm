/* @test    @summary Test wrapping OutputStream with Base64.wrap() method    @run main Base64WitupTest */

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.Base64;

public class Base64WitupTest {

    public static void main(String[] args) throws Exception {
        testWrapMethod();
    }

    public static void testWrapMethod() throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        OutputStream wrappedStream = Base64.getEncoder().wrap(baos);
        String testString = "TestData";
        wrappedStream.write(testString.getBytes(StandardCharsets.UTF_8));
        wrappedStream.close();
        String encoded = baos.toString("UTF-8");
        if (encoded == null || encoded.isEmpty()) {
            throw new AssertionError("Encoded string should not be null or empty");
        }
        // Decode to verify correctness
        byte[] decodedBytes = Base64.getDecoder().decode(encoded);
        String decodedString = new String(decodedBytes, StandardCharsets.UTF_8);
        if (!testString.equals(decodedString)) {
            throw new AssertionError("Decoded string does not match original. Expected: " + testString + ", but got: " + decodedString);
        }
    }
}
