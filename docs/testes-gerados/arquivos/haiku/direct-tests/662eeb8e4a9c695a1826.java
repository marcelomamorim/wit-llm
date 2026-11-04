/*
 * @test
 * @summary Scanner.match() must throw IllegalStateException when no valid match exists and return the last match result otherwise
 * @run main ScannerWitupTest
 */
import java.util.Scanner;
import java.util.regex.MatchResult;

public class ScannerWitupTest {
    public static void main(String[] args) {
        testNoMatchBeforeSearch();
        testMatchAfterFindInLine();
        testGroupCount();
        testMatchAfterFailedSearch();
        testMatchInvalidatedByFailedSearch();
    }

    static void testNoMatchBeforeSearch() {
        Scanner s = new Scanner("1 fish");
        expectNoMatch(s);
    }

    static void testMatchAfterFindInLine() {
        Scanner s = new Scanner("1 fish 2 fish");
        if (s.findInLine("(\\d+) fish") == null) {
            throw new RuntimeException("findInLine should have matched");
        }
        MatchResult r = s.match();
        if (!"1 fish".equals(r.group())) {
            throw new RuntimeException("unexpected group(): " + r.group());
        }
        if (!"1".equals(r.group(1))) {
            throw new RuntimeException("unexpected group(1): " + r.group(1));
        }
    }

    static void testGroupCount() {
        Scanner s = new Scanner("1 fish 2 fish");
        if (s.findInLine("(\\d+) fish (\\d+)") == null) {
            throw new RuntimeException("findInLine should have matched");
        }
        MatchResult r = s.match();
        if (r.groupCount() != 2) {
            throw new RuntimeException("unexpected groupCount: " + r.groupCount());
        }
        if (!"2".equals(r.group(2))) {
            throw new RuntimeException("unexpected group(2): " + r.group(2));
        }
    }

    static void testMatchAfterFailedSearch() {
        Scanner s = new Scanner("abc");
        if (s.findInLine("\\d+") != null) {
            throw new RuntimeException("findInLine should not have matched");
        }
        expectNoMatch(s);
    }

    static void testMatchInvalidatedByFailedSearch() {
        Scanner s = new Scanner("1 fish x");
        if (s.findInLine("(\\d+) fish") == null) {
            throw new RuntimeException("first findInLine should have matched");
        }
        if (s.findInLine("\\d+") != null) {
            throw new RuntimeException("second findInLine should not have matched");
        }
        expectNoMatch(s);
    }

    static void expectNoMatch(Scanner s) {
        try {
            s.match();
        } catch (IllegalStateException e) {
            if (!"No match result available".equals(e.getMessage())) {
                throw new RuntimeException("unexpected message: " + e.getMessage());
            }
            return;
        }
        throw new RuntimeException("expected IllegalStateException from match()");
    }
}
