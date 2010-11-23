package test.other;

import infrastructure.dataaccess.sequential.SequentialFactory;
import infrastructure.dataaccess.sequential.SequentialReader;
import infrastructure.dataaccess.sequential.SequentialWriter;
import domain.implementation.EventImpl;

public class TestXMLEncoder 
{
    public static void main(String[] args) 
    {
        Clase d = new Clase();
        EventImpl ei = new EventImpl("2", "3");
        d.setEvent(ei);
        
        try
        {
            SequentialWriter sw = SequentialFactory.getSequentialWriter();
            sw.connect("Test.xml");
            sw.write(d);
            sw.close();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        
        try
        {
            SequentialReader sr = SequentialFactory.getSequentialReader();
            sr.connect("Test.xml");
            d = (Clase)sr.read();
            sr.close();
            System.out.println(d.getEvent().getThrower());
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
    
}
