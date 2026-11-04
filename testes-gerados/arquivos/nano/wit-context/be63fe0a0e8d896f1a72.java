/*
 * @test
 * @summary Test for sun.net.www.http.ChunkedInputStream.read()
 * @run main ChunkedInputStreamWitupTest
 */

import java.io.*;

public class ChunkedInputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        testReadWhenOpen();
        testReadWhenClosed();
    }

    private static void testReadWhenOpen() throws Exception {
        // Mock InputStream that provides data
        InputStream mockIn = new ByteArrayInputStream(new byte[] {0x01, 0x02, 0x03});
        // Create dummy MessageHeader (null for simplicity)
        sun.net.www.MessageHeader responses = null;
        // Instantiate ChunkedInputStream with mock InputStream
        sun.net.www.http.ChunkedInputStream cis = new sun.net.www.http.ChunkedInputStream(mockIn, null, responses);
        // Read bytes until end
        int byteRead;
        while ((byteRead = cis.read()) != -1) {
            // Consume data
        }
        // No exception expected
    }

    private static void testReadWhenClosed() throws Exception {
        InputStream mockIn = new ByteArrayInputStream(new byte[] {0x01});
        sun.net.www.MessageHeader responses = null;
        sun.net.www.http.ChunkedInputStream cis = new sun.net.www.http.ChunkedInputStream(mockIn, null, responses);
        // Close the stream to simulate closed state
        cis.close();
        try {
            cis.read();
            // Expect IOException due to closed stream
            throw new AssertionError("Expected IOException when reading closed stream");
        } catch (IOException e) {
            // Expected exception
        }
    }
}
