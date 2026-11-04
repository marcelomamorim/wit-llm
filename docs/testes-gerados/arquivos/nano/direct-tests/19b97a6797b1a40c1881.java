/* @test    @summary Test getPublicCredentials with a valid class type    @run main SubjectWitupTest */

import javax.security.auth.Subject;
import java.util.*;

public class SubjectWitupTest {

    public static void main(String[] args) {
        testGetPublicCredentialsWithValidClass();
        testGetPublicCredentialsWithNullClass();
    }

    static void testGetPublicCredentialsWithValidClass() {
        Subject subject = new Subject();
        Class<String> clazz = String.class;
        Set<String> credentials = subject.getPublicCredentials(clazz);
        if (credentials == null) {
            throw new AssertionError("Expected non-null set for valid class");
        }
        if (!(credentials instanceof Set)) {
            throw new AssertionError("Returned object is not a Set");
        }
    }

    static void testGetPublicCredentialsWithNullClass() {
        Subject subject = new Subject();
        try {
            subject.getPublicCredentials(null);
            throw new AssertionError("Expected NullPointerException for null class");
        } catch (NullPointerException e) {
            // expected
        }
    }
}
