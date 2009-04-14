package portal.core.tools;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;

public class IOTools {

	private IOTools() {

	}

	public static void closeOrThrowRuntimeException(Reader reader) {
		try {
			reader.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static InputStreamReader getInputReaderOrThrowRuntimeException(URL someUrl) {
		InputStreamReader isr;

		try {
			isr = new InputStreamReader(someUrl.openStream());
		} catch (IOException ioe) {
			throw new RuntimeException(ioe);
		}

		return isr;
	}

}
