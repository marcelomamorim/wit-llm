/*
 * @test
 * @summary Behavioral tests for File.renameTo(File dest)
 * @run main FileWitupTest
 */

import java.io.File;
import java.io.IOException;

public class FileWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testRenameExistingFile() throws IOException {
        File src = File.createTempFile("witup", ".src");
        File dst = new File(src.getParentFile(), "witup_dst_" + System.nanoTime() + ".dst");
        try {
            check(src.exists(), "source should exist before rename");
            check(!dst.exists(), "destination should not exist before rename");
            check(src.renameTo(dst), "renameTo should return true for existing source");
            check(!src.exists(), "source should no longer exist after rename");
            check(dst.exists(), "destination should exist after rename");
        } finally {
            src.delete();
            dst.delete();
        }
    }

    static void testRenameNonExistingSource() throws IOException {
        File dir = File.createTempFile("witup", ".dir");
        dir.delete();
        dir.mkdir();
        try {
            File src = new File(dir, "missing_" + System.nanoTime());
            File dst = new File(dir, "target_" + System.nanoTime());
            check(!src.renameTo(dst), "renameTo should return false when source does not exist");
            check(!dst.exists(), "destination should not be created");
        } finally {
            new File(dir, "").delete();
            dir.delete();
        }
    }

    static void testInvalidSourceReturnsFalse() throws IOException {
        File dst = File.createTempFile("witup", ".dst");
        try {
            File src = new File("bad\0name");
            check(!src.renameTo(new File(dst.getParentFile(), "witup_other_" + System.nanoTime())),
                  "renameTo should return false for invalid source path");
        } finally {
            dst.delete();
        }
    }

    static void testInvalidDestinationReturnsFalseAndKeepsSource() throws IOException {
        File src = File.createTempFile("witup", ".src");
        try {
            File dst = new File("bad\0dest");
            check(!src.renameTo(dst), "renameTo should return false for invalid destination path");
            check(src.exists(), "source should still exist when rename fails");
        } finally {
            src.delete();
        }
    }

    static void testNullDestinationThrowsNPE() throws IOException {
        File src = File.createTempFile("witup", ".src");
        try {
            boolean thrown = false;
            try {
                src.renameTo(null);
            } catch (NullPointerException e) {
                thrown = true;
            }
            check(thrown, "renameTo(null) should throw NullPointerException");
            check(src.exists(), "source should be untouched when destination is null");
        } finally {
            src.delete();
        }
    }

    public static void main(String[] args) throws IOException {
        testRenameExistingFile();
        testRenameNonExistingSource();
        testInvalidSourceReturnsFalse();
        testInvalidDestinationReturnsFalseAndKeepsSource();
        testNullDestinationThrowsNPE();
    }
}
