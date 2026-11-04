/* @test    @summary Test for sun.security.pkcs.ContentInfo.encode(DerOutputStream out) method    @run main ContentInfoWitupTest */

import java.io.*;
import sun.security.util.*;
import sun.security.pkcs.*;

public class ContentInfoWitupTest {

    public static void main(String[] args) throws Exception {
        testEncodeWithContent();
        testEncodeWithoutContent();
    }

    private static void testEncodeWithContent() throws Exception {
        ObjectIdentifier contentType = ObjectIdentifier.newInternal(new int[]{1, 2, 840, 113549, 1, 7, 1});
        byte[] dummyBytes = {0x01, 0x02, 0x03};
        DerValue contentDerValue = new DerValue(dummyBytes);
        DerInputStream derInputStream = new DerInputStream(dummyBytes);
        DerValue contentValue = new DerValue(derInputStream);
        ContentInfo ci = new ContentInfo(contentType, contentValue);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DerOutputStream dos = new DerOutputStream(baos);
        ci.encode(dos);
        byte[] encoded = baos.toByteArray();
        if (encoded == null || encoded.length == 0) {
            throw new AssertionError("Encoding with content produced empty output");
        }
        System.out.println("encode with content passed, output length: " + encoded.length);
    }

    private static void testEncodeWithoutContent() throws Exception {
        ObjectIdentifier contentType = ObjectIdentifier.newInternal(new int[]{1, 2, 840, 113549, 1, 7, 1});
        ContentInfo ci = new ContentInfo(contentType, null);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DerOutputStream dos = new DerOutputStream(baos);
        ci.encode(dos);
        byte[] encoded = baos.toByteArray();
        if (encoded == null || encoded.length == 0) {
            throw new AssertionError("Encoding without content produced empty output");
        }
        System.out.println("encode without content passed, output length: " + encoded.length);
    }
}
