package portal.core.tools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.MessageFormat;

public class TemplateTools {

	/**
	 * ST is StringTemplate, template engine.
	 * 
	 * @param theClass
	 * @param templateName
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static final String getSTTemplatePath(Class theClass,
			String templateName) {
		StringBuilder buffer = new StringBuilder(StringTools
				.convertPackageToSlashString(theClass));
		return buffer.append("/").append(templateName).toString();
	}

	/**
	 * This Utility exists to get-at any message format for a given resource.
	 * 
	 * @param containerClass
	 * @param templateResource
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static final MessageFormat getTemplateMessageFormat(
			Class containerClass, String templateResource) {
		URL someUrl = containerClass.getResource(templateResource);
		InputStreamReader isr = IOTools
				.getInputReaderOrThrowRuntimeException(someUrl);

		BufferedReader bufferedReader = new BufferedReader(isr);
		StringBuilder buffer;
		String inputLine;
		try {
			buffer = new StringBuilder();
			while ((inputLine = bufferedReader.readLine()) != null) {
				buffer.append(inputLine);
			}

		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			IOTools.closeOrThrowRuntimeException(bufferedReader);
		}

		return new MessageFormat(buffer.toString());
	}
}
