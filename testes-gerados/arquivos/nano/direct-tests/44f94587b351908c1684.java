/* @test    @summary Test the toString() method of Subject class for correctness and consistency    @run main SubjectWitupTest */

import javax.security.auth.Subject;

public class SubjectWitupTest {

    public static void main(String[] args) {
        testToStringReturnsNonNull();
        testToStringConsistency();
    }

    private static void testToStringReturnsNonNull() {
        Subject subject = new Subject();
        String result = subject.toString();
        if (result == null) {
            throw new AssertionError("toString() returned null");
        }
        System.out.println("testToStringReturnsNonNull passed.");
    }

    private static void testToStringConsistency() {
        Subject subject = new Subject();
        String firstCall = subject.toString();
        String secondCall = subject.toString();
        if (!firstCall.equals(secondCall)) {
            throw new AssertionError("toString() returns inconsistent results");
        }
        System.out.println("testToStringConsistency passed.");
    }
}
