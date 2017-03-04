package pkg;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.List;

public class ObjectToJsonConverter {

	StringBuffer json = new StringBuffer("");
	static Class[] primitiveAndWrappers = {int.class, long.class, boolean.class, Integer.class, Long.class, Boolean.class};
	
	public String startConvert(Object o) {
		if(isPrimitiveOrWrapper(o)){
			return "Error: starting object cannot be a primitive object.";
		}
		try {
			convert(o);
			return json.toString();
		} catch(Exception e){
			return "Error: caught exception when converting Object to JSON String";
		}
	}
	
	public void convert(Object o) throws IllegalAccessException{
		// TODO: lists, maps etc
		if(isPrimitiveOrWrapper(o)){
			json.append(o.toString());
		} else if(o instanceof String){
			json.append("\"");
			json.append(o);
			json.append("\"");
		} else if(o.getClass().isArray()){
			json.append("[");
			for(int i = 0; i < Array.getLength(o); i++){
				convert(Array.get(o, i));
				json.append(", ");
			}
			json.delete(json.length() - 2, json.length()); // remove trailing ", "
			json.append("]");
		} else if(o instanceof List){
			json.append("[");
			for(Object n : (List) o){
				convert(n);
				json.append(", ");
			}
			json.delete(json.length() - 2, json.length()); // remove trailing ", "
			json.append("]");
		} else { // it's an object with fields
			json.append("{");
			for(Field f : o.getClass().getDeclaredFields()){
				json.append("\"");
				json.append(f.getName());
				json.append("\": ");
				convert(f.get(o)); // recursively call this method
				json.append(", ");
			}
			json.delete(json.length() - 2, json.length()); // remove trailing ", "
			json.append("}");
		}
	}
	
	public static boolean isPrimitiveOrWrapper(Object o){
		for(Class c : primitiveAndWrappers){
			if(o.getClass() == c){
				return true;
			}
		}
		return false;
	}

}
