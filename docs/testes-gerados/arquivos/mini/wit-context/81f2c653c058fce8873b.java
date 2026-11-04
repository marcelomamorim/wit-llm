/*
 * @test
 * @summary Test ByteArrayInputStream.readAllBytes() for normal and edge cases
 * @run main ByteArrayInputStreamWitupTest
 */

import java.io.ByteArrayInputStream;
import java.util.Arrays;

public class ByteArrayInputStreamWitupTest {
    public static void main(String[] args) throws Exception {
        testReadAllBytesNormal();
        testReadAllBytesEmpty();
        testReadAllBytesPosEqualsCount();
    }

    private static void testReadAllBytesNormal() {
        byte[] data = {1, 2, 3, 4, 5};
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        byte[] result = bais.readAllBytes();
        if (!Arrays.equals(result, data)) {
            throw new AssertionError("readAllBytes() did not return expected full array");
        }
        // After readAllBytes, pos should be at count, so subsequent readAllBytes returns empty array
        byte[] secondRead = bais.readAllBytes();
        if (secondRead.length != 0) {
            throw new AssertionError("readAllBytes() after consuming all bytes should return empty array");
        }
    }

    private static void testReadAllBytesEmpty() {
        byte[] empty = new byte[0];
        ByteArrayInputStream bais = new ByteArrayInputStream(empty);
        byte[] result = bais.readAllBytes();
        if (result.length != 0) {
            throw new AssertionError("readAllBytes() on empty buffer should return empty array");
        }
    }

    private static void testReadAllBytesPosEqualsCount() throws Exception {
        byte[] data = {10, 20, 30};
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        // Use reflection to set pos to count to simulate pos == count
        // Reflection is used because pos is protected and no public setter exists
        java.lang.reflect.Field posField = ByteArrayInputStream.class.getDeclaredField("pos");
        posField.setAccessible(true);
        java.lang.reflect.Field countField = ByteArrayInputStream.class.getDeclaredField("count");
        countField.setAccessible(true);
        int count = countField.getInt(bais);
        posField.setInt(bais, count);

        byte[] result = bais.readAllBytes();
        if (result.length != 0) {
            throw new AssertionError("readAllBytes() when pos == count should return empty array");
        }
    }
}
