/*
 * @test
 * @summary Witup generated checks for MethodHandles.Lookup.findClass behavior
 * @run main MethodHandlesWitupTest
 */

import java.lang.invoke.MethodHandles;

public class MethodHandlesWitupTest {
    public static void main(String[] args) throws Exception {
        MethodHandles.Lookup lookup = MethodHandles.lookup();

        Class<?> c = lookup.findClass("java.lang.String");
        if (c != String.class) {
            throw new RuntimeException("expected String.class but got " + c);
        }

        try {
            lookup.findClass("java.lang.NoSuchWitupClass");
            throw new RuntimeException("expected ClassNotFoundException");
        } catch (ClassNotFoundException expected) {
        }

        try {
            lookup.findClass("java.lang.invoke.MethodHandleStatics");
            throw new RuntimeException("expected IllegalAccessException");
        } catch (IllegalAccessException expected) {
        }
    }
}
