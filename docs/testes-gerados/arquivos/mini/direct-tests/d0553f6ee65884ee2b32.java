/*
 * @test
 * @summary Test DataInputStream.readFully(byte[]) method behavior
 * @run main DataInputStreamWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;

public class DataInputStreamWitupTest {
    public static void main(String[] args) throws IOException {
        testReadFullyExactLength();
        testReadFullyEmptyArray();
        testReadFullyEOF();
    }

    private static void testReadFullyExactLength() throws IOException {
        byte[] input = {1, 2, 3, 4, 5};
        DataInputStream dis = new DataInputStream(new ByteArrayInputStream(input));
        byte[] buffer = new byte[5];
        dis.readFully(buffer);
        for (int i = 0; i < buffer.length; i++) {
            if (buffer[i] != input[i]) {
                throw new AssertionError("testReadFullyExactLength: buffer[" + i + "] != input[" + i + "]");
            }
        }
    }

    private static void testReadFullyEmptyArray() throws IOException {
        byte[] input = {1, 2, 3};
        DataInputStream dis = new DataInputStream(new ByteArrayInputStream(input));
        byte[] buffer = new byte[0];
        dis.readFully(buffer); // should return immediately without exception
    }

    private static void testReadFullyEOF() {
        byte[] input = {1, 2};
        DataInputStream dis = new DataInputStream(new ByteArrayInputStream(input));
        byte[] buffer = new byte[3];
        try {
            dis.readFully(buffer);
            throw new AssertionError("testReadFullyEOF: expected EOFException not thrown");
        } catch (EOFException e) {
            // expected
        } catch (IOException e) {
            throw new AssertionError("testReadFullyEOF: unexpected IOException", e);
        }
    }
}
