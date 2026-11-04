/*
 * @test
 * @summary Verifies FileOutputStream(String) truncates existing files and reports missing parent directories
 * @run main FileOutputStreamWitupTest
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileOutputStreamWitupTest {

    public static void main(String[] args) throws IOException {
        testStringConstructorTruncatesExistingFile();
        testStringConstructorWritesBytes();
        testStringConstructorMissingParentDirectory();
    }

    static void testStringConstructorTruncatesExistingFile() throws IOException {
        File f = File.createTempFile("fos_trunc", ".txt");
        f.deleteOnExit();
        try (FileOutputStream seed = new FileOutputStream(f)) {
            seed.write("oldcontent".getBytes("US-ASCII"));
        }
        try (FileOutputStream out = new FileOutputStream(f.getPath())) {
            out.write("new".getBytes("US-ASCII"));
        }
        String actual = readAll(f);
        if (!"new".equals(actual)) {
            throw new RuntimeException("Expected truncated content 'new' but was '" + actual + "'");
        }
    }

    static void testStringConstructorWritesBytes() throws IOException {
        File f = File.createTempFile("fos_write", ".bin");
        f.deleteOnExit();
        try (FileOutputStream out = new FileOutputStream(f.getPath())) {
            out.write(new byte[] {1, 2, 3, 4});
        }
        byte[] data;
        try (FileInputStream in = new FileInputStream(f)) {
            data = in.readAllBytes();
        }
        if (data.length != 4 || data[0] != 1 || data[1] != 2 || data[2] != 3 || data[3] != 4) {
            throw new RuntimeException("Unexpected bytes written: length=" + data.length);
        }
    }

    static void testStringConstructorMissingParentDirectory() throws IOException {
        File tmp = File.createTempFile("fos_parent", "");
        tmp.delete();
        File missing = new File(new File(tmp, "no-such-subdir"), "file.txt");
        try {
            new FileOutputStream(missing.getPath());
            throw new RuntimeException("Expected FileNotFoundException for missing parent directory");
        } catch (FileNotFoundException expected) {
            // expected
        }
    }

    static String readAll(File f) throws IOException {
        try (FileInputStream in = new FileInputStream(f)) {
            return new String(in.readAllBytes(), "US-ASCII");
        }
    }
}
