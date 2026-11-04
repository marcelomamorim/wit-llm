/* @test    @summary Test constructor of ImageStream with ByteBuffer parameter    @run main ImageStreamWitupTest */

import java.nio.ByteBuffer;
import java.util.Objects;

public class ImageStreamWitupTest {

    public static void main(String[] args) {
        testConstructorWithByteBuffer();
    }

    public static void testConstructorWithByteBuffer() {
        ByteBuffer buffer = ByteBuffer.allocate(10);
        buffer.putInt(12345);
        buffer.flip();
        ImageStream stream = new ImageStream(buffer);
        Objects.requireNonNull(stream, "ImageStream instance should not be null");
        // Verify that the buffer inside ImageStream is the same as the one passed
        assert stream.buffer.equals(buffer) : "Buffer inside ImageStream should be the same as the one passed";
    }
}
