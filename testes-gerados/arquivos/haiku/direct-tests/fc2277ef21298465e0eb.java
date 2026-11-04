/*
 * @test
 * @summary Verifies that PrintStream(String fileName) writes to the named file and rejects targets that cannot be opened
 * @run main PrintStreamWitupTest
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintStream;

public class PrintStreamWitupTest {

    public static void main(String[] args) throws Exception {
        testWritesToNamedFile();
        testDirectoryTargetThrows();
        testMissingParentDirectoryThrows();
    }

    static void testWritesToNamedFile() throws Exception {
        String name = "PrintStreamWitupTest_out.txt";
        File f = new File(name);
        if (f.exists()) {
            f.delete();
        }
        try (PrintStream ps = new PrintStream(name)) {
            ps.print("abc");
        }
        byte[] data;
        try (FileInputStream in = new FileInputStream(f)) {
            data = in.readAllBytes();
        }
        f.delete();
        if (data.length != 3 || data[0] != 'a' || data[1] != 'b' || data[2] != 'c') {
            throw new RuntimeException("Unexpected file content length: " + data.length);
        }
    }

    static void testDirectoryTargetThrows() {
        boolean thrown = false;
        try {
            new PrintStream(".");
        } catch (FileNotFoundException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("FileNotFoundException expected for directory target");
        }
    }

    static void testMissingParentDirectoryThrows() {
        boolean thrown = false;
        try {
            new PrintStream("no_such_dir_witup_xyz/out.txt");
        } catch (FileNotFoundException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("FileNotFoundException expected for missing parent directory");
        }
    }
}
