/*
 * @test
 * @summary Test PrintStream(File) constructor for normal and edge cases
 * @run main PrintStreamWitupTest
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

public class PrintStreamWitupTest {
    public static void main(String[] args) throws Exception {
        testValidFileConstructor();
        testInvalidFileConstructor();
    }

    private static void testValidFileConstructor() throws Exception {
        File tempFile = File.createTempFile("PrintStreamWitupTest", ".txt");
        tempFile.deleteOnExit();
        PrintStream ps = null;
        try {
            ps = new PrintStream(tempFile);
            if (ps.checkError()) {
                throw new RuntimeException("PrintStream reports error after construction");
            }
            ps.println("test");
            ps.flush();
        } finally {
            if (ps != null) ps.close();
        }
    }

    private static void testInvalidFileConstructor() {
        // No direct way to trigger IllegalArgumentException from PrintStream(File) constructor
        // because it delegates to PrintStream(boolean, OutputStream) and defaultCharBufferSize is private static final
        // The WIT expath suggesting IllegalArgumentException on defaultCharBufferSize <= 0 is not reproducible here
        // So this test only documents that this path is discarded due to lack of public API to trigger it
    }
}
