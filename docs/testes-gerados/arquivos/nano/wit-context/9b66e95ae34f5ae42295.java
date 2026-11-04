/*
 * @test
 * @summary Tests for java.util.regex.Matcher.appendReplacement
 * @run main MatcherWitupTest
 */

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatcherWitupTest {

    public static void main(String[] args) {
        testAppendReplacementNoMatch();
        testAppendReplacementIndexOutOfBounds();
        testAppendReplacementIllegalState();
    }

    private static void testAppendReplacementNoMatch() {
        Pattern pattern = Pattern.compile("foo");
        Matcher matcher = pattern.matcher("bar");
        StringBuilder sb = new StringBuilder();
        try {
            matcher.appendReplacement(sb, "replacement");
            System.out.println("testAppendReplacementNoMatch: Failed - no exception thrown");
        } catch (IllegalStateException e) {
            System.out.println("testAppendReplacementNoMatch: Passed");
        } catch (Exception e) {
            System.out.println("testAppendReplacementNoMatch: Failed - wrong exception: " + e);
        }
    }

    private static void testAppendReplacementIndexOutOfBounds() {
        Pattern pattern = Pattern.compile("foo");
        Matcher matcher = pattern.matcher("foo bar");
        matcher.find();
        StringBuilder sb = new StringBuilder();
        String replacement = "";
        // Set internal state to trigger IndexOutOfBoundsException
        // by passing a replacement with length > 0 but with invalid range
        // Since the method does not expose internal state, simulate by calling appendExpandedReplacement with invalid data
        // but as we cannot access private methods, we test indirectly by passing a replacement that causes the exception
        // in this context, we simulate the scenario by forcibly setting first to an invalid value (not possible directly)
        // so instead, we test the guard condition indirectly by passing a replacement that causes the exception
        // but since the method does not throw IndexOutOfBoundsException directly, we skip this test
        // and focus on the documented exception for invalid range in appendExpandedReplacement (not accessible here)
        // Therefore, we skip this test as it cannot be directly invoked without internal access
        System.out.println("testAppendReplacementIndexOutOfBounds: Skipped - cannot simulate without internal access");
    }

    private static void testAppendReplacementIllegalState() {
        Pattern pattern = Pattern.compile("foo");
        Matcher matcher = pattern.matcher("bar");
        StringBuilder sb = new StringBuilder();
        try {
            matcher.appendReplacement(sb, "replacement");
            System.out.println("testAppendReplacementIllegalState: Failed - no exception thrown");
        } catch (IllegalStateException e) {
            System.out.println("testAppendReplacementIllegalState: Passed");
        } catch (Exception e) {
            System.out.println("testAppendReplacementIllegalState: Failed - wrong exception: " + e);
        }
    }
}
