/*
 * @test
 * @summary Checks public behavior of AccessibleObject.getDeclaredAnnotation for a reflected Method
 * @run main AccessibleObjectWitupTest
 */

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Method;

public class AccessibleObjectWitupTest {

    @Retention(RetentionPolicy.RUNTIME)
    @interface Present {
    }

    @Retention(RetentionPolicy.RUNTIME)
    @interface Absent {
    }

    @Present
    public void annotated() {
    }

    public void plain() {
    }

    public static void main(String[] args) throws Exception {
        Method annotated = AccessibleObjectWitupTest.class.getDeclaredMethod("annotated");
        Method plain = AccessibleObjectWitupTest.class.getDeclaredMethod("plain");

        Present p = annotated.getDeclaredAnnotation(Present.class);
        if (p == null) {
            throw new RuntimeException("Expected @Present on annotated method");
        }

        if (annotated.getDeclaredAnnotation(Absent.class) != null) {
            throw new RuntimeException("Absent annotation should not be reported");
        }

        if (plain.getDeclaredAnnotation(Present.class) != null) {
            throw new RuntimeException("Unannotated method should return null");
        }

        Present viaGetAnnotation = annotated.getAnnotation(Present.class);
        if (viaGetAnnotation == null) {
            throw new RuntimeException("getAnnotation should also find @Present");
        }
    }
}
