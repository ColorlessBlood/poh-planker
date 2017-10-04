package io.colorless.scripts.planker.states;

import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.api.ui.Tab;
import org.osbot.rs07.event.WalkingEvent;
import org.osbot.rs07.input.mouse.MainScreenTileDestination;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.utility.ConditionalSleep;

import static org.osbot.rs07.script.MethodProvider.random;


public class Servant
{
    private Script script;
    public Servant(Script script)
    {
        this.script = script;
    }

   // private NPC butler = script.getNpcs().closest("Demon butler");

    private ConditionalSleep waitDialogue = new ConditionalSleep(2500)
    {
        @Override
        public boolean condition() throws InterruptedException
        {
            return script.getDialogues().inDialogue();
        }
    };

    public void service() throws InterruptedException
    {

        //Walks to a valid location where the butler can 100% talk to you
        //Calls the butler
        //Will repeat last task and ONLY if you are doing 25x planks per trip!

        script.sleep(random(500,750));
        script.getWalking().walk(script.myPosition().translate(0, 2, 0));

        script.log("Walked to call location");
        script.sleep(random(500,750));

        script.getTabs().open(Tab.SETTINGS);
        script.log("Opening Settings Tab");

        script.getWidgets().interact(261, 76, "View House Options");
        script.log("Viewing House Options");

        script.getWidgets().interact(370, 15, "Call Servant");
        script.log("Called servant");

        waitDialogue.sleep();

        if (script.getDialogues().inDialogue())
        {
            script.log("You are in a dialogue");
            if (script.getDialogues().isPendingOption())
            {
                script.log("Waiting on selection");
                script.getDialogues().selectOption("Take to sawmill: 25 x Oak logs");
                script.sleep(random(500,750));
                script.getDialogues().clickContinue();
                script.sleep(random(500,750));
                script.getDialogues().selectOption("Yes");
                script.sleep(random(500,750));
                script.getDialogues().clickContinue();
                script.sleep(random(500,750));
            }
            else
            {
                script.log("You are not presented with the right dialogue!");
                script.log("Please do the task once by hand!");
                script.stop();
            }
        }
    }
}
