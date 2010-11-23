package config;

import infrastructure.communication.action.ClearMessageAction;
import infrastructure.communication.action.ConnectAction;
import infrastructure.communication.action.CreateMessageAction;
import infrastructure.communication.action.DisconnectAction;
import infrastructure.communication.action.ExternalCommandAction;
import infrastructure.communication.action.GetElementAction;
import infrastructure.communication.action.SendMessageAction;
import infrastructure.communication.action.SetElementAction;
import infrastructure.communication.action.SetElementsAction;
import infrastructure.devices.OpenDeviceManagerAction;
import infrastructure.devices.controllers.DisplayDeviceAction;
import infrastructure.devices.controllers.OpenDeviceAction;
import infrastructure.devices.controllers.msd.MsdReadAction;
import infrastructure.devices.controllers.ptr.PtrPrintDocAction;
import infrastructure.devices.controllers.tio.TioReadAction;
import domain.implementation.ConditionalAction;
import domain.implementation.CountBean;
import domain.implementation.DebugAction;
import domain.implementation.Delay;
import domain.implementation.KeyMapAction;
import domain.implementation.StateChange;
import domain.implementation.SystemExit;
import domain.implementation.TimeOutBean;
import domain.state.TriggerBean;
import infrastructure.communication.action.ConnectChannel;
import infrastructure.communication.action.DisconnectChannel;
import presentation.display.AppendTextAction;
import presentation.display.DisplayAction;
import presentation.display.GetTextAction;
import presentation.display.SetTextAction;
import tools.stateeditor.objects.ObjectCreatorHandler;
import tools.stateeditor.objects.ObjectCreatorHandlerFactory;
import util.DefaultCreator;

/**
 * Carga de los ComponentCreators
 */
public class MainDefaultObjectCreators
{
    public static void main(String[] args)
    {
        try
        {
            SystemConfig.getInstance().configure();
            ObjectCreatorHandler hnd = ObjectCreatorHandlerFactory.getObjectCreatorHandler();
            /**
             * Triggers.
             * Se crean.
             */
            TriggerBean tb = new TriggerBean();
            tb.setThrower("<Thrower>");
            tb.setType("<Type>");
            TimeOutBean tob = new TimeOutBean();
            tob.setThrower("<Thrower>");
            tob.setType("<Type>");

            CountBean cb = new CountBean();
            cb.setThrower("<Thrower>");
            cb.setType("<Type>");
            
            
            /**
             * y se agregan
             */
            hnd.addCreator("Trigger", "Count", new DefaultCreator(CountBean.class, cb));
            hnd.addCreator("Trigger", "Time Out", new DefaultCreator(TimeOutBean.class, tob));
            hnd.addCreator("Trigger", "Trigger", new DefaultCreator(TriggerBean.class, tb));
            
            /**
             * Actions.
             * Se crean.
             */
            Delay delay = new Delay(1, 1000);
            DebugAction debug = new DebugAction("<Debug>");
            StateChange stch = new StateChange("<State>");
            DisplayAction display = new DisplayAction("<Screen>");
            GetTextAction gettxt = new GetTextAction("<Component>");
            SetTextAction settxt = new SetTextAction("<Component>", "<Text>");
            ClearMessageAction cmssg = new ClearMessageAction();
            cmssg.setMsgId("<Message ID>");
            ConnectAction cnac = new ConnectAction();
            CreateMessageAction cmac = new CreateMessageAction();
            cmac.setMsgId("<Message ID>");
            DisconnectAction dact = new DisconnectAction();
            ExternalCommandAction eca = new ExternalCommandAction();
            eca.setKey("<Key>");
            GetElementAction gea = new GetElementAction();
            gea.setElement("<Element>");
            gea.setKey("<Key>");
            gea.setMsgId("<Message ID>");
            SendMessageAction sma = new SendMessageAction();
            sma.setChannelName("<Channel Name>");
            sma.setMsgId("<Message ID>");
            SetElementAction sea = new SetElementAction();
            sea.setDefaultValue("<Default>");
            sea.setElement("<Element>");
            sea.setKey("<Key>");
            sea.setMsgId("<Message ID>");
            SetElementsAction sesa = new SetElementsAction();
            sesa.setMsgId("<Message ID>");
            MsdReadAction mra = new MsdReadAction();
            PtrPrintDocAction ppda = new PtrPrintDocAction();
            ppda.setData("<Data>");
            ppda.setDeviceName("<Device name>");
            TioReadAction tra = new TioReadAction();
            tra.setPin("<Pin>");
            DisplayDeviceAction dda = new DisplayDeviceAction();
            dda.setGuiPosition(new Integer(0));
            OpenDeviceAction oda = new OpenDeviceAction();
            oda.setDeviceName("<Device name>");
            KeyMapAction kma = new KeyMapAction();
            kma.setSourceKey("<Source Key>");
            kma.setTargetKey("<Target key>");
            SystemExit sex = new SystemExit();
            AppendTextAction ata = new AppendTextAction();
            ata.setComp("<Component>");
            ata.setText("<Text>");
            OpenDeviceManagerAction odma = new OpenDeviceManagerAction();
            
            ConditionalAction coa = new ConditionalAction();
            coa.setKey("<Key>");
            coa.setValue("<Value>");
            coa.setType("<Type>");
            coa.setThrower("<Thrower>");
            
            ConnectChannel cch = new ConnectChannel();
            cch.setChannelName("<Channel>");
            
            DisconnectChannel dch = new DisconnectChannel();
            dch.setChannelName("<Channel>");            
            /**
             * Y se agregan
             */
            hnd.addCreator("Action", "Append Text", new DefaultCreator(AppendTextAction.class, ata));
            hnd.addCreator("Action", "Clear message", new DefaultCreator(ClearMessageAction.class, cmssg));
            hnd.addCreator("Action", "Connect", new DefaultCreator(ConnectAction.class, cnac));
            hnd.addCreator("Action", "Create message", new DefaultCreator(CreateMessageAction.class, cmac));
            hnd.addCreator("Action", "Debug", new DefaultCreator(DebugAction.class, debug));
            hnd.addCreator("Action", "Delay", new DefaultCreator(Delay.class, delay));
            hnd.addCreator("Action", "Disconnect", new DefaultCreator(DisconnectAction.class, dact));
            hnd.addCreator("Action", "Display screen", new DefaultCreator(DisplayAction.class, display));
            hnd.addCreator("Action", "Display Device", new DefaultCreator(DisplayDeviceAction.class, dda));
            hnd.addCreator("Action", "Exit", new DefaultCreator(SystemExit.class, sex));
            hnd.addCreator("Action", "External command", new DefaultCreator(ExternalCommandAction.class, eca));
            hnd.addCreator("Action", "Get element", new DefaultCreator(GetElementAction.class, gea));
            hnd.addCreator("Action", "Get Text", new DefaultCreator(GetTextAction.class, gettxt));
            hnd.addCreator("Action", "Key Map", new DefaultCreator(KeyMapAction.class, kma));
            hnd.addCreator("Action", "MSD Read", new DefaultCreator(MsdReadAction.class, mra));
            hnd.addCreator("Action", "Ptr. Print Doc", new DefaultCreator(PtrPrintDocAction.class, ppda));
            hnd.addCreator("Action", "Send message", new DefaultCreator(SendMessageAction.class, sma));
            hnd.addCreator("Action", "Set Element", new DefaultCreator(SetElementAction.class, sea));
            hnd.addCreator("Action", "Set Elements", new DefaultCreator(SetElementsAction.class, sesa));
            hnd.addCreator("Action", "Set Text", new DefaultCreator(SetTextAction.class, settxt));
            hnd.addCreator("Action", "State Change", new DefaultCreator(StateChange.class, stch));
            hnd.addCreator("Action", "T.I.O. Read", new DefaultCreator(TioReadAction.class, tra));
            hnd.addCreator("Action", "Open Device", new DefaultCreator(OpenDeviceAction.class, oda));
            hnd.addCreator("Action", "Open Device Manager", new DefaultCreator(OpenDeviceManagerAction.class, odma));
            hnd.addCreator("Action", "Condition", new DefaultCreator(ConditionalAction.class, coa));
            hnd.addCreator("Action", "Connect channel", new DefaultCreator(ConnectChannel.class, cch));
            hnd.addCreator("Action", "Disconnect channel", new DefaultCreator(DisconnectChannel.class, dch));
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
}
