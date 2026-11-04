/*
 * @test
 * @summary Test for Parameter.getAnnotationsByType handling null annotationClass
 * @run main ParameterWitupTest
 */

import java.lang.annotation.*;
import java.lang.reflect.Parameter;

public class ParameterWitupTest {

    public static void main(String[] args) {
        testGetAnnotationsByTypeNull();
        testGetAnnotationsByTypeValid();
    }

    private static void testGetAnnotationsByTypeNull() {
        try {
            Parameter param = createSampleParameter();
            param.getAnnotationsByType(null);
            System.out.println("FAIL: Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown as expected");
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception: " + t);
        }
    }

    private static void testGetAnnotationsByTypeValid() {
        try {
            Parameter param = createSampleParameter();
            // Using a known annotation type present in the test
            AnnotationType annotation = new AnnotationType() {};
            AnnotationType[] result = param.getAnnotationsByType(AnnotationType.class);
            if (result != null) {
                System.out.println("PASS: getAnnotationsByType returned non-null result");
            } else {
                System.out.println("FAIL: getAnnotationsByType returned null");
            }
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception: " + t);
        }
    }

    private static Parameter createSampleParameter() {
        // Since Parameter constructor is package-private, create a dummy subclass for testing
        class DummyParameter extends Parameter {
            DummyParameter() {
                super("param", 0, null, 0);
            }
        }
        return new DummyParameter();
    }

    // Dummy annotation for testing
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.PARAMETER)
    public @interface AnnotationType {}
}
