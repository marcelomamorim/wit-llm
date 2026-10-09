/*
 * @test
 * @summary Test java.io.File.renameTo(File) for null dest and invalid files
 * @run main FileWitupTest_Paaf7659738fd
 */

import java.io.File;

public class FileWitupTest_Paaf7659738fd {
    public static void main(String[] args) {
        testRenameToNullDestThrowsNPE();
        testRenameToInvalidFilesReturnsFalse();
    }

    private static void testRenameToNullDestThrowsNPE() {
        File f = new File("testfile");
        try {
            f.renameTo(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException npe) {
            // expected
        }
    }

    private static void testRenameToInvalidFilesReturnsFalse() {
        // Create anonymous subclass to override isInvalid() to return true
        File invalidSrc = new File("invalidSrc") {
            @Override
            boolean isInvalid() {
                return true;
            }
        };
        File validDest = new File("validDest") {
            @Override
            boolean isInvalid() {
                return false;
            }
        };
        if (invalidSrc.renameTo(validDest)) {
            throw new RuntimeException("renameTo should return false when source is invalid");
        }

        File validSrc = new File("validSrc") {
            @Override
            boolean isInvalid() {
                return false;
            }
        };
        File invalidDest = new File("invalidDest") {
            @Override
            boolean isInvalid() {
                return true;
            }
        };
        if (validSrc.renameTo(invalidDest)) {
            throw new RuntimeException("renameTo should return false when destination is invalid");
        }
    }
}
