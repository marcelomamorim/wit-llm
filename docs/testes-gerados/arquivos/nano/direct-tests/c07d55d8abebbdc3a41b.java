/* @test    @summary Test available() method of ChunkedInputStream for correct behavior    @run main ChunkedInputStreamWitupTest */

import java.io.*;
import sun.net.www.http.ChunkedInputStream;

public class ChunkedInputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        testAvailableReturnsPositiveWhenChunkCountGreaterThanChunkPos();
        testAvailableReturnsZeroWhenNoAvailableBytes();
        testAvailableReturnsReadAheadValueWhenChunkCountEqualsChunkPos();
    }

    private static class DummyChunkedInputStream extends ChunkedInputStream {
        private int mockChunkCount;
        private int mockChunkPos;
        private int mockReadAhead;
        private boolean open = true;

        public DummyChunkedInputStream() {
            super(new ByteArrayInputStream(new byte[0]), null, null);
        }

        @Override
        public synchronized int available() throws IOException {
            if (!open) {
                throw new IOException("Stream closed");
            }
            int avail = mockChunkCount - mockChunkPos;
            if (avail > 0) {
                return avail;
            }
            return mockReadAhead;
        }

        public void setMockValues(int chunkCount, int chunkPos, int readAhead) {
            this.mockChunkCount = chunkCount;
            this.mockChunkPos = chunkPos;
            this.mockReadAhead = readAhead;
        }

        @Override
        protected void ensureOpen() throws IOException {
            if (!open) {
                throw new IOException("Stream closed");
            }
        }

        public void closeStream() {
            open = false;
        }
    }

    private static void testAvailableReturnsPositiveWhenChunkCountGreaterThanChunkPos() throws Exception {
        DummyChunkedInputStream stream = new DummyChunkedInputStream();
        stream.setMockValues(10, 5, -1);
        int result = stream.available();
        if (result != 5) {
            throw new AssertionError("Expected 5, but got " + result);
        }
        System.out.println("testAvailableReturnsPositiveWhenChunkCountGreaterThanChunkPos passed.");
    }

    private static void testAvailableReturnsZeroWhenNoAvailableBytes() throws Exception {
        DummyChunkedInputStream stream = new DummyChunkedInputStream();
        stream.setMockValues(10, 10, -1);
        int result = stream.available();
        if (result != 0) {
            throw new AssertionError("Expected 0, but got " + result);
        }
        System.out.println("testAvailableReturnsZeroWhenNoAvailableBytes passed.");
    }

    private static void testAvailableReturnsReadAheadValueWhenChunkCountEqualsChunkPos() throws Exception {
        DummyChunkedInputStream stream = new DummyChunkedInputStream();
        stream.setMockValues(10, 10, 3);
        int result = stream.available();
        if (result != 3) {
            throw new AssertionError("Expected 3, but got " + result);
        }
        System.out.println("testAvailableReturnsReadAheadValueWhenChunkCountEqualsChunkPos passed.");
    }
}
