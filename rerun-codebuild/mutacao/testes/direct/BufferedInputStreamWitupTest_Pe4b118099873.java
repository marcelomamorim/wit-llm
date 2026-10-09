/* @test    @summary Test reset() method of BufferedInputStream for valid and invalid mark positions    @run main BufferedInputStreamWitupTest_Pe4b118099873 */

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;

public class BufferedInputStreamWitupTest_Pe4b118099873 {

    public static void main(String[] args) throws IOException {
        testResetWithValidMark();
        testResetWithInvalidMark();
    }

    private static void testResetWithValidMark() throws IOException {
        byte[] data = {1, 2, 3, 4, 5};
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        BufferedInputStream bis = new BufferedInputStream(bais);
        bis.mark(10);
        bis.read();
        bis.read();
        bis.reset();
        int firstByte = bis.read();
        assert firstByte == data[0] : "Expected first byte after reset to be " + data[0] + " but got " + firstByte;
    }

    private static void testResetWithInvalidMark() throws IOException {
        byte[] data = {10, 20, 30};
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        BufferedInputStream bis = new BufferedInputStream(bais);
        // Do not mark, so markpos remains -1
        try {
            bis.reset();
            assert false : "Expected IOException when resetting without mark";
        } catch (IOException e) {
            assert e.getMessage().equals("Resetting to invalid mark") : "Unexpected exception message: " + e.getMessage();
        }
    }
}
