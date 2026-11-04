/* @test    @summary Test constructor ZipInputStream(InputStream, Charset) for null arguments and correct initialization    @run main ZipInputStreamWitupTest */

import java.io.InputStream;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.zip.ZipInputStream;

public class ZipInputStreamWitupTest {

    public static void main(String[] args) {
        testConstructorWithNullInputStream();
        testConstructorWithNullCharset();
        testConstructorWithValidArguments();
    }

    static void testConstructorWithNullInputStream() {
        try {
            new ZipInputStream(null, StandardCharsets.UTF_8);
            throw new AssertionError("Expected NullPointerException for null InputStream");
        } catch (NullPointerException e) {
            // expected
        }
    }

    static void testConstructorWithNullCharset() {
        InputStream in = new ByteArrayInputStream(new byte[0]);
        try {
            new ZipInputStream(in, null);
            throw new AssertionError("Expected NullPointerException for null Charset");
        } catch (NullPointerException e) {
            // expected
        }
    }

    static void testConstructorWithValidArguments() {
        byte[] data = {};
        InputStream in = new ByteArrayInputStream(data);
        ZipInputStream zis = new ZipInputStream(in, StandardCharsets.UTF_8);
        if (zis == null) {
            throw new AssertionError("ZipInputStream instance should not be null");
        }
        // Additional checks could include verifying internal state if accessible,
        // but for this test, instantiation success suffices.
    }
}
