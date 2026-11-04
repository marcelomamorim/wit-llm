/*
 * @test
 * @summary Test ObjectInputStream.readFully(byte[]) for EOFException and normal behavior
 * @run main ObjectInputStreamWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;

public class ObjectInputStreamWitupTest {
    public static void main(String[] args) throws Exception {
        testReadFullyNormal();
        testReadFullyEOFException();
    }

    private static void testReadFullyNormal() throws IOException {
        byte[] data = new byte[] {1, 2, 3, 4, 5};
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        ObjectInputStream ois = new ObjectInputStream(bais);
        byte[] buf = new byte[data.length];
        ois.readFully(buf);
        for (int i = 0; i < data.length; i++) {
            if (buf[i] != data[i]) {
                throw new AssertionError("readFully did not read expected data at index " + i);
            }
        }
        ois.close();
    }

    private static void testReadFullyEOFException() throws IOException {
        byte[] data = new byte[] {1, 2, 3};
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        ObjectInputStream ois = new ObjectInputStream(bais);
        byte[] buf = new byte[data.length + 1]; // request more bytes than available
        boolean threwEOF = false;
        try {
            ois.readFully(buf);
        } catch (EOFException e) {
            threwEOF = true;
        }
        if (!threwEOF) {
            throw new AssertionError("Expected EOFException was not thrown by readFully");
        }
        ois.close();
    }
}
