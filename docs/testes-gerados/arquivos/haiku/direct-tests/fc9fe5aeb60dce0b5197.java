/*
 * @test
 * @summary Tests CRLDistributionPointsExtension.delete(String) for recognized and unrecognized attribute names
 * @modules java.base/sun.security.x509
 * @run main CRLDistributionPointsExtensionWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import sun.security.x509.CRLDistributionPointsExtension;
import sun.security.x509.DistributionPoint;

public class CRLDistributionPointsExtensionWitupTest {

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    static byte[] encodeOf(CRLDistributionPointsExtension ext) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ext.encode(bos);
        return bos.toByteArray();
    }

    static void testDeleteUnknownNameThrows() throws IOException {
        CRLDistributionPointsExtension ext =
                new CRLDistributionPointsExtension(new ArrayList<DistributionPoint>());
        boolean thrown = false;
        try {
            ext.delete("bogus");
        } catch (IOException e) {
            thrown = true;
        }
        check(thrown, "delete with unknown name must throw IOException");
    }

    static void testDeleteEmptyNameThrows() throws IOException {
        CRLDistributionPointsExtension ext =
                new CRLDistributionPointsExtension(new ArrayList<DistributionPoint>());
        boolean thrown = false;
        try {
            ext.delete("");
        } catch (IOException e) {
            thrown = true;
        }
        check(thrown, "delete with empty name must throw IOException");
    }

    static void testDeletePointsKeepsEncodingOfEmptyList() throws IOException {
        CRLDistributionPointsExtension ext =
                new CRLDistributionPointsExtension(new ArrayList<DistributionPoint>());
        CRLDistributionPointsExtension fresh =
                new CRLDistributionPointsExtension(new ArrayList<DistributionPoint>());
        ext.delete(CRLDistributionPointsExtension.POINTS);
        check(Arrays.equals(encodeOf(ext), encodeOf(fresh)),
                "encoding after delete(POINTS) must equal empty extension encoding");
    }

    static void testDeletePointsIsCaseInsensitive() throws IOException {
        CRLDistributionPointsExtension ext =
                new CRLDistributionPointsExtension(new ArrayList<DistributionPoint>());
        CRLDistributionPointsExtension fresh =
                new CRLDistributionPointsExtension(new ArrayList<DistributionPoint>());
        ext.delete(CRLDistributionPointsExtension.POINTS.toUpperCase());
        check(Arrays.equals(encodeOf(ext), encodeOf(fresh)),
                "delete must match POINTS ignoring case");
    }

    public static void main(String[] args) throws Exception {
        testDeleteUnknownNameThrows();
        testDeleteEmptyNameThrows();
        testDeletePointsKeepsEncodingOfEmptyList();
        testDeletePointsIsCaseInsensitive();
    }
}
