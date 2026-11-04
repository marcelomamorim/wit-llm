/*
 * @test
 * @summary Verifies that Base64 Encoder.wrap encodes bytes written to the returned stream
 * @run main Base64WitupTest
 */

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class Base64WitupTest {

    public static void main(String[] args) throws Exception {
        testBasicWrapEncodesOnClose();
        testUrlWrapUsesUrlSafeAlphabet();
        testChunkedWritesMatchSingleWrite();
        testMimeWrapMatchesEncodeToString();
        testNullArgumentThrowsNpe();
        System.out.println("PASSED");
    }

    static String ascii(ByteArrayOutputStream out) {
        return new String(out.toByteArray(), StandardCharsets.US_ASCII);
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testBasicWrapEncodesOnClose() throws Exception {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        OutputStream os = Base64.getEncoder().wrap(bos);
        os.write("foobar".getBytes(StandardCharsets.US_ASCII));
        os.close();
        check("Zm9vYmFy".equals(ascii(bos)),
              "basic wrap output mismatch: " + ascii(bos));
    }

    static void testUrlWrapUsesUrlSafeAlphabet() throws Exception {
        byte[] data = new byte[] { (byte) 0xfb, (byte) 0xff };
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        OutputStream os = Base64.getUrlEncoder().wrap(bos);
        os.write(data);
        os.close();
        check("-_8=".equals(ascii(bos)),
              "url wrap output mismatch: " + ascii(bos));
    }

    static void testChunkedWritesMatchSingleWrite() throws Exception {
        byte[] data = "foobar".getBytes(StandardCharsets.US_ASCII);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        OutputStream os = Base64.getEncoder().wrap(bos);
        os.write(data, 0, 2);
        os.write(data, 2, 4);
        os.close();
        check("Zm9vYmFy".equals(ascii(bos)),
              "chunked wrap output mismatch: " + ascii(bos));
    }

    static void testMimeWrapMatchesEncodeToString() throws Exception {
        byte[] data = new byte[100];
        for (int i = 0; i < data.length; i++) {
            data[i] = (byte) i;
        }
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        OutputStream os = Base64.getMimeEncoder().wrap(bos);
        os.write(data);
        os.close();
        String expected = Base64.getMimeEncoder().encodeToString(data);
        check(expected.equals(ascii(bos)),
              "mime wrap output differs from encodeToString");
    }

    static void testNullArgumentThrowsNpe() {
        boolean thrown = false;
        try {
            Base64.getEncoder().wrap(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "wrap(null) should throw NullPointerException");
    }
}
