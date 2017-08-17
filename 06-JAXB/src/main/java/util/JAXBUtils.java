package util;

import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class JAXBUtils {
	public final static String CHARSET_NAME = "UTF-8";

	public static String marshal(Object obj) throws JAXBException {
		JAXBContext jaxbContext = JAXBContext.newInstance(obj.getClass());
		Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
		// output pretty printed
		jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		jaxbMarshaller.setProperty(Marshaller.JAXB_ENCODING, CHARSET_NAME);

		StringWriter writer = new StringWriter();
		jaxbMarshaller.marshal(obj, writer);
		return writer.toString();
	}

	public static <T> T unmarshal(String xml, Class<T> cls)
			throws JAXBException {
		JAXBContext jaxbContext = JAXBContext.newInstance(cls);
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

		StringReader reader = null;

		reader = new StringReader(xml);
		try {
			return (T) jaxbUnmarshaller.unmarshal(reader);
			
		} finally {
			if(reader != null) {
				reader.close();
			}
        }

	}
	
	public static <T> T unmarshal(InputStream is, Class<T> cls)
			throws JAXBException {
		JAXBContext jaxbContext = JAXBContext.newInstance(cls);
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		return (T) jaxbUnmarshaller.unmarshal(is);

	}
}