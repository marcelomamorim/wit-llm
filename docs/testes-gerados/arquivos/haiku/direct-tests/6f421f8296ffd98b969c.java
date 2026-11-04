/*
 * @test
 * @summary Checks state validation of CheckSignatureAdapter.visitInnerClassType
 * @modules java.base/jdk.internal.org.objectweb.asm.util
 * @run main CheckSignatureAdapterWitupTest
 */

import jdk.internal.org.objectweb.asm.util.CheckSignatureAdapter;

public class CheckSignatureAdapterWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testThrowsWithoutClassType() {
        CheckSignatureAdapter adapter = new CheckSignatureAdapter(CheckSignatureAdapter.TYPE_SIGNATURE, null);
        boolean thrown = false;
        try {
            adapter.visitInnerClassType("Inner");
        } catch (IllegalStateException e) {
            thrown = true;
        }
        check(thrown, "expected IllegalStateException when no class type was started");
    }

    static void testAcceptsAfterClassType() {
        CheckSignatureAdapter adapter = new CheckSignatureAdapter(CheckSignatureAdapter.TYPE_SIGNATURE, null);
        adapter.visitClassType("java/util/Map");
        adapter.visitInnerClassType("Entry");
    }

    public static void main(String[] args) {
        testThrowsWithoutClassType();
        testAcceptsAfterClassType();
    }
}
