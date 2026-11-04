/* @test    @summary Test default constructor of Subject for correct initialization    @run main SubjectWitupTest */

import javax.security.auth.Subject;
import java.util.*;

public class SubjectWitupTest {

    public static void main(String[] args) {
        testDefaultConstructor();
    }

    public static void testDefaultConstructor() {
        Subject subject = new Subject();
        assertNotNull("Principals set should not be null", getPrincipals(subject));
        assertNotNull("Public credentials set should not be null", getPublicCredentials(subject));
        assertNotNull("Private credentials set should not be null", getPrivateCredentials(subject));
        assertEquals("Principals set should be empty initially", 0, getPrincipals(subject).size());
        assertEquals("Public credentials set should be empty initially", 0, getPublicCredentials(subject).size());
        assertEquals("Private credentials set should be empty initially", 0, getPrivateCredentials(subject).size());
    }

    private static Set<?> getPrincipals(Subject subject) {
        try {
            Method method = Subject.class.getDeclaredMethod("getPrincipals");
            method.setAccessible(true);
            return (Set<?>) method.invoke(subject);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static Set<?> getPublicCredentials(Subject subject) {
        try {
            Method method = Subject.class.getDeclaredMethod("getPublicCredentials");
            method.setAccessible(true);
            return (Set<?>) method.invoke(subject);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static Set<?> getPrivateCredentials(Subject subject) {
        try {
            Method method = Subject.class.getDeclaredMethod("getPrivateCredentials");
            method.setAccessible(true);
            return (Set<?>) method.invoke(subject);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static void assertNotNull(String message, Object obj) {
        if (obj == null) {
            throw new AssertionError(message);
        }
    }

    private static void assertEquals(String message, int expected, int actual) {
        if (expected != actual) {
            throw new AssertionError(message + " Expected: " + expected + " but was: " + actual);
        }
    }
}
