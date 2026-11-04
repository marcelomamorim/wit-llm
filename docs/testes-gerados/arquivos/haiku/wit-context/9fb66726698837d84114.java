/*
 * @test
 * @summary Verifies that CheckClassAdapter.visitSource throws IllegalStateException when visit has not been called first
 * @modules java.base/jdk.internal.org.objectweb.asm.util
 * @run main CheckClassAdapterWitupTest
 */

import jdk.internal.org.objectweb.asm.util.CheckClassAdapter;

public class CheckClassAdapterWitupTest {

    public static void main(String[] args) {
        testVisitSourceBeforeVisitThrows();
    }

    static void testVisitSourceBeforeVisitThrows() {
        CheckClassAdapter cca = new CheckClassAdapter(null);
        try {
            cca.visitSource("A.java", null);
        } catch (IllegalStateException e) {
            return;
        }
        throw new AssertionError("expected IllegalStateException when visitSource is called before visit");
    }
}
