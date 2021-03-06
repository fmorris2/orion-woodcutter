package org.mission.worker.impl.bank;

import java.util.Map;

import org.mission.OrionWoodcutter;
import org.mission.worker.OWWorker;

import viking.api.skills.woodcutting.enums.AxeType;

public class OW_GetAxeUpgrade extends OWWorker
{
	private AxeType upgrade;
	
	public OW_GetAxeUpgrade(OrionWoodcutter mission)
	{
		super(mission);
	}

	@Override
	public void work()
	{
		script.log(this, false, "Get axe upgrade: " + upgrade);
		if(!bank.isOpen())
			bankUtils.open();
		else if(!inventory.contains(upgrade.getItemID()))
		{
			if(!inventory.isEmpty())
				bank.depositAll();
			else
				bank.withdraw(upgrade.getItemID(), 1);
		}
	}
	
	public boolean hasUpgradeInBank()
	{
		Map<Integer, Integer> bankCache = mission.getScript().BANK_CACHE.get();
		
		AxeType[] axes = AxeType.values();
		AxeType current = mission.currentAxe;
		AxeType approp = woodcutting.currentAppropriateAxe();
		for(int i = axes.length - 1; i >= 0; i--)
		{
			if(axes[i].ordinal() > approp.ordinal())
				continue;
			
			if(bankCache.containsKey(axes[i].getItemID())
					&& (current == null || current.ordinal() < axes[i].ordinal()))
			{
				upgrade = axes[i];
				return true;
			}
		}	
		
		return false;
	}

}
