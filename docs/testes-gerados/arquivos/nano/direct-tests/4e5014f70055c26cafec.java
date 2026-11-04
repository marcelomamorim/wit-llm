/* @test    @summary Test setExtra(byte[] extra) method of ZipEntry for basic functionality and edge cases    @run main ZipEntryWitupTest */

import java.util.zip.ZipEntry;
import static java.util.zip.ZipConstants64.*;

public class ZipEntryWitupTest {

    public static void main(String[] args) {
        testSetExtraWithValidData();
        testSetExtraWithNull();
        testSetExtraWithEmptyArray();
        testSetExtraWithLargeArray();
    }

    static void testSetExtraWithValidData() {
        ZipEntry entry = new ZipEntry("test.txt");
        byte[] extraData = {1, 2, 3, 4, 5};
        entry.setExtra(extraData);
        assert entry.extra != null : "Extra data should not be null after setExtra";
        assert entry.extra.length == extraData.length : "Extra data length mismatch";
        for (int i = 0; i < extraData.length; i++) {
            assert entry.extra[i] == extraData[i] : "Mismatch at index " + i;
        }
    }

    static void testSetExtraWithNull() {
        ZipEntry entry = new ZipEntry("nullTest.txt");
        try {
            entry.setExtra(null);
            assert true : "setExtra(null) should not throw";
        } catch (NullPointerException e) {
            assert false : "setExtra(null) threw NullPointerException";
        }
        assert entry.extra == null : "Extra data should be null after setting null";
    }

    static void testSetExtraWithEmptyArray() {
        ZipEntry entry = new ZipEntry("emptyTest.txt");
        byte[] empty = {};
        entry.setExtra(empty);
        assert entry.extra != null : "Extra data should not be null after setting empty array";
        assert entry.extra.length == 0 : "Extra data length should be zero";
    }

    static void testSetExtraWithLargeArray() {
        ZipEntry entry = new ZipEntry("largeTest.txt");
        byte[] largeArray = new byte[0xFFFF];
        for (int i = 0; i < largeArray.length; i++) {
            largeArray[i] = (byte) (i % 256);
        }
        entry.setExtra(largeArray);
        assert entry.extra != null : "Extra data should not be null for large array";
        assert entry.extra.length == largeArray.length : "Large array length mismatch";
        for (int i = 0; i < 10; i++) { // check first 10 bytes for correctness
            assert entry.extra[i] == largeArray[i] : "Mismatch at index " + i;
        }
    }
}
