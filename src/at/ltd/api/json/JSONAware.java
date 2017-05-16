package at.ltd.api.json;

/**
 * Beans that support customized output of JSON text shall implement this interface.  
 */
public interface JSONAware {
	String toJSONString();
}
