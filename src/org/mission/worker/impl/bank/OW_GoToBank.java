package org.mission.worker.impl.bank;

import org.mission.OrionWoodcutter;
import org.mission.worker.OWWorker;

public class OW_GoToBank extends OWWorker
{
	private final boolean ACCEPT_DEPOSIT_BOX;
	
	public OW_GoToBank(OrionWoodcutter mission, boolean depositBox)
	{
		super(mission);
		ACCEPT_DEPOSIT_BOX = depositBox;
	}

	@Override
	public void work()
	{
		script.log(this, false, "Go to bank");
	}

}