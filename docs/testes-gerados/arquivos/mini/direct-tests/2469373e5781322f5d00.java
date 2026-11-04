/*
 * @test
 * @summary Test AccessibleObject.getDeclaredAnnotation returns same as getAnnotation
 * @run main AccessibleObjectWitupTest
 */

import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Method;

public class AccessibleObjectWitupTest {

    public static void main(String[] args) throws Exception {
        // Obtain a Method object (which extends AccessibleObject)
        Method m = AccessibleObjectWitupTest.class.getMethod("main", String[].class);

        // Test with an annotation that is present (Override on main is not present, so use Deprecated)
        Deprecated deprecated = m.getDeclaredAnnotation(Deprecated.class);
        Deprecated deprecatedViaGetDeclared = m.getDeclaredAnnotation(Deprecated.class);

        // Both calls should return the same result (null expected since main is not deprecated)
        if (deprecated != deprecatedViaGetDeclared) {
            throw new AssertionError("getDeclaredAnnotation and getAnnotation results differ");
        }

        // Test with an annotation that is definitely not present
        SuppressWarnings sw = m.getDeclaredAnnotation(SuppressWarnings.class);
        SuppressWarnings swViaGetDeclared = m.getDeclaredAnnotation(SuppressWarnings.class);

        if (sw != swViaGetDeclared) {
            throw new AssertionError("getDeclaredAnnotation and getAnnotation results differ for absent annotation");
        }

        // Test that getDeclaredAnnotation returns null for absent annotation
        if (m.getDeclaredAnnotation(Deprecated.class) != m.getAnnotation(Deprecated.class)) {
            throw new AssertionError("getDeclaredAnnotation and getAnnotation differ for Deprecated");
        }

        // Test that getDeclaredAnnotation returns null for annotation not present
        if (m.getDeclaredAnnotation(SuppressWarnings.class) != m.getAnnotation(SuppressWarnings.class)) {
            throw new AssertionError("getDeclaredAnnotation and getAnnotation differ for SuppressWarnings");
        }

        System.out.println("AccessibleObject.getDeclaredAnnotation behaves as expected");
    }
}
