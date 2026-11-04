/*
 * @test
 * @summary Test constructor InflaterOutputStream(OutputStream out) for null argument and normal usage
 * @run main InflaterOutputStreamWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.InflaterOutputStream;
import java.util.zip.Inflater;

public class InflaterOutputStreamWitupTest {
    public static void main(String[] args) throws IOException {
        testNullOutputStream();
        testValidOutputStream();
    }

    private static void testNullOutputStream() {
        try {
            new InflaterOutputStream(null);
            throw new RuntimeException("Expected NullPointerException for null OutputStream");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }

    private static void testValidOutputStream() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        InflaterOutputStream ios = new InflaterOutputStream(baos);
        // The constructor uses a default Inflater and sets usesDefaultInflater to true
        // We test that the stream is open and writable by writing a byte array
        byte[] data = new byte[] {0};
        ios.write(data);
        ios.flush();
        ios.close();
        // After close, writing should throw IOException
        try {
            ios.write(1);
            throw new RuntimeException("Expected IOException after close");
        } catch (IOException expected) {
            // Expected exception
        }
    }
}
