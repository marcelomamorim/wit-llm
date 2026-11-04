/*
 * @test
 * @summary Witup-generated checks for Module.isExported(String)
 * @run main ModuleWitupTest
 */

public class ModuleWitupTest {
    public static void main(String[] args) throws Exception {
        Module base = Object.class.getModule();
        if (!base.isExported("java.lang")) {
            throw new AssertionError("java.lang should be exported by java.base");
        }
        if (base.isExported("jdk.internal.misc")) {
            throw new AssertionError("jdk.internal.misc should not be exported by java.base");
        }
        if (base.isExported("no.such.pkg")) {
            throw new AssertionError("unknown package should not be exported");
        }
        Module unnamed = ModuleWitupTest.class.getModule();
        if (!unnamed.isExported("jdk.internal.misc")) {
            throw new AssertionError("unnamed module should export all packages");
        }
        try {
            base.isExported(null);
            throw new AssertionError("expected NullPointerException for null package name");
        } catch (NullPointerException expected) {
        }
    }
}
