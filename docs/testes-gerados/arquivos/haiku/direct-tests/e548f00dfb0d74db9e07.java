/*
 * @test
 * @summary Checks observable behavior of DeflaterInputStream.available()
 * @run main DeflaterInputStreamWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.Deflater;
import java.util.zip.DeflaterInputStream;

public class DeflaterInputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        testAvailableBeforeReadReturnsOne();
        testAvailableIsStableWithoutReading();
        testAvailableAfterEofReturnsZero();
        testAvailableAfterCloseThrowsIOException();
    }

    static void testAvailableBeforeReadReturnsOne() throws IOException {
        InputStream in = new ByteArrayInputStream(new byte[] {1, 2, 3, 4});
        DeflaterInputStream dis = new DeflaterInputStream(in);
        int avail = dis.available();
        if (avail != 1) {
            throw new RuntimeException("Expected available() == 1 before reading, got " + avail);
        }
        dis.close();
    }

    static void testAvailableIsStableWithoutReading() throws IOException {
        InputStream in = new ByteArrayInputStream(new byte[] {5, 6, 7});
        Deflater d = new Deflater();
        DeflaterInputStream dis = new DeflaterInputStream(in, d, 16);
        for (int i = 0; i < 3; i++) {
            int avail = dis.available();
            if (avail != 1) {
                throw new RuntimeException("Expected available() == 1 on call " + i + ", got " + avail);
            }
        }
        dis.close();
    }

    static void testAvailableAfterEofReturnsZero() throws IOException {
        InputStream in = new ByteArrayInputStream(new byte[] {10, 20, 30, 40, 50});
        DeflaterInputStream dis = new DeflaterInputStream(in, new Deflater(), 8);
        byte[] out = new byte[256];
        int total = 0;
        int n;
        while ((n = dis.read(out, 0, out.length)) != -1) {
            total += n;
        }
        if (total <= 0) {
            throw new RuntimeException("Expected some compressed output, got " + total);
        }
        int avail = dis.available();
        if (avail != 0) {
            throw new RuntimeException("Expected available() == 0 at EOF, got " + avail);
        }
        dis.close();
    }

    static void testAvailableAfterCloseThrowsIOException() throws IOException {
        InputStream in = new ByteArrayInputStream(new byte[] {1});
        DeflaterInputStream dis = new DeflaterInputStream(in);
        dis.close();
        try {
            dis.available();
            throw new RuntimeException("Expected IOException from available() after close()");
        } catch (IOException expected) {
            // expected: ensureOpen() reports closed stream
        }
    }
}
