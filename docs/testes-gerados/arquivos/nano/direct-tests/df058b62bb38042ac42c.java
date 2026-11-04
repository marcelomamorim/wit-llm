/* @test    @summary Test BufferedInputStream.read() method for correct byte reading and end-of-stream handling    @run main BufferedInputStreamWitupTest */

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;

public class BufferedInputStreamWitupTest {

    public static void main(String[] args) throws IOException {
        testReadReturnsCorrectByte();
        testReadReturnsMinusOneAtEnd();
    }

    private static void testReadReturnsCorrectByte() throws IOException {
        byte[] data = {10, 20, 30};
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        BufferedInputStream bis = new BufferedInputStream(bais);
        int firstByte = bis.read();
        int secondByte = bis.read();
        int thirdByte = bis.read();
        assert firstByte == 10 : "Expected 10, got " + firstByte;
        assert secondByte == 20 : "Expected 20, got " + secondByte;
        assert thirdByte == 30 : "Expected 30, got " + thirdByte;
    }

    private static void testReadReturnsMinusOneAtEnd() throws IOException {
        byte[] data = {42};
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        BufferedInputStream bis = new BufferedInputStream(bais);
        int byteRead = bis.read();
        int endOfStream = bis.read();
        assert byteRead == 42 : "Expected 42, got " + byteRead;
        assert endOfStream == -1 : "Expected -1 at end of stream, got " + endOfStream;
    }
}
