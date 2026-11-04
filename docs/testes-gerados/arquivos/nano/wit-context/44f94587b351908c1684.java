/*
 * @test
 * @summary Test for javax.security.auth.Subject.toString()
 * @run main SubjectWitupTest
 */

import javax.security.auth.Subject;
import java.util.*;

public class SubjectWitupTest {

    public static void main(String[] args) {
        testToStringNormal();
        testToStringWithNullPrincipals();
    }

    public static void testToStringNormal() {
        Subject subject = new Subject();
        String result = subject.toString();
        if (result == null) {
            throw new AssertionError("Expected non-null result from toString()");
        }
        System.out.println("testToStringNormal passed: " + result);
    }

    public static void testToStringWithNullPrincipals() {
        Subject subject = new Subject();
        // No principals added, should still produce a non-null string
        String result = subject.toString();
        if (result == null) {
            throw new AssertionError("Expected non-null result from toString() with no principals");
        }
        System.out.println("testToStringWithNullPrincipals passed: " + result);
    }
}
