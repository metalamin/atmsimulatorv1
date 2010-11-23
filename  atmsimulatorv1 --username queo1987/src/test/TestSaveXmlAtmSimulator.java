package test;

import config.SystemConfig;
import infrastructure.communication.action.ClearMessageAction;
import infrastructure.communication.action.ConnectAction;
import infrastructure.communication.action.CreateMessageAction;
import infrastructure.communication.action.ExternalCommandAction;
import infrastructure.communication.action.SendMessageAction;
import infrastructure.communication.action.SetElementAction;
import infrastructure.devices.OpenDeviceManagerAction;
import infrastructure.devices.controllers.DisplayDeviceAction;
import infrastructure.devices.controllers.OpenDeviceAction;
import infrastructure.devices.controllers.msd.IMsdConst;
import infrastructure.devices.controllers.msd.MsdReadAction;
import infrastructure.devices.controllers.ptr.IPtrConst;
import infrastructure.devices.controllers.tio.ITioConst;
import infrastructure.devices.controllers.tio.TioReadAction;
import infrastructure.devices.general.IDeviceConst;
import domain.implementation.CountTrigger;
import domain.implementation.DebugAction;
import domain.implementation.Delay;
import domain.implementation.KeyMapAction;
import domain.implementation.StateChange;
import domain.implementation.SystemExit;
import domain.state.Action;
import domain.state.StateConstants;
import domain.state.StateHandler;
import domain.state.StateHandlerFactory;
import domain.state.Trigger;
import domain.state.TriggerFactory;
import infrastructure.communication.CommunicationConstants;
import infrastructure.devices.controllers.ptr.PtrPrintDocAction;
import presentation.display.ButtonEvent;
import presentation.display.DisplayAction;
import presentation.display.SetTextAction;

public class TestSaveXmlAtmSimulator
{
    public static void main(String[] args) 
    {
        DisplayAction da;
        Trigger tr;
        MsdReadAction mra;
        TioReadAction tra;
        SetTextAction sta;
        SendMessageAction sma;
        Action act;
        Delay delay;
        
        try
        {
            SystemConfig.getInstance().configure();
            StateHandler st = StateHandlerFactory.getStateHandler();
            
            /**
             * Define all the states.
             */
            st.addState("INITIAL_STATE");
            st.addState("OUT_OF_SERVICE");
            st.addState("INSERT_CARD");
            st.addState("ENTER_PIN");
            st.addState("SELECT_OPERATION");
            st.addState("ENTER_NEW_PIN");
            st.addState("RE_ENTER_NEW_PIN");
            st.addState("SELECT_WITHDRAWAL_ACCOUNT");
            st.addState("SELECT_BALANCE_ACCOUNT");
            st.addState("SELECT_SOURCE_ACCOUNT");
            st.addState("SELECT_DESTINATION_ACCOUNT");
            st.addState("SELECT_CURRENCY");
            st.addState("ENTER_AMMOUNT");
            st.addState("PROCESS_REQUEST");
            st.addState("ANOTHER_OPERATION");

            //---------------------
            // INITIALIZATION STATE
            //---------------------
            
            // Display a screen
            da = new DisplayAction();
            da.setScreen("cfg/demo/client/screen/D1_Initializing.xml");
            st.addAction("INITIAL_STATE",  StateConstants.STARTUP_TYPE, StateConstants.STARTUP_THROWER, 0, da);
            // Se conecta al server IFX

            act = new ConnectAction();
            st.addAction("INITIAL_STATE",  StateConstants.STARTUP_TYPE, StateConstants.STARTUP_THROWER, 1, act);

            // JXFS - Initialize jxfs devices
            // -- Initialize text in/out
            act = new OpenDeviceAction();
            ((OpenDeviceAction)act).setDeviceName("TextInOut");
            st.addAction("INITIAL_STATE",  StateConstants.STARTUP_TYPE, StateConstants.STARTUP_THROWER, 2, act);
            // -- Initialize magstripe
            act = new OpenDeviceAction();
            ((OpenDeviceAction)act).setDeviceName("MagStripe");
            st.addAction("INITIAL_STATE",  StateConstants.STARTUP_TYPE, StateConstants.STARTUP_THROWER, 3, act);
            // -- Initialize printer
            act = new OpenDeviceAction();
            ((OpenDeviceAction)act).setDeviceName("DocuPrinter");
            st.addAction("INITIAL_STATE",  StateConstants.STARTUP_TYPE, StateConstants.STARTUP_THROWER, 4, act);
            
            // IFX Message
            // -- Initialize a sample ifx output message
            act = new CreateMessageAction(CommunicationConstants.MESSAGE + ".OUT");
            st.addAction("INITIAL_STATE",  StateConstants.STARTUP_TYPE, StateConstants.STARTUP_THROWER, 5, act);
            
            // Display msd in the gui
            tr = TriggerFactory.createTrigger(IDeviceConst.O_OPEN, IMsdConst.MSD_THROWER);
            st.addTrigger("INITIAL_STATE",  tr);
            tr.addAction(new DisplayDeviceAction(4));
            
            // Display printer in the gui
            tr = TriggerFactory.createTrigger(IDeviceConst.O_OPEN, ITioConst.TIO_THROWER);
            st.addTrigger("INITIAL_STATE",  tr);
            tr.addAction(new DisplayDeviceAction(3));

            // Display printer in the gui
            tr = TriggerFactory.createTrigger(IDeviceConst.O_OPEN, IPtrConst.PTR_THROWER);
            st.addTrigger("INITIAL_STATE",  tr);
            tr.addAction(new DisplayDeviceAction(2));
            
            // Sinchronize display events to change to the next state
            CountTrigger ctr = new CountTrigger("COUNT_TRIGGER_TYPE","COUNT_TRIGGER_THROWER",3);
            ctr.addAction(new StateChange("OUT_OF_SERVICE"));
            st.addTrigger("INITIAL_STATE", ctr);
            
            ctr.addCountTrigger(IDeviceConst.O_OPEN, IMsdConst.MSD_THROWER);
            ctr.addCountTrigger(IDeviceConst.O_OPEN, ITioConst.TIO_THROWER);
            ctr.addCountTrigger(IDeviceConst.O_OPEN, IPtrConst.PTR_THROWER);

            act = new OpenDeviceManagerAction();
            st.addAction("INITIAL_STATE",  StateConstants.STARTUP_TYPE, StateConstants.STARTUP_THROWER, 0, act);

            //----------------------
            // OUT_OF_SERVICE STATE
            //----------------------

            da = new DisplayAction();
            da.setScreen("cfg/demo/client/screen/D1_GeneralErrorWithTwoLines.xml");
            st.addAction("OUT_OF_SERVICE",  StateConstants.STARTUP_TYPE, StateConstants.STARTUP_THROWER, 0, da);
            
            sta = new SetTextAction("lblFirstLine", "Sorry, ATM Simulator is currently out of service");
            st.addAction("OUT_OF_SERVICE",  StateConstants.STARTUP_TYPE, StateConstants.STARTUP_THROWER, 1, sta);

//            sta = new SetTextAction("lblSecondLine", "Would you like a cup of coffee?");
//            st.addAction("OUT_OF_SERVICE",  StateConstants.STARTUP_TYPE, StateConstants.STARTUP_THROWER, 2, sta);
            
            //----------------------
            // INSERT_CARD STATE
            //----------------------

            // Display a screen
            da = new DisplayAction();
            da.setScreen("cfg/demo/client/screen/D1_InsertCard.xml");
            st.addAction("INSERT_CARD",  StateConstants.STARTUP_TYPE, StateConstants.STARTUP_THROWER, 0, da);
            // Initialize an ifx output message
            act = new ClearMessageAction();
            st.addAction("INSERT_CARD",  StateConstants.STARTUP_TYPE, StateConstants.STARTUP_THROWER, 0, act);

            // Read card data from msd
            act = new MsdReadAction();
            st.addAction("INSERT_CARD",  StateConstants.STARTUP_TYPE, StateConstants.STARTUP_THROWER, 0, act);

            // Enter information in the output message
            tr = TriggerFactory.createTrigger(IMsdConst.O_MSD_READDATA,  IMsdConst.MSD_THROWER);
            st.addTrigger("INSERT_CARD",  tr);
            tr.addAction(new KeyMapAction(IMsdConst.MSD_TRACK2, "BankSvcRq[0].DebitAddRq[0].DebitInfo.CardAcctId[0].CardMagData.MagData2"));
            tr.addAction(new SetElementAction(CommunicationConstants.MESSAGE + ".OUT",
                         "BankSvcRq[0].DebitAddRq[0].DebitInfo.CardAcctId[0].CardMagData.MagData2", 
                         null, "")); // null element forces system to find the element with the same key in the event context
            tr.addAction(new StateChange("ENTER_PIN"));

            //----------------------
            // ENTER_PIN STATE
            //----------------------

            // Display a screen
            da = new DisplayAction();
            da.setScreen("cfg/demo/client/screen/D1_EnterPIN.xml");
            st.addAction("ENTER_PIN",  StateConstants.STARTUP_TYPE, StateConstants.STARTUP_THROWER, 0, da);

            // Read pin code from the text in/out device
            tra = new TioReadAction();
            st.addAction("ENTER_PIN",  StateConstants.STARTUP_TYPE, StateConstants.STARTUP_THROWER, 0, tra);

            // Display on the screen each key red from the text in/out device
            tr = TriggerFactory.createTrigger(ITioConst.O_TIO_READ_KEY,  ITioConst.TIO_THROWER);
            st.addTrigger("ENTER_PIN",  tr);
            sta = new SetTextAction();
            sta.setComp("pwdPin");
            sta.setKey("TEXT"); // KeyMapAction is not necesary hear because device set exactly this key value in the context
            tr.addAction(sta);
            
            // Change state
            tr = TriggerFactory.createTrigger(ITioConst.O_TIO_READ,  ITioConst.TIO_THROWER);
            st.addTrigger("ENTER_PIN",  tr);
            tr.addAction(new StateChange("SELECT_OPERATION"));

            //----------------------
            // SELECT_OPERATION STATE
            //----------------------
           
            // Display a screen
            da = new DisplayAction();
            da.setScreen("cfg/demo/client/screen/D1_SelectOperation.xml");
            st.addAction("SELECT_OPERATION",  StateConstants.STARTUP_TYPE, StateConstants.STARTUP_THROWER, 0, da);

            // Pin Change button pressed
            tr = TriggerFactory.createTrigger(ButtonEvent.BUTTON_EVENT_TYPE,  "btnChangePIN");
            st.addTrigger("SELECT_OPERATION",  tr);
            tr.addAction(new SetElementAction(CommunicationConstants.MESSAGE + ".OUT",
                        "BankSvcRq[0].DebitAddRq[0].DebitInfo.DebitAuthType", 
                        "PIN Change", ""));
            tr.addAction(new StateChange("ENTER_NEW_PIN"));

            // Withdrawal pressed
            tr = TriggerFactory.createTrigger(ButtonEvent.BUTTON_EVENT_TYPE,  "btnWithdrawal");
            st.addTrigger("SELECT_OPERATION",  tr);
            tr.addAction(new SetElementAction(CommunicationConstants.MESSAGE + ".OUT",
                        "BankSvcRq[0].DebitAddRq[0].DebitInfo.DebitAuthType", 
                        "Withdrawal", ""));
            tr.addAction(new StateChange("SELECT_WITHDRAWAL_ACCOUNT"));

            // Balance button pressed
            tr = TriggerFactory.createTrigger(ButtonEvent.BUTTON_EVENT_TYPE,  "btnBalance");
            st.addTrigger("SELECT_OPERATION",  tr);
            tr.addAction(new SetElementAction(CommunicationConstants.MESSAGE + ".OUT",
                        "BankSvcRq[0].DebitAddRq[0].DebitInfo.DebitAuthType", 
                        "Transfer", ""));
            tr.addAction(new StateChange("SELECT_BALANCE_ACCOUNT"));
            
            // Transfer button pressed
            tr = TriggerFactory.createTrigger(ButtonEvent.BUTTON_EVENT_TYPE,  "btnTransfer");
            st.addTrigger("SELECT_OPERATION",  tr);
            tr.addAction(new SetElementAction(CommunicationConstants.MESSAGE + ".OUT",
                        "BankSvcRq[0].DebitAddRq[0].DebitInfo.DebitAuthType", 
                        "Transfer", ""));
            tr.addAction(new StateChange("SELECT_SOURCE_ACCOUNT"));

            //----------------------
            // ENTER_NEW_PIN STATE
            //----------------------

            // Display a screen
            da = new DisplayAction();
            da.setScreen("cfg/demo/client/screen/D1_EnterPIN.xml");
            st.addAction("ENTER_NEW_PIN",  StateConstants.STARTUP_TYPE, StateConstants.STARTUP_THROWER, 0, da);

            // Customize the screen
            sta = new SetTextAction("lblTitle", "Enter your new PIN code");
            st.addAction("ENTER_NEW_PIN",  StateConstants.STARTUP_TYPE, StateConstants.STARTUP_THROWER, 1, sta);

            // Read pin code from the text in/out device
            tra = new TioReadAction();
            st.addAction("ENTER_NEW_PIN",  StateConstants.STARTUP_TYPE, StateConstants.STARTUP_THROWER, 0, tra);

            // Display on the screen each key red from the text in/out device
            tr = TriggerFactory.createTrigger(ITioConst.O_TIO_READ_KEY,  ITioConst.TIO_THROWER);
            st.addTrigger("ENTER_NEW_PIN",  tr);
            sta = new SetTextAction();
            sta.setComp("pwdPin");
            sta.setKey("TEXT"); // KeyMapAction is not necesary hear because device set exactly this key value in the context
            tr.addAction(sta);
            
            // Change state
            tr = TriggerFactory.createTrigger(ITioConst.O_TIO_READ,  ITioConst.TIO_THROWER);
            st.addTrigger("ENTER_NEW_PIN",  tr);
            tr.addAction(new StateChange("RE_ENTER_NEW_PIN"));

            //-----------------------
            // RE_ENTER_NEW_PIN STATE
            //-----------------------

            // Display a screen
            da = new DisplayAction();
            da.setScreen("cfg/demo/client/screen/D1_EnterPIN.xml");
            st.addAction("RE_ENTER_NEW_PIN",  StateConstants.STARTUP_TYPE, StateConstants.STARTUP_THROWER, 0, da);

            // Customize the screen
            sta = new SetTextAction("lblTitle", "Re-Enter your new PIN code");
            st.addAction("RE_ENTER_NEW_PIN",  StateConstants.STARTUP_TYPE, StateConstants.STARTUP_THROWER, 1, sta);
            
            // Read pin code from the text in/out device
            tra = new TioReadAction();
            st.addAction("RE_ENTER_NEW_PIN",  StateConstants.STARTUP_TYPE, StateConstants.STARTUP_THROWER, 0, tra);

            // Display on the screen each key red from the text in/out device
            tr = TriggerFactory.createTrigger(ITioConst.O_TIO_READ_KEY,  ITioConst.TIO_THROWER);
            st.addTrigger("RE_ENTER_NEW_PIN",  tr);
            sta = new SetTextAction();
            sta.setComp("pwdPin");
            sta.setKey("TEXT"); // KeyMapAction is not necesary hear because device set exactly this key value in the context
            tr.addAction(sta);
            
            // Change state
            tr = TriggerFactory.createTrigger(ITioConst.O_TIO_READ,  ITioConst.TIO_THROWER);
            st.addTrigger("RE_ENTER_NEW_PIN",  tr);
            tr.addAction(new StateChange("PROCESS_REQUEST"));
            
            //--------------------------------
            // SELECT_WITHDRAWAL_ACCOUNT STATE
            //--------------------------------
           
            // Display a screen
            da = new DisplayAction();
            da.setScreen("cfg/demo/client/screen/D1_SelectAccount.xml");
            st.addAction("SELECT_WITHDRAWAL_ACCOUNT",  StateConstants.STARTUP_TYPE, StateConstants.STARTUP_THROWER, 0, da);

            // Set elements in the output message and change state
            tr = TriggerFactory.createTrigger(ButtonEvent.BUTTON_EVENT_TYPE,  "btnAccount1");
            st.addTrigger("SELECT_WITHDRAWAL_ACCOUNT",  tr);
            tr.addAction(new SetElementAction(CommunicationConstants.MESSAGE + ".OUT",
                        "BankSvcRq[0].DebitAddRq[0].DebitInfo.CardAcctId[0].AcctType", 
                        "Account1", ""));
            tr.addAction(new StateChange("SELECT_CURRENCY"));            

            tr = TriggerFactory.createTrigger(ButtonEvent.BUTTON_EVENT_TYPE,  "btnAccount2");
            st.addTrigger("SELECT_WITHDRAWAL_ACCOUNT",  tr);
            tr.addAction(new SetElementAction(CommunicationConstants.MESSAGE + ".OUT",
                        "BankSvcRq[0].DebitAddRq[0].DebitInfo.CardAcctId[0].AcctType", 
                        "Account2", ""));
            tr.addAction(new StateChange("SELECT_CURRENCY"));            
            
            tr = TriggerFactory.createTrigger(ButtonEvent.BUTTON_EVENT_TYPE,  "btnAccount3");
            st.addTrigger("SELECT_WITHDRAWAL_ACCOUNT",  tr);
            tr.addAction(new SetElementAction(CommunicationConstants.MESSAGE + ".OUT",
                        "BankSvcRq[0].DebitAddRq[0].DebitInfo.CardAcctId[0].AcctType", 
                        "Account3", ""));
            tr.addAction(new StateChange("SELECT_CURRENCY"));            

            tr = TriggerFactory.createTrigger(ButtonEvent.BUTTON_EVENT_TYPE,  "btnAccount4");
            st.addTrigger("SELECT_WITHDRAWAL_ACCOUNT",  tr);
            tr.addAction(new SetElementAction(CommunicationConstants.MESSAGE + ".OUT",
                        "BankSvcRq[0].DebitAddRq[0].DebitInfo.CardAcctId[0].AcctType", 
                        "Account4", ""));
            tr.addAction(new StateChange("SELECT_CURRENCY"));            

            //------------------------------
            // SELECT_BALANCE_ACCOUNT STATE
            //------------------------------
           
            // Display a screen
            da = new DisplayAction();
            da.setScreen("cfg/demo/client/screen/D1_SelectAccount.xml");
            st.addAction("SELECT_BALANCE_ACCOUNT",  StateConstants.STARTUP_TYPE, StateConstants.STARTUP_THROWER, 0, da);

            // Set elements in the output message and change state
            tr = TriggerFactory.createTrigger(ButtonEvent.BUTTON_EVENT_TYPE,  "btnAccount1");
            st.addTrigger("SELECT_BALANCE_ACCOUNT",  tr);
            tr.addAction(new SetElementAction(CommunicationConstants.MESSAGE + ".OUT",
                        "BankSvcRq[0].DebitAddRq[0].DebitInfo.CardAcctId[0].AcctType", 
                        "Account1", ""));
            tr.addAction(new StateChange("PROCESS_REQUEST"));            

            tr = TriggerFactory.createTrigger(ButtonEvent.BUTTON_EVENT_TYPE,  "btnAccount2");
            st.addTrigger("SELECT_BALANCE_ACCOUNT",  tr);
            tr.addAction(new SetElementAction(CommunicationConstants.MESSAGE + ".OUT",
                        "BankSvcRq[0].DebitAddRq[0].DebitInfo.CardAcctId[0].AcctType", 
                        "Account2", ""));
            tr.addAction(new StateChange("PROCESS_REQUEST"));
            
            tr = TriggerFactory.createTrigger(ButtonEvent.BUTTON_EVENT_TYPE,  "btnAccount3");
            st.addTrigger("SELECT_BALANCE_ACCOUNT",  tr);
            tr.addAction(new SetElementAction(CommunicationConstants.MESSAGE + ".OUT",
                        "BankSvcRq[0].DebitAddRq[0].DebitInfo.CardAcctId[0].AcctType", 
                        "Account3", ""));
            tr.addAction(new StateChange("PROCESS_REQUEST"));

            tr = TriggerFactory.createTrigger(ButtonEvent.BUTTON_EVENT_TYPE,  "btnAccount4");
            st.addTrigger("SELECT_BALANCE_ACCOUNT",  tr);
            tr.addAction(new SetElementAction(CommunicationConstants.MESSAGE + ".OUT",
                        "BankSvcRq[0].DebitAddRq[0].DebitInfo.CardAcctId[0].AcctType", 
                        "Account4", ""));
            tr.addAction(new StateChange("PROCESS_REQUEST"));
            
            
            //------------------------------
            // SELECT_SOURCE_ACCOUNT STATE
            //------------------------------
           
            // Display a screen
            da = new DisplayAction();
            da.setScreen("cfg/demo/client/screen/D1_SelectAccount.xml");
            st.addAction("SELECT_SOURCE_ACCOUNT",  StateConstants.STARTUP_TYPE, StateConstants.STARTUP_THROWER, 0, da);

            // Customize the screen
            sta = new SetTextAction("lblTitle", "Select source account");
            st.addAction("SELECT_SOURCE_ACCOUNT",  StateConstants.STARTUP_TYPE, StateConstants.STARTUP_THROWER, 1, sta);
            
            // Set elements in the output message and change state
            tr = TriggerFactory.createTrigger(ButtonEvent.BUTTON_EVENT_TYPE,  "btnAccount1");
            st.addTrigger("SELECT_SOURCE_ACCOUNT",  tr);
            tr.addAction(new SetElementAction(CommunicationConstants.MESSAGE + ".OUT",
                        "BankSvcRq[0].DebitAddRq[0].DebitInfo.CardAcctId[0].AcctType", 
                        "Account1", ""));
            tr.addAction(new StateChange("SELECT_DESTINATION_ACCOUNT"));            

            tr = TriggerFactory.createTrigger(ButtonEvent.BUTTON_EVENT_TYPE,  "btnAccount2");
            st.addTrigger("SELECT_SOURCE_ACCOUNT",  tr);
            tr.addAction(new SetElementAction(CommunicationConstants.MESSAGE + ".OUT",
                        "BankSvcRq[0].DebitAddRq[0].DebitInfo.CardAcctId[0].AcctType", 
                        "Account2", ""));
            tr.addAction(new StateChange("SELECT_DESTINATION_ACCOUNT"));            
            
            tr = TriggerFactory.createTrigger(ButtonEvent.BUTTON_EVENT_TYPE,  "btnAccount3");
            st.addTrigger("SELECT_SOURCE_ACCOUNT",  tr);
            tr.addAction(new SetElementAction(CommunicationConstants.MESSAGE + ".OUT",
                        "BankSvcRq[0].DebitAddRq[0].DebitInfo.CardAcctId[0].AcctType", 
                        "Account3", ""));
            tr.addAction(new StateChange("SELECT_DESTINATION_ACCOUNT"));            

            tr = TriggerFactory.createTrigger(ButtonEvent.BUTTON_EVENT_TYPE,  "btnAccount4");
            st.addTrigger("SELECT_SOURCE_ACCOUNT",  tr);
            tr.addAction(new SetElementAction(CommunicationConstants.MESSAGE + ".OUT",
                        "BankSvcRq[0].DebitAddRq[0].DebitInfo.CardAcctId[0].AcctType", 
                        "Account4", ""));
            tr.addAction(new StateChange("SELECT_DESTINATION_ACCOUNT"));            

            //----------------------------------
            // SELECT_DESTINATION_ACCOUNT STATE
            //----------------------------------
           
            // Display a screen
            da = new DisplayAction();
            da.setScreen("cfg/demo/client/screen/D1_SelectAccount.xml");
            st.addAction("SELECT_DESTINATION_ACCOUNT",  StateConstants.STARTUP_TYPE, StateConstants.STARTUP_THROWER, 0, da);
            
            // Customize the screen
            sta = new SetTextAction("lblTitle", "Select destination account");
            st.addAction("SELECT_DESTINATION_ACCOUNT",  StateConstants.STARTUP_TYPE, StateConstants.STARTUP_THROWER, 1, sta);

            // Set elements in the output message and change state
            tr = TriggerFactory.createTrigger(ButtonEvent.BUTTON_EVENT_TYPE,  "btnAccount1");
            st.addTrigger("SELECT_DESTINATION_ACCOUNT",  tr);
            tr.addAction(new SetElementAction(CommunicationConstants.MESSAGE + ".OUT",
                        "BankSvcRq[0].DebitAddRq[0].DebitInfo.CardAcctId[0].AcctType", 
                        "Account1", ""));
            tr.addAction(new StateChange("SELECT_CURRENCY"));            

            tr = TriggerFactory.createTrigger(ButtonEvent.BUTTON_EVENT_TYPE,  "btnAccount2");
            st.addTrigger("SELECT_DESTINATION_ACCOUNT",  tr);
            tr.addAction(new SetElementAction(CommunicationConstants.MESSAGE + ".OUT",
                        "BankSvcRq[0].DebitAddRq[0].DebitInfo.CardAcctId[0].AcctType", 
                        "Account2", ""));
            tr.addAction(new StateChange("SELECT_CURRENCY"));            
            
            tr = TriggerFactory.createTrigger(ButtonEvent.BUTTON_EVENT_TYPE,  "btnAccount3");
            st.addTrigger("SELECT_DESTINATION_ACCOUNT",  tr);
            tr.addAction(new SetElementAction(CommunicationConstants.MESSAGE + ".OUT",
                        "BankSvcRq[0].DebitAddRq[0].DebitInfo.CardAcctId[0].AcctType", 
                        "Account3", ""));
            tr.addAction(new StateChange("SELECT_CURRENCY"));            

            tr = TriggerFactory.createTrigger(ButtonEvent.BUTTON_EVENT_TYPE,  "btnAccount4");
            st.addTrigger("SELECT_DESTINATION_ACCOUNT",  tr);
            tr.addAction(new SetElementAction(CommunicationConstants.MESSAGE + ".OUT",
                        "BankSvcRq[0].DebitAddRq[0].DebitInfo.CardAcctId[0].AcctType", 
                        "Account4", ""));
            tr.addAction(new StateChange("SELECT_CURRENCY"));            
            
            //----------------------
            // SELECT_CURRENCY STATE
            //----------------------
           
            // Display a screen
            da = new DisplayAction();
            da.setScreen("cfg/demo/client/screen/D1_SelectCurrency.xml");
            st.addAction("SELECT_CURRENCY",  StateConstants.STARTUP_TYPE, StateConstants.STARTUP_THROWER, 0, da);

            // Set elements in the output message and change state
            tr = TriggerFactory.createTrigger(ButtonEvent.BUTTON_EVENT_TYPE,  "btnPeso");
            st.addTrigger("SELECT_CURRENCY",  tr);
            tr.addAction(new SetElementAction(CommunicationConstants.MESSAGE + ".OUT",
                        "BankSvcRq[0].DebitAddRq[0].DebitInfo.CompositeCurAmt[0].CurAmt.CurCode", 
                        "$U", ""));
            tr.addAction(new StateChange("ENTER_AMMOUNT"));            

            tr = TriggerFactory.createTrigger(ButtonEvent.BUTTON_EVENT_TYPE,  "btnUSDollar");
            st.addTrigger("SELECT_CURRENCY",  tr);
            tr.addAction(new SetElementAction(CommunicationConstants.MESSAGE + ".OUT",
                        "BankSvcRq[0].DebitAddRq[0].DebitInfo.CompositeCurAmt[0].CurAmt.CurCode", 
                        "USD", ""));
            tr.addAction(new StateChange("ENTER_AMMOUNT"));            

            tr = TriggerFactory.createTrigger(ButtonEvent.BUTTON_EVENT_TYPE,  "btnEuro");
            st.addTrigger("SELECT_CURRENCY",  tr);
            tr.addAction(new SetElementAction(CommunicationConstants.MESSAGE + ".OUT",
                        "BankSvcRq[0].DebitAddRq[0].DebitInfo.CompositeCurAmt[0].CurAmt.CurCode", 
                        "EUR", ""));
            tr.addAction(new StateChange("ENTER_AMMOUNT"));            
            
            //----------------------
            // ENTER_AMMOUNT STATE
            //----------------------
           
            // Display a screen
            da = new DisplayAction();
            da.setScreen("cfg/demo/client/screen/D1_EnterAmmount.xml");
            st.addAction("ENTER_AMMOUNT",  StateConstants.STARTUP_TYPE, StateConstants.STARTUP_THROWER, 0, da);

            // Read from the text in/out device
            tra = new TioReadAction();
            st.addAction("ENTER_AMMOUNT",  StateConstants.STARTUP_TYPE, StateConstants.STARTUP_THROWER, 0, tra);

            // Display on the screen each key pressed on the tio device
            tr = TriggerFactory.createTrigger(ITioConst.O_TIO_READ_KEY,  ITioConst.TIO_THROWER);
            st.addTrigger("ENTER_AMMOUNT",  tr);
            sta = new SetTextAction();
            sta.setComp("txtAmmount");
            sta.setKey("TEXT"); // No es necesario un KeyMapAction porque el dispositivo devuelve con esta misma clave
            tr.addAction(sta);
            
            // Change state when Enter Key is pressed
            tr = TriggerFactory.createTrigger(ITioConst.O_TIO_READ,  ITioConst.TIO_THROWER);
            st.addTrigger("ENTER_AMMOUNT",  tr);
            tr.addAction(new KeyMapAction("TEXT", "BankSvcRq[0].DebitAddRq[0].DebitInfo.CompositeCurAmt[0].CurAmt.Amt"));
            tr.addAction(new SetElementAction(CommunicationConstants.MESSAGE + ".OUT",
                        "BankSvcRq[0].DebitAddRq[0].DebitInfo.CompositeCurAmt[0].CurAmt.Amt", 
                        null, 0));
            tr.addAction(new StateChange("PROCESS_REQUEST"));

            //----------------------
            // PROCES_REQUEST STATE
            //----------------------
            
            // Display a screen and send output message to the server
            da = new DisplayAction();
            da.setScreen("cfg/demo/client/screen/D1_ProcessRequest.xml");
            st.addAction("PROCESS_REQUEST",  StateConstants.STARTUP_TYPE, StateConstants.STARTUP_THROWER, 0, da);
            act = new SendMessageAction("ATM_REQUEST_CHANNEL",CommunicationConstants.MESSAGE + ".OUT");
            st.addAction("PROCESS_REQUEST",  StateConstants.STARTUP_TYPE, StateConstants.STARTUP_THROWER, 1, act);
            //act = new ClearMessageAction(CommunicationConstants.MESSAGE + ".OUT");
            //st.addAction("PROCESS_REQUEST",  StateConstants.STARTUP_TYPE, StateConstants.STARTUP_THROWER, 2, act);

            tr = TriggerFactory.createTrigger(CommunicationConstants.COMMUNICATION_TYPE,
                                              "ATM_REQUEST_CHANNEL");
            st.addTrigger("PROCESS_REQUEST", tr);            
            tr.addAction(new ExternalCommandAction("MediaSvcRq[0].DevActionRq[0].ActionsInfo[0]")); 
            
            //----------------------
            // ANOTHER_OPERATION STATE
            //----------------------
            
            // Display a screen
            da = new DisplayAction();
            da.setScreen("cfg/demo/client/screen/D1_AnotherOperation.xml");
            st.addAction("ANOTHER_OPERATION",  StateConstants.STARTUP_TYPE, StateConstants.STARTUP_THROWER, 0, da);

            // Change state for a new operation with the same card
            tr = TriggerFactory.createTrigger(ButtonEvent.BUTTON_EVENT_TYPE,  "btnYes");
            st.addTrigger("ANOTHER_OPERATION",  tr);
            tr.addAction(new StateChange("SELECT_OPERATION"));            

            // Change state for a new card operation
            tr = TriggerFactory.createTrigger(ButtonEvent.BUTTON_EVENT_TYPE,  "btnNo");
            st.addTrigger("ANOTHER_OPERATION",  tr);
            tr.addAction(new StateChange("INSERT_CARD"));            

           // ----------------- 
           // GLOBAL EVENTS
           // -----------------             
           /**
           * If channel is disconnected, an event is performed,
           * and then a disconnection's counter is created...with a limit of 5 disconnections.
           */
            tr = TriggerFactory.createTrigger(CommunicationConstants.COMMUNICATION_TYPE,
                                              "HOST_COMMAND_CHANNEL");
            st.addTrigger(StateConstants.GLOBAL_STATE, tr);            
            tr.addAction(new ExternalCommandAction("MediaSvcRq[0].DevActionRq[0].ActionsInfo[0]")); 
            
            tr = TriggerFactory.createTrigger(
                    CommunicationConstants.DISCONNECT_TYPE,
                    "ATM_REQUEST_CHANNEL");
            tr.addAction(new DebugAction("Host was disconnected"));
            tr.addAction(new Delay(5000));
            tr.addAction(new ConnectAction());
            StateHandlerFactory.getStateHandler().addTrigger(StateConstants.GLOBAL_STATE, tr);

            // Configure the maximum number of disconnections
            CountTrigger ctrg = new CountTrigger("COUNT", "COUNT", 3);
            ctrg.addAction(new DebugAction("Se ha superado la cantidad maxima de intentos de conexion infructuosos"));
            ctrg.addAction(1, new Delay(1000));
            ctrg.addAction(2, new SystemExit()); 
            StateHandlerFactory.getStateHandler().addTrigger(StateConstants.GLOBAL_STATE, ctrg);
            
            ctrg.addCountTrigger(CommunicationConstants.DISCONNECT_TYPE,
                    "ATM_REQUEST_CHANNEL");
            
            // State change - Reason: Card wasn't found in the msd media
            tr = TriggerFactory.createTrigger(IMsdConst.E_MSD_NOMEDIA, IMsdConst.MSD_THROWER);
            st.addTrigger(StateConstants.GLOBAL_STATE,  tr);
            tr.addAction(0, new StateChange("OUT_OF_SERVICE")); 
            tr.addAction(2, new SetTextAction("lblSecondLine", "No Magnetic Stripe Device present"));

            // State change - Reason: Read card operation failed in the msd media
            tr = TriggerFactory.createTrigger(IMsdConst.E_MSD_READFAILURE, IMsdConst.MSD_THROWER);
            st.addTrigger(StateConstants.GLOBAL_STATE,  tr);
            tr.addAction(0, new StateChange("OUT_OF_SERVICE"));          
            tr.addAction(2, new SetTextAction("lblSecondLine", "Magnetic Stripe - Read operation failed"));
            
            // State change - Reason: Read operation failed in the text in/out media
            tr = TriggerFactory.createTrigger(ITioConst.E_TIO_READ, ITioConst.TIO_THROWER);
            st.addTrigger(StateConstants.GLOBAL_STATE,  tr);
            tr.addAction(0, new StateChange("OUT_OF_SERVICE"));          
            tr.addAction(2, new SetTextAction("lblSecondLine", "Keyboard device - Read operation failed"));

            // State change - Reason: Led failed in the text in/out media
            tr = TriggerFactory.createTrigger(ITioConst.E_TIO_LED, ITioConst.TIO_THROWER);
            st.addTrigger(StateConstants.GLOBAL_STATE,  tr);
            tr.addAction(0, new StateChange("OUT_OF_SERVICE"));          
            tr.addAction(2, new SetTextAction("lblSecondLine", "Keyboard device - LED failed"));

            // State change - Reason: Printer exception, data overflow
            tr = TriggerFactory.createTrigger(IPtrConst.E_PTR_MEDIA_OVERFLOW, IPtrConst.PTR_THROWER);
            st.addTrigger(StateConstants.GLOBAL_STATE,  tr);
            tr.addAction(0, new StateChange("OUT_OF_SERVICE"));          
            tr.addAction(2, new SetTextAction("lblSecondLine", "Printer device - Data overflow"));

            // State change - Reason: Printer exception, paper out
            tr = TriggerFactory.createTrigger(IPtrConst.E_PTR_PAPEROUT, IPtrConst.PTR_THROWER);
            st.addTrigger(StateConstants.GLOBAL_STATE,  tr);
            tr.addAction(0, new StateChange("OUT_OF_SERVICE"));          
            tr.addAction(2, new SetTextAction("lblSecondLine", "Printer device - Paper out"));

            // State change - Reason: Printer exception, printer no present
            tr = TriggerFactory.createTrigger(IPtrConst.E_PTR_NO_MEDIA_PRESENT, IPtrConst.PTR_THROWER);
            st.addTrigger(StateConstants.GLOBAL_STATE,  tr);
            tr.addAction(0, new StateChange("OUT_OF_SERVICE"));          
            tr.addAction(2, new SetTextAction("lblSecondLine", "Printer device - No media present"));

            /**
             *  Save state machine's configuration
             */
            StateHandlerFactory.getStateHandler().save("cfg/demo/client/DemoClientStateMachine.xml");
            
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        System.exit(0);
    } 
}
