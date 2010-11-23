/*
 * TestSimulador.java
 *
 * Created on 15 de septiembre de 2005, 16:29
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package test.other;

import javax.swing.*;
import presentation.display.Simulator;
import tools.stateeditor.StateMachinePanel;
//import javax.swing.JFrame;
//import javax.swing.JLayeredPane;

/**
 *
 * @author jeronimo
 */
public class TestSimulador {
    
    public static void main(String[] args)
    {
        try{
           
        Simulator sim = Simulator.getInstance();
        /*System.out.println("*******");
        JButton b = new JButton("HOLA!");
        System.out.println("*******");
        sim.setComponentOnPanel(2, b);
        JButton b2 = new JButton("ACA!");
        System.out.println("*******");
        sim.setComponentOnPanel(3, b2);
        System.out.println("*******");
        System.out.println("*******");*/
        JPanel p = sim.getPanel(1);
        p.setBounds(0,0, 640,480);
        JLayeredPane jl = (JLayeredPane)p.getComponent(0);
        
        StateMachinePanel smp = new StateMachinePanel();
        sim.setComponentOnPanel(2, smp);
        
        sim.setBounds(0,0, 800,600);
        sim.setVisible(true);
        /*JFrame f = new JFrame("HOLAL!");
        f.setBounds(0,0, 640, 480);
        JLayeredPane jl = new JLayeredPane();
        */
        /*JButton but2 = new JButton("HOLA22");
        
        but2.setBounds(10, 10, 100,30);*/
        JButton but1 = new JButton("HOLA1");
        but1.setBounds(10, 10, 200,50);
        jl.add(but1, JLayeredPane.PALETTE_LAYER);
        
        /*jl.add(but2, JLayeredPane.PALETTE_LAYER);
        
        f.getContentPane().add(jl);
        f.setVisible(true);*/
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    
}
