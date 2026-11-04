/*
 * @test
 * @summary Verify Executable.getParameterCount() through public Method and Constructor instances
 * @run main ExecutableWitupTest
 */
import java.lang.reflect.Constructor;
import java.lang.reflect.Executable;

public class ExecutableWitupTest {
    public static void main(String[] args) throws Exception {
        Executable substring = String.class.getMethod("substring", int.class, int.class);
        check(substring.getParameterCount() == 2, "String.substring(int,int) should have 2 parameters");

        Executable hashCode = Object.class.getMethod("hashCode");
        check(hashCode.getParameterCount() == 0, "Object.hashCode() should have 0 parameters");

        Executable max = Math.class.getMethod("max", int.class, int.class);
        check(max.getParameterCount() == 2, "Math.max(int,int) should have 2 parameters");

        Executable stringCtor = String.class.getConstructor(char[].class);
        check(stringCtor.getParameterCount() == 1, "String(char[]) constructor should have 1 parameter");

        Executable objectCtor = Object.class.getConstructor();
        check(objectCtor.getParameterCount() == 0, "Object() constructor should have 0 parameters");

        Constructor<?> ctor = String.class.getConstructor(String.class);
        check(ctor.getParameterCount() == 1, "String(String) constructor should have 1 parameter");
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }
}
