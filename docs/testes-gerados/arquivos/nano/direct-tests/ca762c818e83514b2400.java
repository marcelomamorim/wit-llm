/* @test    @summary Test write(byte[], int, int) method of PosterOutputStream for normal and closed states    @run main PosterOutputStreamWitupTest */

import java.io.*;
import sun.net.www.http.PosterOutputStream;

public class PosterOutputStreamWitupTest {

    public static void main(String[] args) throws IOException {
        testWriteNormal();
        testWriteAfterClose();
    }

    private static void testWriteNormal() throws IOException {
        PosterOutputStream pos = new PosterOutputStream();
        byte[] data = {1, 2, 3, 4, 5};
        pos.write(data, 1, 3); // should write bytes 2, 3, 4
        byte[] buffer = pos.toByteArray();
        assert buffer.length == 3 : "Expected length 3, got " + buffer.length;
        assert buffer[0] == 2 : "Expected byte 2, got " + buffer[0];
        assert buffer[1] == 3 : "Expected byte 3, got " + buffer[1];
        assert buffer[2] == 4 : "Expected byte 4, got " + buffer[2];
    }

    private static void testWriteAfterClose() throws IOException {
        PosterOutputStream pos = new PosterOutputStream();
        byte[] data = {10, 20, 30};
        pos.close();
        pos.write(data, 0, 3); // should have no effect
        byte[] buffer = pos.toByteArray();
        assert buffer.length == 0 : "Expected length 0 after close, got " + buffer.length;
    }
}
