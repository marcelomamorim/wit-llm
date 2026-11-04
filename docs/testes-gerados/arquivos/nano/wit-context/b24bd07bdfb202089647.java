/*
 * @test
 * @summary Test for Scanner.hasNextLong()
 * @run main ScannerWitupTest
 */

import java.util.*;

public class ScannerWitupTest {

    public static void main(String[] args) {
        testHasNextLongDefaultRadix();
        testHasNextLongWithRadix();
    }

    private static void testHasNextLongDefaultRadix() {
        Scanner scanner = new Scanner("1234567890");
        boolean result = scanner.hasNextLong();
        System.out.println("hasNextLong() with default radix: " + result);
        scanner.close();
    }

    private static void testHasNextLongWithRadix() {
        Scanner scanner = new Scanner("1234567890");
        try {
            boolean result = scanner.hasNextLong(10);
            System.out.println("hasNextLong(10): " + result);
        } catch (IllegalArgumentException e) {
            System.out.println("Caught IllegalArgumentException for radix 10");
        }
        scanner.close();
    }
}
