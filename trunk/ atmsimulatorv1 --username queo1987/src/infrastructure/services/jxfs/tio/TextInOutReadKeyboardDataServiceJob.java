
package infrastructure.services.jxfs.tio;

import com.jxfs.events.JxfsException;
import com.jxfs.forum.support.IJxfsServiceJob;

public class TextInOutReadKeyboardDataServiceJob
    implements IJxfsServiceJob
{

    public TextInOutReadKeyboardDataServiceJob(TextInOutService tioService, int i, int j, int k, int l, int i1, int j1, 
            int k1, boolean flag, boolean flag1, boolean flag2, int l1)
    {
        cancel_val_ = true;
        service_ = tioService;
        numOfChars_ = i;
        mode_ = j;
        posX_ = k;
        posY_ = l;
        echoMode_ = i1;
        echoAttr_ = j1;
        keys_ = k1;
        cursor_ = flag;
        flush_ = flag1;
        autoEnd_ = flag2;
        control_id_ = l1;
    }

    public void execute()
        throws JxfsException
    {
        cancel_val_ = false;
        service_.readKeyboardDataInternal(this, numOfChars_, mode_, posX_, posY_, echoMode_, echoAttr_, keys_, cursor_, flush_, autoEnd_);
    }

    public boolean cancel()
    {
        return cancel_val_;
    }

    public int getControlId()
    {
        return control_id_;
    }

    public int getOperationID()
    {
        return operation_id;
    }

    public int getIdentificationID()
    {
        return identificationID_;
    }

    public void setIdentificationID(int i)
        throws JxfsException
    {
        identificationID_ = i;
    }

    private static final int operation_id = 8028;
    TextInOutService service_;
    int control_id_;
    int identificationID_;
    boolean cancel_val_;
    boolean cursor_;
    boolean flush_;
    boolean autoEnd_;
    int numOfChars_;
    int mode_;
    int posX_;
    int posY_;
    int echoMode_;
    int echoAttr_;
    int keys_;
}
