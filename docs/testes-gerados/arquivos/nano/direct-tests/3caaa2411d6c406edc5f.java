/*
 * @test
 * @summary Test getDeclaredAnnotations() method of AccessibleObject.
 * @run main AccessibleObjectWitupTest
 */

import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;

public class AccessibleObjectWitupTest {

    public static void main(String[] args) {
        testGetDeclaredAnnotations();
    }

    public static void testGetDeclaredAnnotations() {
        class DummyAnnotation implements Annotation {
            public Class<? extends Annotation> annotationType() {
                return DummyAnnotation.class;
            }
        }
        class DummyAccessibleObject extends AccessibleObject {
            @Override
            public Annotation[] getDeclaredAnnotations() {
                return new Annotation[] { new DummyAnnotation() };
            }
        }
        AccessibleObject ao = new DummyAccessibleObject();
        Annotation[] annotations = ao.getDeclaredAnnotations();
        assert annotations != null : "Annotations should not be null";
        assert annotations.length == 1 : "There should be exactly one annotation";
        assert annotations[0].annotationType() == DummyAnnotation.class : "Annotation type mismatch";
    }
}
