package config;

/**
 * Main que setea las propiedades conocidas...
 * Igual que los main de esta pckg., es de un solo uso.
 */
public class MainSetProperties
{
    public static void main(String[] args)
    {
        try
        {
            Config.getInstance().setConnectionName(SystemConfig.getInstance().getConnection());
            
            Config.getInstance().addProperty("TYPES", "javax.swing.plaf.ColorUIResource,javax.swing.plaf.FontUIResource,java.lang.String");
            Config.getInstance().addProperty("VALUES", "javax.swing.plaf.ColorUIResource,javax.swing.plaf.FontUIResource,java.awt.Rectangle,java.lang.String");
            Config.getInstance().addProperty("javax.swing.plaf.ColorUIResource", 
                    "editor.ColorMenuType");
            Config.getInstance().addProperty("javax.swing.plaf.FontUIResource", 
                    "editor.FontMenuType");
            Config.getInstance().addProperty("java.lang.String", 
                    "editor.StringMenuType");
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        
    }
    
}
