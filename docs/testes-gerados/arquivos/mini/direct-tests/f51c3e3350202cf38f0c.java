/*
 * @test
 * @summary Test java.lang.reflect.Constructor.getAnnotation method
 * @run main ConstructorWitupTest
 */

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;

public class ConstructorWitupTest {
    public static void main(String[] args) throws Exception {
        // Obtain a Constructor object for a known class
        Constructor<String> ctor = String.class.getConstructor(String.class);

        // Test getAnnotation with a known annotation class (Override.class) which is not present
        Annotation ann = ctor.getAnnotation(Override.class);
        if (ann != null) {
            throw new RuntimeException("Expected null annotation for Override on String constructor");
        }

        // Test getAnnotation with null argument - should throw NullPointerException
        try {
            ctor.getAnnotation(null);
            throw new RuntimeException("Expected NullPointerException for null argument");
        } catch (NullPointerException expected) {
            // expected
        }

        // Test getAnnotation with an annotation class that is present on a constructor
        // Define a local class with an annotated constructor
        class Local {
            @Deprecated
            public Local() {}
        }
        Constructor<Local> localCtor = Local.class.getConstructor();
        Deprecated dep = localCtor.getAnnotation(Deprecated.class);
        if (dep == null) {
            throw new RuntimeException("Expected Deprecated annotation on Local constructor");
        }

        System.out.println("All tests passed.");
    }
}
