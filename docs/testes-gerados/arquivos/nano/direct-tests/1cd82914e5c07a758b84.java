/* @test    @summary Test ObjectOutputStream.write(byte[], int, int) for correct behavior and exception handling    @run main ObjectOutputStreamWitupTest */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class ObjectOutputStreamWitupTest {

    public static void main(String[] args) {
        testWriteValid();
        testWriteNullBuffer();
        testWriteNegativeOffset();
        testWriteNegativeLength();
        testWriteOffsetPlusLengthExceedsBuffer();
    }

    static void testWriteValid() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (ObjectOutputStream oos = new ObjectOutputStream(baos)) {
            byte[] data = {1, 2, 3, 4, 5};
            oos.write(data, 1, 3); // should write bytes 2, 3, 4
            byte[] result = baos.toByteArray();
            assert result.length == 3 : "Expected 3 bytes written";
            assert result[0] == 2 : "First byte should be 2";
            assert result[1] == 3 : "Second byte should be 3";
            assert result[2] == 4 : "Third byte should be 4";
        } catch (IOException e) {
            throw new RuntimeException("Unexpected IOException in testWriteValid", e);
        }
    }

    static void testWriteNullBuffer() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (ObjectOutputStream oos = new ObjectOutputStream(baos)) {
            oos.write(null, 0, 0);
            throw new AssertionError("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // expected
        } catch (IOException e) {
            throw new RuntimeException("Unexpected IOException in testWriteNullBuffer", e);
        }
    }

    static void testWriteNegativeOffset() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (ObjectOutputStream oos = new ObjectOutputStream(baos)) {
            byte[] data = {1, 2, 3};
            oos.write(data, -1, 2);
            throw new AssertionError("Expected IndexOutOfBoundsException not thrown");
        } catch (IndexOutOfBoundsException expected) {
            // expected
        } catch (IOException e) {
            throw new RuntimeException("Unexpected IOException in testWriteNegativeOffset", e);
        }
    }

    static void testWriteNegativeLength() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (ObjectOutputStream oos = new ObjectOutputStream(baos)) {
            byte[] data = {1, 2, 3};
            oos.write(data, 0, -1);
            throw new AssertionError("Expected IndexOutOfBoundsException not thrown");
        } catch (IndexOutOfBoundsException expected) {
            // expected
        } catch (IOException e) {
            throw new RuntimeException("Unexpected IOException in testWriteNegativeLength", e);
        }
    }

    static void testWriteOffsetPlusLengthExceedsBuffer() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (ObjectOutputStream oos = new ObjectOutputStream(baos)) {
            byte[] data = {1, 2, 3};
            oos.write(data, 1, 3); // 1 + 3 = 4 > data.length
            throw new AssertionError("Expected IndexOutOfBoundsException not thrown");
        } catch (IndexOutOfBoundsException expected) {
            // expected
        } catch (IOException e) {
            throw new RuntimeException("Unexpected IOException in testWriteOffsetPlusLengthExceedsBuffer", e);
        }
    }
}
