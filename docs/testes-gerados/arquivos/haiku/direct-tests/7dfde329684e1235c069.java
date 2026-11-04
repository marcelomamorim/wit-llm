/*
 * @test
 * @summary Basic behavior of MethodHandles.iteratedLoop with null and explicit iterators
 * @run main MethodHandlesWitupTest
 */

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class MethodHandlesWitupTest {

    static String empty() {
        return "";
    }

    static String concat(String acc, String t) {
        return acc + t;
    }

    static void check(Object expected, Object actual) {
        if (!expected.equals(actual)) {
            throw new AssertionError("expected " + expected + " but was " + actual);
        }
    }

    public static void main(String[] args) throws Throwable {
        MethodHandles.Lookup lookup = MethodHandles.lookup();
        MethodHandle init = lookup.findStatic(MethodHandlesWitupTest.class, "empty",
                MethodType.methodType(String.class));
        MethodHandle body = lookup.findStatic(MethodHandlesWitupTest.class, "concat",
                MethodType.methodType(String.class, String.class, String.class));

        MethodHandle loopNullIter = MethodHandles.iteratedLoop(null, init, body);
        check(String.class, loopNullIter.type().returnType());
        check(1, loopNullIter.type().parameterCount());
        String r1 = (String) loopNullIter.invoke(Arrays.asList("a", "b", "c"));
        check("abc", r1);
        String r1Empty = (String) loopNullIter.invoke(Collections.emptyList());
        check("", r1Empty);

        MethodHandle iter = lookup.findVirtual(List.class, "iterator",
                MethodType.methodType(Iterator.class));
        MethodHandle loopExplicitIter = MethodHandles.iteratedLoop(iter, init, body);
        check(String.class, loopExplicitIter.type().returnType());
        String r2 = (String) loopExplicitIter.invoke(Arrays.asList("x", "y"));
        check("xy", r2);
    }
}
