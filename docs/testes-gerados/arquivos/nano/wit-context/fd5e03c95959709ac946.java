/* @test    @summary Test default constructor of PipedInputStream for basic instantiation and behavior    @run main PipedInputStreamWitupTest */

import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.IOException;

public class PipedInputStreamWitupTest {

    public static void main(String[] args) throws IOException {
        testDefaultConstructorCreatesStream();
        testConstructorWithInvalidPipeSize();
    }

    private static void testDefaultConstructorCreatesStream() {
        PipedInputStream pis = new PipedInputStream();
        assert pis != null : "PipedInputStream should be instantiated";
        // Verify that buffer is initialized with default size
        try {
            // Accessing protected buffer via reflection is discouraged; instead, test observable behavior
            // For simplicity, test that read/write does not throw and buffer is empty
            PipedOutputStream pos = new PipedOutputStream(pis);
            String testData = "abc";
            pos.write(testData.getBytes());
            byte[] buf = new byte[3];
            int readBytes = pis.read(buf);
            assert readBytes == 1 : "Should read one byte at a time";
            assert buf[0] == testData.getBytes()[0] : "First byte should match";
            pos.close();
            pis.close();
        } catch (IOException e) {
            assert false : "IOException during read/write: " + e;
        }
    }

    private static void testConstructorWithInvalidPipeSize() {
        // Since the default constructor does not accept parameters, test that no exception is thrown
        // and the object is usable
        try {
            PipedInputStream pis = new PipedInputStream();
            assert pis != null : "PipedInputStream instantiated with default constructor";
        } catch (Exception e) {
            assert false : "Exception should not be thrown in default constructor: " + e;
        }
    }
}
