/*
 * @test
 * @summary Test java.util.Scanner.match() method behavior
 * @run main ScannerWitupTest
 */

import java.util.Scanner;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class ScannerWitupTest {
    public static void main(String[] args) throws Exception {
        testMatchWithoutFind();
        testMatchAfterFind();
    }

    private static void testMatchWithoutFind() {
        Scanner scanner = new Scanner("");
        boolean thrown = false;
        try {
            scanner.match();
        } catch (IllegalStateException e) {
            if ("No match result available".equals(e.getMessage())) {
                thrown = true;
            }
        }
        if (!thrown) {
            throw new RuntimeException("Expected IllegalStateException was not thrown");
        }
        scanner.close();
    }

    private static void testMatchAfterFind() {
        String input = "abc123def";
        Scanner scanner = new Scanner(input);
        // Use a pattern and find to set matchValid and matcher
        Pattern p = Pattern.compile("\\d+");
        Matcher m = p.matcher(input);
        if (m.find()) {
            // Use reflection to set private fields matchValid and matcher
            // Because match() depends on these private fields
            try {
                java.lang.reflect.Field matchValidField = Scanner.class.getDeclaredField("matchValid");
                matchValidField.setAccessible(true);
                matchValidField.setBoolean(scanner, true);

                java.lang.reflect.Field matcherField = Scanner.class.getDeclaredField("matcher");
                matcherField.setAccessible(true);
                matcherField.set(scanner, m);
            } catch (ReflectiveOperationException e) {
                throw new RuntimeException("Reflection failed", e);
            }

            MatchResult result = scanner.match();
            if (result.groupCount() != 1 || !"123".equals(result.group(0))) {
                throw new RuntimeException("Unexpected match result");
            }
        } else {
            throw new RuntimeException("Pattern did not find a match");
        }
        scanner.close();
    }
}
