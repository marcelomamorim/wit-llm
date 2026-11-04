/* @test    @summary Test constructor of GeneralName with valid GeneralNameInterface instance    @run main GeneralNameWitupTest */

import sun.security.x509.GeneralName;
import sun.security.x509.GeneralNameInterface;

public class GeneralNameWitupTest {

    static class DummyName implements GeneralNameInterface {
        @Override
        public String toString() {
            return "DummyName";
        }
    }

    public static void main(String[] args) {
        testConstructorWithValidName();
        testConstructorWithNull();
    }

    static void testConstructorWithValidName() {
        GeneralNameInterface dummy = new DummyName();
        GeneralName gn = new GeneralName(dummy);
        if (gn == null) {
            throw new AssertionError("Constructor returned null");
        }
        System.out.println("testConstructorWithValidName passed");
    }

    static void testConstructorWithNull() {
        boolean exceptionThrown = false;
        try {
            new GeneralName((GeneralNameInterface) null);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected NullPointerException not thrown");
        }
        System.out.println("testConstructorWithNull passed");
    }
}
