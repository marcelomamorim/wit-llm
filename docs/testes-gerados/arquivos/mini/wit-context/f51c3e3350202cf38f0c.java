/*
 * @test
 * @summary Test java.lang.reflect.Constructor.getAnnotation(Class<T>) for null argument handling
 * @run main ConstructorWitupTest
 */

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;

public class ConstructorWitupTest {
    public static void main(String[] args) {
        try {
            Constructor<String> ctor = String.class.getConstructor();
            // Test with valid annotation class (should return null as String has no annotations of Deprecated)
            Annotation ann = ctor.getAnnotation(Deprecated.class);
            if (ann != null) {
                throw new RuntimeException("Expected null for annotation not present");
            }

            // Test with null argument: expect NullPointerException
            try {
                ctor.getAnnotation(null);
                throw new RuntimeException("Expected NullPointerException for null argument");
            } catch (NullPointerException expected) {
                // expected
            }

            System.out.println("PASS");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Test failed", e);
        }
    }
}
