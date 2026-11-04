/*
 * @test
 * @summary Tests for java.lang.ref.Cleaner.register method behavior.
 * @run main CleanerWitupTest
 */

import java.lang.ref.Cleaner;
import java.util.Objects;

public class CleanerWitupTest {

    public static void main(String[] args) {
        testRegisterWithNullObject();
        testRegisterWithNullAction();
    }

    public static void testRegisterWithNullObject() {
        Cleaner cleaner = Cleaner.create();
        try {
            cleaner.register(null, () -> {});
            // Expect NullPointerException
            System.err.println("Failed: Expected NullPointerException for null obj");
        } catch (NullPointerException e) {
            System.out.println("Passed: NullPointerException thrown for null obj");
        }
    }

    public static void testRegisterWithNullAction() {
        Cleaner cleaner = Cleaner.create();
        Object obj = new Object();
        try {
            cleaner.register(obj, null);
            // Expect NullPointerException
            System.err.println("Failed: Expected NullPointerException for null action");
        } catch (NullPointerException e) {
            System.out.println("Passed: NullPointerException thrown for null action");
        }
    }
}
