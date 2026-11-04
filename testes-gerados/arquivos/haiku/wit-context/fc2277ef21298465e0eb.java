/*
 * @test
 * @summary Witup-generated checks for PrintStream(String fileName) constructor: file output, missing directory and null file name
 * @run main PrintStreamWitupTest
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;

public class PrintStreamWitupTest {

    public static void main(String[] args) throws Exception {
        testWritesToFile();
        testMissingDirectoryThrows();
        testNullFileNameThrows();
    }

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    static void testWritesToFile() throws IOException {
        File f = File.createTempFile("printstream-witup", ".txt");
        try {
            PrintStream ps = new PrintStream(f.getPath());
            ps.print("hello");
            ps.close();

            FileInputStream in = new FileInputStream(f);
            byte[] buf = new byte[64];
            int n = in.read(buf);
            in.close();

            check(n == 5, "expected 5 bytes but got " + n);
            check(new String(buf, 0, n, "US-ASCII").equals("hello"), "unexpected content");
        } finally {
            f.delete();
        }
    }

    static void testMissingDirectoryThrows() {
        try {
            new PrintStream("witup-no-such-dir/out.txt");
            throw new RuntimeException("expected FileNotFoundException");
        } catch (FileNotFoundException expected) {
            // expected
        }
    }

    static void testNullFileNameThrows() {
        try {
            new PrintStream((String) null);
            throw new RuntimeException("expected NullPointerException");
        } catch (NullPointerException expected) {
            // expected
        }
    }
}
