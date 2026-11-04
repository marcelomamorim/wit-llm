/*
 * @test
 * @summary Test GeneralName constructor with GeneralNameInterface parameter
 * @run main GeneralNameWitupTest
 */

import sun.security.x509.GeneralName;
import sun.security.x509.GeneralNameInterface;

public class GeneralNameWitupTest {
    public static void main(String[] args) {
        testConstructorNullArgument();
        testConstructorValidArgument();
    }

    private static void testConstructorNullArgument() {
        try {
            new GeneralName(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            if (!"GeneralName must not be null".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected exception message: " + e.getMessage());
            }
        }
    }

    private static void testConstructorValidArgument() {
        GeneralNameInterface dummyName = new GeneralNameInterface() {
            public int getType() { return 0; }
            public String toString() { return "dummy"; }
            public boolean equals(Object obj) { return this == obj; }
            public int hashCode() { return 42; }
            public void encode(sun.security.util.DerOutputStream out) { /* no-op */ }
        };
        GeneralName gn = new GeneralName(dummyName);
        if (gn == null) {
            throw new RuntimeException("GeneralName constructor returned null");
        }
    }
}
