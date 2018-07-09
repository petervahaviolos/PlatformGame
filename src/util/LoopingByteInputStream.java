package util;

import java.io.ByteArrayInputStream;
import java.io.IOException;

/**
    The LoopingByteInputStream is a ByteArrayInputStream that
    loops indefinitly. The looping stops when the close() method
    is called.
*/
public class LoopingByteInputStream extends ByteArrayInputStream {

    private boolean closed;

    /**
        Creates a new LoopingByteInputStream with the specified
        byte array. The array is not copied.
    */
    public LoopingByteInputStream(byte[] buffer) {
        super(buffer);
        closed = false;
    }


    /**
        Reads length bytes from the array. If the
        end of the array is reached, the reading starts over from
        the beginning of the array. Returns -1 if the array has
        been closed.
    */
    public int read(byte[] buffer, int offset, int length) {
        if (closed) {
            return -1;
        }
        int totalBytesRead = 0;

        while (totalBytesRead < length) {
            int numBytesRead = super.read(buffer,
                offset + totalBytesRead,
                length - totalBytesRead);

            if (numBytesRead > 0) {
                totalBytesRead += numBytesRead;
            }
            else {
                reset();
            }
        }
        return totalBytesRead;
    }


    /**
        Closes the stream. Future calls to the read() methods
        will return 1.
    */
    public void close() throws IOException {
        super.close();
        closed = true;
    }

}
