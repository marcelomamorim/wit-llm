/*
 * @test
 * @summary ModuleVisitor(int api) delegates to ModuleVisitor(api, null), stores api and tolerates calls with no delegate
 * @modules java.base/jdk.internal.org.objectweb.asm
 * @run main ModuleVisitorWitupTest
 */
import jdk.internal.org.objectweb.asm.ModuleVisitor;
import jdk.internal.org.objectweb.asm.Opcodes;

public class ModuleVisitorWitupTest {
    static class V extends ModuleVisitor {
        V(int api) {
            super(api);
        }

        int storedApi() {
            return this.api;
        }
    }

    public static void main(String[] args) {
        V v = new V(Opcodes.ASM6);
        check(v.storedApi() == Opcodes.ASM6, "api field must equal the value passed to the constructor");

        v.visitMainClass("a/B");
        v.visitPackage("a.b");
        v.visitRequire("java.base", 0, null);
        v.visitExport("a.b", 0);
        v.visitOpen("a.b", 0);
        v.visitUse("a/S");
        v.visitProvide("a/S", "a/Impl");
        v.visitEnd();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
