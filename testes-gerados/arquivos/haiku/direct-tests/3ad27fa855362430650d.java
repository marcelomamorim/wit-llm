/*
 * @test
 * @summary Tests the public constructor of KeyStore.SecretKeyEntry: null checks, defensive copy of attributes and unmodifiable attribute view
 * @run main KeyStoreWitupTest
 */

import java.security.KeyStore;
import java.security.KeyStore.Entry.Attribute;
import java.security.KeyStore.SecretKeyEntry;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import javax.crypto.SecretKey;

public class KeyStoreWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static SecretKey key(final String alg) {
        return new SecretKey() {
            public String getAlgorithm() { return alg; }
            public String getFormat() { return "RAW"; }
            public byte[] getEncoded() { return new byte[] {1, 2, 3}; }
        };
    }

    static class Attr implements Attribute {
        private final String name;
        private final String value;

        Attr(String name, String value) {
            this.name = name;
            this.value = value;
        }

        public String getName() { return name; }
        public String getValue() { return value; }
    }

    static void expectNPE(SecretKey k, Set<Attribute> attrs) {
        try {
            new SecretKeyEntry(k, attrs);
        } catch (NullPointerException expected) {
            return;
        }
        throw new RuntimeException("NullPointerException expected");
    }

    public static void main(String[] args) {
        SecretKey k = key("AES");
        Set<Attribute> attrs = new HashSet<Attribute>();
        attrs.add(new Attr("a", "1"));

        SecretKeyEntry entry = new SecretKeyEntry(k, attrs);
        check(entry.getSecretKey() == k, "secret key should be the same instance");
        check(entry.getAttributes().size() == 1, "attributes size should be 1");

        attrs.add(new Attr("b", "2"));
        check(entry.getAttributes().size() == 1,
              "entry must hold a defensive copy of attributes");

        boolean unsupported = false;
        try {
            entry.getAttributes().add(new Attr("c", "3"));
        } catch (UnsupportedOperationException e) {
            unsupported = true;
        }
        check(unsupported, "attributes view must be unmodifiable");

        expectNPE(null, attrs);
        expectNPE(k, null);
        expectNPE(null, null);

        SecretKeyEntry empty = new SecretKeyEntry(k, new HashSet<Attribute>());
        check(empty.getAttributes().isEmpty(), "empty attribute set should be accepted");
    }
}
