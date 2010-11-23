
package infrastructure.services.jxfs.msd;

import java.util.Enumeration;
import java.util.Vector;

public class MagneticStripeHardwareSimulator
{

    public MagneticStripeHardwareSimulator()
    {
        dataRead = new byte[3][];
    }

    public boolean open()
    {
        boolean flag = true;
        return flag;
    }

    public boolean close()
    {
        boolean flag = true;
        return flag;
    }

    public int writeData(Vector vector)
    {
        boolean flag = true;
        int i = 0;
        for(Enumeration enumeration = vector.elements(); enumeration.hasMoreElements();)
        {
            byte abyte0[] = (byte[])enumeration.nextElement();
            if(abyte0 != null)
                data[i] = abyte0;
            i++;
        }

        return 0;
    }

    public int readData(boolean flag, boolean flag1, boolean flag2)
    {
        if(flag)
            dataRead[0] = data[0];
        else
            dataRead[0] = null;
        if(flag1)
            dataRead[1] = data[1];
        else
            dataRead[1] = null;
        if(flag2)
            dataRead[2] = data[2];
        else
            dataRead[2] = null;
        return 0;
    }

    public byte[][] getDataRead()
    {
        return dataRead;
    }

    public boolean waitForCardInsert()
    {
        if(waitingForCardInsert > 9)
        {
            waitingForCardInsert = 0;
            return true;
        } else
        {
            waitingForCardInsert++;
            return false;
        }
    }

    public boolean waitUntilCardHasBeenTaken()
    {
        if(waitingForCardTaken > 9)
        {
            waitingForCardTaken = 0;
            return true;
        } else
        {
            waitingForCardTaken++;
            return false;
        }
    }

    public int[] getFirmwareVersion()
    {
        return (new int[] {
            2, 3, 2
        });
    }

    public boolean updateFirmware(byte abyte0[])
    {
        return true;
    }

    public byte data[][] = {
        "ksadjfkj".getBytes(), "231j4kj32g4g32".getBytes(), "898fduj3hj3".getBytes()
    };
    public byte dataRead[][];
    private static int waitingForCardInsert = 0;
    private static int waitingForCardTaken = 0;

}
