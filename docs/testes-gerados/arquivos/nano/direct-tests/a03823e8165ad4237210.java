/* @test    @summary Test ContentInfo(byte[] bytes) constructor with valid input    @run main ContentInfoWitupTest */

import java.io.*;
import sun.security.pkcs.*;
import sun.security.util.*;

public class ContentInfoWitupTest {

    public static void main(String[] args) {
        testValidByteArrayInput();
        testEmptyByteArray();
        testInvalidByteArray();
    }

    static void testValidByteArrayInput() {
        try {
            byte[] dataBytes = {0x01, 0x02, 0x03};
            ContentInfo ci = new ContentInfo(dataBytes);
            assert ci != null : "ContentInfo instance should not be null";
            assert ci.contentType.equals(ContentInfo.DATA_OID) : "ContentType should be DATA_OID";
            assert ci.content != null : "Content should not be null";
            byte[] contentBytes = ci.content.getOctetString();
            assert java.util.Arrays.equals(contentBytes, dataBytes) : "Content bytes should match input";
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in testValidByteArrayInput", e);
        }
    }

    static void testEmptyByteArray() {
        try {
            byte[] emptyBytes = {};
            ContentInfo ci = new ContentInfo(emptyBytes);
            assert ci != null : "ContentInfo instance should not be null";
            assert ci.contentType.equals(ContentInfo.DATA_OID) : "ContentType should be DATA_OID";
            assert ci.content != null : "Content should not be null";
            byte[] contentBytes = ci.content.getOctetString();
            assert contentBytes.length == 0 : "Content bytes should be empty";
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in testEmptyByteArray", e);
        }
    }

    static void testInvalidByteArray() {
        try {
            byte[] invalidBytes = null;
            new ContentInfo(invalidBytes);
            throw new AssertionError("Expected NullPointerException for null input");
        } catch (NullPointerException e) {
            // expected
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type", e);
        }
    }
}
