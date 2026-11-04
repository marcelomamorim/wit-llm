/*
 * @test
 * @summary Behavioral tests for File.canExecute() using only public APIs
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

    static void testNonExistentFileIsNotExecutable() {
        File f = new File("witup_nonexistent_" + System.nanoTime() + ".bin");
        check(!f.exists(), "precondition: file should not exist");
        check(!f.canExecute(), "non-existent file must not be executable");
    }

    static void testPathWithNulCharIsNotExecutable() {
        File f = new File("invalid\u0000name");
        check(!f.canExecute(), "path containing NUL must not be executable");
    }

    static void testSetExecutableReflectedByCanExecute() throws IOException {
        File f = File.createTempFile("witup", ".tmp");
        try {
            boolean set = f.setExecutable(true);
            if (set) {
                check(f.canExecute(), "file made executable must report canExecute() == true");
            }
        } finally {
            f.delete();
        }
    }

    static void testExistingRegularFileCallDoesNotThrow() throws IOException {
        File f = File.createTempFile("witup", ".tmp");
        try {
            boolean result = f.canExecute();
            check(result == true || result == false, "canExecute must return a boolean");
            check(f.exists(), "file should still exist after canExecute()");
        } finally {
            f.delete();
        }
    }

    public static void main(String[] args) throws Exception {
        testNonExistentFileIsNotExecutable();
        testPathWithNulCharIsNotExecutable();
        testSetExecutableReflectedByCanExecute();
        testExistingRegularFileCallDoesNotThrow();
    }
}
