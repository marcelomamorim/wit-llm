/*
 * @test
 * @summary Test readFully method for various inputs
 * @run main ObjectInputStreamWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class ObjectInputStreamWitupTest {

    public static void main(String[] args) {
        testReadFullyValid();
        testReadFullyIndexOutOfBounds();
    }

    static void testReadFullyValid() {
        byte[] buffer = new byte[10];
        byte[] data = {1, 2, 3, 4, 5};
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(bais);
            ois.readFully(buffer, 0, data.length);
        } catch (IOException e) {
            throw new RuntimeException("Unexpected IOException in valid case", e);
        } finally {
            if (ois != null) {
                try {
                    ois.close();
                } catch (IOException e) {
                    // ignore
                }
            }
        }
    }

    static void testReadFullyIndexOutOfBounds() {
        byte[] buffer = new byte[5];
        ByteArrayInputStream bais = new ByteArrayInputStream(new byte[]{1, 2, 3});
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(bais);
            // Trigger IndexOutOfBoundsException by passing invalid off and len
            ois.readFully(buffer, -1, 3);
            throw new AssertionError("Expected IndexOutOfBoundsException for negative off");
        } catch (IndexOutOfBoundsException e) {
            // expected
        } catch (IOException e) {
            throw new RuntimeException("Unexpected IOException", e);
        } finally {
            if (ois != null) {
                try {
                    ois.close();
                } catch (IOException e) {
                    // ignore
                }
            }
        }
        try {
            ois = new ObjectInputStream(new ByteArrayInputStream(new byte[]{1, 2, 3}));
            // Trigger IndexOutOfBoundsException by passing len larger than buffer
            ois.readFully(buffer, 0, 10);
            throw new AssertionError("Expected IndexOutOfBoundsException for len too large");
        } catch (IndexOutOfBoundsException e) {
            // expected
        } catch (IOException e) {
            throw new RuntimeException("Unexpected IOException", e);
        } finally {
            if (ois != null) {
                try {
                    ois.close();
                } catch (IOException e) {
                    // ignore
                }
            }
        }
    }
}
