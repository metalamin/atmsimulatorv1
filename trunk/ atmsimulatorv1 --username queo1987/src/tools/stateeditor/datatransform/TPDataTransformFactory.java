/*
 * TPDataTransformHandler.java
 *
 * Created on 5 de febrero de 2006, 16:09
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package tools.stateeditor.datatransform;

import config.Config;
import infrastructure.dataaccess.sequential.SequentialFactory;
import infrastructure.dataaccess.sequential.SequentialReader;
import java.util.HashMap;

/**
 *
 * @author jeronimo
 * Esta clase se encarga de transformar los strings de la tabla en a sus tipos
 * de datos correspondientes. De esta manera el objecto transformado puede setearse
 * al objeto de la logica. Por ejemplo: Las propiedades que son Long o Integer en las acciones o 
 * triggers se desplegan como strings en la tabla, pero cuando queremos setearle el nuevo valor 
 * que el usuario ingresó debemos transformar este valor al tipo original( en este caso Long o Integer)
 * antes de setearselo al action o trigger. Si no hicieramos este nos daria una exception al querer invocar
 * el método del objeto para querer setearle la propiedad.
 */
public class TPDataTransformFactory {
    
    private HashMap dataMap;
    private static TPDataTransformFactory inst = null;
    /** Creates a new instance of TPDataTransformHandler */
    private TPDataTransformFactory() {
        //Config.getInstance().setConnectionName("cfg/tableDataTransform.xml");
        try{
        SequentialReader sw = SequentialFactory.getSequentialReader();
        sw.connect("cfg/tableDataTransform.xml");
        dataMap = (HashMap)sw.read();
        sw.close();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    
    public static TPDataTransformFactory getInstance()
    {
        if(inst == null)
            inst = new TPDataTransformFactory();
        return inst;
    }
    
    public Object getValidDataObject(Object type, Object data)
    {
        try{
        if(dataMap.containsKey(type.getClass()))
        {
            Class clase = (Class)dataMap.get(type.getClass());
            TPropertiesDataTransform tpdt = (TPropertiesDataTransform)Class.forName(clase.getName()).newInstance();
            data = tpdt.getValidObject(data);
        }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return data;
    }
    
}
