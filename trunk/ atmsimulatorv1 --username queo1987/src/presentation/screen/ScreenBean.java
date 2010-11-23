package presentation.screen;
import java.lang.*;
import java.util.*;
import java.net.*;
import javax.swing.*;
import java.awt.*;

public class ScreenBean {
  private Collection components;
  private String url;
  private Rectangle bounds;


  public ScreenBean() {
  }

    public Collection getComponents() {
        return components;
    }

    public void setComponents(Collection components) {
        this.components = components;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public void setBounds(Rectangle bounds) {
        this.bounds = bounds;
    }

}