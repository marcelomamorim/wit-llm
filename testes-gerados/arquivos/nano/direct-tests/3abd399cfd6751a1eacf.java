/* @test    @summary Test PipedWriter constructor with connected PipedReader    @run main PipedWriterWitupTest */

import java.io.PipedReader;
import java.io.PipedWriter;
import java.io.IOException;

public class PipedWriterWitupTest {

    public static void main(String[] args) {
        testConstructorWithConnectedReader();
        testConstructorWithNullReader();
    }

    private static void testConstructorWithConnectedReader() {
        try {
            PipedReader reader = new PipedReader();
            PipedWriter writer = new PipedWriter(reader);
            if (reader.in != -1 || reader.out != 0 || !reader.connected) {
                throw new AssertionError("Reader state not as expected after connection");
            }
            if (writer == null) {
                throw new AssertionError("PipedWriter is null");
            }
        } catch (IOException e) {
            throw new AssertionError("Unexpected IOException: " + e);
        }
    }

    private static void testConstructorWithNullReader() {
        boolean exceptionThrown = false;
        try {
            new PipedWriter((PipedReader) null);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        } catch (IOException e) {
            throw new AssertionError("Unexpected IOException: " + e);
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected NullPointerException not thrown");
        }
    }
}
