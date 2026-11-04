/* @test    @summary Test RandomAccessFile.writeChars method for correct writing of characters to file    @run main RandomAccessFileWitupTest */

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;

public class RandomAccessFileWitupTest {

    public static void main(String[] args) throws IOException {
        testWriteCharsBasic();
        testWriteCharsEmptyString();
        testWriteCharsUnicode();
    }

    private static void testWriteCharsBasic() throws IOException {
        File tempFile = File.createTempFile("testWriteCharsBasic", ".txt");
        tempFile.deleteOnExit();
        try (RandomAccessFile raf = new RandomAccessFile(tempFile, "rw")) {
            String testString = "Hello";
            raf.writeChars(testString);
            long length = raf.length();
            if (length != testString.length() * 2) {
                throw new AssertionError("File length mismatch: expected " + (testString.length() * 2) + " but was " + length);
            }
            raf.seek(0);
            byte[] data = new byte[(int) length];
            raf.readFully(data);
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < data.length; i += 2) {
                char c = (char)(((data[i] & 0xFF) << 8) | (data[i + 1] & 0xFF));
                sb.append(c);
            }
            if (!sb.toString().equals(testString)) {
                throw new AssertionError("Content mismatch: expected '" + testString + "' but was '" + sb.toString() + "'");
            }
        }
    }

    private static void testWriteCharsEmptyString() throws IOException {
        File tempFile = File.createTempFile("testWriteCharsEmpty", ".txt");
        tempFile.deleteOnExit();
        try (RandomAccessFile raf = new RandomAccessFile(tempFile, "rw")) {
            String empty = "";
            raf.writeChars(empty);
            if (raf.length() != 0) {
                throw new AssertionError("File should be empty but length is " + raf.length());
            }
        }
    }

    private static void testWriteCharsUnicode() throws IOException {
        File tempFile = File.createTempFile("testWriteCharsUnicode", ".txt");
        tempFile.deleteOnExit();
        try (RandomAccessFile raf = new RandomAccessFile(tempFile, "rw")) {
            String unicodeStr = "こんにちは"; // Japanese for "Hello"
            raf.writeChars(unicodeStr);
            long length = raf.length();
            if (length != unicodeStr.length() * 2) {
                throw new AssertionError("File length mismatch for unicode string: expected " + (unicodeStr.length() * 2) + " but was " + length);
            }
            raf.seek(0);
            byte[] data = new byte[(int) length];
            raf.readFully(data);
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < data.length; i += 2) {
                char c = (char)(((data[i] & 0xFF) << 8) | (data[i + 1] & 0xFF));
                sb.append(c);
            }
            if (!sb.toString().equals(unicodeStr)) {
                throw new AssertionError("Unicode content mismatch: expected '" + unicodeStr + "' but was '" + sb.toString() + "'");
            }
        }
    }
}
