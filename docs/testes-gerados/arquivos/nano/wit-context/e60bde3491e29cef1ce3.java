/*
 * @test
 * @summary Test for Subject.getSubject with null AccessControlContext
 * @run main SubjectWitupTest
 */

import javax.security.auth.Subject;
import java.security.AccessControlContext;
import java.security.DomainCombiner;

public class SubjectWitupTest {

    public static void main(String[] args) {
        testGetSubjectWithNullAcc();
    }

    public static void testGetSubjectWithNullAcc() {
        try {
            Subject.getSubject(null);
            System.out.println("FAIL: Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown as expected");
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception: " + t);
        }
    }
}
