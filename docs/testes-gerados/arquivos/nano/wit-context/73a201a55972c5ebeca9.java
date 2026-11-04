/* @test    @summary Test for ImageHeader.writeTo with null stream    @run main ImageHeaderWitupTest */

import jdk.internal.jimage.ImageHeader;
import jdk.internal.jimage.ImageStream;
import java.nio.ByteBuffer;

public class ImageHeaderWitupTest {

    public static void main(String[] args) {
        testWriteToNullStream();
        testWriteToValidStream();
    }

    private static void testWriteToNullStream() {
        try {
            ImageHeader header = new ImageHeader(1, 1, 0, 0, 1, 1, 1, 1);
            header.writeTo((ImageStream) null);
            System.out.println("FAIL: Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown as expected");
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception: " + t);
        }
    }

    private static void testWriteToValidStream() {
        try {
            ImageHeader header = new ImageHeader(2, 1, 0, 0, 2, 2, 2, 2);
            ImageStream stream = new ImageStream();
            header.writeTo(stream);
            ByteBuffer buffer = stream.getBuffer();
            if (buffer.capacity() >= ImageHeader.getHeaderSize()) {
                System.out.println("PASS: writeTo completed without exception");
            } else {
                System.out.println("FAIL: Buffer capacity insufficient after write");
            }
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception: " + t);
        }
    }

    // Mock implementation of ImageStream for testing purposes
    static class ImageStream {
        private ByteBuffer buffer = ByteBuffer.allocate(64);
        public ByteBuffer getBuffer() { return buffer; }
        public void ensure(int size) {
            if (buffer.capacity() < size) {
                buffer = ByteBuffer.allocate(size * 2);
            }
        }
    }
}
