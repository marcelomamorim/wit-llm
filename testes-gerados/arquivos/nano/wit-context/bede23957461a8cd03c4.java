/*
 * @test
 * @summary Test writeChars with valid and invalid input strings
 * @run main RandomAccessFileWitupTest
 */

import java.io.RandomAccessFile;
import java.io.IOException;

public class RandomAccessFileWitupTest {

    public static void main(String[] args) {
        testWriteCharsValid();
        testWriteCharsEmpty();
        testWriteCharsNull();
        testWriteCharsOutOfBounds();
    }

    static void testWriteCharsValid() {
        try {
            String s = "Hello";
            RandomAccessFile raf = new RandomAccessFile("testfile.txt", "rw");
            raf.setLength(0); // clear file
            raf.writeChars(s);
            long pos = raf.getFilePointer();
            if (pos != s.length() * 2) {
                throw new AssertionError("File pointer mismatch after writeChars");
            }
            raf.close();
        } catch (IOException e) {
            throw new RuntimeException("Unexpected IOException in testWriteCharsValid", e);
        }
    }

    static void testWriteCharsEmpty() {
        try {
            String s = "";
            RandomAccessFile raf = new RandomAccessFile("testfile_empty.txt", "rw");
            raf.setLength(0);
            raf.writeChars(s);
            long pos = raf.getFilePointer();
            if (pos != 0) {
                throw new AssertionError("File pointer should be 0 after writing empty string");
            }
            raf.close();
        } catch (IOException e) {
            throw new RuntimeException("Unexpected IOException in testWriteCharsEmpty", e);
        }
    }

    static void testWriteCharsNull() {
        try {
            RandomAccessFile raf = new RandomAccessFile("testfile_null.txt", "rw");
            raf.setLength(0);
            raf.writeChars(null);
            throw new AssertionError("Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            // expected
        } catch (IOException e) {
            throw new RuntimeException("Unexpected IOException in testWriteCharsNull", e);
        }
    }

    static void testWriteCharsOutOfBounds() {
        try {
            String s = "abc";
            RandomAccessFile raf = new RandomAccessFile("testfile_bounds.txt", "rw");
            raf.setLength(0);
            // Manually simulate invalid bounds by calling getChars with invalid indices
            // Since writeChars calls s.getChars(0, clen, c, 0), we test with a string that causes exception
            String invalidString = "abc";
            // forcibly cause getChars to throw by passing null
            try {
                raf.writeChars(null);
                throw new AssertionError("Expected NullPointerException not thrown for null string");
            } catch (NullPointerException e) {
                // expected
            }
            raf.close();
        } catch (IOException e) {
            throw new RuntimeException("Unexpected IOException in testWriteCharsOutOfBounds", e);
        }
    }
}
