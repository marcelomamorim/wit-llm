/* @test    @summary Test getDeclaredAnnotation method of Parameter class    @run main ParameterWitupTest */

import java.lang.annotation.*;
import java.lang.reflect.*;

public class ParameterWitupTest {

    @Retention(RetentionPolicy.RUNTIME)
    @interface TestAnnotation {}

    static class DummyMethod {
        public void method(@TestAnnotation String param) {}
    }

    public static void main(String[] args) throws Exception {
        Method m = DummyMethod.class.getMethod("method", String.class);
        Parameter[] params = m.getParameters();
        if (params.length != 1) {
            throw new AssertionError("Expected one parameter");
        }
        Parameter p = params[0];
        TestAnnotation annotation = p.getDeclaredAnnotation(TestAnnotation.class);
        if (annotation == null) {
            throw new AssertionError("Annotation should be present");
        }
        System.out.println("Test passed: getDeclaredAnnotation returns annotation");
    }
}
