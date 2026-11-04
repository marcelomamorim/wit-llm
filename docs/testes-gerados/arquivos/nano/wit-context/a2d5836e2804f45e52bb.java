/*
 * @test
 * @summary Test for java.util.Objects.requireNonNull method
 * @run main ObjectsWitupTest
 */

import java.util.Objects;

public class ObjectsWitupTest {

    public static void main(String[] args) {
        testRequireNonNullWithNull();
        testRequireNonNullWithNonNull();
    }

    public static void testRequireNonNullWithNull() {
        try {
            Objects.requireNonNull(null);
            System.err.println("Failed: Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            System.out.println("Passed: NullPointerException thrown as expected");
        }
    }

    public static void testRequireNonNullWithNonNull() {
        String obj = "test";
        String result = Objects.requireNonNull(obj);
        if (result == obj) {
            System.out.println("Passed: Non-null object returned correctly");
        } else {
            System.err.println("Failed: Returned object does not match input");
        }
    }
}
