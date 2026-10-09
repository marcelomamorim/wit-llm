/* @test    @summary Test reset() method of InflaterInputStream that throws IOException as specified.    @run main InflaterInputStreamWitupTest_P8ae34faacc1a */

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;

public class InflaterInputStreamWitupTest_P8ae34faacc1a {

    public static void main(String[] args) {
        testResetThrowsIOException();
    }

    public static void testResetThrowsIOException() {
        ByteArrayInputStream byteStream = new ByteArrayInputStream(new byte[] {0x01, 0x02});
        Inflater inflater = new Inflater();
        InflaterInputStream iis = new InflaterInputStream(byteStream, inflater);
        try {
            iis.reset();
            // If no exception, test fails
            throw new AssertionError("Expected IOException not thrown");
        } catch (IOException e) {
            if (!"mark/reset not supported".equals(e.getMessage())) {
                throw new AssertionError("Unexpected exception message: " + e.getMessage(), e);
            }
            // Expected exception caught, test passes
        } finally {
            try {
                iis.close();
            } catch (IOException e) {
                // ignore
            }
        }
    }
}
