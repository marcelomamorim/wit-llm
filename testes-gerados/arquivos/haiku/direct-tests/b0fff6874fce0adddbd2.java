/*
 * @test
 * @summary Verifies Parameter.isVarArgs() for varargs and non-varargs methods obtained via public reflection
 * @run main ParameterWitupTest
 */

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

public class ParameterWitupTest {

    public static void withVarargs(String a, String... rest) {}

    public static void withArray(String a, String[] rest) {}

    public static void onlyVarargs(int... xs) {}

    public static void noParams() {}

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) throws Exception {
        Method m1 = ParameterWitupTest.class.getDeclaredMethod("withVarargs", String.class, String[].class);
        Parameter[] p1 = m1.getParameters();
        check(p1.length == 2, "withVarargs: expected 2 parameters");
        check(!p1[0].isVarArgs(), "withVarargs: first parameter must not be varargs");
        check(p1[1].isVarArgs(), "withVarargs: last parameter must be varargs");

        Method m2 = ParameterWitupTest.class.getDeclaredMethod("withArray", String.class, String[].class);
        Parameter[] p2 = m2.getParameters();
        check(!p2[0].isVarArgs(), "withArray: first parameter must not be varargs");
        check(!p2[1].isVarArgs(), "withArray: array parameter must not be varargs");

        Method m3 = ParameterWitupTest.class.getDeclaredMethod("onlyVarargs", int[].class);
        Parameter[] p3 = m3.getParameters();
        check(p3.length == 1, "onlyVarargs: expected 1 parameter");
        check(p3[0].isVarArgs(), "onlyVarargs: sole parameter must be varargs");

        Method m4 = ParameterWitupTest.class.getDeclaredMethod("noParams");
        Parameter[] p4 = m4.getParameters();
        check(p4.length == 0, "noParams: expected no parameters");

        System.out.println("PASS");
    }
}
