/* @test    @summary Test the add method of GeneralNames class for null and valid input    @run main GeneralNamesWitupTest */

import sun.security.x509.GeneralNames;
import sun.security.x509.GeneralName;
import java.io.IOException;

public class GeneralNamesWitupTest {

    public static void main(String[] args) {
        testAddNull();
        testAddValid();
    }

    private static void testAddNull() {
        GeneralNames gnames = new GeneralNames();
        boolean exceptionThrown = false;
        try {
            gnames.add(null);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected NullPointerException when adding null");
        }
    }

    private static void testAddValid() {
        GeneralNames gnames = new GeneralNames();
        GeneralName name = new GeneralName();
        gnames.add(name);
        if (gnames.size() != 1) {
            throw new AssertionError("Size should be 1 after adding one name");
        }
        if (gnames.get(0) != name) {
            throw new AssertionError("The retrieved name does not match the added one");
        }
    }
}
