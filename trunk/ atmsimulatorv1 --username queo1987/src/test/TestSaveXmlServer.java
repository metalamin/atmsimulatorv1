package test;

import config.SystemConfig;
import domain.implementation.ConditionalAction;
import domain.implementation.CountTrigger;
import domain.implementation.DebugAction;
import domain.implementation.Delay;
import domain.implementation.PersistAction;
import domain.implementation.StateChange;
import domain.implementation.SystemExit;
import domain.state.Action;
import domain.state.StateConstants;
import domain.state.StateHandler;
import domain.state.StateHandlerFactory;
import domain.state.Trigger;
import domain.state.TriggerFactory;
import infrastructure.communication.CommunicationConstants;
import infrastructure.communication.action.ClearMessageAction;
import infrastructure.communication.action.ConnectChannel;
import infrastructure.communication.action.CreateMessageAction;
import infrastructure.communication.action.DisconnectChannel;
import infrastructure.communication.action.GetElementAction;
import infrastructure.communication.action.SendMessageAction;
import infrastructure.communication.action.SetElementAction;
import infrastructure.dataaccess.XMLCollectionWriter;
import infrastructure.devices.controllers.msd.MsdReadAction;
import infrastructure.devices.controllers.ptr.PtrPrintDocAction;
import infrastructure.devices.controllers.tio.TioReadAction;
import presentation.display.ButtonEvent;
import presentation.display.DisplayAloneAction;
import presentation.display.SetTextAction;

public class TestSaveXmlServer
{
    public static void main(String[] args) 
    {
        DisplayAloneAction da;
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
             * Add new states.
             */
            st.addState(StateConstants.INITIAL_STATE);
            st.addState("CONNECT_ATM_STATE");
            st.addState("MAIN_PANEL_STATE");
            st.addState("WITHDRAWAL_STATE");
            
            //---------------------------------------
            // INITIAL_STATE: System Initialization
            //---------------------------------------

            // Display a screen
            da = new DisplayAloneAction();
            da.setScreen("cfg/demo/server/screen/D1_AtmDisconnected.xml");
            st.addAction("INITIAL_STATE",  StateConstants.STARTUP_TYPE, StateConstants.STARTUP_THROWER, 0, da);
            
            act = new CreateMessageAction(CommunicationConstants.MESSAGE + ".OUT");
            st.addAction(StateConstants.INITIAL_STATE,  StateConstants.STARTUP_TYPE, StateConstants.STARTUP_THROWER, 0, act);

            act = new ConnectChannel("ATM_REQUEST_CHANNEL");
            st.addAction(StateConstants.INITIAL_STATE,  StateConstants.STARTUP_TYPE, StateConstants.STARTUP_THROWER, 1, act);

            Trigger trgCnnct = TriggerFactory.createTrigger(CommunicationConstants.CONNECT_TYPE, "ATM_REQUEST_CHANNEL");
            trgCnnct.addAction(new DebugAction("Se conecto al ATM_REQ_CH"));
            st.addTrigger(StateConstants.INITIAL_STATE, trgCnnct);
                    
            
            // Sinchronize display events to change to the next state
            CountTrigger ctr = new CountTrigger("COUNT_TRIGGER_TYPE","COUNT_TRIGGER_THROWER",1);
            ctr.addAction(new StateChange("CONNECT_ATM_STATE"));
            st.addTrigger(StateConstants.INITIAL_STATE, ctr);

            ctr.addCountTrigger(CommunicationConstants.CONNECT_TYPE, "ATM_REQUEST_CHANNEL");

            // Exit button is pressed
            tr = TriggerFactory.createTrigger(ButtonEvent.BUTTON_EVENT_TYPE,  "btnExit");
            st.addTrigger("INITIAL_STATE",  tr);
            tr.addAction(new SystemExit());
            
            //---------------------------------------
            // CONNECT_ATM_STATE : Connect as a atm's client
            //---------------------------------------
            act = new ConnectChannel("HOST_COMMAND_CHANNEL");
            st.addAction("CONNECT_ATM_STATE",  StateConstants.STARTUP_TYPE, StateConstants.STARTUP_THROWER, 2, act);
            st.addTrigger("CONNECT_ATM_STATE", 
                    TriggerFactory.createTrigger(CommunicationConstants.CONNECT_TYPE, "HOST_COMMAND_CHANNEL"));
            
            // Sinchronize display events to change to the next state
            ctr = new CountTrigger("COUNT_TRIGGER_TYPE","COUNT_TRIGGER_THROWER",1);
            ctr.addAction(new StateChange("MAIN_PANEL_STATE"));
            st.addTrigger("CONNECT_ATM_STATE", ctr);

            ctr.addCountTrigger(CommunicationConstants.CONNECT_TYPE, "HOST_COMMAND_CHANNEL");
            
            //---------------------------------------
            // MAIN_PANEL state: Includes most important server commands
            //---------------------------------------
            
            // Display Main Panel screen
            da = new DisplayAloneAction();
            da.setScreen("cfg/demo/server/screen/D1_MainPanel.xml");
            st.addAction("MAIN_PANEL_STATE",  StateConstants.STARTUP_TYPE, StateConstants.STARTUP_THROWER, 0, da);

            // Shutdown button is pressed
            tr = TriggerFactory.createTrigger(ButtonEvent.BUTTON_EVENT_TYPE,  "btnShutdown");
            st.addTrigger("MAIN_PANEL_STATE",  tr);
            XMLCollectionWriter xmlcw = new XMLCollectionWriter();
            act = new SystemExit();
            xmlcw.addObject(act);
            String formattedAction = xmlcw.toString();
            tr.addAction(1, new CreateMessageAction(CommunicationConstants.MESSAGE + ".OUT"));

            tr.addAction(2, new SetElementAction(CommunicationConstants.MESSAGE + ".OUT",
                        "MediaSvcRq[0].RqUID", 
                        "3a64839c-17ff-40c8-8747-33683bb728b7", ""));
            tr.addAction(3, new SetElementAction(CommunicationConstants.MESSAGE + ".OUT",
                        "MediaSvcRq[0].DevActionRq[0].RqUID", 
                        "3a64839c-17ff-40c8-8747-33683bb728b7", ""));
            tr.addAction(4, new SetElementAction(CommunicationConstants.MESSAGE + ".OUT",
                        "MediaSvcRq[0].DevActionRq[0].ActionsInfo[0]", 
                        formattedAction, ""));
            tr.addAction(5, new SendMessageAction("HOST_COMMAND_CHANNEL",CommunicationConstants.MESSAGE + ".OUT"));
            tr.addAction(6, new ClearMessageAction(CommunicationConstants.MESSAGE + ".OUT"));

            // InService button is pressed
            tr = TriggerFactory.createTrigger(ButtonEvent.BUTTON_EVENT_TYPE,  "btnInService");
            st.addTrigger("MAIN_PANEL_STATE",  tr);
            xmlcw = new XMLCollectionWriter();
            act = new StateChange("INSERT_CARD");
            xmlcw.addObject(act);
            formattedAction = xmlcw.toString();
            tr.addAction(4, new CreateMessageAction(CommunicationConstants.MESSAGE + ".OUT"));            
            tr.addAction(6, new SetElementAction(CommunicationConstants.MESSAGE + ".OUT",
                        "MediaSvcRq[0].RqUID", 
                        "3a64839c-17ff-40c8-8747-33683bb728b7", ""));
            tr.addAction(8, new SetElementAction(CommunicationConstants.MESSAGE + ".OUT",
                        "MediaSvcRq[0].DevActionRq[0].RqUID", 
                        "3a64839c-17ff-40c8-8747-33683bb728b7", ""));
            tr.addAction(10, new SetElementAction(CommunicationConstants.MESSAGE + ".OUT",
                        "MediaSvcRq[0].DevActionRq[0].ActionsInfo[0]", 
                        formattedAction, ""));
            tr.addAction(12, new SendMessageAction("HOST_COMMAND_CHANNEL",CommunicationConstants.MESSAGE + ".OUT"));
            tr.addAction(new DebugAction("InService presionado"));
            
            // OutOfService button is pressed
            tr = TriggerFactory.createTrigger(ButtonEvent.BUTTON_EVENT_TYPE,  "btnOutOfService");
            st.addTrigger("MAIN_PANEL_STATE",  tr);
            xmlcw = new XMLCollectionWriter();
            act = new StateChange("OUT_OF_SERVICE");
            xmlcw.addObject(act);
            formattedAction = xmlcw.toString();
            tr.addAction(4, new CreateMessageAction(CommunicationConstants.MESSAGE + ".OUT"));            
            tr.addAction(6, new SetElementAction(CommunicationConstants.MESSAGE + ".OUT",
                        "MediaSvcRq[0].RqUID", 
                        "3a64839c-17ff-40c8-8747-33683bb728b7", ""));
            tr.addAction(8, new SetElementAction(CommunicationConstants.MESSAGE + ".OUT",
                        "MediaSvcRq[0].DevActionRq[0].RqUID", 
                        "3a64839c-17ff-40c8-8747-33683bb728b7", ""));
            tr.addAction(10, new SetElementAction(CommunicationConstants.MESSAGE + ".OUT",
                        "MediaSvcRq[0].DevActionRq[0].ActionsInfo[0]", 
                        formattedAction, ""));
            tr.addAction(12, new SendMessageAction("HOST_COMMAND_CHANNEL",CommunicationConstants.MESSAGE + ".OUT"));
            
            // Exit button is pressed
            tr = TriggerFactory.createTrigger(ButtonEvent.BUTTON_EVENT_TYPE,  "btnExit");
            st.addTrigger("MAIN_PANEL_STATE",  tr);
            tr.addAction(new SystemExit());
            
           // ----------------- 
           // WITHDRAWAL STATE
           // -----------------             

            // Display Main Panel screen
            da = new DisplayAloneAction();
            da.setScreen("cfg/demo/server/screen/D1_Withdrawal.xml");
            st.addAction("WITHDRAWAL_STATE",  StateConstants.STARTUP_TYPE, StateConstants.STARTUP_THROWER, 0, da);
            
           // Get Card Number
           //-----------------
            act = new GetElementAction(CommunicationConstants.MESSAGE + ".IN",
                      "BankSvcRq[0].DebitAddRq[0].DebitInfo.CardAcctId[0].CardMagData.MagData2");
            st.addAction("WITHDRAWAL_STATE",  StateConstants.STARTUP_TYPE, StateConstants.STARTUP_THROWER, 2, act);

            sta = new SetTextAction("lblCardNumber", null);
            sta.setKey("BankSvcRq[0].DebitAddRq[0].DebitInfo.CardAcctId[0].CardMagData.MagData2");
            st.addAction("WITHDRAWAL_STATE",  StateConstants.STARTUP_TYPE, StateConstants.STARTUP_THROWER, 4, sta);

           // Get Operation Type
           //-------------------
            act = new GetElementAction(CommunicationConstants.MESSAGE + ".IN",
                      "BankSvcRq[0].DebitAddRq[0].DebitInfo.DebitAuthType");
            st.addAction("WITHDRAWAL_STATE",  StateConstants.STARTUP_TYPE, StateConstants.STARTUP_THROWER, 2, act);
            
            sta = new SetTextAction("lblOperation", null);
            sta.setKey("BankSvcRq[0].DebitAddRq[0].DebitInfo.DebitAuthType");
            st.addAction("WITHDRAWAL_STATE",  StateConstants.STARTUP_TYPE, StateConstants.STARTUP_THROWER, 4, sta);

           // Get Account
           //-------------------
            act = new GetElementAction(CommunicationConstants.MESSAGE + ".IN",
                      "BankSvcRq[0].DebitAddRq[0].DebitInfo.CardAcctId[0].AcctType");
            st.addAction("WITHDRAWAL_STATE",  StateConstants.STARTUP_TYPE, StateConstants.STARTUP_THROWER, 2, act);
            
            sta = new SetTextAction("lblAccount", null);
            sta.setKey("BankSvcRq[0].DebitAddRq[0].DebitInfo.CardAcctId[0].AcctType");
            st.addAction("WITHDRAWAL_STATE",  StateConstants.STARTUP_TYPE, StateConstants.STARTUP_THROWER, 4, sta);

           // Get Currency
           //-------------------
            act = new GetElementAction(CommunicationConstants.MESSAGE + ".IN",
                      "BankSvcRq[0].DebitAddRq[0].DebitInfo.CompositeCurAmt[0].CurAmt.CurCode");
            st.addAction("WITHDRAWAL_STATE",  StateConstants.STARTUP_TYPE, StateConstants.STARTUP_THROWER, 2, act);
            
            sta = new SetTextAction("lblCurrency", null);
            sta.setKey("BankSvcRq[0].DebitAddRq[0].DebitInfo.CompositeCurAmt[0].CurAmt.CurCode");
            st.addAction("WITHDRAWAL_STATE",  StateConstants.STARTUP_TYPE, StateConstants.STARTUP_THROWER, 4, sta);

           // Get Ammount
           //-------------------
            act = new GetElementAction(CommunicationConstants.MESSAGE + ".IN",
                      "BankSvcRq[0].DebitAddRq[0].DebitInfo.CompositeCurAmt[0].CurAmt.Amt");
            st.addAction("WITHDRAWAL_STATE",  StateConstants.STARTUP_TYPE, StateConstants.STARTUP_THROWER, 2, act);
            
            sta = new SetTextAction("lblAmmount", null);
            sta.setKey("BankSvcRq[0].DebitAddRq[0].DebitInfo.CompositeCurAmt[0].CurAmt.Amt");
            st.addAction("WITHDRAWAL_STATE",  StateConstants.STARTUP_TYPE, StateConstants.STARTUP_THROWER, 4, sta);
            
            //---------------------------
            // Button YES was pressed
            //---------------------------
            tr = TriggerFactory.createTrigger(ButtonEvent.BUTTON_EVENT_TYPE,  "btnYes");
            st.addTrigger("WITHDRAWAL_STATE", tr);
            xmlcw = new XMLCollectionWriter();
            // First action
            act = new StateChange("ANOTHER_OPERATION");
            xmlcw.addObject(act);
            // Second action
            act = new SetTextAction("lblTitle", "Transaction approved");
            xmlcw.addObject(act);
            // Third action
            act = new SetTextAction("lblFirstLine", "Please, take your ticket.");
            xmlcw.addObject(act);
            // Fourth action
            act = new SetTextAction("lblSecondLine", "Thanks for your preference.");
            xmlcw.addObject(act);
       
            // Printer related external actions
            
            // Get Card Number
            act = new GetElementAction(CommunicationConstants.MESSAGE + ".OUT",
                      "BankSvcRq[0].DebitAddRq[0].DebitInfo.CardAcctId[0].CardMagData.MagData2");
            xmlcw.addObject(act);
            // Get Operation Type
            act = new GetElementAction(CommunicationConstants.MESSAGE + ".OUT",
                      "BankSvcRq[0].DebitAddRq[0].DebitInfo.DebitAuthType");
            xmlcw.addObject(act);
            // Get Account
            act = new GetElementAction(CommunicationConstants.MESSAGE + ".OUT",
                      "BankSvcRq[0].DebitAddRq[0].DebitInfo.CardAcctId[0].AcctType");
            xmlcw.addObject(act);
            // Get Currency
            act = new GetElementAction(CommunicationConstants.MESSAGE + ".OUT",
                      "BankSvcRq[0].DebitAddRq[0].DebitInfo.CompositeCurAmt[0].CurAmt.CurCode");
            xmlcw.addObject(act);
            // Get Ammount
            act = new GetElementAction(CommunicationConstants.MESSAGE + ".OUT",
                      "BankSvcRq[0].DebitAddRq[0].DebitInfo.CompositeCurAmt[0].CurAmt.Amt");
            xmlcw.addObject(act);
            // Fifth action
            PtrPrintDocAction pa;
            String[] labels = {"Withdrawal Bill", "Card Number", "Account", "Currency", "Ammount", "Thank you!"};
            String[] dataKeys = {"",
                "BankSvcRq[0].DebitAddRq[0].DebitInfo.CardAcctId[0].CardMagData.MagData2",
                "BankSvcRq[0].DebitAddRq[0].DebitInfo.CardAcctId[0].AcctType",
                "BankSvcRq[0].DebitAddRq[0].DebitInfo.CompositeCurAmt[0].CurAmt.CurCode",
                "BankSvcRq[0].DebitAddRq[0].DebitInfo.CompositeCurAmt[0].CurAmt.Amt",
                ""
                };
            act = new PtrPrintDocAction("DocuPrinter", labels, dataKeys);
            xmlcw.addObject(act);
            
            formattedAction = xmlcw.toString();
            tr.addAction(2, new CreateMessageAction(CommunicationConstants.MESSAGE + ".OUT"));
            tr.addAction(4, new SetElementAction(CommunicationConstants.MESSAGE + ".OUT",
                        "MediaSvcRq[0].RqUID", 
                        "3a64839c-17ff-40c8-8747-33683bb728b7", ""));
            tr.addAction(6, new SetElementAction(CommunicationConstants.MESSAGE + ".OUT",
                        "MediaSvcRq[0].DevActionRq[0].RqUID", 
                        "3a64839c-17ff-40c8-8747-33683bb728b7", ""));
            tr.addAction(8, new SetElementAction(CommunicationConstants.MESSAGE + ".OUT",
                        "MediaSvcRq[0].DevActionRq[0].ActionsInfo[0]", 
                        formattedAction, ""));
            tr.addAction(10, new SendMessageAction("ATM_REQUEST_CHANNEL",CommunicationConstants.MESSAGE + ".OUT"));
            tr.addAction(12, new StateChange("MAIN_PANEL_STATE"));
            //-------------------------
            // Button NO was pressed
            //-------------------------
            tr = TriggerFactory.createTrigger(ButtonEvent.BUTTON_EVENT_TYPE,  "btnNo");
            st.addTrigger("WITHDRAWAL_STATE", tr);
            xmlcw = new XMLCollectionWriter();

            // First action
            act = new StateChange("ANOTHER_OPERATION");
            xmlcw.addObject(act);
            // Second action
            act = new SetTextAction("lblTitle", "Transaction rejected");
            xmlcw.addObject(act);
            // Third action
            act = new SetTextAction("lblFirstLine", "Fraudulent operation detected!");
            xmlcw.addObject(act);
            // Fourth action
            act = new SetTextAction("lblSecondLine", "Please, go to jail. Thanks.");
            xmlcw.addObject(act);
            
            formattedAction = xmlcw.toString();
            tr.addAction(2, new CreateMessageAction(CommunicationConstants.MESSAGE + ".OUT"));
            tr.addAction(4, new SetElementAction(CommunicationConstants.MESSAGE + ".OUT",
                        "MediaSvcRq[0].RqUID", 
                        "3a64839c-17ff-40c8-8747-33683bb728b7", ""));
            tr.addAction(6, new SetElementAction(CommunicationConstants.MESSAGE + ".OUT",
                        "MediaSvcRq[0].DevActionRq[0].RqUID", 
                        "3a64839c-17ff-40c8-8747-33683bb728b7", ""));
            tr.addAction(8, new SetElementAction(CommunicationConstants.MESSAGE + ".OUT",
                        "MediaSvcRq[0].DevActionRq[0].ActionsInfo[0]", 
                        formattedAction, ""));
            tr.addAction(10, new SendMessageAction("ATM_REQUEST_CHANNEL",CommunicationConstants.MESSAGE + ".OUT"));
            tr.addAction(12, new StateChange("MAIN_PANEL_STATE"));

           // ----------------- 
           // Global Events
           // -----------------             

            // ATM Request received
            tr = TriggerFactory.createTrigger(CommunicationConstants.COMMUNICATION_TYPE,
                                              "ATM_REQUEST_CHANNEL");
            st.addTrigger(StateConstants.GLOBAL_STATE, tr);
            tr.addAction(4, new PersistAction(CommunicationConstants.MESSAGE, CommunicationConstants.MESSAGE + ".IN")); 
            tr.addAction(5, new GetElementAction(CommunicationConstants.MESSAGE + ".IN",
                      "BankSvcRq[0].DebitAddRq[0].DebitInfo.DebitAuthType"));
            tr.addAction(6, new ConditionalAction("BankSvcRq[0].DebitAddRq[0].DebitInfo.DebitAuthType",
                                                  "Withdrawal",
                                                  "WITHDRAWAL_TYPE",
                                                  "ATM_REQUEST_THROWER"));
            tr.addAction(8, new ConditionalAction("BankSvcRq[0].DebitAddRq[0].DebitInfo.DebitAuthType",
                                                  "PIN Change",
                                                  "PIN_CHANGE_TYPE",
                                                  "ATM_REQUEST_THROWER"));

            
            // State change for a withdrawal operation request
            tr = TriggerFactory.createTrigger("WITHDRAWAL_TYPE",
                                              "ATM_REQUEST_THROWER");
            st.addTrigger(StateConstants.GLOBAL_STATE, tr);
            tr.addAction(2, new StateChange("WITHDRAWAL_STATE"));

            // State change for a change pin operation request            
            tr = TriggerFactory.createTrigger("PIN_CHANGE_TYPE",
                                              "ATM_REQUEST_THROWER");
            st.addTrigger(StateConstants.GLOBAL_STATE, tr);
            tr.addAction(2, new StateChange("WITHDRAWAL_STATE"));
            
            
            // Channel disconnected
            tr = TriggerFactory.createTrigger(
                    CommunicationConstants.DISCONNECT_TYPE,
                    "HOST_COMMAND_CHANNEL");
            tr.addAction(0, new DebugAction("Se ha perdido la conexion con el atm"));
            tr.addAction(1, new DisconnectChannel("HOST_COMMAND_CHANNEL"));
            tr.addAction(2, new DisconnectChannel("ATM_REQUEST_CHANNEL"));
            tr.addAction(3, new StateChange(StateConstants.INITIAL_STATE));
            StateHandlerFactory.getStateHandler().addTrigger(StateConstants.GLOBAL_STATE, tr);
            

            /**
             *  Save state machine configuration
             */
            StateHandlerFactory.getStateHandler().save("cfg/demo/server/DemoServerStateMachine.xml");
            
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        System.exit(0);
    } 
}
