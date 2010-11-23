/*
 * IMsdConst.java
 *
 */

package infrastructure.devices.controllers.msd;

/**
 * @author Arya Baher
 * Constantes utilizadas por el controlador del lector de banda magnética
 */
public interface IMsdConst {
    
    // msd controller configuration parameters
    public static String MSD_TRACK1_SELECTED    =  "MSD_TRACK1_SELECTED";
    public static String MSD_TRACK2_SELECTED    =  "MSD_TRACK2_SELECTED";
    public static String MSD_TRACK3_SELECTED    =  "MSD_TRACK3_SELECTED";

    // event context keys
    public static String MSD_TRACK1  =  "MSD_TRACK1";
    public static String MSD_TRACK2  =  "MSD_TRACK2";
    public static String MSD_TRACK3  =  "MSD_TRACK3";
        
    // msd controller event's parameters
    public static final String MSD_THROWER = "MAGSTRIPE";
    public static final String MSD_EXCEPTION_TYPE = "MSD_EXCEPTION_TYPE";
    public static final String MSD_STATUS_TYPE = "MSD_STATUS_TYPE";
    public static final String MSD_TYPE_SWIPE = "MSD_TYPE_SWIPE";
    public static final String MSD_TYPE_DIP = "MSD_TYPE_DIP";
    public static final String MSD_TYPE_MOTOR = "MSD_TYPE_MOTOR";
    public static final String S_MSD_MEDIA_STATUS = "S_MSD_MEDIA_STATUS";
    public static final String O_MSD_READDATA = "O_MSD_READDATA";
    public static final String E_MSD_READFAILURE = "E_MSD_READFAILURE";
    public static final String O_MSD_WRITEDATA = "O_MSD_WRITEDATA";
    public static final String E_MSD_WRITEFAILURE = "E_MSD_WRITEFAILURE";
    public static final String E_MSD_NOMEDIA = "E_MSD_NOMEDIA";
    public static final String E_MSD_INVALIDMEDIA = "E_MSD_INVALIDMEDIA";
    public static final String E_MSD_MEDIAJAMMED = "E_MSD_MEDIAJAMMED";
    public static final String E_MSD_SHUTTERFAIL = "E_MSD_SHUTTERFAIL";
    public static final String I_MSD_NO_MEDIA_PRESENT = "I_MSD_NO_MEDIA_PRESENT";
    public static final String I_MSD_MEDIA_INSERTED = "I_MSD_MEDIA_INSERTED";
    public static final String E_MSD_NOTSUPPORTEDTRACK = "E_MSD_NOTSUPPORTEDTRACK";
    public static final String E_MSD_NOTRACKS = "E_MSD_NOTRACKS";
    public static final String E_MSD_BADDATA = "E_MSD_BADDATA";
    public static final String S_MSD_BIN_STATUS = "S_MSD_BIN_STATUS";
    public static final String MSD_SECTYPE_NOTSUPPORTED = "MSD_SECTYPE_NOTSUPPORTED";
    public static final String MSD_SECTYPE_MMBOX = "MSD_SECTYPE_MMBOX";
    public static final String MSD_SECTYPE_CIM86 = "MSD_SECTYPE_CIM86";
    public static final String S_MSD_SEC_READY = "S_MSD_SEC_READY";
    public static final String S_MSD_SEC_NOTREADY = "S_MSD_SEC_NOTREADY";
    public static final String S_MSD_SEC_UNKNOWN = "S_MSD_SEC_UNKNOWN";
    public static final String S_MSD_SEC_STATUS = "S_MSD_SEC_STATUS";
    public static final String E_MSD_NOTSUPPORTEDCAP = "E_MSD_NOTSUPPORTEDCAP";
    public static final String E_MSD_PARITY = "E_MSD_PARITY";
    public static final String E_MSD_READ_EOF = "E_MSD_READ_EOF";
    public static final String E_MSD_NO_STRIPE = "E_MSD_NO_STRIPE";
    public static final String E_MSD_READ_OTHER = "E_MSD_READ_OTHER";
    public static final String MSD_SEC_NOCHECK = "MSD_SEC_NOCHECK";
    public static final String MSD_SEC_NOTREADY = "MSD_SEC_NOTREADY";
    public static final String MSD_SEC_SECFAIL = "MSD_SEC_SECFAIL";
    public static final String MSD_SEC_SECOK = "MSD_SEC_SECOK";
}
