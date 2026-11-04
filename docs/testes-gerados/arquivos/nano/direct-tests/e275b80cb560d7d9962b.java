/* @test    @summary Test AdaptableX509CertSelector.setSubjectKeyIdentifier method for exception throwing and behavior.    @run main AdaptableX509CertSelectorWitupTest */

import java.lang.reflect.Method;
import sun.security.provider.certpath.AdaptableX509CertSelector;

public class AdaptableX509CertSelectorWitupTest {

    public static void main(String[] args) throws Exception {
        testSetSubjectKeyIdentifierThrowsException();
        testSetSubjectKeyIdentifierBehavior();
    }

    private static void testSetSubjectKeyIdentifierThrowsException() {
        AdaptableX509CertSelector selector = new AdaptableX509CertSelector();
        byte[] dummyBytes = {0x01, 0x02, 0x03};
        boolean exceptionThrown = false;
        try {
            selector.setSubjectKeyIdentifier(dummyBytes);
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected IllegalArgumentException was not thrown.");
        }
    }

    private static void testSetSubjectKeyIdentifierBehavior() throws Exception {
        AdaptableX509CertSelector selector = new AdaptableX509CertSelector();
        Method method = AdaptableX509CertSelector.class.getMethod("setSubjectKeyIdentifier", byte[].class);
        byte[] testBytes = {0x0A, 0x0B};
        boolean exceptionOccurred = false;
        try {
            method.invoke(selector, (Object) testBytes);
        } catch (java.lang.reflect.InvocationTargetException e) {
            if (e.getCause() instanceof IllegalArgumentException) {
                exceptionOccurred = true;
            } else {
                throw e;
            }
        }
        if (!exceptionOccurred) {
            throw new AssertionError("Reflection invocation did not throw IllegalArgumentException as expected.");
        }
    }
}
