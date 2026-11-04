/*
 * @test
 * @summary Test java.lang.invoke.MethodHandles.findClass(String) for expected behavior and exceptions
 * @run main MethodHandlesWitupTest
 */

import java.lang.invoke.MethodHandles;

public class MethodHandlesWitupTest {
    public static void main(String[] args) throws Throwable {
        testFindClassValid();
        testFindClassInvalid();
    }

    private static void testFindClassValid() throws Exception {
        MethodHandles mh = new MethodHandles(); // private constructor, but class has no public constructor, so use static context
        // findClass is instance method, but MethodHandles has no public constructor, so use reflection to get instance is not allowed by rules
        // Instead, call findClass via a static context workaround: findClass is not static, so we create a subclass to expose it
        // But since no constructor is public, we test via a static helper method below
        Class<?> cls = findClassHelper("java.lang.String");
        if (!cls.equals(String.class)) {
            throw new AssertionError("Expected java.lang.String class, got " + cls);
        }
    }

    private static void testFindClassInvalid() {
        try {
            findClassHelper("non.existent.ClassName12345");
            throw new AssertionError("Expected ClassNotFoundException for non-existent class");
        } catch (ClassNotFoundException e) {
            // expected
        } catch (IllegalAccessException e) {
            // allowed as per method signature, but unexpected here
            throw new AssertionError("Unexpected IllegalAccessException", e);
        } catch (Throwable t) {
            throw new AssertionError("Unexpected exception", t);
        }
    }

    // Helper method to call findClass since MethodHandles has no public constructor
    private static Class<?> findClassHelper(String name) throws ClassNotFoundException, IllegalAccessException {
        // Use MethodHandles.Lookup public lookup to call findClass indirectly
        // But findClass is not static and no public constructor for MethodHandles
        // So use reflection to get MethodHandles instance is disallowed
        // Instead, use a subclass with a public constructor to call findClass
        return new MethodHandlesSubclass().findClass(name);
    }

    // Subclass to expose findClass with public constructor
    static class MethodHandlesSubclass extends MethodHandles {
        public MethodHandlesSubclass() {
            super();
        }

        @Override
        public Class<?> findClass(String targetName) throws ClassNotFoundException, IllegalAccessException {
            return super.findClass(targetName);
        }
    }
}
