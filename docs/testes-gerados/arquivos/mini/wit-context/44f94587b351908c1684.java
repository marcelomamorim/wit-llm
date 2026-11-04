/*
 * @test
 * @summary Test javax.security.auth.Subject.toString() method
 * @run main javax.security.auth.SubjectWitupTest
 */

import javax.security.auth.Subject;
import java.util.HashSet;
import java.security.Principal;

public class SubjectWitupTest {
    public static void main(String[] args) {
        testToStringBasic();
    }

    private static void testToStringBasic() {
        // Create a Subject with empty sets
        Subject subject = new Subject(false, new HashSet<Principal>(), new HashSet<Object>(), new HashSet<Object>());

        // toString() should delegate to toString(true) and return a non-null String
        String str = subject.toString();
        if (str == null) {
            throw new RuntimeException("Subject.toString() returned null");
        }

        // The string should contain the class name
        if (!str.contains("Subject")) {
            throw new RuntimeException("Subject.toString() output does not contain 'Subject'");
        }

        // The string should reflect the principals set (empty here)
        if (!str.contains("Principals") && !str.contains("principals")) {
            throw new RuntimeException("Subject.toString() output does not mention principals");
        }
    }
}
