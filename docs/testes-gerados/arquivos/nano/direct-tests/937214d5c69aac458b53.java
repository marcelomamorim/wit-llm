/*
 * @test
 * @summary Test the available() method of BufferedInputStream
 * @run main BufferedInputStreamWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.BufferedInputStream;
import java.io.IOException;

public class BufferedInputStreamWitupTest {

    public static void main(String[] args) throws IOException {
        testAvailableWithData();
        testAvailableWithEmptyStream();
        testAvailableAfterRead();
    }

    static void testAvailableWithData() throws IOException {
        byte[] data = {1, 2, 3, 4, 5};
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        BufferedInputStream bis = new BufferedInputStream(bais);
        int initialAvailable = bis.available();
        if (initialAvailable != data.length) {
            throw new AssertionError("Expected available() to return " + data.length + " but got " + initialAvailable);
        }
        int readByte = bis.read();
        int afterReadAvailable = bis.available();
        if (afterReadAvailable != data.length - 1) {
            throw new AssertionError("After reading one byte, expected available() to return " + (data.length - 1) + " but got " + afterReadAvailable);
        }
    }

    static void testAvailableWithEmptyStream() throws IOException {
        byte[] emptyData = {};
        ByteArrayInputStream bais = new ByteArrayInputStream(emptyData);
        BufferedInputStream bis = new BufferedInputStream(bais);
        int available = bis.available();
        if (available != 0) {
            throw new AssertionError("Expected available() to return 0 for empty stream but got " + available);
        }
    }

    static void testAvailableAfterRead() throws IOException {
        byte[] data = {10, 20, 30};
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        BufferedInputStream bis = new BufferedInputStream(bais);
        int firstByte = bis.read();
        int secondByte = bis.read();
        int available = bis.available();
        int expected = data.length - 2;
        if (available != expected) {
            throw new AssertionError("After reading two bytes, expected available() to return " + expected + " but got " + available);
        }
    }
}
