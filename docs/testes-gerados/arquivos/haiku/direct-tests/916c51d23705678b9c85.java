/*
 * @test
 * @summary Verify ZipEntry.setSize accepts non-negative sizes and rejects negative sizes without changing state
 * @run main ZipEntryWitupTest
 */

public class ZipEntryWitupTest {

    public static void main(String[] args) throws Exception {
        ZipEntry e = new ZipEntry("entry.txt");
        if (e.getSize() != -1L) {
            throw new RuntimeException("Unexpected initial size: " + e.getSize());
        }

        e.setSize(0L);
        if (e.getSize() != 0L) {
            throw new RuntimeException("Expected size 0 but got " + e.getSize());
        }

        e.setSize(12345L);
        if (e.getSize() != 12345L) {
            throw new RuntimeException("Expected size 12345 but got " + e.getSize());
        }

        e.setSize(Long.MAX_VALUE);
        if (e.getSize() != Long.MAX_VALUE) {
            throw new RuntimeException("Expected Long.MAX_VALUE but got " + e.getSize());
        }

        boolean thrown = false;
        try {
            e.setSize(-1L);
        } catch (IllegalArgumentException ex) {
            thrown = true;
            if (!"invalid entry size".equals(ex.getMessage())) {
                throw new RuntimeException("Unexpected message: " + ex.getMessage());
            }
        }
        if (!thrown) {
            throw new RuntimeException("setSize(-1) did not throw IllegalArgumentException");
        }
        if (e.getSize() != Long.MAX_VALUE) {
            throw new RuntimeException("Size changed after rejected value: " + e.getSize());
        }

        ZipEntry f = new ZipEntry("other.txt");
        thrown = false;
        try {
            f.setSize(Long.MIN_VALUE);
        } catch (IllegalArgumentException ex) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("setSize(Long.MIN_VALUE) did not throw IllegalArgumentException");
        }
        if (f.getSize() != -1L) {
            throw new RuntimeException("Size changed after rejected Long.MIN_VALUE: " + f.getSize());
        }
    }
}
