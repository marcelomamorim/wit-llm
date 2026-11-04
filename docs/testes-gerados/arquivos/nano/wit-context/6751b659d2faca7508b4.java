/* @test    @summary Test for OAEPParameterSpec default constructor and null inputs    @run main OAEPParameterSpecWitupTest */

import javax.crypto.spec.OAEPParameterSpec;
import javax.crypto.spec.PSource;

public class OAEPParameterSpecWitupTest {

    public static void main(String[] args) {
        testDefaultConstructor();
        testNullMdName();
        testNullMgfName();
        testNullPSource();
    }

    private static void testDefaultConstructor() {
        OAEPParameterSpec spec = new OAEPParameterSpec();
        assert spec != null : "Default constructor returned null";
        assert "SHA-1".equals(spec.mdName) : "Default mdName should be SHA-1";
        assert "MGF1".equals(spec.mgfName) : "Default mgfName should be MGF1";
        assert spec.mgfSpec != null : "mgfSpec should not be null";
        assert spec.pSrc != null : "pSrc should not be null";
        assert spec.pSrc.equals(PSource.PSpecified.DEFAULT) : "Default pSrc should be PSource.PSpecified.DEFAULT";
    }

    private static void testNullMdName() {
        try {
            new OAEPParameterSpec(null, "MGF1", PSource.PSpecified.DEFAULT, PSource.PSpecified.DEFAULT);
            assert false : "Expected NullPointerException for null mdName";
        } catch (NullPointerException e) {
            // expected
        }
    }

    private static void testNullMgfName() {
        try {
            new OAEPParameterSpec("SHA-1", null, PSource.PSpecified.DEFAULT, PSource.PSpecified.DEFAULT);
            assert false : "Expected NullPointerException for null mgfName";
        } catch (NullPointerException e) {
            // expected
        }
    }

    private static void testNullPSource() {
        try {
            new OAEPParameterSpec("SHA-1", "MGF1", null, PSource.PSpecified.DEFAULT);
            assert false : "Expected NullPointerException for null pSrc";
        } catch (NullPointerException e) {
            // expected
        }
    }
}
