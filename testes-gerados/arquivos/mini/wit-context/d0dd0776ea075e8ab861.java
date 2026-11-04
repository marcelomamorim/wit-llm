/*
 * @test
 * @summary Test java.util.Scanner.findWithinHorizon(Pattern,int) for exceptions and basic behavior
 * @run main ScannerWitupTest
 */

import java.util.Scanner;
import java.util.regex.Pattern;
import java.io.StringReader;

public class ScannerWitupTest {
    public static void main(String[] args) throws Exception {
        testFindWithinHorizonClosedScanner();
        testFindWithinHorizonNullPattern();
        testFindWithinHorizonNegativeHorizon();
        testFindWithinHorizonBasicMatch();
    }

    private static void testFindWithinHorizonClosedScanner() {
        Scanner sc = new Scanner(new StringReader("test input"));
        sc.close();
        try {
            sc.findWithinHorizon(Pattern.compile("test"), 10);
            throw new RuntimeException("Expected IllegalStateException for closed scanner");
        } catch (IllegalStateException expected) {
            // expected
        }
    }

    private static void testFindWithinHorizonNullPattern() {
        Scanner sc = new Scanner(new StringReader("test input"));
        try {
            sc.findWithinHorizon(null, 10);
            throw new RuntimeException("Expected NullPointerException for null pattern");
        } catch (NullPointerException expected) {
            // expected
        } finally {
            sc.close();
        }
    }

    private static void testFindWithinHorizonNegativeHorizon() {
        Scanner sc = new Scanner(new StringReader("test input"));
        try {
            sc.findWithinHorizon(Pattern.compile("test"), -1);
            throw new RuntimeException("Expected IllegalArgumentException for negative horizon");
        } catch (IllegalArgumentException expected) {
            // expected
        } finally {
            sc.close();
        }
    }

    private static void testFindWithinHorizonBasicMatch() {
        String input = "abc 123 def";
        Scanner sc = new Scanner(input);
        Pattern p = Pattern.compile("\\d+");
        String found = sc.findWithinHorizon(p, 100);
        if (!"123".equals(found)) {
            throw new RuntimeException("Expected to find '123' but found: " + found);
        }
        sc.close();
    }
}
