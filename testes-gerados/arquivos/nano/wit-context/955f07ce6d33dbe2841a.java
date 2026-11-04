/*
 * @test
 * @summary Testa o método remove de Subject para verificar comportamento com entrada nula.
 * @run main SubjectWitupTest
 */

import javax.security.auth.Subject;
import java.util.*;

public class SubjectWitupTest {

    public static void main(String[] args) {
        testRemoveWithNullObject();
        testRemoveWithExistingObject();
        testRemoveWithNonExistingObject();
    }

    private static void testRemoveWithNullObject() {
        Subject subject = new Subject();
        try {
            subject.remove(null);
            System.out.println("testRemoveWithNullObject: Failed - Expected NullPointerException");
        } catch (NullPointerException e) {
            System.out.println("testRemoveWithNullObject: Passed");
        } catch (Exception e) {
            System.out.println("testRemoveWithNullObject: Failed - Unexpected exception: " + e);
        }
    }

    private static void testRemoveWithExistingObject() {
        Object credential = new Object();
        Subject subject = new Subject();
        subject.getPrivateCredentials().add(credential);
        boolean removed = subject.remove(credential);
        if (removed && !subject.getPrivateCredentials().contains(credential)) {
            System.out.println("testRemoveWithExistingObject: Passed");
        } else {
            System.out.println("testRemoveWithExistingObject: Failed");
        }
    }

    private static void testRemoveWithNonExistingObject() {
        Object credential = new Object();
        Subject subject = new Subject();
        boolean removed = subject.remove(credential);
        if (!removed) {
            System.out.println("testRemoveWithNonExistingObject: Passed");
        } else {
            System.out.println("testRemoveWithNonExistingObject: Failed");
        }
    }
}
