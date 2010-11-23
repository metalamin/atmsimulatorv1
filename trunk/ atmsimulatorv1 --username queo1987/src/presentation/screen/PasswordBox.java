package presentation.screen;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.lang.*;
import java.lang.reflect.*;


public class PasswordBox extends JPasswordField implements ITextComponent{

  public PasswordBox() {
  super();
  }
 
   public Vector getGetMethods()
  {

    Vector result = new Vector();
    Method[] m = ITextComponent.class.getMethods();
    int i =0;
    String s = new String("get");
    while(i< m.length)
    {
      Method met = m[i];
      if(met.getName().startsWith(s))
      result.add(met);

      i++;
    }
  return result;
  }

  public Vector getSetMethods()
  {

    Vector result = new Vector();
    Method[] m = ITextComponent.class.getMethods();
    int i =0;
    String s = new String("set");
    while(i< m.length)
    {
      Method met = m[i];
      if(met.getName().startsWith(s))
      result.add(met);

      i++;
    }
  return result;
  }

}