/* @test    @summary Test for FileOutputStream constructor with String argument.    @run main FileOutputStreamWitupTest */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class FileOutputStreamWitupTest {

    public static void main(String[] args) {
        testConstructorWithNullName();
        testConstructorWithValidName();
    }

    private static void testConstructorWithNullName() {
        try {
            new FileOutputStream((String) null);
            System.out.println("testConstructorWithNullName: FAILED - Expected NullPointerException");
        } catch (NullPointerException e) {
            System.out.println("testConstructorWithNullName: PASSED");
        } catch (Exception e) {
            System.out.println("testConstructorWithNullName: FAILED - Unexpected exception: " + e);
        }
    }

    private static void testConstructorWithValidName() {
        String filename = "testfile.txt";
        File file = new File(filename);
        try (FileOutputStream fos = new FileOutputStream(filename)) {
            if (fos != null) {
                System.out.println("testConstructorWithValidName: PASSED");
            } else {
                System.out.println("testConstructorWithValidName: FAILED - Null FileOutputStream");
            }
        } catch (FileNotFoundException e) {
            System.out.println("testConstructorWithValidName: FAILED - FileNotFoundException");
        } catch (Exception e) {
            System.out.println("testConstructorWithValidName: FAILED - Unexpected exception: " + e);
        } finally {
            if (file.exists()) {
                file.delete();
            }
        }
    }
}
