/*
 * @test
 * @summary Test for ImageHeader.writeTo(ByteBuffer buffer)
 * @run main ImageHeaderWitupTest
 */

import java.nio.ByteBuffer;
import java.util.Objects;

public class ImageHeaderWitupTest {

    public static void main(String[] args) {
        testWriteToWithNullBuffer();
        testWriteToWithValidBuffer();
    }

    private static void testWriteToWithNullBuffer() {
        try {
            jdk.internal.jimage.ImageHeader header = new jdk.internal.jimage.ImageHeader(1, 1, 0, 0, 0, 0, 0, 0);
            header.writeTo(null);
            System.out.println("FAIL: Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown as expected");
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception: " + t);
        }
    }

    private static void testWriteToWithValidBuffer() {
        try {
            jdk.internal.jimage.ImageHeader header = new jdk.internal.jimage.ImageHeader(42, 1, 0, 0, 5, 10, 15, 20);
            ByteBuffer buffer = ByteBuffer.allocate(jdk.internal.jimage.ImageHeader.getHeaderSize());
            header.writeTo(buffer);
            buffer.flip();
            int magic = buffer.getInt();
            int version = buffer.getInt();
            int majorVersion = version >>> 16;
            int minorVersion = version & 0xFFFF;
            int flags = buffer.getInt();
            int resourceCount = buffer.getInt();
            int tableLength = buffer.getInt();
            int locationsSize = buffer.getInt();
            int stringsSize = buffer.getInt();

            boolean condition = (magic == jdk.internal.jimage.ImageHeader.MAGIC) &&
                                (majorVersion == 1) &&
                                (minorVersion == 0) &&
                                (flags == 0) &&
                                (resourceCount == 5) &&
                                (tableLength == 10) &&
                                (locationsSize == 15) &&
                                (stringsSize == 20);
            if (condition) {
                System.out.println("PASS: Buffer written correctly");
            } else {
                System.out.println("FAIL: Buffer contents incorrect");
            }
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception: " + t);
        }
    }
}
