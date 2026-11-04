/*
 * @test
 * @summary Checks CheckSignatureAdapter.visitClassType state and type validation
 * @modules java.base/jdk.internal.org.objectweb.asm.util
 * @run main CheckSignatureAdapterWitupTest
 */

import jdk.internal.org.objectweb.asm.util.CheckSignatureAdapter;

public class CheckSignatureAdapterWitupTest {

    public static void main(String[] args) {
        testValidClassTypeOnTypeSignature();
        testSecondCallThrowsIllegalState();
        testWrongSignatureTypeClassThrowsIllegalState();
        testWrongSignatureTypeMethodThrowsIllegalState();
    }

    static void testValidClassTypeOnTypeSignature() {
        CheckSignatureAdapter a = new CheckSignatureAdapter(CheckSignatureAdapter.TYPE_SIGNATURE, null);
        a.visitClassType("java/lang/String");
    }

    static void testSecondCallThrowsIllegalState() {
        final CheckSignatureAdapter a = new CheckSignatureAdapter(CheckSignatureAdapter.TYPE_SIGNATURE, null);
        a.visitClassType("java/lang/String");
        expectIllegalState(new Runnable() {
            public void run() {
                a.visitClassType("java/lang/Object");
            }
        });
    }

    static void testWrongSignatureTypeClassThrowsIllegalState() {
        final CheckSignatureAdapter a = new CheckSignatureAdapter(CheckSignatureAdapter.CLASS_SIGNATURE, null);
        expectIllegalState(new Runnable() {
            public void run() {
                a.visitClassType("java/lang/String");
            }
        });
    }

    static void testWrongSignatureTypeMethodThrowsIllegalState() {
        final CheckSignatureAdapter a = new CheckSignatureAdapter(CheckSignatureAdapter.METHOD_SIGNATURE, null);
        expectIllegalState(new Runnable() {
            public void run() {
                a.visitClassType("java/lang/String");
            }
        });
    }

    static void expectIllegalState(Runnable r) {
        try {
            r.run();
        } catch (IllegalStateException e) {
            return;
        }
        throw new RuntimeException("Expected IllegalStateException was not thrown");
    }
}
