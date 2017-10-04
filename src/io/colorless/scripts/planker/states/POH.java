package io.colorless.scripts.planker.states;

import org.osbot.rs07.script.Script;
import org.osbot.rs07.utility.ConditionalSleep;

import static org.osbot.rs07.script.MethodProvider.random;

public class POH
{
    private Script script;
    public POH(Script script)
    {
        this.script = script;
    }

    private ConditionalSleep waitTP = new ConditionalSleep(2500)
    {
        @Override
        public boolean condition() throws InterruptedException
        {
            return !script.myPlayer().isAnimating();
        }
    };

    public void teleportPOH() throws InterruptedException
    {
        script.log("Teleporting to POH");
        script.getInventory().getItem("Teleport to house").interact("Break");
        waitTP.sleep();
        script.sleep(random(500,750));
    }

}
