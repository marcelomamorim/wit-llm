/* @test    @summary Test for Scanner.findWithinHorizon to verify behavior when Scanner is closed.    @run main ScannerWitupTest */

import java.util.Scanner;
import java.io.*;

public class ScannerWitupTest {

    public static void main(String[] args) {
        testFindWithinHorizonWhenClosed();
    }

    private static void testFindWithinHorizonWhenClosed() {
        String pattern = "\\d+";
        int horizon = 10;
        Scanner scanner = new Scanner("12345 67890");
        scanner.close();
        try {
            scanner.findWithinHorizon(pattern, horizon);
            System.err.println("Expected IllegalStateException was not thrown.");
            System.exit(1);
        } catch (IllegalStateException e) {
            // Expected exception, test passes
            System.out.println("Caught expected IllegalStateException.");
        } catch (Exception e) {
            System.err.println("Unexpected exception: " + e);
            System.exit(1);
        }
    }
}
