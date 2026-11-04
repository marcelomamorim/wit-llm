/*
 * @test
 * @summary Test jdk.internal.jimage.ImageHeader.writeTo(ByteBuffer) for null buffer and correct writing
 * @run main ImageHeaderWitupTest
 */

import java.nio.ByteBuffer;
import jdk.internal.jimage.ImageHeader;

public class ImageHeaderWitupTest {
    public static void main(String[] args) {
        testWriteToNullBuffer();
        testWriteToByteBufferContent();
    }

    private static void testWriteToNullBuffer() {
        ImageHeader header = new ImageHeader(1, 2, 3, 4);
        try {
            header.writeTo((ByteBuffer) null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }

    private static void testWriteToByteBufferContent() {
        int resourceCount = 10;
        int tableCount = 20;
        int locationsSize = 30;
        int stringsSize = 40;

        ImageHeader header = new ImageHeader(resourceCount, tableCount, locationsSize, stringsSize);

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
            throw new RuntimeException("Magic number mismatch: expected " + Integer.toHexString(ImageHeader.MAGIC) + ", got " + Integer.toHexString(magic));
        }

        int majorVersion = version >>> 16;
        int minorVersion = version & 0xFFFF;

        if (majorVersion != ImageHeader.MAJOR_VERSION) {
            throw new RuntimeException("Major version mismatch: expected " + ImageHeader.MAJOR_VERSION + ", got " + majorVersion);
        }

        if (minorVersion != ImageHeader.MINOR_VERSION) {
            throw new RuntimeException("Minor version mismatch: expected " + ImageHeader.MINOR_VERSION + ", got " + minorVersion);
        }

        if (flags != 0) {
            throw new RuntimeException("Flags mismatch: expected 0, got " + flags);
        }

        if (resCount != resourceCount) {
            throw new RuntimeException("Resource count mismatch: expected " + resourceCount + ", got " + resCount);
        }

        if (tblLength != tableCount) {
            throw new RuntimeException("Table length mismatch: expected " + tableCount + ", got " + tblLength);
        }

        if (locSize != locationsSize) {
            throw new RuntimeException("Locations size mismatch: expected " + locationsSize + ", got " + locSize);
        }

        if (strSize != stringsSize) {
            throw new RuntimeException("Strings size mismatch: expected " + stringsSize + ", got " + strSize);
        }
    }
}
