/*
 * @test
 * @summary Witup-style tests for PushbackInputStream.read()
 * @run main PushbackInputStreamWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;

public class PushbackInputStreamWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void checkEquals(int expected, int actual, String message) {
        if (expected != actual) {
            throw new RuntimeException(message + ": expected " + expected + " but was " + actual);
        }
    }

    static void testPassThroughMasksBytesAndReturnsEof() throws IOException {
        InputStream in = new ByteArrayInputStream(new byte[] {(byte) 0xFF, 0x7F});
        PushbackInputStream p = new PushbackInputStream(in);
        checkEquals(255, p.read(), "0xFF byte must be returned as unsigned 255");
        checkEquals(127, p.read(), "0x7F byte must be returned as 127");
        checkEquals(-1, p.read(), "end of stream must return -1");
    }

    static void testEmptyStreamReturnsMinusOne() throws IOException {
        PushbackInputStream p = new PushbackInputStream(new ByteArrayInputStream(new byte[0]));
        checkEquals(-1, p.read(), "empty underlying stream must return -1");
    }

    static void testPushedBackBytesAreReturnedFirstInLifoOrder() throws IOException {
        PushbackInputStream p = new PushbackInputStream(
                new ByteArrayInputStream(new byte[] {5}), 2);
        p.unread(0xFF);
        p.unread(7);
        checkEquals(7, p.read(), "last unread byte must be read first");
        checkEquals(255, p.read(), "pushed back 0xFF must be read as unsigned 255");
        checkEquals(5, p.read(), "underlying stream byte must follow pushback buffer");
        checkEquals(-1, p.read(), "end of stream must return -1 after draining");
    }

    static void testDefaultSizeOneBufferMasksPushedBackByte() throws IOException {
        PushbackInputStream p = new PushbackInputStream(new ByteArrayInputStream(new byte[0]));
        p.unread(0x80);
        checkEquals(128, p.read(), "pushed back 0x80 must be read as 128");
        checkEquals(-1, p.read(), "empty stream after pushback must return -1");
    }

    public static void main(String[] args) throws IOException {
        testPassThroughMasksBytesAndReturnsEof();
        testEmptyStreamReturnsMinusOne();
        testPushedBackBytesAreReturnedFirstInLifoOrder();
        testDefaultSizeOneBufferMasksPushedBackByte();
    }
}
