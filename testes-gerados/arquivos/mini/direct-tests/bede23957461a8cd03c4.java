/*
 * @test
 * @summary Test RandomAccessFile.writeChars(String) method
 * @run main RandomAccessFileWitupTest
 */

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class RandomAccessFileWitupTest {
    public static void main(String[] args) throws IOException {
        testWriteCharsBasic();
        testWriteCharsEmptyString();
        testWriteCharsUnicode();
    }

    private static void testWriteCharsBasic() throws IOException {
        File file = File.createTempFile("rafwctest", ".tmp");
        file.deleteOnExit();
        RandomAccessFile raf = new RandomAccessFile(file, "rw");
        String testString = "ABCD";
        raf.writeChars(testString);
        raf.seek(0);
        // Each char is 2 bytes, big-endian
        for (int i = 0; i < testString.length(); i++) {
            int high = raf.readUnsignedByte();
            int low = raf.readUnsignedByte();
            char c = (char)((high << 8) + low);
            if (c != testString.charAt(i)) {
                throw new AssertionError("Mismatch at index " + i + ": expected '" + testString.charAt(i) + "' but got '" + c + "'");
            }
        }
        raf.close();
    }

    private static void testWriteCharsEmptyString() throws IOException {
        File file = File.createTempFile("rafwctest", ".tmp");
        file.deleteOnExit();
        RandomAccessFile raf = new RandomAccessFile(file, "rw");
        String testString = "";
        raf.writeChars(testString);
        if (raf.length() != 0) {
            throw new AssertionError("File length should be 0 for empty string writeChars");
        }
        raf.close();
    }

    private static void testWriteCharsUnicode() throws IOException {
        File file = File.createTempFile("rafwctest", ".tmp");
        file.deleteOnExit();
        RandomAccessFile raf = new RandomAccessFile(file, "rw");
        String testString = "\u00A9\u20AC\uD834\uDD1E"; // ©, €, 𝄞 (surrogate pair)
        raf.writeChars(testString);
        raf.seek(0);
        for (int i = 0; i < testString.length(); i++) {
            int high = raf.readUnsignedByte();
            int low = raf.readUnsignedByte();
            char c = (char)((high << 8) + low);
            if (c != testString.charAt(i)) {
                throw new AssertionError("Mismatch at index " + i + ": expected '" + testString.charAt(i) + "' but got '" + c + "'");
            }
        }
        raf.close();
    }
}
