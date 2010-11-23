
package infrastructure.services.jxfs.ptr;

import com.jxfs.events.JxfsException;
import com.jxfs.forum.i18n.LocalizedErrorMessages;
import com.jxfs.forum.support.IJxfsServiceJob;

public abstract class PrinterServiceJob
    implements IJxfsServiceJob
{

    public PrinterServiceJob(int i, int j)
    {
        identificationSet = false;
        controlId = i;
        operationID = j;
    }

    private boolean isIdentificationSet()
    {
        return identificationSet;
    }

    private void setIdentificationSet(boolean flag)
    {
        identificationSet = flag;
    }

    public int getControlId()
    {
        return controlId;
    }

    public int getOperationID()
    {
        return operationID;
    }

    public int getIdentificationID()
    {
        return identificationID;
    }

    public synchronized void setIdentificationID(int i)
        throws JxfsException
    {
        if(isIdentificationSet())
        {
            JxfsException jxfsexception = new JxfsException(1014, LocalizedErrorMessages.getFormattedMessage("JXFS007", new Object[] {
                "Identification ID"
            }));
            throw jxfsexception;
        } else
        {
            identificationID = i;
            setIdentificationSet(true);
            return;
        }
    }

    protected abstract String getLogOrigin();

    public abstract boolean cancel();

    public abstract void execute()
        throws JxfsException;

    private final int controlId;
    private final int operationID;
    private int identificationID;
    private boolean identificationSet;
}
