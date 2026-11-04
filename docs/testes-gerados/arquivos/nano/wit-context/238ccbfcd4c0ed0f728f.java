/*
 * @test
 * @summary Test for ZipOutputStream constructor with null Charset
 * @run main ZipOutputStreamWitupTest
 */

import java.io.OutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.zip.ZipOutputStream;

public class ZipOutputStreamWitupTest {

    public static void main(String[] args) throws IOException {
        testConstructorWithNullCharset();
    }

    public static void testConstructorWithNullCharset() {
        OutputStream dummyOut = new OutputStream() {
            @Override
            public void write(int b) throws IOException {
                // no-op
            }
        };
        try {
            new ZipOutputStream(dummyOut, null);
            // If no exception, test fails
            System.err.println("Expected NullPointerException was not thrown.");
            System.exit(1);
        } catch (NullPointerException e) {
            // Expected exception
            if ("charset is null".equals(e.getMessage())) {
                System.out.println("NullPointerException with expected message thrown as expected.");
            } else {
                System.err.println("NullPointerException thrown, but message was unexpected: " + e.getMessage());
                System.exit(1);
            }
        } catch (Throwable t) {
            System.err.println("Unexpected exception type: " + t);
            System.exit(1);
        }
    }
}
