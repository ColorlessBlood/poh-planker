package io.colorless.scripts.planker;

import io.colorless.scripts.planker.states.POH;
import io.colorless.scripts.planker.states.Servant;
import io.colorless.scripts.planker.states.Withdraw;
import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.script.Script;

import org.osbot.rs07.script.ScriptManifest;
import org.osbot.rs07.utility.ConditionalSleep;

import java.awt.*;

@ScriptManifest(name = "POH Planker11", author = "Colorless", version = 1.0, info = "", logo = "")

public class Main extends Script
{
    private Servant servant = new Servant(this);
    private POH house = new POH(this);
    private Withdraw withdraw = new Withdraw(this);

    public enum State
    {
        CAMELOT,
        POH,
        SERVANT,
        WITHDRAW,
        BANK,
        EXIT
    }

    private ConditionalSleep waitTP = new ConditionalSleep(2500)
    {
        @Override
        public boolean condition() throws InterruptedException
        {
            return !myPlayer().isAnimating();
        }
    };

    private State getState()
    {
        boolean essentials = getInventory().contains("Coins", "Camelot teleport", "Teleport to house");
        boolean logs = getInventory().contains("Oak logs");
        Position bankNE = new Position(2760, 3481, 0);
        Position bankSW = new Position(2754, 3477, 0);
        Area bank = new Area(bankSW, bankNE);

        if (essentials)
        {
            if (logs)
            {
                if (getMap().isInHouse() && myPlayer().isVisible())
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
                    return State.CAMELOT;
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
        log("Starting");

        //Code here will execute before the loop is started
    }

    @Override
    public void onExit()
    {
        log("Exiting");
        //Code here will execute after the script ends
    }

    @Override
    public int onLoop() throws InterruptedException
    {

        switch (getState())
        {
            case SERVANT:
                servant.service();
                break;

            case POH:
                house.teleportPOH();
                break;

            case WITHDRAW:
                withdraw.logs();
                break;

            case BANK:
                getBank().open();
                break;

            case CAMELOT:
                log("Teleporting to Camelot");
                getInventory().getItem("Camelot teleport").interact("Break");
                waitTP.sleep();
                sleep(random(500, 750));
                break;
        }
        return 100;
    }

    @Override
    public void onPaint(Graphics2D g)
    {


    }

}