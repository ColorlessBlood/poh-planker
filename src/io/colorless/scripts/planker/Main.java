package io.colorless.scripts.planker;

import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.script.Script;

import org.osbot.rs07.script.ScriptManifest;

import java.awt.*;

@ScriptManifest(name = "POH Planker", author = "Colorless", version = 1.0, info = "", logo = "")

public class Main extends Script
{
    public enum State
    {
        CAMELOT,
        POH,
        SERVANT,
        WITHDRAW,
        BANK,
        EXIT
    }

    private final boolean ESSENTIALS = getInventory().contains("Coins", "Camelot teleport", "Teleport to house");
    private final boolean LOGS = getInventory().contains("Oak logs");
    private final Position bankNE = new Position(2760, 3481, 0);
    private final Position bankSW = new Position(2754, 3477, 0);
    private final Area bank = new Area(bankSW, bankNE);

    public State getState()
    {
        if (ESSENTIALS)
        {
            if (LOGS)
            {
                if (getMap().isInHouse())
                {
                    return State.SERVANT;
                }
                else
                {
                    return State.POH;
                }
            }
            else
            {
                if (bank.contains(myPlayer()))
                {
                    if (getBank().isOpen())
                    {
                        if (getBank().contains("Oak logs"))
                        {
                            return State.WITHDRAW;
                        }
                        else
                        {
                            return State.EXIT;
                        }
                    }
                    else
                    {
                        return State.BANK;
                    }
                }
                else
                {
                    return State.POH;
                }
            }
        }
        else
        {
            return State.EXIT;
        }
    }

    @Override
    public void onStart()
    {
        //Code here will execute before the loop is started
    }

    @Override
    public void onExit()
    {
        //Code here will execute after the script ends
    }

    @Override
    public int onLoop()
    {
        return 100; //The amount of time in milliseconds before the loop starts over
    }

    @Override
    public void onPaint(Graphics2D g)
    {

        //This is where you will put your code for paint(s)

    }

}