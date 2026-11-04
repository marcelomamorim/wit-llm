/*
 * @test
 * @summary Tests for ZipInputStream constructor with null parameters
 * @run main ZipInputStreamWitupTest
 */

import java.io.InputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.zip.ZipInputStream;

public class ZipInputStreamWitupTest {

    public static void main(String[] args) {
        testConstructorWithNullInputStream();
        testConstructorWithNullCharset();
    }

    public static void testConstructorWithNullInputStream() {
        try {
            new ZipInputStream(null, StandardCharsets.UTF_8);
            System.err.println("Failed: Expected NullPointerException for null InputStream");
        } catch (NullPointerException e) {
            // Expected exception
        } catch (Exception e) {
            System.err.println("Failed: Unexpected exception type: " + e);
        }
    }

    public static void testConstructorWithNullCharset() {
        InputStream dummyStream = new InputStream() {
            @Override
            public int read() throws IOException {
                return -1;
            }
        };
        try {
            new ZipInputStream(dummyStream, null);
            System.err.println("Failed: Expected NullPointerException for null Charset");
        } catch (NullPointerException e) {
            // Expected exception
        } catch (Exception e) {
            System.err.println("Failed: Unexpected exception type: " + e);
        }
    }
}
