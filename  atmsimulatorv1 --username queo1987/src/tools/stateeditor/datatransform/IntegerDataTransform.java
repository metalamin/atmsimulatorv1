/*
 * IntegerDataTransform.java
 *
 * Created on 4 de febrero de 2006, 19:56
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package tools.stateeditor.datatransform;

/**
 *
 * @author jeronimo
 */
public class IntegerDataTransform implements TPropertiesDataTransform{
    
    /** Creates a new instance of IntegerDataTransform */
    public IntegerDataTransform() {
    }
    
    public Object getValidObject(Object data)
    {
        data = new Integer((String)data);
        return data;
    }
    
}
