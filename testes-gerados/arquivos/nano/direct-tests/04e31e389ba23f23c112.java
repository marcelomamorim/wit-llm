/*
 * @test
 * @summary Test hasNextShort() method of java.util.Scanner
 * @run main ScannerWitupTest
 */

import java.util.Scanner;
import java.io.*;

public class ScannerWitupTest {
    public static void main(String[] args) {
        testHasNextShortWithValidInput();
        testHasNextShortWithInvalidInput();
        testHasNextShortWithEmptyInput();
    }

    private static void testHasNextShortWithValidInput() {
        String inputStr = "32767"; // Max value for short
        try (Scanner scanner = new Scanner(new StringReader(inputStr))) {
            boolean result = scanner.hasNextShort();
            System.out.println("hasNextShort with valid input: " + result); // Expect true
        } catch (Exception e) {
            System.err.println("Exception in testHasNextShortWithValidInput: " + e);
        }
    }

    private static void testHasNextShortWithInvalidInput() {
        String inputStr = "notANumber";
        try (Scanner scanner = new Scanner(new StringReader(inputStr))) {
            boolean result = scanner.hasNextShort();
            System.out.println("hasNextShort with invalid input: " + result); // Expect false
        } catch (Exception e) {
            System.err.println("Exception in testHasNextShortWithInvalidInput: " + e);
        }
    }

    private static void testHasNextShortWithEmptyInput() {
        String inputStr = "";
        try (Scanner scanner = new Scanner(new StringReader(inputStr))) {
            boolean result = scanner.hasNextShort();
            System.out.println("hasNextShort with empty input: " + result); // Expect false
        } catch (Exception e) {
            System.err.println("Exception in testHasNextShortWithEmptyInput: " + e);
        }
    }
}
