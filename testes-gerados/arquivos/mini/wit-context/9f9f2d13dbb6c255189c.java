/*
 * @test
 * @summary Test that RemappingClassAdapter.visitModule always throws RuntimeException as deprecated
 * @run main RemappingClassAdapterWitupTest
 */

import jdk.internal.org.objectweb.asm.commons.RemappingClassAdapter;
import jdk.internal.org.objectweb.asm.ClassVisitor;
import jdk.internal.org.objectweb.asm.ModuleVisitor;

public class RemappingClassAdapterWitupTest {

    public static void main(String[] args) {
        ClassVisitor cv = new ClassVisitor(0) {};
        RemappingClassAdapter adapter = new RemappingClassAdapter(cv, null);

        try {
            adapter.visitModule("TestModule", 0, "1.0");
            throw new Error("Expected RuntimeException not thrown");
        } catch (RuntimeException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains("deprecated")) {
                throw new Error("Unexpected exception message: " + msg);
            }
        }
    }
}
