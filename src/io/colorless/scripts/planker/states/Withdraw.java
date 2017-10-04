package io.colorless.scripts.planker.states;

import org.osbot.rs07.script.Script;
import org.osbot.rs07.utility.ConditionalSleep;

public class Withdraw
{
    private Script script;
    public Withdraw(Script script)
    {
        this.script = script;
    }

    private ConditionalSleep logsInventory = new ConditionalSleep(3000)
    {
        @Override
        public boolean condition() throws InterruptedException
        {
            return script.getInventory().contains("Oak logs");
        }
    };

    public void logs()
    {
        if (!script.getInventory().onlyContains("Coins", "Camelot teleport", "Teleport to house"))
        {
            script.log("Your inventory isn't ready!");
            script.getBank().depositAllExcept("Coins", "Camelot teleport", "Teleport to house");
            script.getBank().close();
        }
        else
        {
            script.log("Withdrawing logs!");
            script.getBank().withdraw("Oak logs", 25);
            script.log("Withdrew 25 Oak logs");

            logsInventory.sleep();
            script.getBank().close();
        }
    }
}
