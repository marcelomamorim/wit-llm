/*
 * @test
 * @summary Verify that CheckSignatureAdapter.visitInnerClassType throws IllegalStateException when the adapter is not in the class type state
 * @modules java.base/jdk.internal.org.objectweb.asm.util
 * @run main CheckSignatureAdapterWitupTest
 */
import jdk.internal.org.objectweb.asm.util.CheckSignatureAdapter;

public class CheckSignatureAdapterWitupTest {
    public static void main(String[] args) {
        int[] types = {
            CheckSignatureAdapter.CLASS_SIGNATURE,
            CheckSignatureAdapter.METHOD_SIGNATURE,
            CheckSignatureAdapter.TYPE_SIGNATURE
        };
        for (int type : types) {
            expectIllegalState(new CheckSignatureAdapter(type, null), "Name");
            expectIllegalState(new CheckSignatureAdapter(type, null), null);
            expectIllegalState(new CheckSignatureAdapter(type, null), "");
        }
    }

    private static void expectIllegalState(CheckSignatureAdapter csa, String name) {
        try {
            csa.visitInnerClassType(name);
        } catch (IllegalStateException e) {
            return;
        }
        throw new RuntimeException("Expected IllegalStateException for name=" + name);
    }
}
