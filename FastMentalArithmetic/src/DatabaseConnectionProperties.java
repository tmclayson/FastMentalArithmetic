import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 
 * A monostate is a 'conceptual singleton' — all data members of a monostate are
 * static, so all instances of the monostate use the same (static) data.
 * Applications using a monostate can create any number of instances that they
 * desire, as each instance uses the same data.
 */
public class DatabaseConnectionProperties {

	private static final String CONNECTION_FILE_WITH_PATH = "dbconnection.properties";
	private static Properties properties = new Properties();
	static {
		InputStream file;
		try {
			file = new FileInputStream(new File(CONNECTION_FILE_WITH_PATH));
			properties.load(file);
		} catch (FileNotFoundException exp) {
			exp.printStackTrace();
		} catch (IOException exp) {
			exp.printStackTrace();
		}
	}

	public static String getPropertyValue(String propertyKey) {
		if (propertyKey != null && !propertyKey.isEmpty()) {
			return properties.getProperty(propertyKey);
		}
		return null;
	}

	public static Map<String, String> getProperties() {
		Map<String, String> propertyMap = new HashMap<String, String>();
		for (String propertyKey : properties.stringPropertyNames()) {
			propertyMap.put(propertyKey, properties.getProperty(propertyKey));
		}
		return propertyMap;
	}

}
