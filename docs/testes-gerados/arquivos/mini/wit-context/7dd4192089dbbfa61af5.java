/*
 * @test
 * @summary Test constructor InflaterOutputStream(OutputStream out, Inflater infl) for null checks
 * @run main InflaterOutputStreamWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.zip.Inflater;
import java.util.zip.InflaterOutputStream;
import java.io.IOException;

public class InflaterOutputStreamWitupTest {
    public static void main(String[] args) throws IOException {
        testNullOutputStream();
        testNullInflater();
        testValidConstruction();
        System.out.println("All tests passed.");
    }

    private static void testNullOutputStream() {
        Inflater infl = new Inflater();
        try {
            new InflaterOutputStream(null, infl);
            throw new AssertionError("Expected NullPointerException for null OutputStream");
        } catch (NullPointerException e) {
            // Expected
        }
    }

    private static void testNullInflater() {
        OutputStream out = new ByteArrayOutputStream();
        try {
            new InflaterOutputStream(out, null);
            throw new AssertionError("Expected NullPointerException for null Inflater");
        } catch (NullPointerException e) {
            // Expected
        }
    }

    private static void testValidConstruction() throws IOException {
        OutputStream out = new ByteArrayOutputStream();
        Inflater infl = new Inflater();
        InflaterOutputStream ios = new InflaterOutputStream(out, infl);
        // Basic sanity check: write and close without exception
        ios.write(new byte[] {0, 1, 2, 3}, 0, 4);
        ios.close();
    }
}
