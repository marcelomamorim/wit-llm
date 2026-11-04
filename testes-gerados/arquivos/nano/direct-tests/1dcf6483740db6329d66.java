/*
 * @test
 * @summary Test that ImageReader.close() throws IOException if already closed
 * @run main ImageReaderWitupTest
 */
import java.io.IOException;
import jdk.internal.jimage.ImageReader;

public class ImageReaderWitupTest {
    public static void main(String[] args) throws Exception {
        testCloseThrowsWhenAlreadyClosed();
    }

    public static void testCloseThrowsWhenAlreadyClosed() throws Exception {
        // Create a mock or dummy SharedImageReader to pass to ImageReader
        // Since SharedImageReader is not accessible, we simulate the scenario by subclassing or reflection
        // but as per constraints, we test the public close() method behavior.
        // Here, we simulate the behavior by creating a subclass that sets 'closed' to true.
        class DummyImageReader extends ImageReader {
            DummyImageReader() {
                super(null);
                // forcibly set 'closed' to true to simulate already closed state
                // 'closed' is private, so we cannot set directly; instead, we invoke close() twice
            }
            @Override
            public void close() throws IOException {
                super.close();
            }
        }
        // Instantiate the dummy reader
        DummyImageReader reader = new DummyImageReader();
        // First close to set 'closed' to true
        try {
            reader.close();
        } catch (IOException e) {
            // ignore
        }
        // Second close should throw IOException
        boolean exceptionThrown = false;
        try {
            reader.close();
        } catch (IOException e) {
            exceptionThrown = true;
            if (!e.getMessage().equals("image file already closed")) {
                throw new AssertionError("Unexpected exception message: " + e.getMessage());
            }
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected IOException was not thrown on second close()");
        }
    }
}
