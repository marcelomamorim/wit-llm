/*
 * @test
 * @summary Test java.util.Scanner.hasNext(String) for normal and closed scanner states
 * @run main java.util.ScannerWitupTest
 */

import java.util.Scanner;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

public class ScannerWitupTest {
    public static void main(String[] args) throws Exception {
        testHasNextWithValidPattern();
        testHasNextOnClosedScannerThrows();
    }

    private static void testHasNextWithValidPattern() {
        String input = "hello 123";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));
        boolean result = scanner.hasNext("\\w+"); // pattern to match words
        if (!result) {
            throw new AssertionError("Expected hasNext to return true for pattern matching input");
        }
        scanner.close();
    }

    private static void testHasNextOnClosedScannerThrows() {
        String input = "test";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));
        scanner.close();
        try {
            scanner.hasNext("test");
            throw new AssertionError("Expected IllegalStateException when calling hasNext on closed scanner");
        } catch (IllegalStateException expected) {
            // Expected exception
        }
    }
}
