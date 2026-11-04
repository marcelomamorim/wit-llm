/*
 * @test
 * @summary Test sun.security.util.DerInputStream.peekByte() for normal and boundary conditions
 * @run main DerInputStreamWitupTest
 */

import sun.security.util.DerInputStream;
import sun.security.util.DerInputBuffer;
import java.io.IOException;

public class DerInputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        testPeekByteNormal();
        testPeekByteAtEndThrows();
        System.out.println("All tests passed.");
    }

    private static void testPeekByteNormal() throws IOException {
        // Prepare a buffer with some bytes
        byte[] data = new byte[] {0x01, 0x02, 0x03};
        DerInputStream derIn = new DerInputStream(data);

        // peekByte should return the first byte as int (0x01 = 1)
        int val = derIn.peekByte();
        if (val != 0x01) {
            throw new AssertionError("peekByte() returned " + val + ", expected 1");
        }

        // peekByte again should return the same byte (peek does not consume)
        int val2 = derIn.peekByte();
        if (val2 != 0x01) {
            throw new AssertionError("peekByte() returned " + val2 + " on second call, expected 1");
        }
    }

    private static void testPeekByteAtEndThrows() {
        // Create a DerInputStream with empty data to simulate pos >= count
        byte[] empty = new byte[0];
        try {
            DerInputStream derIn = new DerInputStream(empty);
            derIn.peekByte();
            throw new AssertionError("peekByte() did not throw IOException on empty input");
        } catch (IOException e) {
            // Expected exception
        }
    }
}
