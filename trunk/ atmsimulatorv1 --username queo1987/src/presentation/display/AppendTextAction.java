package presentation.display;

import domain.state.Action;
import domain.state.Event;

import presentation.screen.ITextComponent;

/**
 * Action de appendText
 */
public class AppendTextAction implements Action
{
    private String comp;
    private String text;
    
    public AppendTextAction()
    {
    }
    
    public AppendTextAction(String comp, String text) 
    {
        this.setComp(comp);
        this.setText(text);
    }
    
    public void update(Event ev)
    {
        String componentValue = ((ITextComponent)Display.getInstance().getPantalla().getComponent(getComp())).getText();
        ((ITextComponent)Display.getInstance().getPantalla().getComponent(getComp())).setText(componentValue.concat(getText()));
    }

    public String getComp()
    {
        return comp;
    }

    public void setComp(String comp)
    {
        this.comp = comp;
    }

    public String getText()
    {
        return text;
    }

    public void setText(String text)
    {
        this.text = text;
    }
    
    public String toString()
    {
        return "Append text (" + getComp() + ")";
    }    
}

