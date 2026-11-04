/*
 * @test
 * @summary Tests observable behavior of the SNIHostName(String) constructor
 * @run main SNIHostNameWitupTest
 */

import javax.net.ssl.SNIHostName;

public class SNIHostNameWitupTest {

    public static void main(String[] args) throws Exception {
        testValidHostName();
        testIdnHostNameIsConvertedToAce();
        testNullHostNameThrowsNPE();
        testEmptyHostNameThrowsIAE();
        testTrailingDotThrowsIAE();
        testStd3InvalidCharacterThrowsIAE();
    }

    static void testValidHostName() {
        SNIHostName name = new SNIHostName("www.example.com");
        if (!"www.example.com".equals(name.getAsciiName())) {
            throw new AssertionError("unexpected ascii name: " + name.getAsciiName());
        }
    }

    static void testIdnHostNameIsConvertedToAce() {
        SNIHostName name = new SNIHostName("b\u00fccher.example");
        if (!"xn--bcher-kva.example".equals(name.getAsciiName())) {
            throw new AssertionError("unexpected ace name: " + name.getAsciiName());
        }
    }

    static void testNullHostNameThrowsNPE() {
        expectThrows(NullPointerException.class, () -> new SNIHostName((String) null));
    }

    static void testEmptyHostNameThrowsIAE() {
        expectThrows(IllegalArgumentException.class, () -> new SNIHostName(""));
    }

    static void testTrailingDotThrowsIAE() {
        expectThrows(IllegalArgumentException.class, () -> new SNIHostName("example.com."));
    }

    static void testStd3InvalidCharacterThrowsIAE() {
        expectThrows(IllegalArgumentException.class, () -> new SNIHostName("exa_mple.com"));
    }

    static void expectThrows(Class<? extends Throwable> type, Runnable r) {
        try {
            r.run();
        } catch (Throwable t) {
            if (type.isInstance(t)) {
                return;
            }
            throw new AssertionError("unexpected exception: " + t, t);
        }
        throw new AssertionError("expected " + type.getName() + " was not thrown");
    }
}
