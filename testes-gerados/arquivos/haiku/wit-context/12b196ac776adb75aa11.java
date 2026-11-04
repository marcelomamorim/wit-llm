/*
 * @test
 * @summary Witup-derived checks for CheckSignatureAdapter.visitTypeVariable exception paths
 * @modules java.base/jdk.internal.org.objectweb.asm.util java.base/jdk.internal.org.objectweb.asm.signature
 * @run main CheckSignatureAdapterWitupTest
 */

import jdk.internal.org.objectweb.asm.util.CheckSignatureAdapter;

public class CheckSignatureAdapterWitupTest {

    public static void main(String[] args) {
        testValidTypeVariableAccepted();
        testIllegalStateForClassSignatureType();
        testIllegalStateForMethodSignatureType();
        testIllegalStateOnSecondCall();
        testIllegalArgumentForNullName();
        testIllegalArgumentForEmptyName();
    }

    static void testValidTypeVariableAccepted() {
        CheckSignatureAdapter csa =
                new CheckSignatureAdapter(CheckSignatureAdapter.TYPE_SIGNATURE, null);
        csa.visitTypeVariable("T");
    }

    static void testIllegalStateForClassSignatureType() {
        CheckSignatureAdapter csa =
                new CheckSignatureAdapter(CheckSignatureAdapter.CLASS_SIGNATURE, null);
        try {
            csa.visitTypeVariable("T");
        } catch (IllegalStateException expected) {
            return;
        }
        throw new RuntimeException("IllegalStateException expected for CLASS_SIGNATURE");
    }

    static void testIllegalStateForMethodSignatureType() {
        CheckSignatureAdapter csa =
                new CheckSignatureAdapter(CheckSignatureAdapter.METHOD_SIGNATURE, null);
        try {
            csa.visitTypeVariable("T");
        } catch (IllegalStateException expected) {
            return;
        }
        throw new RuntimeException("IllegalStateException expected for METHOD_SIGNATURE");
    }

    static void testIllegalStateOnSecondCall() {
        CheckSignatureAdapter csa =
                new CheckSignatureAdapter(CheckSignatureAdapter.TYPE_SIGNATURE, null);
        csa.visitTypeVariable("T");
        try {
            csa.visitTypeVariable("U");
        } catch (IllegalStateException expected) {
            return;
        }
        throw new RuntimeException("IllegalStateException expected on second call");
    }

    static void testIllegalArgumentForNullName() {
        CheckSignatureAdapter csa =
                new CheckSignatureAdapter(CheckSignatureAdapter.TYPE_SIGNATURE, null);
        try {
            csa.visitTypeVariable(null);
        } catch (IllegalArgumentException expected) {
            return;
        }
        throw new RuntimeException("IllegalArgumentException expected for null name");
    }

    static void testIllegalArgumentForEmptyName() {
        CheckSignatureAdapter csa =
                new CheckSignatureAdapter(CheckSignatureAdapter.TYPE_SIGNATURE, null);
        try {
            csa.visitTypeVariable("");
        } catch (IllegalArgumentException expected) {
            return;
        }
        throw new RuntimeException("IllegalArgumentException expected for empty name");
    }
}
