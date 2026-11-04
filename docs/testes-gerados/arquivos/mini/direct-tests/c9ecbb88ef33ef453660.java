/*
 * @test
 * @summary Test jdk.internal.jimage.ImageHeader.writeTo(ByteBuffer) method
 * @run main ImageHeaderWitupTest
 */

import java.nio.ByteBuffer;
import jdk.internal.jimage.ImageHeader;

public class ImageHeaderWitupTest {
    public static void main(String[] args) {
        testWriteToWritesCorrectData();
        testWriteToNullBufferThrowsNPE();
    }

    private static void testWriteToWritesCorrectData() {
        int resourceCount = 10;
        int tableLength = 20;
        int locationsSize = 30;
        int stringsSize = 40;

        ImageHeader header = new ImageHeader(resourceCount, tableLength, locationsSize, stringsSize);

        ByteBuffer buffer = ByteBuffer.allocate(ImageHeader.getHeaderSize());

        header.writeTo(buffer);

        buffer.flip();

        int magic = buffer.getInt();
        int version = buffer.getInt();
        int flags = buffer.getInt();
        int resCount = buffer.getInt();
        int tblLength = buffer.getInt();
        int locSize = buffer.getInt();
        int strSize = buffer.getInt();

        if (magic != ImageHeader.MAGIC) {
            throw new AssertionError("magic mismatch: expected " + ImageHeader.MAGIC + " but got " + magic);
        }

        int expectedVersion = (ImageHeader.MAJOR_VERSION << 16) | ImageHeader.MINOR_VERSION;
        if (version != expectedVersion) {
            throw new AssertionError("version mismatch: expected " + expectedVersion + " but got " + version);
        }

        if (flags != 0) {
            throw new AssertionError("flags mismatch: expected 0 but got " + flags);
        }

        if (resCount != resourceCount) {
            throw new AssertionError("resourceCount mismatch: expected " + resourceCount + " but got " + resCount);
        }

        if (tblLength != tableLength) {
            throw new AssertionError("tableLength mismatch: expected " + tableLength + " but got " + tblLength);
        }

        if (locSize != locationsSize) {
            throw new AssertionError("locationsSize mismatch: expected " + locationsSize + " but got " + locSize);
        }

        if (strSize != stringsSize) {
            throw new AssertionError("stringsSize mismatch: expected " + stringsSize + " but got " + strSize);
        }
    }

    private static void testWriteToNullBufferThrowsNPE() {
        ImageHeader header = new ImageHeader(1, 1, 1, 1);
        try {
            header.writeTo(null);
            throw new AssertionError("Expected NullPointerException when passing null buffer");
        } catch (NullPointerException npe) {
            // expected
        }
    }
}
