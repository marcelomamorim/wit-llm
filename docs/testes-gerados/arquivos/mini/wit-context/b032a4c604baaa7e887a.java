/*
 * @test
 * @summary Test java.util.zip.InflaterInputStream.skip(long) for exceptions and normal behavior
 * @run main InflaterInputStreamWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;

public class InflaterInputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        testSkipNegativeThrows();
        testSkipOnClosedStreamThrows();
        testSkipNormalBehavior();
    }

    private static void testSkipNegativeThrows() {
        InflaterInputStream iis = new InflaterInputStream(new ByteArrayInputStream(new byte[0]));
        try {
            iis.skip(-1L);
            throw new RuntimeException("Expected IllegalArgumentException for negative skip length");
        } catch (IllegalArgumentException e) {
            // expected
        } catch (IOException e) {
            throw new RuntimeException("Unexpected IOException for negative skip length", e);
        }
    }

    private static void testSkipOnClosedStreamThrows() throws IOException {
        InflaterInputStream iis = new InflaterInputStream(new ByteArrayInputStream(new byte[0]));
        iis.close();
        try {
            iis.skip(0L);
            throw new RuntimeException("Expected IOException when skipping on closed stream");
        } catch (IOException e) {
            // expected
        }
    }

    private static void testSkipNormalBehavior() throws IOException {
        byte[] compressedData = new byte[10]; // empty or invalid compressed data
        ByteArrayInputStream bais = new ByteArrayInputStream(compressedData);
        InflaterInputStream iis = new InflaterInputStream(bais, new Inflater());

        long skipped = iis.skip(5L);
        if (skipped < 0 || skipped > 5) {
            throw new RuntimeException("skip returned invalid value: " + skipped);
        }

        // skip zero should return zero
        long skippedZero = iis.skip(0L);
        if (skippedZero != 0) {
            throw new RuntimeException("skip(0) should return 0 but returned " + skippedZero);
        }

        iis.close();
    }
}
