package presentation.display;

import config.GlobalConfig;
import domain.state.Action;
import domain.state.Event;

import presentation.screen.ITextComponent;

/**
 * Action de setText
 */
public class SetTextAction implements Action
{
    private String comp;
    private String text;
    private String key;
    
    public SetTextAction()
    {
    }
    
    public SetTextAction(String comp, String text) 
    {
        this.setComp(comp);
        this.setText(text);
    }
    
    public void update(Event ev)
    {
        //Si no se seteo el texto en el contexto...
        if (ev.getContext().get(getKey()) != null)
        {
            setText((String)ev.getContext().get(getKey()));
        }
        if (getText() == null)
        {
            try{
                setText((String)GlobalConfig.getInstance().getProperty(getKey()));
            }catch(Exception e){
                e.printStackTrace();
            }
        }

        ((ITextComponent)Display.getInstance().getPantalla().getComponent(getComp())).setText(getText());

        /**
         * @TODO Sincronizar con Jero
         */
        //setText(null);
            
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
        return "Set text (" + getComp() + ")";
    }
}

