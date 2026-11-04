/*
 * @test
 * @summary Witup generated tests for CRLNumberExtension.set(String, Object)
 * @modules java.base/sun.security.x509
 * @run main CRLNumberExtensionWitupTest
 */

import java.io.IOException;
import java.math.BigInteger;

import sun.security.x509.CRLNumberExtension;

public class CRLNumberExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        testSetValueWithBigIntegerAccepted();
        testSetValueIsCaseInsensitive();
        testSetUnknownNameThrowsIOException();
        testSetNonBigIntegerValueThrowsIOException();
        testSetNullValueThrowsIOException();
    }

    static void testSetValueWithBigIntegerAccepted() throws IOException {
        CRLNumberExtension ext = new CRLNumberExtension(BigInteger.valueOf(5));
        ext.set(CRLNumberExtension.NUMBER, BigInteger.valueOf(42));
    }

    static void testSetValueIsCaseInsensitive() throws IOException {
        CRLNumberExtension ext = new CRLNumberExtension(BigInteger.valueOf(5));
        ext.set("VALUE", BigInteger.valueOf(7));
        ext.set("Value", BigInteger.TEN);
    }

    static void testSetUnknownNameThrowsIOException() throws IOException {
        CRLNumberExtension ext = new CRLNumberExtension(BigInteger.valueOf(5));
        expectIOException(() -> ext.set("unknown", BigInteger.ONE));
    }

    static void testSetNonBigIntegerValueThrowsIOException() throws IOException {
        CRLNumberExtension ext = new CRLNumberExtension(BigInteger.valueOf(5));
        expectIOException(() -> ext.set(CRLNumberExtension.NUMBER, Integer.valueOf(3)));
        expectIOException(() -> ext.set(CRLNumberExtension.NUMBER, "3"));
    }

    static void testSetNullValueThrowsIOException() throws IOException {
        CRLNumberExtension ext = new CRLNumberExtension(BigInteger.valueOf(5));
        expectIOException(() -> ext.set(CRLNumberExtension.NUMBER, null));
    }

    interface IOAction {
        void run() throws IOException;
    }

    static void expectIOException(IOAction action) {
        try {
            action.run();
        } catch (IOException expected) {
            return;
        }
        throw new AssertionError("Expected IOException was not thrown");
    }
}
