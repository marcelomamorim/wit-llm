/*
 * @test
 * @summary Verify observable behavior of the FileOutputStream(File) constructor
 * @run main FileOutputStreamWitupTest
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileOutputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        testNullFileThrowsNPE();
        testMissingParentThrowsFNFE();
        testDirectoryThrowsFNFE();
        testCreatesAndWritesFile();
        testTruncatesExistingFile();
        System.out.println("PASSED");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNullFileThrowsNPE() {
        try {
            new FileOutputStream((File) null);
            throw new RuntimeException("expected NullPointerException");
        } catch (NullPointerException expected) {
        } catch (FileNotFoundException e) {
            throw new RuntimeException("unexpected FileNotFoundException", e);
        }
    }

    static void testMissingParentThrowsFNFE() {
        File base = new File(System.getProperty("java.io.tmpdir"));
        File missingParent = new File(base, "nodir_" + System.nanoTime());
        File target = new File(missingParent, "x.txt");
        try {
            new FileOutputStream(target);
            throw new RuntimeException("expected FileNotFoundException");
        } catch (FileNotFoundException expected) {
        }
    }

    static void testDirectoryThrowsFNFE() throws IOException {
        File dir = File.createTempFile("fosw", "d");
        check(dir.delete(), "could not delete temp file");
        check(dir.mkdir(), "could not create temp directory");
        try {
            new FileOutputStream(dir);
            throw new RuntimeException("expected FileNotFoundException for directory");
        } catch (FileNotFoundException expected) {
        } finally {
            dir.delete();
        }
    }

    static void testCreatesAndWritesFile() throws IOException {
        File f = File.createTempFile("fosw", ".bin");
        try {
            try (FileOutputStream out = new FileOutputStream(f)) {
                out.write(new byte[] {1, 2, 3});
            }
            byte[] data = readAll(f);
            check(data.length == 3, "expected 3 bytes but got " + data.length);
            check(data[0] == 1 && data[1] == 2 && data[2] == 3, "unexpected content");
        } finally {
            f.delete();
        }
    }

    static void testTruncatesExistingFile() throws IOException {
        File f = File.createTempFile("fosw", ".bin");
        try {
            try (FileOutputStream out = new FileOutputStream(f)) {
                out.write(new byte[] {'a', 'b', 'c', 'd', 'e', 'f'});
            }
            try (FileOutputStream out = new FileOutputStream(f)) {
                out.write(9);
            }
            byte[] data = readAll(f);
            check(data.length == 1, "expected truncation to 1 byte but got " + data.length);
            check(data[0] == 9, "unexpected content after truncation");
        } finally {
            f.delete();
        }
    }

    static byte[] readAll(File f) throws IOException {
        try (FileInputStream in = new FileInputStream(f)) {
            byte[] buf = new byte[64];
            int total = 0;
            int n;
            while ((n = in.read(buf, total, buf.length - total)) > 0) {
                total += n;
                if (total == buf.length) {
                    byte[] bigger = new byte[buf.length * 2];
                    System.arraycopy(buf, 0, bigger, 0, total);
                    buf = bigger;
                }
            }
            byte[] result = new byte[total];
            System.arraycopy(buf, 0, result, 0, total);
            return result;
        }
    }
}
