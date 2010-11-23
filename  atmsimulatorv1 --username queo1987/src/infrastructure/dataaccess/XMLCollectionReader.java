package infrastructure.dataaccess;

import java.beans.XMLDecoder;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Vector;

/**
 * Esta clase toma un String representando
 * una serie de objetos, y devuelve un iterador
 * sobre los mismos.
 */
public class XMLCollectionReader
{
    //Los objects representados
    private Vector objects;
    
    public XMLCollectionReader(String frmtdObjects)
    {
        objects = new Vector();
        byte[] bArray = frmtdObjects.getBytes();
        ByteArrayInputStream bais = new ByteArrayInputStream(bArray);            
        XMLDecoder decoder = new XMLDecoder(new BufferedInputStream(bais));
        boolean finished = false;
        while (!finished)
        {
            try
            {
                objects.add(decoder.readObject());
            }
            catch (NoSuchElementException nsex)
            {
                decoder.close();
                finished = true;
            }
            catch (ArrayIndexOutOfBoundsException aoex)
            {
                decoder.close();
                finished = true;
            }
        }
    }
    
    public Iterator iterator()
    {
        return objects.iterator();
    }
    
}
