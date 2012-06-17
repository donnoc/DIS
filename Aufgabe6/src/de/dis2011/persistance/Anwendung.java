package de.dis2011.persistance;

import java.util.Random;
import de.dis2011.persistance.core.*;

public class Anwendung
{
    private static Koordinator _ko      = null;
    private static String      _userDir = "./";

    public static void init()
    {
        if (_ko == null)
        {
            _ko = Koordinator.getInstance();
            _ko.setUserDir(_userDir);
        }
    }

    /**
     * @param args
     */
    public static void main(String[] args)
    {
        init();
        ResourceManager rm1 = new ResourceManager("Manager1", _userDir);
        ResourceManager rm2 = new ResourceManager("Manager2", _userDir);
        Random random = new Random();

        // bis zu 100 transaktionen
        for (int i = 0; i <= random.nextInt(100); i++)
        {
            int taid = _ko.begin();
            // bis zu 10 writes in der transaktion
            for (int j = 0; j <= random.nextInt(10); j++)
            {
                int pid = random.nextInt(50);
                int welcherManager = random.nextInt(2);
                //System.out.println(welcherManager + " " + taid);
                String data = String.format("taid=%d, ResouceManager=%d, Page=%d", taid, welcherManager, pid);
                if (welcherManager == 0)
                {
                    rm1.write(taid, pid, data);
                }
                else if (welcherManager == 1)
                {
                    rm2.write(taid, pid, data);
                }
            }

            // jede 10. transaktion wird rollback
            if (random.nextInt(10) == 0)
                _ko.rollback(taid);
            else
                _ko.commit(taid);
        }
    }
}
