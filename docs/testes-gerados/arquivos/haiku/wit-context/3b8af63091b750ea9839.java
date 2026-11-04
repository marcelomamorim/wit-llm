/*
 * @test
 * @summary Tests RandomAccessFile.readChar() for EOF and big-endian decoding behavior
 * @run main RandomAccessFileWitupTest
 */

import java.io.EOFException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;

public class RandomAccessFileWitupTest {

    public static void main(String[] args) throws Exception {
        testEmptyFileThrowsEOF();
        testSingleByteFileThrowsEOF();
        testTwoBytesDecodedBigEndian();
        testWriteCharReadCharRoundTrip();
        testSecondReadAfterExhaustionThrowsEOF();
    }

    static File newTempFile() throws IOException {
        File f = File.createTempFile("raf_witup", ".bin");
        f.deleteOnExit();
        return f;
    }

    static void writeBytes(File f, byte[] data) throws IOException {
        try (FileOutputStream out = new FileOutputStream(f)) {
            out.write(data);
        }
    }

    static void testEmptyFileThrowsEOF() throws IOException {
        File f = newTempFile();
        writeBytes(f, new byte[0]);
        try (RandomAccessFile raf = new RandomAccessFile(f, "r")) {
            raf.readChar();
            throw new RuntimeException("expected EOFException for empty file");
        } catch (EOFException expected) {
        } finally {
            f.delete();
        }
    }

    static void testSingleByteFileThrowsEOF() throws IOException {
        File f = newTempFile();
        writeBytes(f, new byte[] { 0x12 });
        try (RandomAccessFile raf = new RandomAccessFile(f, "r")) {
            raf.readChar();
            throw new RuntimeException("expected EOFException for one-byte file");
        } catch (EOFException expected) {
        } finally {
            f.delete();
        }
    }

    static void testTwoBytesDecodedBigEndian() throws IOException {
        File f = newTempFile();
        writeBytes(f, new byte[] { 0x12, 0x34 });
        try (RandomAccessFile raf = new RandomAccessFile(f, "r")) {
            char c = raf.readChar();
            if (c != (char) 0x1234) {
                throw new RuntimeException("unexpected char: 0x" + Integer.toHexString(c));
            }
        } finally {
            f.delete();
        }
    }

    static void testWriteCharReadCharRoundTrip() throws IOException {
        File f = newTempFile();
        try (RandomAccessFile raf = new RandomAccessFile(f, "rw")) {
            raf.writeChar('Z');
            raf.seek(0);
            char c = raf.readChar();
            if (c != 'Z') {
                throw new RuntimeException("round trip failed: " + c);
            }
        } finally {
            f.delete();
        }
    }

    static void testSecondReadAfterExhaustionThrowsEOF() throws IOException {
        File f = newTempFile();
        writeBytes(f, new byte[] { 0x00, 0x41, 0x42 });
        try (RandomAccessFile raf = new RandomAccessFile(f, "r")) {
            char first = raf.readChar();
            if (first != 'A') {
                throw new RuntimeException("unexpected first char: " + first);
            }
            raf.readChar();
            throw new RuntimeException("expected EOFException on exhausted stream");
        } catch (EOFException expected) {
        } finally {
            f.delete();
        }
    }
}
