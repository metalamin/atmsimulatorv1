package infrastructure.services.comm.message.ifx;

import infrastructure.services.comm.message.Message;
import infrastructure.services.comm.message.MessageFactory;
import infrastructure.services.comm.message.Packager;

/**
 * MessageFactory for IFX
 */
public class IFXMessageFactory implements MessageFactory
{
    public Message createMessage()
    {
        return new IFXMessage(getIfxSample());
    }
    
    public Packager createPackager()
    {
        return new IFXPackager();
    }


    public static String getIfxSample(){
        
        String xmlString="";
        
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
}
