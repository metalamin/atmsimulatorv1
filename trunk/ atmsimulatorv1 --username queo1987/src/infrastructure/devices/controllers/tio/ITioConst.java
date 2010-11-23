/*
 * ITioConst.java
 *
 */

package infrastructure.devices.controllers.tio;

/**
 * @author Arya Baher
 * Constantes utilizadas por el controlador del teclado de PIN
 */
public interface ITioConst {
    
    // tio controller configuration parameters
    public static String TIO_FLUSH              =  "TIO_FLUSH";
    public static String TIO_AUTOEND            =  "TIO_AUTOEND";
    public static String TIO_DISPLAY_LIGHT_ON   =  "TIO_DISPLAY_LIGHT_ON";
    public static String TIO_MODE               =  "TIO_MODE";
    public static String TIO_NUM_OF_CHARS       =  "TIO_NUM_OF_CHARS";    

    // tio controller event's parameters
    public static final String TIO_THROWER = "TEXT_IN_OUT";
    public static final String TIO_EXCEPTION_TYPE = "TIO_EXCEPTION_TYPE";
    public static final String TIO_STATUS_TYPE = "TIO_STATUS_TYPE";
    public static final String TIO_BEEP_OFF = "TIO_BEEP_OFF";
    public static final String TIO_BEEP_KEYPRESS = "TIO_BEEP_KEYPRESS";
    public static final String TIO_BEEP_CONTINUOUS = "TIO_BEEP_CONTINUOUS";
    public static final String TIO_BEEP_WARNING = "TIO_BEEP_WARNING";
    public static final String TIO_BEEP_ERROR = "TIO_BEEP_ERROR";
    public static final String TIO_BEEP_CRITICAL = "TIO_BEEP_CRITICAL";
    public static final String TIO_LED_OFF = "TIO_LED_OFF";
    public static final String TIO_LED_CONTINUOUS = "TIO_LED_CONTINUOUS";
    public static final String TIO_LED_SLOWFLASH = "TIO_LED_SLOWFLASH";
    public static final String TIO_LED_MEDIUMFLASH = "TIO_LED_MEDIUMFLASH";
    public static final String TIO_LED_QUICKFLASH = "TIO_LED_QUICKFLASH";
    public static final String TIO_LED_ERROR = "TIO_LED_ERROR";
    public static final String TIO_LED_WARNING = "TIO_LED_WARNING";
    public static final String TIO_LED_ONLINE = "TIO_LED_ONLINE";
    public static final String TIO_LED_OFFLINE = "TIO_LED_OFFLINE";
    public static final String TIO_LED_NORMAL = "TIO_LED_NORMAL";
    public static final String TIO_LED_PAPERLOW = "TIO_LED_PAPERLOW";
    public static final String TIO_LED_PAPEREMPTY = "TIO_LED_PAPEREMPTY";
    public static final String TIO_LED_PAPERJAM = "TIO_LED_PAPERJAM";
    public static final String TIO_LED_TONERLOW = "TIO_LED_TONERLOW";
    public static final String TIO_LED_TONEREMPTY = "TIO_LED_TONEREMPTY";
    public static final String TIO_POS_RELATIVE = "TIO_POS_RELATIVE";
    public static final String TIO_POS_ABSOLUTE = "TIO_POS_ABSOLUTE";
    public static final String TIO_TEXT_NORMAL = "TIO_TEXT_NORMAL";
    public static final String TIO_TEXT_UNDERLINED = "TIO_TEXT_UNDERLINED";
    public static final String TIO_TEXT_INVERTED = "TIO_TEXT_INVERTED";
    public static final String TIO_TEXT_FLASH = "TIO_TEXT_FLASH";
    public static final String TIO_ECHO_TEXT = "TIO_ECHO_TEXT";
    public static final String TIO_ECHO_INVISIBLE = "TIO_ECHO_INVISIBLE";
    public static final String TIO_ECHO_PASSWORD = "TIO_ECHO_PASSWORD";
    public static final String TIO_KEY_NUMERIC = "TIO_KEY_NUMERIC";
    public static final String TIO_KEY_HEXADECIMAL = "TIO_KEY_HEXADECIMAL";
    public static final String TIO_KEY_ALPHANUMERIC = "TIO_KEY_ALPHANUMERIC";
    public static final String TIO_KEY_FUNCTION = "TIO_KEY_FUNCTION";
    public static final String E_TIO_BEEP = "E_TIO_BEEP";
    public static final String E_TIO_LIGHT = "E_TIO_LIGHT";
    public static final String E_TIO_LED = "E_TIO_LED";
    public static final String E_TIO_CLEAR = "E_TIO_CLEAR";
    public static final String E_TIO_DISPLAY = "E_TIO_DISPLAY";
    public static final String E_TIO_READ = "E_TIO_READ";
    public static final String O_TIO_BEEP = "O_TIO_BEEP";
    public static final String O_TIO_LIGHT = "O_TIO_LIGHT";
    public static final String O_TIO_LED = "O_TIO_LED";
    public static final String O_TIO_CLEAR = "O_TIO_CLEAR";
    public static final String O_TIO_DISPLAY = "O_TIO_DISPLAY";
    public static final String O_TIO_READ = "O_TIO_READ";
    public static final String O_TIO_READ_KEY = "O_TIO_READ_KEY";
    public static final String S_TIO_STATUS_CHANGED = "S_TIO_STATUS_CHANGED";
    public static final String TIO_BEEP_EXCLAMATION = "TIO_BEEP_EXCLAMATION";
    public static final String TIO_KEY_0 = "TIO_KEY_0";
    public static final String TIO_KEY_1 = "TIO_KEY_1";
    public static final String TIO_KEY_2 = "TIO_KEY_2";
    public static final String TIO_KEY_3 = "TIO_KEY_3";
    public static final String TIO_KEY_4 = "TIO_KEY_4";
    public static final String TIO_KEY_5 = "TIO_KEY_5";
    public static final String TIO_KEY_6 = "TIO_KEY_6";
    public static final String TIO_KEY_7 = "TIO_KEY_7";
    public static final String TIO_KEY_8 = "TIO_KEY_8";
    public static final String TIO_KEY_9 = "TIO_KEY_9";
    public static final String TIO_KEY_A = "TIO_KEY_10";
    public static final String TIO_KEY_B = "TIO_KEY_11";
    public static final String TIO_KEY_C = "TIO_KEY_12";
    public static final String TIO_KEY_D = "TIO_KEY_13";
    public static final String TIO_KEY_E = "TIO_KEY_14";
    public static final String TIO_KEY_F = "TIO_KEY_15";
    public static final String TIO_KEY_DOT = "TIO_KEY_DOT";
    public static final String TIO_KEY_COMMA = "TIO_KEY_COMMA";
    public static final String TIO_KEY_SEMICOLON = "TIO_KEY_SEMICOLON";
    public static final String TIO_KEY_FENCE = "TIO_KEY_FENCE";
    public static final String TIO_KEY_MULTI = "TIO_KEY_MULTI";
    public static final String TIO_KEY_SLASH = "TIO_KEY_SLASH";
    public static final String TIO_KEY_PLUS = "TIO_KEY_PLUS";
    public static final String TIO_KEY_MINUS = "TIO_KEY_MINUS";
    public static final String TIO_KEY_DELETE = "TIO_KEY_DELETE";
    public static final String TIO_KEY_CANCEL = "TIO_KEY_CANCEL";
    public static final String TIO_KEY_ENTER = "TIO_KEY_ENTER";
    public static final String TIO_KEY_F1 = "TIO_KEY_F1";
    public static final String TIO_KEY_F2 = "TIO_KEY_F2";
    public static final String TIO_KEY_F3 = "TIO_KEY_F3";
    public static final String TIO_KEY_F4 = "TIO_KEY_F4";
    public static final String TIO_KEY_F5 = "TIO_KEY_F5";
    public static final String TIO_KEY_F6 = "TIO_KEY_F6";
    public static final String TIO_KEY_F7 = "TIO_KEY_F7";
    public static final String TIO_KEY_F8 = "TIO_KEY_F8";
    public static final String TIO_KEY_F9 = "TIO_KEY_F9";
    public static final String TIO_KEY_F10 = "TIO_KEY_F10";
}
