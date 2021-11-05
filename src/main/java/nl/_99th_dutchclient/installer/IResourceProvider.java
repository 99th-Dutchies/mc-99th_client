package nl._99th_dutchclient.installer;

import java.io.IOException;
import java.io.InputStream;

public interface IResourceProvider {
    InputStream getResourceStream(String paramString) throws IOException;
}
