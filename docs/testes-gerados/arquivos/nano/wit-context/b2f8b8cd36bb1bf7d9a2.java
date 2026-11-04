/* @test    @summary Test for DeflaterInputStream.reset() method to verify it throws IOException as specified.    @run main DeflaterInputStreamWitupTest */

import java.io.IOException;
import java.util.zip.Deflater;
import java.util.zip.DeflaterInputStream;

public class DeflaterInputStreamWitupTest {

    public static void main(String[] args) {
        testResetThrowsIOException();
    }

    public static void testResetThrowsIOException() {
        InputStreamStub inputStream = new InputStreamStub();
        Deflater deflater = new Deflater();
        DeflaterInputStream dis = new DeflaterInputStream(inputStream, deflater);
        try {
            dis.reset();
            // If no exception is thrown, the test fails
            System.err.println("Test failed: reset() did not throw IOException");
            System.exit(1);
        } catch (IOException e) {
            // Expected exception
            if ("mark/reset not supported".equals(e.getMessage())) {
                System.out.println("Test passed: reset() threw expected IOException.");
                System.exit(0);
            } else {
                System.err.println("Test failed: reset() threw IOException with unexpected message: " + e.getMessage());
                System.exit(1);
            }
        } catch (Throwable t) {
            System.err.println("Test failed: reset() threw unexpected exception: " + t);
            System.exit(1);
        }
    }

    static class InputStreamStub extends java.io.InputStream {
        @Override
        public int read() throws IOException {
            return -1; // End of stream
        }
    }
}
