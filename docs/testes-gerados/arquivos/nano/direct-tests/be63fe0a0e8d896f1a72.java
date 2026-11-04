/* @test    @summary Test reading from ChunkedInputStream's read() method.    @run main ChunkedInputStreamWitupTest */

import java.io.*;
import sun.net.www.http.*;

public class ChunkedInputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        testReadReturnsByte();
        testReadReturnsMinusOneAtEnd();
    }

    private static void testReadReturnsByte() throws Exception {
        // Create a dummy InputStream with predefined data
        byte[] data = {10, 20, 30};
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        // Instantiate ChunkedInputStream with dummy dependencies
        ChunkedInputStream cis = new ChunkedInputStream(bais, null, null) {
            @Override
            public synchronized int read() throws IOException {
                return super.read();
            }
        };
        // Read bytes until end
        int totalRead = 0;
        int byteRead;
        while ((byteRead = cis.read()) != -1) {
            totalRead++;
        }
        assert totalRead == data.length : "Expected to read " + data.length + " bytes, but read " + totalRead;
    }

    private static void testReadReturnsMinusOneAtEnd() throws Exception {
        byte[] data = {1};
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        ChunkedInputStream cis = new ChunkedInputStream(bais, null, null) {
            @Override
            public synchronized int read() throws IOException {
                return super.read();
            }
        };
        // Read the only byte
        int firstByte = cis.read();
        assert firstByte == data[0] : "First byte mismatch";
        // Next read should return -1
        int endByte = cis.read();
        assert endByte == -1 : "Expected -1 at end of stream";
    }
}
