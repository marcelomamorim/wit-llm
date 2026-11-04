/* @test    @summary Test for ContentInfo(byte[] bytes) constructor in sun.security.pkcs.ContentInfo    @run main ContentInfoWitupTest */

import java.io.*;
import sun.security.pkcs.*;
import sun.security.util.*;

public class ContentInfoWitupTest {

    public static void main(String[] args) {
        testValidByteArray();
        testNullByteArray();
        testEmptyByteArray();
        testInvalidByteArray();
    }

    static void testValidByteArray() {
        try {
            byte[] validBytes = {0x04, 0x02, 0x00, 0x00}; // DER octet string
            ContentInfo ci = new ContentInfo(validBytes);
            if (ci == null || ci.contentType == null || ci.content == null) {
                throw new AssertionError("Valid byte array test failed: null result");
            }
        } catch (Exception e) {
            throw new AssertionError("Valid byte array test threw exception: " + e);
        }
    }

    static void testNullByteArray() {
        try {
            byte[] nullBytes = null;
            ContentInfo ci = new ContentInfo(nullBytes);
            if (ci == null || ci.contentType == null || ci.content == null) {
                throw new AssertionError("Null byte array test failed: null result");
            }
        } catch (NullPointerException e) {
            // Expected if constructor does not handle null
        } catch (Exception e) {
            throw new AssertionError("Null byte array test threw unexpected exception: " + e);
        }
    }

    static void testEmptyByteArray() {
        try {
            byte[] emptyBytes = {};
            ContentInfo ci = new ContentInfo(emptyBytes);
            if (ci == null || ci.contentType == null || ci.content == null) {
                throw new AssertionError("Empty byte array test failed: null result");
            }
        } catch (Exception e) {
            throw new AssertionError("Empty byte array test threw exception: " + e);
        }
    }

    static void testInvalidByteArray() {
        try {
            byte[] invalidBytes = {0x00, 0x01, 0x02};
            ContentInfo ci = new ContentInfo(invalidBytes);
            if (ci == null || ci.contentType == null || ci.content == null) {
                throw new AssertionError("Invalid byte array test failed: null result");
            }
        } catch (Exception e) {
            // Expecting possible IOException or DerValueException
        }
    }
}
