/*
 * @test
 * @summary Behavioral checks for Pattern.compile(String) on the current checkout
 * @run main PatternWitupTest
 */

public class PatternWitupTest {

    public static void main(String[] args) {
        Pattern p = Pattern.compile("a*b");
        check(p != null, "compile returned null");
        check(p.pattern().equals("a*b"), "pattern() mismatch");
        check(p.flags() == 0, "flags() should be 0 for compile(String)");
        check(p.matcher("aaaaab").matches(), "a*b should match aaaaab");
        check(!p.matcher("aaaa").matches(), "a*b should not match aaaa");

        Pattern literalDot = Pattern.compile("a[.]b");
        check(literalDot.matcher("a.b").matches(), "a[.]b should match a.b");
        check(!literalDot.matcher("axb").matches(), "a[.]b should not match axb");

        Pattern empty = Pattern.compile("");
        check(empty.pattern().isEmpty(), "empty pattern() mismatch");
        check(empty.matcher("").matches(), "empty pattern should match empty input");

        Pattern inline = Pattern.compile("(?i)abc");
        check(inline.matcher("ABC").matches(), "(?i)abc should match ABC");

        boolean syntaxRejected = false;
        try {
            Pattern.compile("(");
        } catch (PatternSyntaxException e) {
            syntaxRejected = true;
        }
        check(syntaxRejected, "invalid regex '(' must throw PatternSyntaxException");

        boolean npeThrown = false;
        try {
            Pattern.compile(null);
        } catch (NullPointerException e) {
            npeThrown = true;
        }
        check(npeThrown, "compile(null) must throw NullPointerException");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
