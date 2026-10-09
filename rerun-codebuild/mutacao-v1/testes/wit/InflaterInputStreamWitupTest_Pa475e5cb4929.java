/* @test    @summary Test for reset method in InflaterInputStream to verify IOException is thrown as expected    @run main InflaterInputStreamWitupTest_Pa475e5cb4929 */

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;

public class InflaterInputStreamWitupTest_Pa475e5cb4929 {

    public static void main(String[] args) {
        testResetThrowsIOException();
    }

    public static void testResetThrowsIOException() {
        InputStream dummyInput = new ByteArrayInputStream(new byte[0]);
        Inflater inflater = new Inflater();
        InflaterInputStream iis = null;
        try {
            iis = new InflaterInputStream(dummyInput, inflater);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create InflaterInputStream", e);
        }
        try {
            iis.reset();
            throw new AssertionError("Expected IOException was not thrown");
        } catch (IOException e) {
            if (!"mark/reset not supported".equals(e.getMessage())) {
                throw new AssertionError("Unexpected IOException message: " + e.getMessage(), e);
            }
            // Test passed: IOException thrown with expected message
        } finally {
            if (iis != null) {
                try {
                    iis.close();
                } catch (IOException e) {
                    // ignore
                }
            }
        }
    }
}
