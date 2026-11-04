/*
 * @test
 * @summary Verifies Matcher.find(int) start index validation, reset behavior and search results from a given start index
 * @run main MatcherWitupTest
 */

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatcherWitupTest {

    public static void main(String[] args) {
        testFindFromStartReturnsMatch();
        testFindStartAtLengthNoMatch();
        testFindStartAtLengthEmptyPatternMatches();
        testNegativeStartThrows();
        testStartBeyondLengthThrows();
        testFindResetsPreviousState();
    }

    static void testFindFromStartReturnsMatch() {
        Matcher m = Pattern.compile("a").matcher("baa");
        if (!m.find(1)) {
            throw new RuntimeException("Expected match from index 1");
        }
        if (m.start() != 1 || m.end() != 2) {
            throw new RuntimeException("Unexpected match bounds: " + m.start() + "," + m.end());
        }
    }

    static void testFindStartAtLengthNoMatch() {
        Matcher m = Pattern.compile("a").matcher("baa");
        if (m.find(3)) {
            throw new RuntimeException("Expected no match when start equals input length");
        }
    }

    static void testFindStartAtLengthEmptyPatternMatches() {
        Matcher m = Pattern.compile("").matcher("ab");
        if (!m.find(2)) {
            throw new RuntimeException("Expected empty match at end of input");
        }
        if (m.start() != 2 || m.end() != 2) {
            throw new RuntimeException("Unexpected empty match bounds: " + m.start() + "," + m.end());
        }
    }

    static void testNegativeStartThrows() {
        Matcher m = Pattern.compile("a").matcher("aaa");
        try {
            m.find(-1);
        } catch (IndexOutOfBoundsException e) {
            return;
        }
        throw new RuntimeException("Expected IndexOutOfBoundsException for negative start");
    }

    static void testStartBeyondLengthThrows() {
        Matcher m = Pattern.compile("a").matcher("aaa");
        try {
            m.find(4);
        } catch (IndexOutOfBoundsException e) {
            return;
        }
        throw new RuntimeException("Expected IndexOutOfBoundsException for start beyond input length");
    }

    static void testFindResetsPreviousState() {
        Matcher m = Pattern.compile("a").matcher("aXa");
        if (!m.find(2) || m.start() != 2) {
            throw new RuntimeException("Expected match at index 2");
        }
        if (!m.find(0) || m.start() != 0) {
            throw new RuntimeException("Expected find(0) to reset and match at index 0");
        }
    }
}
