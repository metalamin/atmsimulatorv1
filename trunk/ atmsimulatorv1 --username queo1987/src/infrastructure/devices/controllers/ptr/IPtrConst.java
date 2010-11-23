/*
 * IPtrConst.java
 *
 */

package infrastructure.devices.controllers.ptr;

/**
 * @author Arya Baher
 * Constantes utilizadas por el controlador del printer
 */
public interface IPtrConst {
    
    // ptr controller event's parameters
    public static final String PTR_THROWER = "PTR";
    public static final String PTR_EXCEPTION_TYPE = "PTR_EXCEPTION_TYPE";
    public static final String PTR_STATUS_TYPE = "PTR_STATUS_TYPE";
    public static final String O_PTR_PRINTED = "O_PTR_PRINTED";
    public static final String S_PTR_STATUS_CHANGED = "S_PTR_STATUS_CHANGED";
    public static final String TCKT_DELIMITER = "--------------------";
    public static final String E_PTR_NO_MEDIA_PRESENT = "E_PTR_NO_MEDIA_PRESENT";    
    public static final String E_PTR_MEDIA_OVERFLOW = "E_PTR_MEDIA_OVERFLOW";
    public static final String E_PTR_PAPEROUT = "E_PTR_PAPEROUT";    
    // ptr action's parameter
    public static final String PTR_DATA = "PTR_DATA";
}
