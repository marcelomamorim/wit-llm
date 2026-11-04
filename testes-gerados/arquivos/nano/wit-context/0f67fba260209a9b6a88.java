/* @test    @summary Test for InflaterInputStream.reset() method to verify it throws IOException as specified.    @run main InflaterInputStreamWitupTest */

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;

public class InflaterInputStreamWitupTest {

    public static void main(String[] args) {
        testResetThrowsIOException();
    }

    public static void testResetThrowsIOException() {
        byte[] data = {0x01, 0x02, 0x03};
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        Inflater inflater = new Inflater();
        InflaterInputStream iis = new InflaterInputStream(bais, inflater);
        try {
            iis.reset();
            // If no exception, test fails
            throw new AssertionError("Expected IOException was not thrown");
        } catch (IOException e) {
            if (!"mark/reset not supported".equals(e.getMessage())) {
                throw new AssertionError("Unexpected IOException message: " + e.getMessage(), e);
            }
            // Expected exception caught
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type: " + e.getClass(), e);
        } finally {
            try {
                iis.close();
            } catch (IOException e) {
                // ignore
            }
        }
    }
}
