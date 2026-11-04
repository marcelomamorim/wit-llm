/*
 * @test
 * @summary Test ProcessBuilder.to(File) static method behavior
 * @run main ProcessBuilderWitupTest
 */

import java.io.File;
import java.lang.ProcessBuilder;
import java.lang.ProcessBuilder.Redirect;

public class ProcessBuilderWitupTest {
    public static void main(String[] args) throws Exception {
        testToWithValidFile();
        testToWithNullFile();
    }

    static void testToWithValidFile() {
        File f = new File("testfile.txt");
        Redirect r = ProcessBuilder.to(f);
        if (r == null) throw new AssertionError("Redirect should not be null");
        if (r.type() != Redirect.Type.WRITE) throw new AssertionError("Redirect type should be WRITE");
        if (!f.equals(r.file())) throw new AssertionError("Redirect file should be the same as input file");
        String s = r.toString();
        if (!s.contains(f.toString())) throw new AssertionError("toString should contain file name");
        if (r.append()) throw new AssertionError("append() should return false");
    }

    static void testToWithNullFile() {
        try {
            ProcessBuilder.to(null);
            throw new AssertionError("Expected NullPointerException for null file");
        } catch (NullPointerException expected) {
            // expected
        }
    }
}
