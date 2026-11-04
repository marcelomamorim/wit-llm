/*
 * @test
 * @summary Test for sun.net.www.http.ChunkedInputStream.available()
 * @run main ChunkedInputStreamWitupTest
 */

import java.io.*;
import sun.net.www.http.ChunkedInputStream;

public class ChunkedInputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        testAvailableWhenOpen();
        testAvailableWhenClosed();
    }

    private static void testAvailableWhenOpen() throws Exception {
        // Setup: create a dummy InputStream with some data
        byte[] data = new byte[10];
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        // Instantiate ChunkedInputStream with dummy dependencies
        ChunkedInputStream cis = new ChunkedInputStream(bais, null, null);
        // Call available and verify it returns non-negative value
        int avail = cis.available();
        if (avail < 0) {
            throw new AssertionError("available() returned negative value when stream is open");
        }
        System.out.println("testAvailableWhenOpen passed, available: " + avail);
    }

    private static void testAvailableWhenClosed() throws Exception {
        // Setup: create a dummy InputStream
        byte[] data = new byte[10];
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        ChunkedInputStream cis = new ChunkedInputStream(bais, null, null);
        // Close the underlying stream to simulate closed state
        // Note: ByteArrayInputStream does not throw on close, but we can simulate by closing it
        bais.close();
        try {
            int avail = cis.available();
            // Expect an IOException due to ensureOpen() in available()
            throw new AssertionError("Expected IOException when calling available() on closed stream");
        } catch (IOException e) {
            System.out.println("testAvailableWhenClosed passed, caught expected IOException: " + e.getMessage());
        }
    }
}
