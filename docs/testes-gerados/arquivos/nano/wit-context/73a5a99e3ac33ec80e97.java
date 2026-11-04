/* @test    @summary Test for ProcessBuilder.appendTo(final File file) method.
 * @run main ProcessBuilderWitupTest
 */

import java.io.File;
import java.lang.ProcessBuilder;

public class ProcessBuilderWitupTest {

    public static void main(String[] args) {
        testAppendToWithNullFile();
        testAppendToWithValidFile();
    }

    private static void testAppendToWithNullFile() {
        try {
            ProcessBuilder.appendTo(null);
            System.err.println("Failed: Expected NullPointerException for null file");
        } catch (NullPointerException e) {
            System.out.println("Passed: NullPointerException thrown as expected for null file");
        } catch (Throwable t) {
            System.err.println("Failed: Unexpected exception type: " + t);
        }
    }

    private static void testAppendToWithValidFile() {
        File file = new File("testfile.txt");
        try {
            ProcessBuilder.Redirect redirect = ProcessBuilder.appendTo(file);
            if (redirect != null && redirect.type() == ProcessBuilder.Redirect.Type.APPEND && redirect.file().equals(file)) {
                System.out.println("Passed: appendTo with valid file returns correct Redirect");
            } else {
                System.err.println("Failed: appendTo with valid file did not return expected Redirect");
            }
        } catch (Throwable t) {
            System.err.println("Failed: Exception thrown during valid appendTo: " + t);
        }
    }
}
