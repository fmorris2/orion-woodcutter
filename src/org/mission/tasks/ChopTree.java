package org.mission.tasks;

import org.mission.OrionWoodcutter;
import org.mission.data.vars.OW_Vars;
import org.osbot.rs07.api.model.RS2Object;

import viking.api.Timing;
import viking.api.skills.woodcutting.enums.AxeType;
import viking.framework.task.Task;

/**
 * Created by Sphiinx on 12/28/2016.
 */
public class ChopTree extends Task<OrionWoodcutter> {

    public ChopTree(OrionWoodcutter mission) {
        super(mission);
    }

    @Override
    public boolean validate() {
        if (OW_Vars.get().is_upgrading_axe)
            return false;

        final RS2Object TREE = objects.closest(OW_Vars.get().chopping_location.getArea(), OW_Vars.get().tree_type.getTreeName());
        return TREE != null && !inventory.isFull() && (inventory.getAmount(AxeType.getItemIDs()) > 0 || equipment.getAmount(AxeType.getItemIDs()) > 0);
    }

    @Override
    public void execute() {
        final RS2Object TREE = objects.closest(OW_Vars.get().chopping_location.getArea(), OW_Vars.get().tree_type.getTreeName());
        if (TREE == null)
            return;

        if (myPlayer().getAnimation() != -1 || myPlayer().isMoving())
            return;

        if (TREE.isVisible()) {
            if (TREE.interact("Chop down"))
                Timing.waitCondition(() -> myPlayer().getAnimation() != -1, 150, random(2000, 2500));
        } else {
            if (walkUtils.walkToArea(TREE.getArea(3)))
                Timing.waitCondition(TREE::isVisible, 150, random(2000, 2500));
        }
    }

    @Override
    public String toString() {
        return "Chopping " + OW_Vars.get().tree_type;
    }

}

