/*
 * @test
 * @summary Checks filterArguments filter application, null filter skipping and rejection of too many filters
 * @run main MethodHandlesWitupTest
 */

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

public class MethodHandlesWitupTest {

    public static String upper(String s) {
        return s.toUpperCase();
    }

    public static void main(String[] args) throws Throwable {
        MethodHandles.Lookup lookup = MethodHandles.lookup();
        MethodHandle concat = lookup.findVirtual(String.class, "concat",
                MethodType.methodType(String.class, String.class));
        MethodHandle upper = lookup.findStatic(MethodHandlesWitupTest.class, "upper",
                MethodType.methodType(String.class, String.class));

        MethodHandle filteredFirst = MethodHandles.filterArguments(concat, 0, upper, null);
        String r1 = (String) filteredFirst.invoke("ab", "cd");
        if (!"ABcd".equals(r1)) {
            throw new RuntimeException("Unexpected result for pos 0 filter: " + r1);
        }

        MethodHandle filteredSecond = MethodHandles.filterArguments(concat, 1, upper);
        String r2 = (String) filteredSecond.invoke("ab", "cd");
        if (!"abCD".equals(r2)) {
            throw new RuntimeException("Unexpected result for pos 1 filter: " + r2);
        }

        boolean thrown = false;
        try {
            MethodHandles.filterArguments(concat, 1, upper, upper);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected IllegalArgumentException for too many filters");
        }
    }
}
