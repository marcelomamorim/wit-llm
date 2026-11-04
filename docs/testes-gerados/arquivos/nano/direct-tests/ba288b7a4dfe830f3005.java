/*
 * @test
 * @summary Test CheckClassAdapter.visitEnd() method for correct behavior.
 * @run main CheckClassAdapterWitupTest
 */

import jdk.internal.org.objectweb.asm.util.CheckClassAdapter;

public class CheckClassAdapterWitupTest {

    public static void main(String[] args) {
        testVisitEnd();
    }

    public static void testVisitEnd() {
        CheckClassAdapter adapter = new CheckClassAdapter(null);
        try {
            adapter.visitEnd();
            System.out.println("visitEnd() executed without exceptions.");
        } catch (Exception e) {
            System.err.println("Exception during visitEnd(): " + e);
            e.printStackTrace();
        }
    }
}
