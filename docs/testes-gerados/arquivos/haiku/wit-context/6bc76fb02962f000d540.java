/*
 * @test
 * @summary Witup-derived tests for the ZipEntry(String name) constructor
 * @run main ZipEntryWitupTest
 */

import java.util.zip.ZipEntry;

public class ZipEntryWitupTest {

    public static void main(String[] args) {
        testNullNameThrowsNPE();
        testTooLongNameThrowsIAE();
        testMaxLengthNameAccepted();
        testRegularNameIsStored();
    }

    static void testNullNameThrowsNPE() {
        try {
            new ZipEntry((String) null);
            throw new AssertionError("expected NullPointerException for null name");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testTooLongNameThrowsIAE() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 0x10000; i++) {
            sb.append('a');
        }
        try {
            new ZipEntry(sb.toString());
            throw new AssertionError("expected IllegalArgumentException for name longer than 0xFFFF");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }

    static void testMaxLengthNameAccepted() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 0xFFFF; i++) {
            sb.append('b');
        }
        String name = sb.toString();
        ZipEntry e = new ZipEntry(name);
        if (!name.equals(e.getName())) {
            throw new AssertionError("name of length 0xFFFF not preserved");
        }
    }

    static void testRegularNameIsStored() {
        String name = "dir/file.txt";
        ZipEntry e = new ZipEntry(name);
        if (!name.equals(e.getName())) {
            throw new AssertionError("expected name " + name + " but was " + e.getName());
        }
    }
}
