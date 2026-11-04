/*
 * @test
 * @summary Verify that the two-argument URISyntaxException constructor delegates with index -1 and validates null arguments
 * @run main URISyntaxExceptionWitupTest
 */

import java.net.URISyntaxException;

public class URISyntaxExceptionWitupTest {

    public static void main(String[] args) {
        testStoresInputAndReason();
        testDefaultIndexIsMinusOne();
        testDelegationMatchesThreeArgConstructor();
        testNullInputThrowsNPE();
        testNullReasonThrowsNPE();
    }

    static void testStoresInputAndReason() {
        URISyntaxException e = new URISyntaxException("http://a b", "Illegal character");
        check("http://a b".equals(e.getInput()), "getInput should return the input string");
        check("Illegal character".equals(e.getReason()), "getReason should return the reason string");
    }

    static void testDefaultIndexIsMinusOne() {
        URISyntaxException e = new URISyntaxException("abc", "bad");
        check(e.getIndex() == -1, "two-argument constructor must set index to -1, got " + e.getIndex());
    }

    static void testDelegationMatchesThreeArgConstructor() {
        URISyntaxException two = new URISyntaxException("xyz", "reason");
        URISyntaxException three = new URISyntaxException("xyz", "reason", -1);
        check(two.getIndex() == three.getIndex(), "index mismatch with explicit -1");
        check(two.getInput().equals(three.getInput()), "input mismatch with explicit -1");
        check(two.getReason().equals(three.getReason()), "reason mismatch with explicit -1");
    }

    static void testNullInputThrowsNPE() {
        boolean thrown = false;
        try {
            new URISyntaxException(null, "reason");
        } catch (NullPointerException npe) {
            thrown = true;
        }
        check(thrown, "null input must throw NullPointerException");
    }

    static void testNullReasonThrowsNPE() {
        boolean thrown = false;
        try {
            new URISyntaxException("input", null);
        } catch (NullPointerException npe) {
            thrown = true;
        }
        check(thrown, "null reason must throw NullPointerException");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
