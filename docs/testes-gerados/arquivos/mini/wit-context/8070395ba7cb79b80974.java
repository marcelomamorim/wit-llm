/*
 * @test
 * @summary Test java.util.Scanner.findWithinHorizon(String,int) for closed scanner and normal usage
 * @run main ScannerWitupTest
 */

import java.util.Scanner;
import java.io.StringReader;

public class ScannerWitupTest {
    public static void main(String[] args) throws Exception {
        testFindWithinHorizonClosedScanner();
        testFindWithinHorizonNormal();
    }

    private static void testFindWithinHorizonClosedScanner() {
        Scanner sc = new Scanner(new StringReader("test input"));
        sc.close();
        try {
            sc.findWithinHorizon("test", 10);
            throw new RuntimeException("Expected IllegalStateException for closed scanner");
        } catch (IllegalStateException expected) {
            // expected
        }
    }

    private static void testFindWithinHorizonNormal() {
        Scanner sc = new Scanner(new StringReader("abc 123 xyz"));
        String found = sc.findWithinHorizon("\\d+", 10);
        if (!"123".equals(found)) {
            throw new RuntimeException("Expected to find '123' but found: " + found);
        }
        sc.close();
    }
}
