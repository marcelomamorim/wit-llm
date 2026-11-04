/*
 * @test
 * @summary Test sun.security.x509.GeneralNames add(GeneralName) method
 * @run main GeneralNamesWitupTest
 */

import sun.security.x509.GeneralNames;
import sun.security.x509.GeneralName;
import java.util.Iterator;

public class GeneralNamesWitupTest {
    public static void main(String[] args) throws Exception {
        GeneralNames gnames = new GeneralNames();

        // Test isEmpty and size on new instance
        if (!gnames.isEmpty()) {
            throw new RuntimeException("Expected GeneralNames to be empty initially");
        }
        if (gnames.size() != 0) {
            throw new RuntimeException("Expected size 0 initially");
        }

        // Create a GeneralName instance with minimal valid data
        // Since GeneralName constructor requires DerValue, create a dummy DerValue
        // Use sun.security.util.DerValue with tag 0 and empty data
        sun.security.util.DerValue derVal = new sun.security.util.DerValue(sun.security.util.DerValue.tag_Context, new byte[0]);
        GeneralName gname = new GeneralName(derVal);

        // Add the GeneralName
        GeneralNames returned = gnames.add(gname);

        // Check that add returns the same GeneralNames instance
        if (returned != gnames) {
            throw new RuntimeException("add() should return the same GeneralNames instance");
        }

        // Check size and isEmpty after add
        if (gnames.isEmpty()) {
            throw new RuntimeException("Expected GeneralNames not to be empty after add");
        }
        if (gnames.size() != 1) {
            throw new RuntimeException("Expected size 1 after add");
        }

        // Check get(0) returns the added GeneralName
        GeneralName retrieved = gnames.get(0);
        if (retrieved != gname) {
            throw new RuntimeException("get(0) should return the added GeneralName");
        }

        // Check iterator returns the added GeneralName
        Iterator<GeneralName> it = gnames.iterator();
        if (!it.hasNext()) {
            throw new RuntimeException("Iterator should have next element");
        }
        GeneralName iterated = it.next();
        if (iterated != gname) {
            throw new RuntimeException("Iterator next() should return the added GeneralName");
        }
        if (it.hasNext()) {
            throw new RuntimeException("Iterator should have only one element");
        }

        // Check that adding null throws NullPointerException
        boolean npeThrown = false;
        try {
            gnames.add(null);
        } catch (NullPointerException npe) {
            npeThrown = true;
        }
        if (!npeThrown) {
            throw new RuntimeException("Expected NullPointerException when adding null");
        }
    }
}
