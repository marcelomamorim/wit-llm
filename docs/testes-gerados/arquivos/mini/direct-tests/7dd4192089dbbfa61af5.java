/*
 * @test
 * @summary Test InflaterOutputStream(OutputStream out, Inflater infl) constructor behavior
 * @run main InflaterOutputStreamWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.Inflater;
import java.util.zip.InflaterOutputStream;

public class InflaterOutputStreamWitupTest {
    public static void main(String[] args) throws IOException {
        testConstructorWithValidArgs();
        testConstructorWithNullOutputStream();
        testConstructorWithNullInflater();
    }

    private static void testConstructorWithValidArgs() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Inflater inflater = new Inflater();
        InflaterOutputStream ios = new InflaterOutputStream(baos, inflater);
        // Write some data to ensure stream is open and writable
        ios.write(new byte[] {0, 1, 2});
        ios.close();
        // No exception means success
    }

    private static void testConstructorWithNullOutputStream() {
        Inflater inflater = new Inflater();
        try {
            new InflaterOutputStream(null, inflater);
            throw new RuntimeException("Expected NullPointerException for null OutputStream");
        } catch (NullPointerException e) {
            // Expected
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception for null OutputStream", e);
        }
    }

    private static void testConstructorWithNullInflater() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            new InflaterOutputStream(baos, null);
            throw new RuntimeException("Expected NullPointerException for null Inflater");
        } catch (NullPointerException e) {
            // Expected
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception for null Inflater", e);
        }
    }
}
