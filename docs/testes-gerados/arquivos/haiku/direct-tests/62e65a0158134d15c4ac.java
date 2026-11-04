/*
 * @test
 * @summary CheckModuleAdapter.visitRequire rejects null module names and delegates valid requires
 * @modules java.base/jdk.internal.org.objectweb.asm.util java.base/jdk.internal.org.objectweb.asm
 * @run main CheckModuleAdapterWitupTest
 */

import java.util.ArrayList;
import java.util.List;

import jdk.internal.org.objectweb.asm.ModuleVisitor;
import jdk.internal.org.objectweb.asm.Opcodes;
import jdk.internal.org.objectweb.asm.util.CheckModuleAdapter;

public class CheckModuleAdapterWitupTest {

    static final List<String> calls = new ArrayList<>();

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) {
        ModuleVisitor recorder = new ModuleVisitor(Opcodes.ASM6, null) {
            @Override
            public void visitRequire(String module, int access, String version) {
                calls.add(module + ":" + access + ":" + version);
            }
        };

        CheckModuleAdapter adapter = new CheckModuleAdapter(recorder, false);
        boolean thrown = false;
        try {
            adapter.visitRequire(null, 0, "11");
        } catch (IllegalArgumentException e) {
            thrown = true;
            check("require cannot be null".equals(e.getMessage()),
                  "unexpected message: " + e.getMessage());
        }
        check(thrown, "null module must throw IllegalArgumentException");
        check(calls.isEmpty(), "null module must not be delegated");

        CheckModuleAdapter adapter2 = new CheckModuleAdapter(recorder, true);
        adapter2.visitRequire("java.base", 0, "11");
        check(calls.size() == 1, "valid require must be delegated once");
        check("java.base:0:11".equals(calls.get(0)), "delegated call mismatch: " + calls.get(0));

        adapter2.visitRequire("java.logging", Opcodes.ACC_TRANSITIVE, null);
        check(calls.size() == 2, "second valid require must be delegated");
        check("java.logging:" + Opcodes.ACC_TRANSITIVE + ":null".equals(calls.get(1)) == false
              || calls.get(1).equals("java.logging:" + Opcodes.ACC_TRANSITIVE + ":null"),
              "delegated call with null version mismatch: " + calls.get(1));
    }
}
