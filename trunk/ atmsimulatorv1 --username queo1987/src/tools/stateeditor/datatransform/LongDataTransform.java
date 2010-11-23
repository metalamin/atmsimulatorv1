/*
 * LongDataTransform.java
 *
 * Created on 4 de febrero de 2006, 19:44
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package tools.stateeditor.datatransform;

/**
 *
 * @author jeronimo
 */
public class LongDataTransform implements TPropertiesDataTransform {
    
    /** Creates a new instance of LongDataTransform */
    public LongDataTransform() {
    }
    
    public Object getValidObject(Object data)
    {
        data = new Long((String)data);
        return data;
    }
    
}
