/* @test    @summary Test readAllBytes method of ByteArrayInputStream    @run main ByteArrayInputStreamWitupTest */

import java.io.ByteArrayInputStream;
import java.util.Arrays;

public class ByteArrayInputStreamWitupTest {

    public static void main(String[] args) {
        testReadAllBytesWithFullBuffer();
        testReadAllBytesWithPartialBuffer();
        testReadAllBytesWithEmptyBuffer();
    }

    private static void testReadAllBytesWithFullBuffer() {
        byte[] data = {1, 2, 3, 4, 5};
        ByteArrayInputStream stream = new ByteArrayInputStream(data);
        byte[] result = stream.readAllBytes();
        assert Arrays.equals(result, data) : "Failed: readAllBytes with full buffer";
        assert stream.pos == stream.count : "Stream position should be at end after readAllBytes";
    }

    private static void testReadAllBytesWithPartialBuffer() {
        byte[] data = {10, 20, 30, 40, 50};
        ByteArrayInputStream stream = new ByteArrayInputStream(data);
        stream.pos = 1; // set position to 1
        byte[] result = stream.readAllBytes();
        byte[] expected = {20, 30, 40, 50};
        assert Arrays.equals(result, expected) : "Failed: readAllBytes with partial buffer";
        assert stream.pos == stream.count : "Stream position should be at end after readAllBytes";
    }

    private static void testReadAllBytesWithEmptyBuffer() {
        byte[] data = {};
        ByteArrayInputStream stream = new ByteArrayInputStream(data);
        byte[] result = stream.readAllBytes();
        assert Arrays.equals(result, new byte[0]) : "Failed: readAllBytes with empty buffer";
        assert stream.pos == stream.count : "Stream position should be at end after readAllBytes";
    }
}
