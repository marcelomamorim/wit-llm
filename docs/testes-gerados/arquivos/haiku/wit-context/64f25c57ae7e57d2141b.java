/*
 * @test
 * @summary Verify Executable.getParameterCount() through public Method and Constructor instances
 * @run main ExecutableWitupTest
 */

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class ExecutableWitupTest {

    public static class Sample {
        public Sample() {}

        public Sample(int a, String b) {}

        public void none() {}

        public void three(int a, String b, long c) {}

        public static void varargs(String... s) {}
    }

    public static void main(String[] args) throws Exception {
        Method none = Sample.class.getMethod("none");
        check(none.getParameterCount() == 0, "none() should have 0 parameters");
        check(none.getParameterCount() == 0, "none() count should be stable across calls");

        Method three = Sample.class.getMethod("three", int.class, String.class, long.class);
        check(three.getParameterCount() == 3, "three(int,String,long) should have 3 parameters");

        Method varargs = Sample.class.getMethod("varargs", String[].class);
        check(varargs.getParameterCount() == 1, "varargs(String...) should have 1 parameter");

        Method equals = Object.class.getMethod("equals", Object.class);
        check(equals.getParameterCount() == 1, "Object.equals should have 1 parameter");

        Constructor<?> c0 = Sample.class.getConstructor();
        check(c0.getParameterCount() == 0, "no-arg constructor should have 0 parameters");

        Constructor<?> c2 = Sample.class.getConstructor(int.class, String.class);
        check(c2.getParameterCount() == 2, "constructor(int,String) should have 2 parameters");
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
