/* @test    @summary Test the add method of GeneralSubtrees for null and valid input    @run main GeneralSubtreesWitupTest */

import sun.security.x509.GeneralSubtrees;
import sun.security.x509.GeneralSubtree;
import sun.security.util.DerValue;
import java.io.IOException;

public class GeneralSubtreesWitupTest {

    public static void main(String[] args) throws IOException {
        testAddNull();
        testAddValid();
    }

    private static void testAddNull() {
        GeneralSubtrees gst = new GeneralSubtrees();
        try {
            gst.add(null);
            System.out.println("testAddNull: Failed - no exception thrown");
        } catch (NullPointerException e) {
            System.out.println("testAddNull: Passed");
        }
    }

    private static void testAddValid() throws IOException {
        GeneralSubtrees gst = new GeneralSubtrees();
        DerValue dv = new DerValue(DerValue.tag_Sequence, new byte[] {0x30, 0x00});
        GeneralSubtree gs = new GeneralSubtree(dv);
        int initialSize = gst.size();
        gst.add(gs);
        if (gst.size() == initialSize + 1 && gst.contains(gs)) {
            System.out.println("testAddValid: Passed");
        } else {
            System.out.println("testAddValid: Failed");
        }
    }
}
