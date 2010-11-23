/*
 * DeviceException.java
 *
 * Created on 16 de octubre de 2005, 12:39 PM
 *
 */

package infrastructure.devices.events;

/**
 * @author Arya Baher
 * General exception for devices
 */
public class DeviceException extends Exception
{

    public DeviceException(int i)
    {
        this(i, 0, "" + i, null);
    }

    public DeviceException(int i, int j)
    {
        this(i, j, "" + i + ", " + j, null);
    }

    public DeviceException(int i, int j, String s)
    {
        this(i, j, s, null);
    }

    public DeviceException(int i, int j, String s, Exception exception)
    {
        super(s);
        errorCode = i;
        errorCodeExtended = j;
        origException = exception;
    }

    public DeviceException(int i, String s)
    {
        this(i, 0, s, null);
    }

    public DeviceException(Exception exception)
    {
        this(0, 0, exception.getMessage(), exception);
    }
    
    public DeviceException(int i, String s, Exception exception)
    {
        this(i, 0, s, exception);
    }

    public int getErrorCode()
    {
        return errorCode;
    }

    public int getErrorCodeExtended()
    {
        return errorCodeExtended;
    }

    public Exception getOrigException()
    {
        return origException;
    }

    public String toString()
    {
        return "DeviceException(e=" + errorCode + ",xe=" + errorCodeExtended + "," + super.toString() + ")@" + Integer.toHexString(hashCode());
    }

    protected int errorCode;
    protected int errorCodeExtended;
    private Exception origException;
}
