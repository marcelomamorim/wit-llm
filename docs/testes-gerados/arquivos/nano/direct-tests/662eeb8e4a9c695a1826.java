/*
 * @test
 * @summary Test the match() method of java.util.Scanner for correct behavior.
 * @run main ScannerWitupTest
 */

import java.util.*;
import java.io.*;

public class ScannerWitupTest {

    public static void main(String[] args) {
        testMatchWithValidState();
        testMatchWithInvalidState();
    }

    private static void testMatchWithValidState() {
        String inputStr = "abc";
        Scanner scanner = new Scanner(inputStr);
        try {
            // Use findInLine to set matchValid to true
        
            // Since match() relies on internal matchValid, simulate a match result
            // by calling findInLine with a pattern that matches the entire input
            scanner.findInLine("abc");
            MatchResult result = scanner.match();
            if (result == null) {
                throw new AssertionError("Expected non-null MatchResult");
            }
            if (!result.group().equals("abc")) {
                throw new AssertionError("Expected group to be 'abc'");
            }
            System.out.println("testMatchWithValidState passed.");
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in testMatchWithValidState: " + e);
        } finally {
            scanner.close();
        }
    }

    private static void testMatchWithInvalidState() {
        String inputStr = "xyz";
        Scanner scanner = new Scanner(inputStr);
        try {
            // Do not perform any matching operation, so matchValid remains false
            // forcibly set matchValid to false to simulate invalid state
            // Since matchValid is private, simulate by closing scanner and calling match()
            scanner.close();
            try {
                scanner.match();
                throw new AssertionError("Expected IllegalStateException");
            } catch (IllegalStateException e) {
                System.out.println("testMatchWithInvalidState passed.");
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in testMatchWithInvalidState: " + e);
        }
    }
}
