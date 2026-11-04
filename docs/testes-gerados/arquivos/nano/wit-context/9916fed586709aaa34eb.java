/*
 * @test
 * @summary Tests for java.util.Scanner.hasNext()
 * @run main ScannerWitupTest
 */

import java.util.*;
import java.io.*;

public class ScannerWitupTest {

    public static void main(String[] args) {
        testHasNextOpen();
        testHasNextClosed();
        testHasNextWithToken();
        testHasNextWithInput();
        testHasNextWithNullPattern();
    }

    static void testHasNextOpen() {
        String inputStr = "abc 123";
        Scanner scanner = new Scanner(inputStr);
        try {
            boolean result = scanner.hasNext();
            System.out.println("testHasNextOpen: " + result);
        } catch (Exception e) {
            System.out.println("testHasNextOpen: Exception - " + e);
        } finally {
            scanner.close();
        }
    }

    static void testHasNextClosed() {
        String inputStr = "abc 123";
        Scanner scanner = new Scanner(inputStr);
        scanner.close();
        try {
            boolean result = scanner.hasNext();
            System.out.println("testHasNextClosed: " + result);
        } catch (IllegalStateException e) {
            System.out.println("testHasNextClosed: Caught expected IllegalStateException");
        } catch (Exception e) {
            System.out.println("testHasNextClosed: Unexpected exception - " + e);
        }
    }

    static void testHasNextWithToken() {
        String inputStr = "abc";
        Scanner scanner = new Scanner(inputStr);
        try {
            boolean hasToken = scanner.hasNext();
            System.out.println("testHasNextWithToken: " + hasToken);
        } catch (Exception e) {
            System.out.println("testHasNextWithToken: Exception - " + e);
        } finally {
            scanner.close();
        }
    }

    static void testHasNextWithInput() {
        String inputStr = "123 456";
        Scanner scanner = new Scanner(inputStr);
        try {
            boolean hasNextLong = scanner.hasNextLong();
            System.out.println("testHasNextWithInput: hasNextLong - " + hasNextLong);
        } catch (Exception e) {
            System.out.println("testHasNextWithInput: Exception - " + e);
        } finally {
            scanner.close();
        }
    }

    static void testHasNextWithNullPattern() {
        String inputStr = "abc";
        Scanner scanner = new Scanner(inputStr);
        try {
            // Simulate null pattern scenario by forcibly setting delimiter to null (not directly possible)
            // Instead, test behavior when pattern is invalid by using a null pattern is not feasible; skip this test.
            // Alternatively, test that calling useDelimiter with null throws NullPointerException.
            scanner.useDelimiter((Pattern) null);
            boolean result = scanner.hasNext();
            System.out.println("testHasNextWithNullPattern: " + result);
        } catch (NullPointerException e) {
            System.out.println("testHasNextWithNullPattern: Caught expected NullPointerException");
        } catch (Exception e) {
            System.out.println("testHasNextWithNullPattern: Unexpected exception - " + e);
        } finally {
            scanner.close();
        }
    }
}
