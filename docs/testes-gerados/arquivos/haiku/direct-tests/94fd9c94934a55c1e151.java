/*
 * @test
 * @summary Verifies buffering, flush-on-full, byte truncation and IOException propagation in BufferedOutputStream.write(int)
 * @run main BufferedOutputStreamWitupTest
 */

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class BufferedOutputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        testBytesStayBufferedUntilFlush();
        testBufferFullTriggersFlushOfPreviousBytes();
        testOnlyLowEightBitsAreStored();
        testSizeOneFlushesPreviousByte();
        testIOExceptionFromFlushBufferPropagates();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testBytesStayBufferedUntilFlush() throws IOException {
        ByteArrayOutputStream sink = new ByteArrayOutputStream();
        BufferedOutputStream bos = new BufferedOutputStream(sink, 4);
        bos.write(1);
        bos.write(2);
        check(sink.size() == 0, "bytes must remain buffered before flush");
        bos.flush();
        byte[] data = sink.toByteArray();
        check(data.length == 2, "expected 2 bytes after flush, got " + data.length);
        check(data[0] == 1 && data[1] == 2, "unexpected content after flush");
    }

    static void testBufferFullTriggersFlushOfPreviousBytes() throws IOException {
        ByteArrayOutputStream sink = new ByteArrayOutputStream();
        BufferedOutputStream bos = new BufferedOutputStream(sink, 2);
        bos.write(1);
        bos.write(2);
        check(sink.size() == 0, "buffer not yet full, nothing should be written");
        bos.write(3);
        check(sink.size() == 2, "full buffer must be flushed before storing new byte, size=" + sink.size());
        byte[] afterFull = sink.toByteArray();
        check(afterFull[0] == 1 && afterFull[1] == 2, "flushed content mismatch");
        bos.flush();
        byte[] all = sink.toByteArray();
        check(all.length == 3 && all[2] == 3, "final content mismatch after flush");
    }

    static void testOnlyLowEightBitsAreStored() throws IOException {
        ByteArrayOutputStream sink = new ByteArrayOutputStream();
        BufferedOutputStream bos = new BufferedOutputStream(sink, 8);
        bos.write(0x1FF);
        bos.flush();
        byte[] data = sink.toByteArray();
        check(data.length == 1, "expected exactly one byte, got " + data.length);
        check(data[0] == (byte) 0xFF, "only low 8 bits must be stored");
    }

    static void testSizeOneFlushesPreviousByte() throws IOException {
        ByteArrayOutputStream sink = new ByteArrayOutputStream();
        BufferedOutputStream bos = new BufferedOutputStream(sink, 1);
        bos.write(7);
        check(sink.size() == 0, "first byte must stay buffered");
        bos.write(8);
        check(sink.size() == 1 && sink.toByteArray()[0] == 7, "previous byte must be flushed");
        bos.flush();
        byte[] all = sink.toByteArray();
        check(all.length == 2 && all[0] == 7 && all[1] == 8, "final content mismatch");
    }

    static void testIOExceptionFromFlushBufferPropagates() throws IOException {
        OutputStream failing = new OutputStream() {
            @Override
            public void write(int b) throws IOException {
                throw new IOException("boom");
            }
        };
        BufferedOutputStream bos = new BufferedOutputStream(failing, 1);
        bos.write(1);
        boolean thrown = false;
        try {
            bos.write(2);
        } catch (IOException expected) {
            thrown = true;
        }
        check(thrown, "IOException from underlying stream must propagate");
    }
}
