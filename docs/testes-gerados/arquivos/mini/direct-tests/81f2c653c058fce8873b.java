/*
 * @test
 * @summary Test ByteArrayInputStream.readAllBytes() method
 * @run main ByteArrayInputStreamWitupTest
 */

import java.io.ByteArrayInputStream;
import java.util.Arrays;

public class ByteArrayInputStreamWitupTest {
    public static void main(String[] args) throws Exception {
        testReadAllBytesReturnsRemainingBytes();
        testReadAllBytesOnEmptyStreamReturnsEmptyArray();
        testReadAllBytesAfterPartialReadReturnsRemainingBytes();
    }

    private static void testReadAllBytesReturnsRemainingBytes() {
        byte[] data = {1, 2, 3, 4, 5};
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        byte[] result = bais.readAllBytes();
        if (!Arrays.equals(result, data)) {
            throw new AssertionError("readAllBytes() did not return all bytes as expected");
        }
        // After readAllBytes, further calls should return empty array
        byte[] result2 = bais.readAllBytes();
        if (result2.length != 0) {
            throw new AssertionError("readAllBytes() after stream exhausted should return empty array");
        }
    }

    private static void testReadAllBytesOnEmptyStreamReturnsEmptyArray() {
        byte[] data = new byte[0];
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        byte[] result = bais.readAllBytes();
        if (result.length != 0) {
            throw new AssertionError("readAllBytes() on empty stream should return empty array");
        }
    }

    private static void testReadAllBytesAfterPartialReadReturnsRemainingBytes() throws Exception {
        byte[] data = {10, 20, 30, 40, 50};
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        // Read two bytes manually
        int first = bais.read();
        int second = bais.read();
        if (first != 10 || second != 20) {
            throw new AssertionError("Initial reads did not return expected bytes");
        }
        // Now readAllBytes should return remaining bytes
        byte[] expected = {30, 40, 50};
        byte[] result = bais.readAllBytes();
        if (!Arrays.equals(result, expected)) {
            throw new AssertionError("readAllBytes() did not return remaining bytes after partial read");
        }
        // Subsequent readAllBytes calls return empty array
        byte[] result2 = bais.readAllBytes();
        if (result2.length != 0) {
            throw new AssertionError("readAllBytes() after stream exhausted should return empty array");
        }
    }
}
