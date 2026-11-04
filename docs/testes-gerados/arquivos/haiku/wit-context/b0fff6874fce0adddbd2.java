/*
 * @test
 * @summary Verifies Parameter.isVarArgs() for varargs and non-varargs executable parameters
 * @run main ParameterWitupTest
 */

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

public class ParameterWitupTest {

    static void varargsMethod(int a, String... b) {}

    static void plainMethod(int a, String[] b) {}

    public static void main(String[] args) throws Exception {
        Method v = ParameterWitupTest.class.getDeclaredMethod("varargsMethod", int.class, String[].class);
        Parameter[] vp = v.getParameters();
        check(vp.length == 2, "expected 2 parameters");
        check(!vp[0].isVarArgs(), "first parameter must not be varargs");
        check(vp[1].isVarArgs(), "last varargs parameter must be varargs");

        Method p = ParameterWitupTest.class.getDeclaredMethod("plainMethod", int.class, String[].class);
        Parameter[] pp = p.getParameters();
        check(pp.length == 2, "expected 2 parameters");
        check(!pp[0].isVarArgs(), "non-varargs method first parameter must not be varargs");
        check(!pp[1].isVarArgs(), "array parameter in non-varargs method must not be varargs");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
