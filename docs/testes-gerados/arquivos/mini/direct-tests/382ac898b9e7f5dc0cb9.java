/*
 * @test
 * @summary Test PrintStream(File) constructor behavior
 * @run main PrintStreamWitupTest
 */

import java.io.File;
import java.io.PrintStream;
import java.io.FileNotFoundException;

public class PrintStreamWitupTest {
    public static void main(String[] args) throws Exception {
        testValidFile();
        testNonExistentFile();
    }

    private static void testValidFile() throws Exception {
        File tempFile = File.createTempFile("PrintStreamTest", ".txt");
        tempFile.deleteOnExit();
        PrintStream ps = new PrintStream(tempFile);
        ps.print("test");
        ps.close();
        if (ps.checkError()) {
            throw new RuntimeException("PrintStream reports error after writing to valid file");
        }
    }

    private static void testNonExistentFile() {
        File dir = new File("nonexistent_dir_for_printstream_test");
        // Ensure directory does not exist
        if (dir.exists()) {
            if (!dir.delete()) {
                throw new RuntimeException("Failed to delete test directory");
            }
        }
        File fileInNonExistentDir = new File(dir, "file.txt");
        try {
            new PrintStream(fileInNonExistentDir);
            throw new RuntimeException("Expected FileNotFoundException not thrown");
        } catch (FileNotFoundException e) {
            // Expected exception
        }
    }
}
