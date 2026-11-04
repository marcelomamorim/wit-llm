/* @test    @summary Test PosterOutputStream.write(int b) method for basic functionality and closed state handling    @run main PosterOutputStreamWitupTest */

import java.io.*;
import sun.net.www.http.PosterOutputStream;

public class PosterOutputStreamWitupTest {

    public static void main(String[] args) throws IOException {
        testWriteByte();
        testWriteByteWhenClosed();
    }

    private static void testWriteByte() {
        PosterOutputStream pos = new PosterOutputStream();
        try {
            pos.write(65); // ASCII 'A'
            byte[] data = pos.toByteArray();
            if (data.length != 1 || data[0] != 65) {
                throw new AssertionError("Failed: write(int b) did not write correct byte");
            }
        } catch (IOException e) {
            throw new AssertionError("Unexpected IOException in testWriteByte", e);
        }
    }

    private static void testWriteByteWhenClosed() throws IOException {
        PosterOutputStream pos = new PosterOutputStream();
        pos.close();
        int initialLength = pos.size();
        pos.write(66); // ASCII 'B'
        if (pos.size() != initialLength) {
            throw new AssertionError("Failed: write(int b) should not modify stream after close");
        }
    }
}
