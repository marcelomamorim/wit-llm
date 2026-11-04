/*
 * @test
 * @summary Deterministic tests for java.util.Scanner.next(Pattern) method
 * @run main ScannerWitupTest
 */

import java.util.Scanner;
import java.util.regex.Pattern;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

public class ScannerWitupTest {
    public static void main(String[] args) throws Exception {
        testNextWithClosedScanner();
        testNextWithNullPattern();
        testNextWithValidPattern();
        testNextWithIllegalArgumentExceptionScenario();
    }

    static void testNextWithClosedScanner() {
        Scanner sc = new Scanner("test input");
        sc.close();
        try {
            sc.next(Pattern.compile(".*"));
            throw new RuntimeException("Expected IllegalStateException for closed scanner");
        } catch (IllegalStateException expected) {
            // expected
        }
    }

    static void testNextWithNullPattern() {
        Scanner sc = new Scanner("test input");
        try {
            sc.next(null);
            throw new RuntimeException("Expected NullPointerException for null pattern");
        } catch (NullPointerException expected) {
            // expected
        } finally {
            sc.close();
        }
    }

    static void testNextWithValidPattern() {
        String input = "one two three";
        Scanner sc = new Scanner(input);
        Pattern p = Pattern.compile("\\w+");
        String first = sc.next(p);
        if (!"one".equals(first)) {
            throw new RuntimeException("Expected 'one' but got: " + first);
        }
        String second = sc.next(p);
        if (!"two".equals(second)) {
            throw new RuntimeException("Expected 'two' but got: " + second);
        }
        sc.close();
    }

    static void testNextWithIllegalArgumentExceptionScenario() throws Exception {
        // This test attempts to trigger IllegalArgumentException by using a pattern
        // different from hasNextPattern when delimPattern is null.
        // Since delimPattern and hasNextPattern are internal, we simulate by creating
        // a Scanner on an InputStream with a charset and using a pattern that is unlikely
        // to match the delimiter, forcing internal state to cause the exception.
        // However, as internal state is not accessible, we test that no unexpected exception
        // is thrown and next returns a token or throws as per contract.

        String input = "token1 token2";
        ByteArrayInputStream bais = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
        Scanner sc = new Scanner(bais, "UTF-8");
        Pattern pattern1 = Pattern.compile("token1");
        Pattern pattern2 = Pattern.compile("token2");

        // Call next with pattern1 to cache result
        String t1 = sc.next(pattern1);
        if (!"token1".equals(t1)) {
            throw new RuntimeException("Expected 'token1' but got: " + t1);
        }

        // Now call next with a different pattern to force internal state change
        // According to source, this may throw IllegalArgumentException if delimPattern == null
        // but since delimPattern is internal, we just check behavior.
        try {
            String t2 = sc.next(pattern2);
            if (!"token2".equals(t2)) {
                throw new RuntimeException("Expected 'token2' but got: " + t2);
            }
        } catch (IllegalArgumentException iae) {
            // Acceptable as per documented expath
        } finally {
            sc.close();
        }
    }
}
