package pkg;

import java.lang.reflect.Field;
import java.util.List;

public class Main {
	
	public static void main(String[] args) {
		ExampleObject obj = new ExampleObject();
		ObjectToJsonConverter converter = new ObjectToJsonConverter();
		String json = converter.startConvert(obj);
		System.out.println(json.toString());
	}
	
}
