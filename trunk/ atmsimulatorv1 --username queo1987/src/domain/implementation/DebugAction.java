package domain.implementation;

import java.io.PrintStream;
import domain.state.Action;
import domain.state.Event;

/**
 * Accion de DEBUG. Basicamente
 * esta para eso.
 * Lo que hace es imprimir un
 * String en una salida...
 */
public class DebugAction implements Action 
{
    //La salida.
    private String output;

    public DebugAction()
    {
    }
    
    public DebugAction(String debugString)
    {
        setOutputText(debugString);
    }

    public void update(Event event)
    {
        if (event != null)
        {
            getOutputStream().println("	Type   : " + event.getType());
            getOutputStream().println("	Thrower: " + event.getThrower());
        }
        else
        {
            getOutputStream().println("	Event es null.");
        }
        getOutputStream().println("		 " + getOutputText());
        getOutputStream().println("---");
    }

    public PrintStream getOutputStream()
    {
        return System.out;
    }

    public String getOutputText()
    {
        return output;
    }

    public void setOutputText(String output)
    {
        this.output = output;
    }
    
    public String toString()
    {
        return "Debug";
    }
}
