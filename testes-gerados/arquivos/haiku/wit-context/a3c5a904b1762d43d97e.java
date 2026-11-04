/*
 * @test
 * @summary Witup-generated tests for jdk.internal.module.Checks.requireServiceTypeName
 * @modules java.base/jdk.internal.module
 * @run main ChecksWitupTest
 */

import jdk.internal.module.Checks;

public class ChecksWitupTest {

    public static void main(String[] args) {
        if (!"a.b.C".equals(Checks.requireServiceTypeName("a.b.C"))) {
            throw new AssertionError("qualified name not returned unchanged");
        }
        if (!"Svc".equals(Checks.requireServiceTypeName("Svc"))) {
            throw new AssertionError("simple name not returned unchanged");
        }
        expectIllegalArgument(null);
        expectIllegalArgument("a..b");
        expectIllegalArgument("1a.B");
    }

    static void expectIllegalArgument(String name) {
        try {
            Checks.requireServiceTypeName(name);
        } catch (IllegalArgumentException e) {
            return;
        }
        throw new AssertionError("expected IllegalArgumentException for: " + name);
    }
}
