/*
 * @test
 * @summary Tests for java.io.ObjectInputStream.read(byte[], int, int)
 * @run main ObjectInputStreamWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class ObjectInputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        testRead_NullBuffer();
        testRead_IndexOutOfBounds();
    }

    public static void testRead_NullBuffer() {
        byte[] data = {1, 2, 3};
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        ObjectInputStream ois = new ObjectInputStream(bais);
        try {
            ois.read(null, 0, 1);
            System.err.println("Expected NullPointerException not thrown");
            System.exit(1);
        } catch (NullPointerException e) {
            // Expected exception
        } catch (IOException e) {
            System.err.println("Unexpected IOException: " + e);
            System.exit(1);
        }
    }

    public static void testRead_IndexOutOfBounds() {
        byte[] data = {1, 2, 3};
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        ObjectInputStream ois = new ObjectInputStream(bais);
        try {
            ois.read(data, -1, 2);
            System.err.println("Expected IndexOutOfBoundsException not thrown for negative offset");
            System.exit(1);
        } catch (IndexOutOfBoundsException e) {
            // Expected exception
        } catch (IOException e) {
            System.err.println("Unexpected IOException: " + e);
            System.exit(1);
        }
        try {
            ois.read(data, 0, -1);
            System.err.println("Expected IndexOutOfBoundsException not thrown for negative length");
            System.exit(1);
        } catch (IndexOutOfBoundsException e) {
            // Expected exception
        } catch (IOException e) {
            System.err.println("Unexpected IOException: " + e);
            System.exit(1);
        }
        try {
            ois.read(data, 0, 100);
            System.err.println("Expected IndexOutOfBoundsException not thrown for end offset exceeding buffer length");
            System.exit(1);
        } catch (IndexOutOfBoundsException e) {
            // Expected exception
        } catch (IOException e) {
            System.err.println("Unexpected IOException: " + e);
            System.exit(1);
        }
    }
}
