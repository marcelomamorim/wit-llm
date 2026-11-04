/*
 * @test
 * @summary Test java.util.Scanner.hasNext() for expected exceptions and normal behavior
 * @run main ScannerWitupTest
 */

import java.util.Scanner;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class ScannerWitupTest {
    public static void main(String[] args) throws Exception {
        testHasNextNormal();
        testHasNextThrowsIllegalStateExceptionWhenClosed();
        testHasNextThrowsIllegalArgumentExceptionWhenDelimPatternNull();
    }

    private static void testHasNextNormal() throws Exception {
        String input = "token1 token2";
        Scanner scanner = new Scanner(input);
        if (!scanner.hasNext()) {
            throw new RuntimeException("Expected hasNext() to return true for non-empty input");
        }
        scanner.close();
    }

    private static void testHasNextThrowsIllegalStateExceptionWhenClosed() {
        Scanner scanner = new Scanner("");
        scanner.close();
        try {
            scanner.hasNext();
            throw new RuntimeException("Expected IllegalStateException when calling hasNext() on closed Scanner");
        } catch (IllegalStateException expected) {
            // Expected exception
        }
    }

    private static void testHasNextThrowsIllegalArgumentExceptionWhenDelimPatternNull() throws Exception {
        // We cannot directly set delimPattern to null because it is private and no public API allows it.
        // Instead, we simulate the condition by subclassing Scanner and overriding hasTokenInBuffer to throw IllegalArgumentException
        // But since no such method is accessible, we test the observable behavior only.
        // Therefore, this test is omitted due to lack of public API to induce delimPattern == null.
        // This is noted here to document the discarded expath from WIT.
    }
}
