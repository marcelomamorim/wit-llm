/*
 * @test
 * @summary Verifies Base64.Decoder.wrap(InputStream) decoding for basic, URL, and MIME decoders and null rejection
 * @run main Base64WitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

public class Base64WitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static byte[] readAll(InputStream in) throws IOException {
        ByteArrayOutputStream bo = new ByteArrayOutputStream();
        int b;
        while ((b = in.read()) != -1) {
            bo.write(b);
        }
        return bo.toByteArray();
    }

    static void testBasicWrap() throws IOException {
        InputStream in = Base64.getDecoder().wrap(
            new ByteArrayInputStream("SGVsbG8=".getBytes("US-ASCII")));
        check(in != null, "wrap returned null");
        byte[] out = readAll(in);
        check(new String(out, "US-ASCII").equals("Hello"),
              "basic decode mismatch: " + new String(out, "US-ASCII"));
    }

    static void testUrlWrap() throws IOException {
        InputStream in = Base64.getUrlDecoder().wrap(
            new ByteArrayInputStream("-_8=".getBytes("US-ASCII")));
        byte[] out = readAll(in);
        check(out.length == 2, "url decode length: " + out.length);
        check((out[0] & 0xFF) == 0xFB, "url decode byte 0");
        check((out[1] & 0xFF) == 0xFF, "url decode byte 1");
    }

    static void testMimeWrapIgnoresLineSeparators() throws IOException {
        InputStream in = Base64.getMimeDecoder().wrap(
            new ByteArrayInputStream("SGVs\r\nbG8=".getBytes("US-ASCII")));
        byte[] out = readAll(in);
        check(new String(out, "US-ASCII").equals("Hello"),
              "mime decode mismatch: " + new String(out, "US-ASCII"));
    }

    static void testNullRejected() {
        boolean thrown = false;
        try {
            Base64.getDecoder().wrap(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "wrap(null) did not throw NullPointerException");
    }

    public static void main(String[] args) throws Exception {
        testBasicWrap();
        testUrlWrap();
        testMimeWrapIgnoresLineSeparators();
        testNullRejected();
    }
}
