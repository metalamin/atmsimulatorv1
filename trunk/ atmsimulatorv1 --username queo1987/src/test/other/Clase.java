package test.other;

import domain.implementation.EventImpl;
import domain.state.Event;

public class Clase {

    private Event event;
    
    /** Creates a new instance of Clase */
    public Clase() 
    {
        event = new EventImpl("1", "2");
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }
    
}
