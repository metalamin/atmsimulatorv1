/*
 * Created on 2/06/2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package test.other;

import org.sourceforge.ifx.utils.*;
import org.sourceforge.ifx.framework.element.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Map;
import java.util.HashMap;
import org.jdom.*;
import junit.framework.*;
import infrastructure.services.comm.message.ifx.IFXMessage;

/**
 * @author Arya Baher
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class TestIFXMessage2 {

	public static void main(String[] args) {

		try{
	        	        
	        //InputStream istream = new FileInputStream("cfg/IFX_Debit_Add_Request_Rec.xml");
	        //IFXMessage ifxMsg = new IFXMessage(istream);
                IFXMessage ifxMsg = new IFXMessage();
	        
	        System.out.println("old:" + (String)ifxMsg.getElement("BankSvcRq[1].DebitAddRq[0].DebitInfo.CompositeCurAmt[1].CurAmt.Amt"));
	        ifxMsg.setElement("BankSvcRq[1].DebitAddRq[0].DebitInfo.CompositeCurAmt[1].CurAmt.Amt", new Float(250.00));
	        System.out.println("new:" + (String)ifxMsg.getElement("BankSvcRq[1].DebitAddRq[0].DebitInfo.CompositeCurAmt[1].CurAmt.Amt"));

	        System.out.println("old:" + (String)ifxMsg.getElement("BankSvcRq[0].DebitAddRq[0].DebitInfo.CompositeCurAmt[0].CurAmt.Amt"));
	        ifxMsg.setElement("BankSvcRq[0].DebitAddRq[0].DebitInfo.CompositeCurAmt[0].CurAmt.Amt", new Float(250.00));
	        System.out.println("new:" + (String)ifxMsg.getElement("BankSvcRq[0].DebitAddRq[0].DebitInfo.CompositeCurAmt[0].CurAmt.Amt"));
                
	        System.out.println("old:" + (String)ifxMsg.getElement("BankSvcRq[0].DebitAddRq[0].DebitInfo.DebitAuthType"));
	        ifxMsg.setElement("BankSvcRq[0].DebitAddRq[0].DebitInfo.DebitAuthType","Deposit");
	        System.out.println("new:" + (String)ifxMsg.getElement("BankSvcRq[0].DebitAddRq[0].DebitInfo.DebitAuthType"));

	        ifxMsg.write(new FileOutputStream("cfg/IFX_Debit_Add_Request_Out.xml"));
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}
}
//----------------------------------------------------
//System.out.println(ifx.getBankSvcRq()[0].getDebitAddRq()[0].getDebitInfo().getDebitAuthType().getString());
//System.out.println(ifx.getBankSvcRq()[0].getDebitAddRq()[0].getDebitInfo().getCompositeCurAmt()[0].getCurAmt().getAmt().getString());
//System.out.println(ifx.getBankSvcRq()[0].getDebitAddRq()[0].getDebitInfo().getCompositeCurAmt()[1].getCurAmt().getAmt().getString());
//System.out.println(ifx.getBankSvcRq()[0].getDebitAddRq()[0].getDebitInfo().getCardAcctId()[0].getCardMagData().getMagData2().getString());
// ----------------------------------------------------
//System.out.println((String)ifxMsg.getElementValue("BankSvcRq[0].DebitAddRq[0].DebitInfo.DebitAuthType"));
//System.out.println((String)ifxMsg.getElementValue("BankSvcRq[0].DebitAddRq[0].DebitInfo.CompositeCurAmt[0].CurAmt.Amt"));
//System.out.println((String)ifxMsg.getElementValue("BankSvcRq[0].DebitAddRq[0].DebitInfo.CompositeCurAmt[1].CurAmt.Amt"));
//System.out.println((String)ifxMsg.getElementValue("BankSvcRq[0].DebitAddRq[0].DebitInfo.CardAcctId[0].CardMagData.MagData2"));
