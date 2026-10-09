/* @test    @summary Test ObjectOutputStream.write(byte[], int, int) for correct behavior and exception handling    @run main ObjectOutputStreamWitupTest_P2e0011781a92 */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class ObjectOutputStreamWitupTest_P2e0011781a92 {

    public static void main(String[] args) {
        testValidWrite();
        testNullBuffer();
        testNegativeOffset();
        testNegativeLength();
        testEndOffsetExceedsBuffer();
    }

    static void testValidWrite() {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            byte[] data = {1, 2, 3, 4, 5};
            oos.write(data, 1, 3); // should write {2, 3, 4}
            oos.close();
            byte[] result = baos.toByteArray();
            if (result.length != 3 || result[0] != 2 || result[1] != 3 || result[2] != 4) {
                throw new AssertionError("Valid write test failed: unexpected output") ;
            }
        } catch (IOException e) {
            throw new RuntimeException("Unexpected IOException in valid write test", e);
        }
    }

    static void testNullBuffer() {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.write(null, 0, 1);
            throw new AssertionError("Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            // expected
        } catch (IOException e) {
            throw new RuntimeException("Unexpected IOException in null buffer test", e);
        }
    }

    static void testNegativeOffset() {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            byte[] data = {1, 2, 3};
            oos.write(data, -1, 2);
            throw new AssertionError("Expected IndexOutOfBoundsException not thrown for negative offset");
        } catch (IndexOutOfBoundsException e) {
            // expected
        } catch (IOException e) {
            throw new RuntimeException("Unexpected IOException in negative offset test", e);
        }
    }

    static void testNegativeLength() {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            byte[] data = {1, 2, 3};
            oos.write(data, 0, -1);
            throw new AssertionError("Expected IndexOutOfBoundsException not thrown for negative length");
        } catch (IndexOutOfBoundsException e) {
            // expected
        } catch (IOException e) {
            throw new RuntimeException("Unexpected IOException in negative length test", e);
        }
    }

    static void testEndOffsetExceedsBuffer() {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            byte[] data = {1, 2, 3};
            oos.write(data, 1, 3); // end offset = 4, buffer length = 3
            throw new AssertionError("Expected IndexOutOfBoundsException not thrown for end offset exceeding buffer length");
        } catch (IndexOutOfBoundsException e) {
            // expected
        } catch (IOException e) {
            throw new RuntimeException("Unexpected IOException in end offset exceeds buffer test", e);
        }
    }
}
