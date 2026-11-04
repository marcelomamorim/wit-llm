/*
 * @test
 * @summary Witup-generated behavioral tests for RandomAccessFile.writeChars
 * @run main RandomAccessFileWitupTest
 */

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class RandomAccessFileWitupTest {

    public static void main(String[] args) throws Exception {
        testWriteAscii();
        testEmptyString();
        testNonAsciiChar();
        testNullStringThrowsNpe();
    }

    static File newTempFile() throws IOException {
        File f = File.createTempFile("raf-writechars", ".bin");
        f.deleteOnExit();
        return f;
    }

    static byte[] writeCharsAndRead(String s) throws IOException {
        File f = newTempFile();
        try (RandomAccessFile raf = new RandomAccessFile(f, "rw")) {
            raf.writeChars(s);
        }
        byte[] data = new byte[(int) f.length()];
        try (RandomAccessFile r = new RandomAccessFile(f, "r")) {
            r.readFully(data);
        }
        f.delete();
        return data;
    }

    static void assertBytes(byte[] expected, byte[] actual) {
        if (expected.length != actual.length) {
            throw new RuntimeException("length expected " + expected.length
                    + " but was " + actual.length);
        }
        for (int i = 0; i < expected.length; i++) {
            if (expected[i] != actual[i]) {
                throw new RuntimeException("byte " + i + " expected " + expected[i]
                        + " but was " + actual[i]);
            }
        }
    }

    static void testWriteAscii() throws IOException {
        byte[] actual = writeCharsAndRead("AB");
        byte[] expected = {0x00, 0x41, 0x00, 0x42};
        assertBytes(expected, actual);
    }

    static void testEmptyString() throws IOException {
        byte[] actual = writeCharsAndRead("");
        assertBytes(new byte[0], actual);
    }

    static void testNonAsciiChar() throws IOException {
        String s = String.valueOf((char) 0x20AC);
        byte[] actual = writeCharsAndRead(s);
        byte[] expected = {(byte) 0x20, (byte) 0xAC};
        assertBytes(expected, actual);
    }

    static void testNullStringThrowsNpe() throws IOException {
        File f = newTempFile();
        try (RandomAccessFile raf = new RandomAccessFile(f, "rw")) {
            try {
                raf.writeChars(null);
                throw new RuntimeException("expected NullPointerException");
            } catch (NullPointerException expected) {
                // expected behavior
            }
        } finally {
            f.delete();
        }
    }
}
