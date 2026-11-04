/*
 * @test
 * @summary Verify Objects.requireNonNull(T) returns its argument for non-null values and throws NullPointerException for null
 * @run main ObjectsWitupTest
 */

import java.util.Objects;

public class ObjectsWitupTest {

    public static void main(String[] args) {
        testReturnsSameStringInstance();
        testReturnsSameObjectInstance();
        testReturnsSameArrayInstance();
        testThrowsNpeForNull();
        testNpeHasNoMessageDetail();
    }

    static void testReturnsSameStringInstance() {
        String s = new String("value");
        String r = Objects.requireNonNull(s);
        if (r != s) {
            throw new RuntimeException("requireNonNull did not return the same instance");
        }
    }

    static void testReturnsSameObjectInstance() {
        Object o = new Object();
        Object r = Objects.requireNonNull(o);
        if (r != o) {
            throw new RuntimeException("requireNonNull did not return the same object");
        }
    }

    static void testReturnsSameArrayInstance() {
        int[] arr = new int[] {1, 2, 3};
        int[] r = Objects.requireNonNull(arr);
        if (r != arr) {
            throw new RuntimeException("requireNonNull did not return the same array");
        }
    }

    static void testThrowsNpeForNull() {
        boolean thrown = false;
        try {
            Objects.requireNonNull(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("NullPointerException not thrown for null");
        }
    }

    static void testNpeHasNoMessageDetail() {
        try {
            Objects.requireNonNull(null);
            throw new RuntimeException("expected NullPointerException");
        } catch (NullPointerException e) {
            if (e.getMessage() != null) {
                throw new RuntimeException("unexpected message: " + e.getMessage());
            }
        }
    }
}
