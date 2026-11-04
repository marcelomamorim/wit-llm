/*
 * @test
 * @summary Tests Checks.requirePackageName for valid names, null and invalid package names
 * @modules java.base/jdk.internal.module
 * @run main ChecksWitupTest
 */
public class ChecksWitupTest {

    public static void main(String[] args) {
        checkValid("java.lang");
        checkValid("jdk.internal.module");
        checkValid("a.b_c");

        checkIllegalArgument(null);
        checkIllegalArgument("java.1lang");
        checkIllegalArgument("a.b-c");
    }

    private static void checkValid(String name) {
        String result = jdk.internal.module.Checks.requirePackageName(name);
        if (!name.equals(result)) {
            throw new RuntimeException("Expected " + name + " but got " + result);
        }
    }

    private static void checkIllegalArgument(String name) {
        try {
            jdk.internal.module.Checks.requirePackageName(name);
        } catch (IllegalArgumentException expected) {
            return;
        }
        throw new RuntimeException("IllegalArgumentException not thrown for: " + name);
    }
}
