/* @test    @summary Test reset() method of DeflaterInputStream which throws IOException as specified.    @run main DeflaterInputStreamWitupTest */

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.zip.Deflater;
import java.util.zip.DeflaterInputStream;

public class DeflaterInputStreamWitupTest {

    public static void main(String[] args) {
        testResetThrowsIOException();
    }

    public static void testResetThrowsIOException() {
        ByteArrayInputStream bais = new ByteArrayInputStream(new byte[] {1, 2, 3});
        Deflater deflater = new Deflater();
        DeflaterInputStream dis = new DeflaterInputStream(bais, deflater);
        try {
            dis.reset();
            // If no exception, test fails
            throw new AssertionError("Expected IOException not thrown");
        } catch (IOException e) {
            if (!"mark/reset not supported".equals(e.getMessage())) {
                throw new AssertionError("Unexpected exception message: " + e.getMessage(), e);
            }
            // Expected exception caught
        }
    }
}
