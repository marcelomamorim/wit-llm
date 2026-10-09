/* @test    @summary Test for InflaterInputStream.reset() method to ensure it throws IOException as specified.    @run main InflaterInputStreamWitupTest_Pa475e5cb4929 */

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;

public class InflaterInputStreamWitupTest_Pa475e5cb4929 {

    public static void main(String[] args) {
        testResetThrowsIOException();
    }

    public static void testResetThrowsIOException() {
        ByteArrayInputStream bais = new ByteArrayInputStream(new byte[]{0x01, 0x02, 0x03});
        Inflater inflater = new Inflater();
        InflaterInputStream iis = new InflaterInputStream(bais, inflater);
        try {
            iis.reset();
            throw new AssertionError("Expected IOException was not thrown");
        } catch (IOException e) {
            if (!"mark/reset not supported".equals(e.getMessage())) {
                throw new AssertionError("Unexpected exception message: " + e.getMessage(), e);
            }
            // Test passed
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type: " + e, e);
        } finally {
            try {
                iis.close();
            } catch (IOException e) {
                // ignore
            }
        }
    }
}
