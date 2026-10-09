/* @test    @summary Test for BufferedReader.skip(long) with negative argument
 * @run main BufferedReaderWitupTest_P818db4e25355
 */

import java.io.BufferedReader;
import java.io.StringReader;

public class BufferedReaderWitupTest_P818db4e25355 {

    public static void main(String[] args) {
        testSkipNegative();
    }

    public static void testSkipNegative() {
        StringReader sr = new StringReader("line1\nline2\n");
        BufferedReader br = new BufferedReader(sr);
        try {
            long result = br.skip(-1);
            throw new AssertionError("Expected IllegalArgumentException for negative skip, but got result: " + result);
        } catch (IllegalArgumentException e) {
            if (!e.getMessage().contains("skip value is negative")) {
                throw new AssertionError("Unexpected exception message: " + e.getMessage(), e);
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type: " + e, e);
        }
    }
}
