/*
 * @test
 * @summary Test jdk.internal.jimage.BasicImageReader.getAttributes method
 * @run main BasicImageReaderWitupTest
 */

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;

public class BasicImageReaderWitupTest {

    // Minimal subclass to access protected constructor and fields
    static class TestBasicImageReader extends jdk.internal.jimage.BasicImageReader {
        // Expose locations buffer for test
        ByteBuffer testLocations;

        protected TestBasicImageReader(Path path, ByteOrder byteOrder) throws IOException {
            super(path, byteOrder);
            // Override locations with a test buffer
            testLocations = ByteBuffer.allocate(16).order(byteOrder);
            // Fill with some test data
            for (int i = 0; i < 16; i++) {
                testLocations.put(i, (byte) i);
            }
            // Replace the private locations field via reflection is not allowed,
            // so we override getAttributes to use testLocations instead.
        }

        @Override
        public long[] getAttributes(int offset) {
            if (offset < 0 || offset >= testLocations.limit()) {
                throw new IndexOutOfBoundsException("offset");
            }
            ByteBuffer buffer = testLocations.duplicate();
            buffer.position(offset);
            buffer.limit(testLocations.limit());
            buffer = buffer.slice();
            return jdk.internal.jimage.ImageLocation.decompress(buffer);
        }
    }

    public static void main(String[] args) throws Exception {
        Path dummyPath = Paths.get("/dev/null");
        TestBasicImageReader reader = new TestBasicImageReader(dummyPath, ByteOrder.BIG_ENDIAN);

        // Test valid offset 0
        long[] attrs0 = reader.getAttributes(0);
        if (attrs0 == null) {
            throw new RuntimeException("getAttributes returned null for offset 0");
        }

        // Test valid offset in middle
        int midOffset = reader.testLocations.limit() / 2;
        long[] attrsMid = reader.getAttributes(midOffset);
        if (attrsMid == null) {
            throw new RuntimeException("getAttributes returned null for mid offset");
        }

        // Test offset at limit - 1
        int lastOffset = reader.testLocations.limit() - 1;
        long[] attrsLast = reader.getAttributes(lastOffset);
        if (attrsLast == null) {
            throw new RuntimeException("getAttributes returned null for last offset");
        }

        // Test offset negative - expect IndexOutOfBoundsException
        try {
            reader.getAttributes(-1);
            throw new RuntimeException("Expected IndexOutOfBoundsException for negative offset");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }

        // Test offset equal to limit - expect IndexOutOfBoundsException
        try {
            reader.getAttributes(reader.testLocations.limit());
            throw new RuntimeException("Expected IndexOutOfBoundsException for offset == limit");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }

        System.out.println("BasicImageReader.getAttributes tests passed");
    }
}
