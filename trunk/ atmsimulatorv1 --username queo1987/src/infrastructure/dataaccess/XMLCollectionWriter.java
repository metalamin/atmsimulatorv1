package infrastructure.dataaccess;

import java.beans.XMLEncoder;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.util.Iterator;
import java.util.Vector;

/**
 * Esta clase toma una serie de objetos y los pasa
 * a formato XML, devolviendo el string representativo.
 */
public class XMLCollectionWriter
{
    //Los objetos a parsear
    private Vector objects;
    
    public XMLCollectionWriter()
    {
        objects = new Vector();
    }
    
    /**
     * Agrega un objeto a parsear.
     * PRE: Debe ser parseable.
     */
    public void addObject(Object obj)
    {
        objects.add(obj);
    }
    
    /**
     * Devuelve la representacion string XML
     * de los objetos.
     */
    public String toString()
    {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        XMLEncoder xmlenc = new XMLEncoder(new BufferedOutputStream(bos));
        Iterator itr = objects.iterator();
        while (itr.hasNext())
        {
            xmlenc.writeObject(itr.next());
        }
        xmlenc.close();
        return bos.toString();
    }
}
