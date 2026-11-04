/*
 * @test
 * @summary Testa o método hasNext(String pattern) do java.util.Scanner
 * @run main ScannerWitupTest
 */

import java.util.Scanner;
import java.io.*;

public class ScannerWitupTest {

    public static void main(String[] args) {
        testHasNextWithOpenScanner();
        testHasNextWithClosedScanner();
    }

    private static void testHasNextWithOpenScanner() {
        String input = "sample input";
        Scanner scanner = new Scanner(new StringReader(input));
        try {
            boolean result = scanner.hasNext("\w+");
            System.out.println("hasNext with open scanner: " + result);
        } catch (Exception e) {
            System.err.println("Unexpected exception in open scanner: " + e);
        } finally {
            scanner.close();
        }
    }

    private static void testHasNextWithClosedScanner() {
        String input = "sample input";
        Scanner scanner = new Scanner(new StringReader(input));
        scanner.close();
        try {
            boolean result = scanner.hasNext("\w+");
            System.out.println("hasNext with closed scanner: " + result);
        } catch (IllegalStateException e) {
            System.out.println("Expected exception for closed scanner: " + e.getClass().getSimpleName());
        } catch (Exception e) {
            System.err.println("Unexpected exception in closed scanner: " + e);
        }
    }
}
