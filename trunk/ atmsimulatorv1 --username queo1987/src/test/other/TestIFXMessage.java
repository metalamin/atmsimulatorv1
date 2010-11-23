/*
 * Created on 2/06/2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package test.other;
import infrastructure.services.comm.message.ifx.IFXMessage;

/**
 * @author Arya Baher
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class TestIFXMessage {
        public static String xmlString="";
        public static String getIfxSample(){
                    xmlString = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                            "<IFX xmlns=\"http://sourceforge.net/ifx-framework/ifx\">" +
                            "  <SignonRq Id=\"ID123456\">"+
                            "    <SignonCert>"+
                            "      <SignonRole>superuser</SignonRole>"+
                            "      <CustId>"+
                            "        <SPName>superuser</SPName>"+
                            "      </CustId>"+
                            "      <Certificate>"+
                            "        <ContentType>Binary</ContentType>"+
                            "        <BinLength>4</BinLength>"+
                            "        <BinData>0x00</BinData>"+
                            "      </Certificate>"+
                            "      <GenSessKey>1</GenSessKey>"+
                            "    </SignonCert>"+
                            "    <ClientDt>2020-12-31T23:59:59.000000-00:00</ClientDt>"+
                            "    <CustLangPref>English</CustLangPref>"+
                            "    <ClientApp>"+
                            "      <Org>Quicken Corporation</Org>"+
                            "      <Name>Quicken 2020 Deluxe</Name>"+
                            "      <Version>99.99</Version>"+
                            "    </ClientApp>"+
                            "    <EU.Cur>0</EU.Cur>"+
                            "    <SuppressEcho>1</SuppressEcho>"+
                            "  </SignonRq>"+
                            "  <BankSvcRq>"+
                            "    <RqUID>3a64839c-17ff-40c8-8747-33683bb728b7</RqUID>"+
                            "		<DebitAddRq>"+
                            "			<RqUID>3a64839c-17ff-40c8-8747-33683bb728b7</RqUID>"+
                            "			 <MsgRqHdr>"+
                            "				<NetworkTrnInfo>"+
                            "					<NetworkOwner>ATM</NetworkOwner>"+
                            "					<TerminalId>22222</TerminalId>"+
                            "					<PostAddr>"+
                            "						<Addr1>7600 Leesburg Pike, Suite 430</Addr1>"+
                            "						<City>Falls Church</City>"+
                            "					 	<StateProv>VA</StateProv>"+
                            "					 	<PostalCode>22043</PostalCode>"+
                            "					 	<Country>US</Country>"+
                            "					</PostAddr>"+
                            "				</NetworkTrnInfo>"+
                            "				 <MsgAuthCode>"+
                            "					 <MacValue>977B9D6AFCCD7F9A304A480666066039</MacValue>"+
                            "				 </MsgAuthCode>"+
                            "			 </MsgRqHdr>"+
                            "			 <DebitInfo>"+
                            "			 <DebitAuthType>CashWithdrawal</DebitAuthType>"+
                            "			 <CompositeCurAmt>"+
                            "			 	<!--CompositeCurAmtId>1</CompositeCurAmtId-->"+
                            "			 	<CompositeCurAmtType>Debit</CompositeCurAmtType>"+
                            "			 	<CurAmt>"+
                            "			 		<Amt>100.00</Amt>"+
                            "			 		<CurCode>USD</CurCode>"+
                            "			 	</CurAmt>"+
                            "			 </CompositeCurAmt>"+
                            "			 <CompositeCurAmt>"+
                            "			 	<CompositeCurAmtId>2</CompositeCurAmtId>"+
                            "			 	<CompositeCurAmtType>Surcharge</CompositeCurAmtType>"+
                            "			 		<CurAmt>"+
                            "			 		<Amt>1.50</Amt>"+
                            "			 		<CurCode>USD</CurCode>"+
                            "			 	</CurAmt>"+
                            "			 </CompositeCurAmt>"+
                            "			 <CardAcctId>"+
                            "			 	<CardMagData>"+
                            "			 		<MagData2>1234567890123456=03120000000000000000</MagData2>"+
                            "			 	</CardMagData>"+
                            "			 	<AcctType>DDA</AcctType>"+
                            "			 </CardAcctId>"+
                            "			 </DebitInfo>"+
                            "		 </DebitAddRq>"+
                            "	</BankSvcRq>"+
                            "</IFX>";    

                    return xmlString;
        }

        public static String getIfxSample2(){
                    xmlString = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                            "<IFX xmlns=\"http://sourceforge.net/ifx-framework/ifx\">" +
                            "  <SignonRq Id=\"ID123456\">"+
                            "    <SignonCert>"+
                            "      <SignonRole>superuser</SignonRole>"+
                            "      <CustId>"+
                            "        <SPName>superuser</SPName>"+
                            "      </CustId>"+
                            "      <Certificate>"+
                            "        <ContentType>Binary</ContentType>"+
                            "        <BinLength>4</BinLength>"+
                            "        <BinData>0x00</BinData>"+
                            "      </Certificate>"+
                            "      <GenSessKey>1</GenSessKey>"+
                            "    </SignonCert>"+
                            "    <ClientDt>2020-12-31T23:59:59.000000-00:00</ClientDt>"+
                            "    <CustLangPref>English</CustLangPref>"+
                            "    <ClientApp>"+
                            "      <Org>Quicken Corporation</Org>"+
                            "      <Name>Quicken 2020 Deluxe</Name>"+
                            "      <Version>99.99</Version>"+
                            "    </ClientApp>"+
                            "    <EU.Cur>0</EU.Cur>"+
                            "    <SuppressEcho>1</SuppressEcho>"+
                            "  </SignonRq>"+
                            "  <BankSvcRq>"+
                            "    <RqUID>3a64839c-17ff-40c8-8747-33683bb728b7</RqUID>"+
                            "		<DebitAddRq>"+
                            "			<RqUID>3a64839c-17ff-40c8-8747-33683bb728b7</RqUID>"+
                            "			 <MsgRqHdr>"+
                            "				<NetworkTrnInfo>"+
                            "					<NetworkOwner>ATM</NetworkOwner>"+
                            "					<TerminalId>22222</TerminalId>"+
                            "					<PostAddr>"+
                            "						<Addr1>7600 Leesburg Pike, Suite 430</Addr1>"+
                            "						<City>Falls Church</City>"+
                            "					 	<StateProv>VA</StateProv>"+
                            "					 	<PostalCode>22043</PostalCode>"+
                            "					 	<Country>US</Country>"+
                            "					</PostAddr>"+
                            "				</NetworkTrnInfo>"+
                            "				 <MsgAuthCode>"+
                            "					 <MacValue>977B9D6AFCCD7F9A304A480666066039</MacValue>"+
                            "				 </MsgAuthCode>"+
                            "			 </MsgRqHdr>"+
                            "			 <DebitInfo>"+
                            "			 <DebitAuthType>CashWithdrawal</DebitAuthType>"+
                            "			 <CompositeCurAmt>"+
                            "			 	<!--CompositeCurAmtId>1</CompositeCurAmtId-->"+
                            "			 	<CompositeCurAmtType>Debit</CompositeCurAmtType>"+
                            "			 	<CurAmt>"+
                            "			 		<Amt>100.00</Amt>"+
                            "			 		<CurCode>USD</CurCode>"+
                            "			 	</CurAmt>"+
                            "			 </CompositeCurAmt>"+
                            "			 <CompositeCurAmt>"+
                            "			 	<CompositeCurAmtId>2</CompositeCurAmtId>"+
                            "			 	<CompositeCurAmtType>Surcharge</CompositeCurAmtType>"+
                            "			 		<CurAmt>"+
                            "			 		<Amt>1.50</Amt>"+
                            "			 		<CurCode>USD</CurCode>"+
                            "			 	</CurAmt>"+
                            "			 </CompositeCurAmt>"+
                            "			 <CardAcctId>"+
                            "			 	<CardMagData>"+
                            "			 		<MagData2>1234567890123456=03120000000000000000</MagData2>"+
                            "			 	</CardMagData>"+
                            "			 	<AcctType>DDA</AcctType>"+
                            "			 </CardAcctId>"+
                            "			 </DebitInfo>"+
                            "		 </DebitAddRq>"+
                            "	</BankSvcRq>"+
                            "</IFX>";    

                    return xmlString;
        }
    
	public static void main(String[] args) {

		try{
	        	        
	        //InputStream istream = new FileInputStream("cfg/IFX_Debit_Add_Request.xml");

                IFXMessage ifxMsg = new IFXMessage(getIfxSample());
                
                //IFXMessage ifxMsg = new IFXMessage(); 
                
                System.out.println("old:" + (String)ifxMsg.getElement("BankSvcRq[0].DebitAddRq[0].DebitInfo.CompositeCurAmt[0].CompositeCurAmtId"));
	        ifxMsg.setElement("BankSvcRq[0].DebitAddRq[0].DebitInfo.CompositeCurAmt[0].CompositeCurAmtId", "5");
	        System.out.println("new:" + (String)ifxMsg.getElement("BankSvcRq[0].DebitAddRq[0].DebitInfo.CompositeCurAmt[0].CompositeCurAmtId"));

                System.out.println("old:" + (String)ifxMsg.getElement("BankSvcRq[0].DebitAddRq[0].DebitInfo.CompositeCurAmt[2].CompositeCurAmtType"));
	        ifxMsg.setElement("BankSvcRq[0].DebitAddRq[0].DebitInfo.CompositeCurAmt[2].CompositeCurAmtType", "Surcharge");
	        System.out.println("new:" + (String)ifxMsg.getElement("BankSvcRq[0].DebitAddRq[0].DebitInfo.CompositeCurAmt[2].CompositeCurAmtType"));
                
                System.out.println("old:" + (String)ifxMsg.getElement("BankSvcRq[0].DebitAddRq[0].DebitInfo.CompositeCurAmt[2].CurAmt.Amt"));
	        ifxMsg.setElement("BankSvcRq[0].DebitAddRq[0].DebitInfo.CompositeCurAmt[2].CurAmt.Amt", new Float(250.00));
	        System.out.println("new:" + (String)ifxMsg.getElement("BankSvcRq[0].DebitAddRq[0].DebitInfo.CompositeCurAmt[2].CurAmt.Amt"));

	        System.out.println("old:" + (String)ifxMsg.getElement("BankSvcRq[0].DebitAddRq[0].DebitInfo.CompositeCurAmt[0].CurAmt.Amt"));
	        ifxMsg.setElement("BankSvcRq[0].DebitAddRq[0].DebitInfo.CompositeCurAmt[0].CurAmt.Amt", new Float(250.00));
	        System.out.println("new:" + (String)ifxMsg.getElement("BankSvcRq[0].DebitAddRq[0].DebitInfo.CompositeCurAmt[0].CurAmt.Amt"));
                
	        System.out.println("old:" + (String)ifxMsg.getElement("BankSvcRq[0].DebitAddRq[0].DebitInfo.DebitAuthType"));
	        ifxMsg.setElement("BankSvcRq[0].DebitAddRq[0].DebitInfo.DebitAuthType","Deposit");
	        System.out.println("new:" + (String)ifxMsg.getElement("BankSvcRq[0].DebitAddRq[0].DebitInfo.DebitAuthType"));

	        //ifxMsg.write(new FileOutputStream("cfg/IFX_Debit_Add_Request_Out.xml"));
                System.out.println(ifxMsg.toString());
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
