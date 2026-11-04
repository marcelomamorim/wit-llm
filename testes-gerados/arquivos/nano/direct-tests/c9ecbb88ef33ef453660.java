/* @test    @summary Test the writeTo(ByteBuffer) method of ImageHeader for correct buffer writing.    @run main ImageHeaderWitupTest */

import java.nio.ByteBuffer;
import java.util.Objects;

public class ImageHeaderWitupTest {

    public static void main(String[] args) {
        testWriteToWritesCorrectData();
    }

    static void testWriteToWritesCorrectData() {
        int magic = 0xCAFEDADA;
        int majorVersion = 1;
        int minorVersion = 0;
        int flags = 0;
        int resourceCount = 5;
        int tableLength = 10;
        int locationsSize = 15;
        int stringsSize = 20;

        jdk.internal.jimage.ImageHeader header = new jdk.internal.jimage.ImageHeader(
            magic, majorVersion, minorVersion, flags, resourceCount, tableLength, locationsSize, stringsSize);

        ByteBuffer buffer = ByteBuffer.allocate(jdk.internal.jimage.ImageHeader.getHeaderSize());
        header.writeTo(buffer);
        buffer.flip();

        // Verify buffer contents
        int expectedSize = jdk.internal.jimage.ImageHeader.getHeaderSize();
        if (buffer.remaining() != expectedSize) {
            throw new AssertionError("Buffer size mismatch: expected " + expectedSize + " but was " + buffer.remaining());
        }

        int readMagic = buffer.getInt();
        int version = buffer.getInt();
        int readMajorVersion = version >>> 16;
        int readMinorVersion = version & 0xFFFF;
        int readFlags = buffer.getInt();
        int readResourceCount = buffer.getInt();
        int readTableLength = buffer.getInt();
        int readLocationsSize = buffer.getInt();
        int readStringsSize = buffer.getInt();

        if (readMagic != magic) {
            throw new AssertionError("Magic mismatch: expected " + magic + " but was " + readMagic);
        }
        if (readMajorVersion != majorVersion) {
            throw new AssertionError("Major version mismatch: expected " + majorVersion + " but was " + readMajorVersion);
        }
        if (readMinorVersion != minorVersion) {
            throw new AssertionError("Minor version mismatch: expected " + minorVersion + " but was " + readMinorVersion);
        }
        if (readFlags != flags) {
            throw new AssertionError("Flags mismatch: expected " + flags + " but was " + readFlags);
        }
        if (readResourceCount != resourceCount) {
            throw new AssertionError("ResourceCount mismatch: expected " + resourceCount + " but was " + readResourceCount);
        }
        if (readTableLength != tableLength) {
            throw new AssertionError("TableLength mismatch: expected " + tableLength + " but was " + readTableLength);
        }
        if (readLocationsSize != locationsSize) {
            throw new AssertionError("LocationsSize mismatch: expected " + locationsSize + " but was " + readLocationsSize);
        }
        if (readStringsSize != stringsSize) {
            throw new AssertionError("StringsSize mismatch: expected " + stringsSize + " but was " + readStringsSize);
        }
        System.out.println("testWriteToWritesCorrectData passed.");
    }
}
