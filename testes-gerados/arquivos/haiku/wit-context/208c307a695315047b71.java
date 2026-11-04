/*
 * @test
 * @summary Witup-generated tests for Checks.requireQualifiedClassName
 * @modules java.base/jdk.internal.module
 * @run main ChecksWitupTest
 */

import jdk.internal.module.Checks;

public class ChecksWitupTest {
    public static void main(String[] args) {
        String qualified = "java.lang.Object";
        String result = Checks.requireQualifiedClassName("service type name", qualified);
        if (!qualified.equals(result))
            throw new RuntimeException("unexpected return value: " + result);

        try {
            Checks.requireQualifiedClassName("service type name", null);
            throw new RuntimeException("expected IllegalArgumentException for null name");
        } catch (IllegalArgumentException expected) {
        }

        try {
            Checks.requireQualifiedClassName("service type name", "Object");
            throw new RuntimeException("expected IllegalArgumentException for unqualified name");
        } catch (IllegalArgumentException expected) {
        }
    }
}
