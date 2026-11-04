/*
 * @test
 * @summary PrintStream(File) writes to the given file and rejects null or unreachable files
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
        testNullFileThrowsNPE();
        testMissingParentThrowsFileNotFound();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static String readFile(File f) throws IOException {
        FileInputStream in = new FileInputStream(f);
        try {
            byte[] buf = new byte[64];
            int n = in.read(buf, 0, buf.length);
            return n <= 0 ? "" : new String(buf, 0, n, "US-ASCII");
        } finally {
            in.close();
        }
    }

    static void testWritesToFile() throws IOException {
        File f = File.createTempFile("witup", ".txt");
        try {
            PrintStream ps = new PrintStream(f);
            ps.print("abc");
            check(!ps.checkError(), "unexpected checkError after print");
            ps.close();
            check("abc".equals(readFile(f)), "file content mismatch");
        } finally {
            f.delete();
        }
    }

    static void testNullFileThrowsNPE() {
        boolean thrown = false;
        try {
            new PrintStream((File) null);
        } catch (NullPointerException e) {
            thrown = true;
        } catch (FileNotFoundException e) {
            throw new RuntimeException("expected NullPointerException, got FileNotFoundException", e);
        }
        check(thrown, "null File must throw NullPointerException");
    }

    static void testMissingParentThrowsFileNotFound() {
        File f = new File("no-such-dir-witup-xyz", "out.txt");
        boolean thrown = false;
        try {
            new PrintStream(f);
        } catch (FileNotFoundException e) {
            thrown = true;
        }
        check(thrown, "file in missing directory must throw FileNotFoundException");
    }
}
