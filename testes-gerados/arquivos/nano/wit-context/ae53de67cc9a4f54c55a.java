/*
 * @test
 * @summary Test for java.util.Scanner.skip(String pattern)
 * @run main ScannerWitupTest
 */

import java.util.Scanner;
import java.io.*;

public class ScannerWitupTest {
    public static void main(String[] args) {
        testSkipClosedScanner();
        testSkipOpenScanner();
    }

    private static void testSkipClosedScanner() {
        ByteArrayInputStream bais = new ByteArrayInputStream(new byte[0]);
        Scanner scanner = new Scanner(bais);
        scanner.close();
        try {
            scanner.skip("\\d+");
            // Expect no exception or specific behavior; skip() may return the scanner itself
            System.out.println("skip on closed scanner did not throw");
        } catch (IllegalStateException e) {
            System.out.println("Caught expected IllegalStateException on closed scanner");
        } catch (Exception e) {
            System.out.println("Unexpected exception: " + e);
        }
    }

    private static void testSkipOpenScanner() {
        String input = "123 abc";
        Scanner scanner = new Scanner(input);
        try {
            Scanner resultScanner = scanner.skip("\\d+");
            if (resultScanner == scanner) {
                System.out.println("skip returned the same scanner instance");
            } else {
                System.out.println("skip did not return the same scanner instance");
            }
            // Additional check: after skip, the scanner should be at position after the digits
            if (scanner.hasNext()) {
                String nextToken = scanner.next();
                System.out.println("Next token after skip: " + nextToken);
            } else {
                System.out.println("No tokens after skip");
            }
        } catch (Exception e) {
            System.out.println("Unexpected exception during skip on open scanner: " + e);
        } finally {
            scanner.close();
        }
    }
}
