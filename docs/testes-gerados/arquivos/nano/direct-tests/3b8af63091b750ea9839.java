/* @test    @summary Test readChar() method of RandomAccessFile for normal and EOF conditions    @run main RandomAccessFileWitupTest */

import java.io.*;

public class RandomAccessFileWitupTest {

    public static void main(String[] args) throws Exception {
        testReadCharNormal();
        testReadCharEOF();
    }

    private static void testReadCharNormal() throws Exception {
        File tempFile = File.createTempFile("test", ".txt");
        tempFile.deleteOnExit();
        try (RandomAccessFile raf = new RandomAccessFile(tempFile, "rw")) {
            raf.writeChar('A');
            raf.writeChar('B');
            raf.seek(0);
            char c1 = raf.readChar();
            char c2 = raf.readChar();
            assert c1 == 'A' : "Expected 'A', got " + c1;
            assert c2 == 'B' : "Expected 'B', got " + c2;
        }
    }

    private static void testReadCharEOF() throws Exception {
        File tempFile = File.createTempFile("testEOF", ".txt");
        tempFile.deleteOnExit();
        try (RandomAccessFile raf = new RandomAccessFile(tempFile, "rw")) {
            raf.writeChar('X');
            raf.seek(0);
            raf.readChar(); // read 'X'
            try {
                raf.readChar(); // should throw EOFException
                assert false : "Expected EOFException";
            } catch (EOFException e) {
                // expected
            }
        }
    }
}
