package test;

import config.SystemConfig;
import infrastructure.devices.general.IDeviceManager;
import javax.swing.*;
import domain.state.StateConstants;
import domain.state.StateHandlerFactory;
import domain.statemachine.StateMachineFactory;
import presentation.splash.SplashScreen;

public class TestAtmSimulator
{
    
    public TestAtmSimulator()
    {
        atmGui = this;
    }
    
    public static TestAtmSimulator getInstance()
    {
        return atmGui;
    }
    
    public static void main(String args[])
    {
        SplashScreen splash = new SplashScreen(10000);
        splash.showSplash();
       
        try
        {
            atmGui = new TestAtmSimulator();
            atmGui.initialization();

            SystemConfig.getInstance().configure();

            StateHandlerFactory.getStateHandler().load("cfg/demo/client/DemoClientStateMachine.xml");
            StateMachineFactory.getStateMachine().startup(StateConstants.INITIAL_STATE);
            
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        };
        
    }
    
    public void initialization()
    {
        try
        {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch(Exception exception)
        {
            System.err.println("Error loading L&F: " + exception);
        }
    }
    
    private static TestAtmSimulator atmGui;
    private static IDeviceManager myManager;
}