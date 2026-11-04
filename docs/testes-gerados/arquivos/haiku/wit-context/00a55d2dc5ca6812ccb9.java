/*
 * @test
 * @summary WITUP-derived checks for CheckSignatureAdapter.visitClassType state and name validation
 * @modules java.base/jdk.internal.org.objectweb.asm.util java.base/jdk.internal.org.objectweb.asm.signature
 * @run main CheckSignatureAdapterWitupTest
 */

import jdk.internal.org.objectweb.asm.util.CheckSignatureAdapter;

public class CheckSignatureAdapterWitupTest {

    public static void main(String[] args) {
        CheckSignatureAdapter valid = new CheckSignatureAdapter(CheckSignatureAdapter.TYPE_SIGNATURE, null);
        valid.visitClassType("java/lang/String");

        final CheckSignatureAdapter wrongType =
                new CheckSignatureAdapter(CheckSignatureAdapter.CLASS_SIGNATURE, null);
        expect(IllegalStateException.class, () -> wrongType.visitClassType("java/lang/String"));

        final CheckSignatureAdapter twice = new CheckSignatureAdapter(CheckSignatureAdapter.TYPE_SIGNATURE, null);
        twice.visitClassType("java/lang/String");
        expect(IllegalStateException.class, () -> twice.visitClassType("java/lang/Integer"));

        final CheckSignatureAdapter nullName =
                new CheckSignatureAdapter(CheckSignatureAdapter.TYPE_SIGNATURE, null);
        expect(IllegalArgumentException.class, () -> nullName.visitClassType(null));

        final CheckSignatureAdapter emptyName =
                new CheckSignatureAdapter(CheckSignatureAdapter.TYPE_SIGNATURE, null);
        expect(IllegalArgumentException.class, () -> emptyName.visitClassType(""));
    }

    private interface Action {
        void run();
    }

    private static void expect(Class<? extends Throwable> expected, Action action) {
        try {
            action.run();
        } catch (Throwable t) {
            if (!expected.isInstance(t)) {
                throw new RuntimeException("Unexpected exception: " + t, t);
            }
            return;
        }
        throw new RuntimeException("Expected " + expected.getName() + " was not thrown");
    }
}
