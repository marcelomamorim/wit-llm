/*
 * @test
 * @summary Verify MethodHandles.Lookup.findClass resolves public classes and throws ClassNotFoundException for unknown names
 * @run main MethodHandlesWitupTest
 */

import java.lang.invoke.MethodHandles;

public class MethodHandlesWitupTest {

    public static void main(String[] args) throws Exception {
        MethodHandles.Lookup lookup = MethodHandles.lookup();

        check(lookup.findClass("java.lang.String") == String.class, "java.lang.String not resolved");
        check(lookup.findClass("java.util.Map$Entry") == java.util.Map.Entry.class, "Map.Entry not resolved");

        Class<?> c = lookup.findClass("java.lang.Integer");
        check(c == Integer.class, "java.lang.Integer not resolved");

        boolean cnfe = false;
        try {
            lookup.findClass("no.such.MissingClassWitup");
        } catch (ClassNotFoundException e) {
            cnfe = true;
        }
        check(cnfe, "ClassNotFoundException expected for unknown class");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
