/*
 * @test
 * @summary PrintStream(File) writes to the given file and fails with FileNotFoundException for an unreachable path
 * @run main PrintStreamWitupTest
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintStream;

public class PrintStreamWitupTest {

    public static void main(String[] args) throws Exception {
        File f = File.createTempFile("pswitup", ".txt");
        try {
            PrintStream ps = new PrintStream(f);
            ps.print("hello");
            if (ps.checkError()) {
                throw new RuntimeException("unexpected error flag after write");
            }
            ps.close();

            byte[] data;
            try (FileInputStream in = new FileInputStream(f)) {
                data = in.readAllBytes();
            }
            String actual = new String(data, "US-ASCII");
            if (!"hello".equals(actual)) {
                throw new RuntimeException("unexpected file content: " + actual);
            }
        } finally {
            f.delete();
        }

        File missing = new File("no_such_dir_witup_xyz", "out.txt");
        try {
            new PrintStream(missing);
            throw new RuntimeException("expected FileNotFoundException");
        } catch (FileNotFoundException expected) {
            // expected
        }
    }
}
