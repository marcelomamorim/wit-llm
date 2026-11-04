/*
 * @test
 * @summary Verifies observable behavior of InhibitAnyPolicyExtension.set(String, Object)
 * @modules java.base/sun.security.x509
 * @run main InhibitAnyPolicyExtensionWitupTest
 */

import java.io.IOException;
import sun.security.x509.InhibitAnyPolicyExtension;

public class InhibitAnyPolicyExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        testSetValidIntegerAccepted();
        testSetMinusOneAccepted();
        testSetNameIsCaseInsensitive();
        testSetNonIntegerRejected();
        testSetNegativeBelowMinusOneRejected();
        testSetUnknownNameRejected();
        testConstructorRejectsBelowMinusOne();
        testSetAfterValidConstruction();
    }

    static void testSetValidIntegerAccepted() throws Exception {
        InhibitAnyPolicyExtension ext = new InhibitAnyPolicyExtension(3);
        ext.set(InhibitAnyPolicyExtension.SKIP_CERTS, Integer.valueOf(0));
        ext.set(InhibitAnyPolicyExtension.SKIP_CERTS, Integer.valueOf(5));
    }

    static void testSetMinusOneAccepted() throws Exception {
        InhibitAnyPolicyExtension ext = new InhibitAnyPolicyExtension(2);
        ext.set(InhibitAnyPolicyExtension.SKIP_CERTS, Integer.valueOf(-1));
    }

    static void testSetNameIsCaseInsensitive() throws Exception {
        InhibitAnyPolicyExtension ext = new InhibitAnyPolicyExtension(1);
        ext.set("SKIP_CERTS", Integer.valueOf(4));
        ext.set("Skip_Certs", Integer.valueOf(-1));
    }

    static void testSetNonIntegerRejected() throws Exception {
        InhibitAnyPolicyExtension ext = new InhibitAnyPolicyExtension(1);
        expectIOException(() -> ext.set(InhibitAnyPolicyExtension.SKIP_CERTS, "3"));
        expectIOException(() -> ext.set(InhibitAnyPolicyExtension.SKIP_CERTS, Long.valueOf(3L)));
        expectIOException(() -> ext.set(InhibitAnyPolicyExtension.SKIP_CERTS, null));
    }

    static void testSetNegativeBelowMinusOneRejected() throws Exception {
        InhibitAnyPolicyExtension ext = new InhibitAnyPolicyExtension(1);
        expectIOException(() -> ext.set(InhibitAnyPolicyExtension.SKIP_CERTS, Integer.valueOf(-2)));
    }

    static void testSetUnknownNameRejected() throws Exception {
        InhibitAnyPolicyExtension ext = new InhibitAnyPolicyExtension(1);
        expectIOException(() -> ext.set("unknown", Integer.valueOf(1)));
    }

    static void testConstructorRejectsBelowMinusOne() throws Exception {
        expectIOException(() -> new InhibitAnyPolicyExtension(-2));
    }

    static void testSetAfterValidConstruction() throws Exception {
        InhibitAnyPolicyExtension ext = new InhibitAnyPolicyExtension(-1);
        ext.set(InhibitAnyPolicyExtension.SKIP_CERTS, Integer.valueOf(7));
        expectIOException(() -> ext.set(InhibitAnyPolicyExtension.SKIP_CERTS, Integer.valueOf(-5)));
    }

    interface IOAction {
        void run() throws IOException;
    }

    static void expectIOException(IOAction action) {
        try {
            action.run();
        } catch (IOException e) {
            return;
        }
        throw new RuntimeException("Expected IOException was not thrown");
    }
}
