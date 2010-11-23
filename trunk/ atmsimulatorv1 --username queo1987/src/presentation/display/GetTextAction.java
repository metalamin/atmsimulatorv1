package presentation.display;

import domain.state.Action;
import domain.state.Event;

import presentation.screen.ITextComponent;

/**
 * Action de getText
 */
public class GetTextAction implements Action
{
    private String text;
    private String nombre;
    private String key;    
    
    public GetTextAction()
    {
        
    }
    
    public GetTextAction(String comp)
    {
        setName(comp);
    }
    
    public void update(Event ev)
    {
        //Se obtiene el texto...
        text = ((ITextComponent)Display.getInstance().getPantalla().getComponent(getName())).getText();
        //y se setea en el contexto.
        ev.getContext().put(getKey(), text);
    }
    
    public String getText()
    {
        return text;
    }
    
    public String getName()
    {
        return nombre;
    }
    
    public void setName(String nombre)
    {
        this.nombre = nombre;
    }

    public String getKey()
    {
        return key;
    }

    public void setKey(String key)
    {
        this.key = key;
    }
    
    public String toString()
    {
        return "Get text (" + getName() + ")";
    }
}
