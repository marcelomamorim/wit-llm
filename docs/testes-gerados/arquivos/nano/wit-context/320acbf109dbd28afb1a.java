/*
 * @test
 * @summary Test for Subject.addAll(Collection) method, focusing on null input handling.
 * @run main SubjectWitupTest
 */

import javax.security.auth.Subject;
import java.util.*;

public class SubjectWitupTest {

    public static void main(String[] args) {
        testAddAllWithNull();
        testAddAllWithEmptyCollection();
        testAddAllWithValidCollection();
    }

    static void testAddAllWithNull() {
        Subject subject = new Subject();
        try {
            subject.addAll(null);
            System.out.println("FAIL: Expected NullPointerException for null collection");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown as expected");
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception: " + e);
        }
    }

    static void testAddAllWithEmptyCollection() {
        Subject subject = new Subject();
        Collection<Object> empty = Collections.emptySet();
        boolean result = subject.addAll(empty);
        if (!result) {
            System.out.println("PASS: addAll with empty collection returned false as expected");
        } else {
            System.out.println("FAIL: addAll with empty collection returned true unexpectedly");
        }
    }

    static void testAddAllWithValidCollection() {
        Subject subject = new Subject();
        List<String> principals = Arrays.asList("Principal1", "Principal2");
        Collection<String> collection = new ArrayList<>(principals);
        boolean result = subject.addAll(collection);
        if (result) {
            System.out.println("PASS: addAll with valid collection returned true");
        } else {
            System.out.println("FAIL: addAll with valid collection returned false");
        }
    }
}
