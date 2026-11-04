/*
 * @test
 * @summary Verifies Attributes.Name(String) construction, interning-independent equality through Attributes.get/put, and NPE for null names
 * @run main AttributesWitupTest
 */

import java.util.jar.Attributes;

public class AttributesWitupTest {
    public static void main(String[] args) {
        Attributes.Name name = new Attributes.Name("Created-By");
        if (name == null) {
            throw new RuntimeException("Name constructor returned null");
        }

        Attributes attrs = new Attributes();
        attrs.put(new Attributes.Name("Test-Attr"), "value1");

        Object value = attrs.get(new Attributes.Name(new String("Test-Attr")));
        if (!"value1".equals(value)) {
            throw new RuntimeException("Expected value1 but got " + value);
        }

        attrs.put(new Attributes.Name(new String("Test-Attr")), "value2");
        Object updated = attrs.get(new Attributes.Name("Test-Attr"));
        if (!"value2".equals(updated)) {
            throw new RuntimeException("Expected value2 after overwrite but got " + updated);
        }

        try {
            new Attributes.Name(null);
            throw new RuntimeException("Expected NullPointerException for null name");
        } catch (NullPointerException expected) {
            // expected: name.intern() dereferences null
        }
    }
}
