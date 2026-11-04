/*
 * @test
 * @summary Test sun.security.x509.GeneralNames.add(GeneralName) method
 * @run main GeneralNamesWitupTest
 */

import sun.security.x509.GeneralNames;
import sun.security.x509.GeneralName;

public class GeneralNamesWitupTest {
    public static void main(String[] args) throws Exception {
        GeneralNames generalNames = new GeneralNames();

        // Test adding a valid GeneralName instance
        GeneralName validName = new GeneralName();
        generalNames.add(validName);
        if (generalNames.size() != 1) {
            throw new RuntimeException("Failed to add valid GeneralName instance");
        }
        if (generalNames.get(0) != validName) {
            throw new RuntimeException("The added GeneralName instance is not retrievable correctly");
        }

        // Test adding null triggers NullPointerException
        boolean npeThrown = false;
        try {
            generalNames.add(null);
        } catch (NullPointerException e) {
            npeThrown = true;
        }
        if (!npeThrown) {
            throw new RuntimeException("Expected NullPointerException when adding null GeneralName");
        }
    }
}
