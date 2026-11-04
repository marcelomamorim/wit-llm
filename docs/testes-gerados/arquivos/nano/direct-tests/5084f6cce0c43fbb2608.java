/* @test    @summary Test the encode method of ContentInfo class for correct DER encoding behavior    @run main ContentInfoWitupTest */

import java.io.*;
import sun.security.util.*;
import sun.security.pkcs.*;

public class ContentInfoWitupTest {

    public static void main(String[] args) throws Exception {
        testEncodeWithContent();
        testEncodeWithoutContent();
    }

    static void testEncodeWithContent() throws Exception {
        ObjectIdentifier oid = ObjectIdentifier.newInternal(new int[]{1, 2, 840, 113549, 1, 7, 1});
        DerValue contentValue = new DerValue(new byte[]{0x01, 0x02, 0x03});
        ContentInfo ci = new ContentInfo(oid, contentValue);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DerOutputStream dos = new DerOutputStream(baos);
        ci.encode(dos);
        byte[] encoded = baos.toByteArray();
        // Verify that the encoding contains the OID and the explicit [0] tag
        boolean containsOID = false;
        boolean containsExplicitTag = false;
        for (int i = 0; i < encoded.length; i++) {
            if (encoded[i] == (byte)0x06) { // OID tag
                containsOID = true;
            }
            if (encoded[i] == (byte)0xA0) { // [0] explicit tag
                containsExplicitTag = true;
            }
        }
        if (!containsOID || !containsExplicitTag) {
            throw new AssertionError("Encoding with content missing expected tags");
        }
    }

    static void testEncodeWithoutContent() throws Exception {
        ObjectIdentifier oid = ObjectIdentifier.newInternal(new int[]{1, 2, 840, 113549, 1, 7, 1});
        ContentInfo ci = new ContentInfo(oid, null);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DerOutputStream dos = new DerOutputStream(baos);
        ci.encode(dos);
        byte[] encoded = baos.toByteArray();
        // Verify that the encoding contains the OID and no explicit [0] tag
        boolean containsOID = false;
        boolean containsExplicitTag = false;
        for (int i = 0; i < encoded.length; i++) {
            if (encoded[i] == (byte)0x06) { // OID tag
                containsOID = true;
            }
            if (encoded[i] == (byte)0xA0) { // [0] explicit tag
                containsExplicitTag = true;
            }
        }
        if (!containsOID) {
            throw new AssertionError("Encoding without content missing OID tag");
        }
        if (containsExplicitTag) {
            throw new AssertionError("Encoding without content should not contain explicit [0] tag");
        }
    }
}
