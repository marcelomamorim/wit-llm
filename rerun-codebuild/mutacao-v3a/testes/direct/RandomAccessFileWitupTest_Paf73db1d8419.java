/* @test    @summary Test for readFully method of RandomAccessFile    @run main RandomAccessFileWitupTest_Paf73db1d8419 */

import java.io.RandomAccessFile;
import java.io.EOFException;
import java.io.IOException;
import java.io.File;
import java.nio.file.Files;

public class RandomAccessFileWitupTest_Paf73db1d8419 {

    public static void main(String[] args) throws Exception {
        testReadFullyNormal();
        testReadFullyEOF();
        testReadFullyPartialRead();
    }

    static void testReadFullyNormal() throws IOException {
        File tempFile = Files.createTempFile("test", ".txt").toFile();
        try {
            try (RandomAccessFile raf = new RandomAccessFile(tempFile, "rw")) {
                byte[] data = {1, 2, 3, 4, 5};
                raf.write(data);
                raf.seek(0);
                byte[] buf = new byte[5];
                raf.readFully(buf, 0, buf.length);
                boolean success = true;
                for (int i = 0; i < buf.length; i++) {
                    if (buf[i] != data[i]) {
                        success = false;
                        break;
                    }
                }
                if (!success) {
                    throw new AssertionError("Data mismatch in testReadFullyNormal");
                }
            }
        } finally {
            tempFile.delete();
        }
    }

    static void testReadFullyEOF() throws IOException {
        File tempFile = Files.createTempFile("test", ".txt").toFile();
        try {
            try (RandomAccessFile raf = new RandomAccessFile(tempFile, "rw")) {
                byte[] data = {1, 2, 3};
                raf.write(data);
                raf.seek(0);
                byte[] buf = new byte[10];
                boolean thrown = false;
                try {
                    raf.readFully(buf, 0, buf.length);
                } catch (EOFException e) {
                    thrown = true;
                }
                if (!thrown) {
                    throw new AssertionError("EOFException not thrown in testReadFullyEOF");
                }
            }
        } finally {
            tempFile.delete();
        }
    }

    static void testReadFullyPartialRead() throws IOException {
        File tempFile = Files.createTempFile("test", ".txt").toFile();
        try {
            try (RandomAccessFile raf = new RandomAccessFile(tempFile, "rw")) {
                byte[] data = {10, 20};
                raf.write(data);
                raf.seek(0);
                byte[] buf = new byte[4];
                // Fill buffer with zeros
                for (int i = 0; i < buf.length; i++) { buf[i] = 0; }
                boolean thrown = false;
                try {
                    raf.readFully(buf, 0, buf.length);
                } catch (EOFException e) {
                    thrown = true;
                }
                if (!thrown) {
                    throw new AssertionError("EOFException not thrown in testReadFullyPartialRead");
                }
            }
        } finally {
            tempFile.delete();
        }
    }
}
