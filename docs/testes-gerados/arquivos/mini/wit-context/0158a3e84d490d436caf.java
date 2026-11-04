/*
 * @test
 * @summary Deterministic tests for java.util.Scanner.nextLine()
 * @run main ScannerWitupTest
 */

import java.util.Scanner;
import java.util.NoSuchElementException;
import java.io.StringReader;

public class ScannerWitupTest {
    public static void main(String[] args) throws Exception {
        testNextLineBasic();
        testNextLineNoSuchElementException();
    }

    private static void testNextLineBasic() {
        String input = "line1\nline2\nline3";
        Scanner scanner = new Scanner(new StringReader(input));
        String line1 = scanner.nextLine();
        if (!"line1".equals(line1)) {
            throw new RuntimeException("Expected 'line1' but got: " + line1);
        }
        String line2 = scanner.nextLine();
        if (!"line2".equals(line2)) {
            throw new RuntimeException("Expected 'line2' but got: " + line2);
        }
        String line3 = scanner.nextLine();
        if (!"line3".equals(line3)) {
            throw new RuntimeException("Expected 'line3' but got: " + line3);
        }
        scanner.close();
    }

    private static void testNextLineNoSuchElementException() {
        Scanner scanner = new Scanner("");
        boolean thrown = false;
        try {
            scanner.nextLine();
        } catch (NoSuchElementException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected NoSuchElementException when calling nextLine() on empty input");
        }
        scanner.close();
    }
}
