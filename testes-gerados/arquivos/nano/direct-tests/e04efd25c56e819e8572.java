/* @test    @summary Test constructor DistributionPointName(GeneralNames) with valid and null input    @run main DistributionPointNameWitupTest */

import sun.security.x509.DistributionPointName;
import sun.security.x509.GeneralNames;
import java.util.*;

public class DistributionPointNameWitupTest {

    public static void main(String[] args) {
        testValidFullName();
        testNullFullName();
    }

    private static void testValidFullName() {
        GeneralNames gn = new GeneralNames();
        DistributionPointName dpn = new DistributionPointName(gn);
        assert dpn != null : "Constructor with valid GeneralNames should not be null";
    }

    private static void testNullFullName() {
        try {
            new DistributionPointName(null);
            assert false : "Constructor with null should throw IllegalArgumentException";
        } catch (IllegalArgumentException e) {
            assert e.getMessage().equals("fullName must not be null") : "Exception message mismatch";
        }
    }
}
