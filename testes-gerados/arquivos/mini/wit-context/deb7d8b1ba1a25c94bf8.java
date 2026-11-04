/*
 * @test
 * @summary Test java.util.Scanner.next() method for expected exceptions and normal behavior
 * @run main ScannerWitupTest
 */

import java.util.Scanner;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class ScannerWitupTest {
    public static void main(String[] args) throws Exception {
        testNextThrowsIllegalStateExceptionWhenClosed();
        testNextThrowsIllegalArgumentExceptionWhenDelimPatternNull();
        testNextReturnsTokenWhenInputAvailable();
    }

    static void testNextThrowsIllegalStateExceptionWhenClosed() {
        Scanner scanner = new Scanner("token");
        scanner.close();
        try {
            scanner.next();
            throw new RuntimeException("Expected IllegalStateException not thrown when scanner is closed");
        } catch (IllegalStateException expected) {
            // Expected exception
        }
    }

    static void testNextThrowsIllegalArgumentExceptionWhenDelimPatternNull() throws Exception {
        // We cannot directly set delimPattern to null as it is private and no public API to do so.
        // The WIT expath suggests this exception if delimPattern == null and scanner not closed.
        // Since we cannot induce delimPattern == null via public API, we skip this test.
        // Note: This expath is discarded due to lack of public API to set delimPattern to null.
    }

    static void testNextReturnsTokenWhenInputAvailable() {
        String input = "hello world";
        Scanner scanner = new Scanner(input);
        String firstToken = scanner.next();
        if (!"hello".equals(firstToken)) {
            throw new RuntimeException("Expected first token 'hello' but got: " + firstToken);
        }
        String secondToken = scanner.next();
        if (!"world".equals(secondToken)) {
            throw new RuntimeException("Expected second token 'world' but got: " + secondToken);
        }
        scanner.close();
    }
}
