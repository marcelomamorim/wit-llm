/*
 * @test
 * @summary Test constructor GeneralName(GeneralNameInterface) for null and valid input
 * @run main GeneralNameWitupTest
 */

import sun.security.x509.GeneralName;
import sun.security.x509.GeneralNameInterface;

public class GeneralNameWitupTest {
    public static void main(String[] args) throws Exception {
        testConstructorWithNull();
        testConstructorWithValidName();
    }

    private static void testConstructorWithNull() {
        try {
            new GeneralName(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            if (!"GeneralName must not be null".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected exception message: " + e.getMessage());
            }
        }
    }

    private static void testConstructorWithValidName() {
        GeneralNameInterface dummyName = new GeneralNameInterface() {
            public int getType() { return 0; }
            public String toString() { return "dummy"; }
            public boolean equals(Object obj) { return this == obj; }
            public int hashCode() { return 42; }
        };
        GeneralName gn = new GeneralName(dummyName);
        if (gn == null) {
            throw new RuntimeException("GeneralName instance is null");
        }
    }
}
