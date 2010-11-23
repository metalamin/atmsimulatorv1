package tools.stateeditor.graphics;

import org.jgraph.graph.AttributeMap;
import org.jgraph.graph.DefaultGraphCell;
import presentation.screen.Screen;

/**
 *
 * @author jeronimo
 */
public class MyGraphCell extends DefaultGraphCell {
    
    private String name;
    /**
     * Creates a new instance of MyGraphCell 
     */
    public MyGraphCell() {
    super();
    setName("");
    }
    
    public MyGraphCell(String n) {
        super(n);
        setName(n);
    }
    public MyGraphCell(Object o, AttributeMap am) {
        super(o,am);
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
           
}
