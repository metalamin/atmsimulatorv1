package presentation.screen;

import java.awt.*;
import java.util.*;
import java.lang.*;

public interface IComponent {

  public void setName(String n);
 
  public String getName();

  public void setBounds(int x, int y, int width, int height);

  public void setBounds(Rectangle rec);
  
  public Rectangle getBounds();

  public void setFont(Font f);

  public Font getFont();

  public void setForeground(Color r);

  public Color getForeground();

  public Color getBackground();

  public void setBackground(Color r);

  public Vector getGetMethods();

  public Vector getSetMethods();

}