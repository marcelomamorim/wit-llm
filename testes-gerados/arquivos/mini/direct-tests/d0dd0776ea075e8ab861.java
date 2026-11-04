/*
 * @test
 * @summary Test java.util.Scanner.findWithinHorizon(Pattern,int) method
 * @run main ScannerWitupTest
 */

import java.util.Scanner;
import java.util.regex.Pattern;

public class ScannerWitupTest {
    public static void main(String[] args) throws Exception {
        testNullPatternThrows();
        testNegativeHorizonThrows();
        testFindWithinHorizonReturnsMatch();
        testFindWithinHorizonReturnsNullAtEnd();
    }

    static void testNullPatternThrows() {
        Scanner sc = new Scanner("");
        try {
            sc.findWithinHorizon(null, 10);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // expected
        } finally {
            sc.close();
        }
    }

    static void testNegativeHorizonThrows() {
        Scanner sc = new Scanner("");
        try {
            sc.findWithinHorizon(Pattern.compile(".*"), -1);
            throw new RuntimeException("Expected IllegalArgumentException not thrown");
        } catch (IllegalArgumentException expected) {
            // expected
        } finally {
            sc.close();
        }
    }

    static void testFindWithinHorizonReturnsMatch() {
        String input = "abc123def";
        Scanner sc = new Scanner(input);
        Pattern p = Pattern.compile("\\d+"); // match digits
        String result = sc.findWithinHorizon(p, 10);
        if (!"123".equals(result)) {
            throw new RuntimeException("Expected '123' but got: " + result);
        }
        sc.close();
    }

    static void testFindWithinHorizonReturnsNullAtEnd() {
        String input = "abcdef";
        Scanner sc = new Scanner(input);
        Pattern p = Pattern.compile("\\d+"); // digits not present
        String result = sc.findWithinHorizon(p, 10);
        if (result != null) {
            throw new RuntimeException("Expected null but got: " + result);
        }
        sc.close();
    }
}
