/*
 * IGUIBuilder.java
 *
 * Created on 14 de noviembre de 2005, 10:30 PM
 *
 */

package presentation;

import java.awt.Component;

/**
 * @author Arya Baher
 */
public interface IGUIBuilder {

    public Component createGUI(int top, int left, int bottom, int right);
    
}
