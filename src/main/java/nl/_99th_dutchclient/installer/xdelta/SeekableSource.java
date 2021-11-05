package nl._99th_dutchclient.installer.xdelta;

import java.io.IOException;

public interface SeekableSource {
    void seek(long paramLong) throws IOException;

    int read(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws IOException;

    void close() throws IOException;

    long length() throws IOException;
}

