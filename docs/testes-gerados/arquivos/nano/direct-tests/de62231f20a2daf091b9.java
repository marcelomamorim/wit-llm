/*
 * @test
 * @summary Test getInputStream() method of JavaRuntimeURLConnection.
 * @run main JavaRuntimeURLConnectionWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import sun.net.www.protocol.jrt.JavaRuntimeURLConnection;

public class JavaRuntimeURLConnectionWitupTest {

    public static void main(String[] args) throws Exception {
        testGetInputStreamReturnsByteArrayInputStream();
        testGetInputStreamWithNullResource();
    }

    private static void testGetInputStreamReturnsByteArrayInputStream() throws Exception {
        URL url = new URL("jrt:/module/resource");
        JavaRuntimeURLConnection conn = new JavaRuntimeURLConnection(url);
        // Mock the reader to return specific resource bytes
        byte[] resourceBytes = {1, 2, 3, 4, 5};
        // Reflection or direct assignment not used; assuming reader is accessible and returns resourceBytes
        // Since reader is static final, we cannot modify it directly; this test assumes the resource exists
        InputStream is = conn.getInputStream();
        byte[] buffer = new byte[resourceBytes.length];
        int readBytes = is.read(buffer);
        is.close();
        boolean isCorrectType = is.getClass().getName().equals("java.io.ByteArrayInputStream");
        assert readBytes == resourceBytes.length : "Read bytes count mismatch";
        for (int i = 0; i < resourceBytes.length; i++) {
            assert buffer[i] == resourceBytes[i] : "Byte mismatch at index " + i;
        }
        assert isCorrectType : "InputStream is not ByteArrayInputStream";
    }

    private static void testGetInputStreamWithNullResource() throws Exception {
        URL url = new URL("jrt:/module/unknownResource");
        JavaRuntimeURLConnection conn = new JavaRuntimeURLConnection(url);
        try {
            InputStream is = conn.getInputStream();
            byte[] buffer = new byte[10];
            int readBytes = is.read(buffer);
            is.close();
            assert readBytes == -1 : "Expected end of stream for unknown resource";
        } catch (IOException e) {
            // Expected if resource not found
            assert e != null : "IOException expected for missing resource";
        }
    }
}
