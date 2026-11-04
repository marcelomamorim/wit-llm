/* @test    @summary Test ProcessBuilder.to(File) method for null and valid File inputs    @run main ProcessBuilderWitupTest */

import java.io.File;
import java.lang.ProcessBuilder;

public class ProcessBuilderWitupTest {

    public static void main(String[] args) {
        testToWithNull();
        testToWithValidFile();
    }

    static void testToWithNull() {
        try {
            ProcessBuilder.Redirect result = ProcessBuilder.to(null);
            // Should throw NullPointerException, so if we reach here, test fails
            System.err.println("testToWithNull: Failed - no exception thrown");
        } catch (NullPointerException e) {
            System.out.println("testToWithNull: Passed");
        } catch (Throwable t) {
            System.err.println("testToWithNull: Failed - unexpected exception: " + t);
        }
    }

    static void testToWithValidFile() {
        try {
            File file = new File("testfile.txt");
            ProcessBuilder.Redirect redirect = ProcessBuilder.to(file);
            if (redirect != null && redirect.type() == ProcessBuilder.Redirect.Type.WRITE && redirect.file().equals(file)) {
                System.out.println("testToWithValidFile: Passed");
            } else {
                System.err.println("testToWithValidFile: Failed - incorrect redirect properties");
            }
        } catch (Throwable t) {
            System.err.println("testToWithValidFile: Failed - exception: " + t);
        }
    }
}
