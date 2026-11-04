/* @test    @summary Test for ProcessBuilder.to(File) method handling null input    @run main ProcessBuilderWitupTest */

import java.io.File;

public class ProcessBuilderWitupTest {

    public static void main(String[] args) {
        testToWithNullFile();
        testToWithValidFile();
    }

    public static void testToWithNullFile() {
        try {
            ProcessBuilder.to(null);
            System.err.println("FAIL: Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown as expected");
        } catch (Throwable t) {
            System.err.println("FAIL: Unexpected exception: " + t);
        }
    }

    public static void testToWithValidFile() {
        try {
            File file = new File("testfile.txt");
            Object redirect = ProcessBuilder.to(file);
            if (redirect != null && redirect.toString().contains("redirect to write to file \"testfile.txt\"")) {
                System.out.println("PASS: Valid file redirect created successfully");
            } else {
                System.err.println("FAIL: Redirect object invalid or toString() mismatch");
            }
        } catch (Throwable t) {
            System.err.println("FAIL: Unexpected exception: " + t);
        }
    }
}
