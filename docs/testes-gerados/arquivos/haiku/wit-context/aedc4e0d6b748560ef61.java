/*
 * @test
 * @summary WITUP-derived tests for FileOutputStream(String name) exception behavior
 * @run main FileOutputStreamWitupTest
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileOutputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        testNullNameThrowsNullPointerException();
        testDirectoryPathThrowsFileNotFoundException();
        testMissingParentDirectoryThrowsFileNotFoundException();
        testValidNameCreatesFileAndWritesBytes();
    }

    static void testNullNameThrowsNullPointerException() {
        try {
            new FileOutputStream((String) null);
        } catch (NullPointerException expected) {
            return;
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Expected NullPointerException, got FileNotFoundException", e);
        }
        throw new RuntimeException("Expected NullPointerException for null name");
    }

    static void testDirectoryPathThrowsFileNotFoundException() {
        try {
            new FileOutputStream(".");
        } catch (FileNotFoundException expected) {
            return;
        }
        throw new RuntimeException("Expected FileNotFoundException when opening a directory");
    }

    static void testMissingParentDirectoryThrowsFileNotFoundException() {
        String name = "no_such_dir_witup_fos/child.bin";
        File parent = new File("no_such_dir_witup_fos");
        if (parent.exists()) {
            throw new RuntimeException("Precondition failed: parent directory exists");
        }
        try {
            new FileOutputStream(name);
        } catch (FileNotFoundException expected) {
            return;
        }
        throw new RuntimeException("Expected FileNotFoundException for missing parent directory");
    }

    static void testValidNameCreatesFileAndWritesBytes() throws IOException {
        File f = new File("FileOutputStreamWitupTest.tmp");
        if (f.exists() && !f.delete()) {
            throw new RuntimeException("Cannot delete stale file");
        }
        try {
            try (FileOutputStream out = new FileOutputStream(f.getPath())) {
                out.write(new byte[] {1, 2, 3});
            }
            if (!f.exists()) {
                throw new RuntimeException("File was not created");
            }
            if (f.length() != 3L) {
                throw new RuntimeException("Unexpected file length: " + f.length());
            }
            try (FileInputStream in = new FileInputStream(f)) {
                if (in.read() != 1 || in.read() != 2 || in.read() != 3) {
                    throw new RuntimeException("Unexpected file content");
                }
            }
        } finally {
            if (f.exists() && !f.delete()) {
                throw new RuntimeException("Cannot delete test file");
            }
        }
    }
}
