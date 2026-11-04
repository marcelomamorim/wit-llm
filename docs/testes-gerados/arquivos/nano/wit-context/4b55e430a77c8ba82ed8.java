/*
 * @test
 * @summary Testa o método hasNextInt() da classe java.util.Scanner
 * @run main ScannerWitupTest
 */

import java.util.Scanner;
import java.io.ByteArrayInputStream;

public class ScannerWitupTest {

    public static void main(String[] args) {
        testHasNextIntWithValidInput();
        testHasNextIntWithInvalidInput();
        testHasNextIntWithEmptyInput();
    }

    private static void testHasNextIntWithValidInput() {
        String inputStr = "123 456";
        ByteArrayInputStream bais = new ByteArrayInputStream(inputStr.getBytes());
        Scanner scanner = new Scanner(bais);
        try {
            boolean result = scanner.hasNextInt();
            System.out.println("hasNextInt() with valid input: " + result);
        } finally {
            scanner.close();
        }
    }

    private static void testHasNextIntWithInvalidInput() {
        String inputStr = "abc def";
        ByteArrayInputStream bais = new ByteArrayInputStream(inputStr.getBytes());
        Scanner scanner = new Scanner(bais);
        try {
            boolean result = scanner.hasNextInt();
            System.out.println("hasNextInt() with invalid input: " + result);
        } finally {
            scanner.close();
        }
    }

    private static void testHasNextIntWithEmptyInput() {
        String inputStr = "";
        ByteArrayInputStream bais = new ByteArrayInputStream(inputStr.getBytes());
        Scanner scanner = new Scanner(bais);
        try {
            boolean result = scanner.hasNextInt();
            System.out.println("hasNextInt() with empty input: " + result);
        } finally {
            scanner.close();
        }
    }
}
