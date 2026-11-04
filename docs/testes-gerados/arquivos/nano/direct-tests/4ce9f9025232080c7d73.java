/*
 * @test
 * @summary Tests for java.lang.reflect.Parameter.getAnnotationsByType
 * @run main ParameterWitupTest
 */

import java.lang.annotation.*;
import java.lang.reflect.Parameter;
import java.util.Objects;

public class ParameterWitupTest {

    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.PARAMETER})
    public @interface SampleAnnotation {}

    public static class TestClass {
        public void testMethod(@SampleAnnotation String param) {}
    }

    public static void main(String[] args) {
        try {
            Parameter[] params = TestClass.class.getMethod("testMethod", String.class).getParameters();
            if (params.length > 0) {
                Parameter param = params[0];
                // Test getAnnotationsByType with existing annotation
                SampleAnnotation[] annotations = param.getAnnotationsByType(SampleAnnotation.class);
                assert annotations != null : "Annotations array should not be null";
                assert annotations.length == 1 : "Should find one SampleAnnotation";
                assert annotations[0] instanceof SampleAnnotation : "Annotation should be of type SampleAnnotation";

                // Test getAnnotationsByType with annotation class not present
                class OtherAnnotation extends Annotation {}
                Annotation[] otherAnnotations = param.getAnnotationsByType(OtherAnnotation.class);
                assert otherAnnotations != null : "Annotations array should not be null";
                assert otherAnnotations.length == 0 : "Should find no OtherAnnotation";
            } else {
                throw new AssertionError("No parameters found in method")
            }
        } catch (NoSuchMethodException e) {
            throw new AssertionError(e);
        }
    }
}
