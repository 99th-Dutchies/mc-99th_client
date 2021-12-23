package nl._99th_client.installer.xdelta;

import java.io.IOException;

public interface DiffWriter {
    void addCopy(int paramInt1, int paramInt2) throws IOException;

    void addData(byte paramByte) throws IOException;

    void flush() throws IOException;

    void close() throws IOException;
}

