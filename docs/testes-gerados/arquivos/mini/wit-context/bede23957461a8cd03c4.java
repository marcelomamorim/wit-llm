/*
 * @test
 * @summary Test deterministic behavior of RandomAccessFile.writeChars(String)
 * @run main RandomAccessFileWitupTest
 */

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class RandomAccessFileWitupTest {
    public static void main(String[] args) throws Exception {
        testWriteCharsNormal();
        testWriteCharsEmptyString();
        testWriteCharsNullString();
    }

    private static void testWriteCharsNormal() throws IOException {
        File tempFile = File.createTempFile("rafwctest", ".tmp");
        tempFile.deleteOnExit();

        RandomAccessFile raf = new RandomAccessFile(tempFile, "rw");
        String testString = "ABCD";
        raf.writeChars(testString);
        raf.seek(0);

        // Read back bytes and verify encoding
        byte[] expected = new byte[testString.length() * 2];
        for (int i = 0; i < testString.length(); i++) {
            char c = testString.charAt(i);
            expected[2 * i] = (byte) (c >>> 8);
            expected[2 * i + 1] = (byte) (c);
        }

        byte[] actual = new byte[expected.length];
        raf.readFully(actual);

        for (int i = 0; i < expected.length; i++) {
            if (actual[i] != expected[i]) {
                throw new AssertionError("Byte mismatch at index " + i + ": expected " + expected[i] + ", got " + actual[i]);
            }
        }

        raf.close();
    }

    private static void testWriteCharsEmptyString() throws IOException {
        File tempFile = File.createTempFile("rafwctest_empty", ".tmp");
        tempFile.deleteOnExit();

        RandomAccessFile raf = new RandomAccessFile(tempFile, "rw");
        String empty = "";
        raf.writeChars(empty);
        if (raf.length() != 0) {
            throw new AssertionError("File length should be 0 after writing empty string");
        }
        raf.close();
    }

    private static void testWriteCharsNullString() {
        File tempFile = null;
        RandomAccessFile raf = null;
        try {
            tempFile = File.createTempFile("rafwctest_null", ".tmp");
            tempFile.deleteOnExit();
            raf = new RandomAccessFile(tempFile, "rw");
            raf.writeChars(null);
            throw new AssertionError("Expected NullPointerException when writing null string");
        } catch (NullPointerException npe) {
            // Expected
        } catch (IOException ioe) {
            throw new AssertionError("Unexpected IOException instead of NullPointerException", ioe);
        } finally {
            if (raf != null) {
                try {
                    raf.close();
                } catch (IOException ignored) {
                }
            }
        }
    }
}
