/*
 * @test
 * @summary Test java.util.Scanner.findInLine(String) for closed scanner IllegalStateException
 * @run main ScannerWitupTest
 */

import java.util.Scanner;
import java.io.StringReader;

public class ScannerWitupTest {
    public static void main(String[] args) throws Exception {
        testFindInLineClosedScanner();
        testFindInLineBasicFunctionality();
    }

    private static void testFindInLineClosedScanner() {
        Scanner scanner = new Scanner(new StringReader("test input"));
        scanner.close();
        try {
            scanner.findInLine("test");
            throw new RuntimeException("Expected IllegalStateException for closed scanner");
        } catch (IllegalStateException expected) {
            // Expected exception
        }
    }

    private static void testFindInLineBasicFunctionality() {
        String input = "123 abc 456";
        Scanner scanner = new Scanner(input);
        String found = scanner.findInLine("\\d+");
        if (!"123".equals(found)) {
            throw new RuntimeException("Expected to find '123' but found: " + found);
        }
        scanner.close();
    }
}
