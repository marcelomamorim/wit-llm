/*
 * @test
 * @summary Verify DuplicateFormatFlagsException constructor stores flags and rejects null
 * @run main DuplicateFormatFlagsExceptionWitupTest
 */

import java.util.DuplicateFormatFlagsException;
import java.util.IllegalFormatException;

public class DuplicateFormatFlagsExceptionWitupTest {

    public static void main(String[] args) {
        DuplicateFormatFlagsException e = new DuplicateFormatFlagsException("-");
        if (!"-".equals(e.getFlags())) {
            throw new RuntimeException("getFlags mismatch: " + e.getFlags());
        }
        if (!"Flags = '-'".equals(e.getMessage())) {
            throw new RuntimeException("getMessage mismatch: " + e.getMessage());
        }
        if (!(e instanceof IllegalFormatException)) {
            throw new RuntimeException("Not an IllegalFormatException");
        }

        DuplicateFormatFlagsException empty = new DuplicateFormatFlagsException("");
        if (!"".equals(empty.getFlags())) {
            throw new RuntimeException("empty getFlags mismatch: " + empty.getFlags());
        }
        if (!"Flags = ''".equals(empty.getMessage())) {
            throw new RuntimeException("empty getMessage mismatch: " + empty.getMessage());
        }

        DuplicateFormatFlagsException multi = new DuplicateFormatFlagsException("-+-");
        if (!"-+-".equals(multi.getFlags())) {
            throw new RuntimeException("multi getFlags mismatch: " + multi.getFlags());
        }

        boolean npeThrown = false;
        try {
            new DuplicateFormatFlagsException(null);
        } catch (NullPointerException npe) {
            npeThrown = true;
        }
        if (!npeThrown) {
            throw new RuntimeException("NullPointerException not thrown for null flags");
        }
    }
}
