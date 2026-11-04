/*
 * @test
 * @summary Verify that the deprecated RemappingClassAdapter.visitModule always throws a RuntimeException with the documented message
 * @modules java.base/jdk.internal.org.objectweb.asm java.base/jdk.internal.org.objectweb.asm.commons
 * @run main RemappingClassAdapterWitupTest
 */

import jdk.internal.org.objectweb.asm.ClassVisitor;
import jdk.internal.org.objectweb.asm.commons.Remapper;
import jdk.internal.org.objectweb.asm.commons.RemappingClassAdapter;

public class RemappingClassAdapterWitupTest {

    public static void main(String[] args) {
        Remapper remapper = new Remapper() {};
        RemappingClassAdapter adapter =
                new RemappingClassAdapter((ClassVisitor) null, remapper);

        checkThrows(adapter, "java/lang/Object", 0, null);
        checkThrows(adapter, null, 0, null);
        checkThrows(adapter, "module", 1, "1.0");
    }

    @SuppressWarnings("deprecation")
    private static void checkThrows(RemappingClassAdapter adapter,
                                    String name, int flags, String version) {
        try {
            adapter.visitModule(name, flags, version);
            throw new AssertionError("expected RuntimeException was not thrown");
        } catch (RuntimeException e) {
            if (e.getClass() != RuntimeException.class) {
                throw new AssertionError("unexpected exception type: " + e.getClass());
            }
            String msg = e.getMessage();
            if (msg == null || !msg.contains("ClassRemapper")) {
                throw new AssertionError("unexpected message: " + msg);
            }
        }
    }
}
