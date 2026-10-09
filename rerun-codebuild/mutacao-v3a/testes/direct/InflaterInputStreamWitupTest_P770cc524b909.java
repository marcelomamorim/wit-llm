/* @test    @summary Test reset method of InflaterInputStream which always throws IOException    @run main InflaterInputStreamWitupTest_P770cc524b909 */

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;

public class InflaterInputStreamWitupTest_P770cc524b909 {

    public static void main(String[] args) {
        try {
            testResetMethodThrowsException();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        System.out.println("All tests passed.");
    }

    public static void testResetMethodThrowsException() throws IOException {
        byte[] data = {0x00};
        ByteArrayInputStream byteStream = new ByteArrayInputStream(data);
        Inflater inflater = new Inflater();
        InflaterInputStream iis = new InflaterInputStream(byteStream, inflater);
        try {
            iis.reset();
            throw new AssertionError("Expected IOException was not thrown.");
        } catch (IOException e) {
            if (!"mark/reset not supported".equals(e.getMessage())) {
                throw new AssertionError("Unexpected exception message: " + e.getMessage());
            }
            // Expected exception caught, test passes.
        } finally {
            iis.close();
        }
    }
}
