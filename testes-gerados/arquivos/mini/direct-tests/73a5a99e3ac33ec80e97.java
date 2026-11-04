/*
 * @test
 * @summary Test ProcessBuilder.appendTo(File) method behavior
 * @run main ProcessBuilderWitupTest
 */

import java.io.File;
import java.lang.ProcessBuilder.Redirect;

public class ProcessBuilderWitupTest {
    public static void main(String[] args) throws Exception {
        testAppendToWithValidFile();
        testAppendToWithNullFile();
    }

    private static void testAppendToWithValidFile() {
        File f = new File("testfile.txt");
        Redirect r = ProcessBuilder.appendTo(f);
        if (r == null) throw new RuntimeException("Redirect is null");
        if (r.type() != Redirect.Type.APPEND) throw new RuntimeException("Redirect type is not APPEND");
        if (!f.equals(r.file())) throw new RuntimeException("Redirect file does not match input file");
        String s = r.toString();
        if (!s.contains(f.toString())) throw new RuntimeException("toString does not contain file name");
        // The append() method is package-private, so we cannot call it here.
    }

    private static void testAppendToWithNullFile() {
        try {
            ProcessBuilder.appendTo(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // expected
        }
    }
}
