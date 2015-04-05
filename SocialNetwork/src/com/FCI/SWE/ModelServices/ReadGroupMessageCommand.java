package com.FCI.SWE.ModelServices;

public class ReadGroupMessageCommand implements ICommand {
	MessageReceiver Receiver;
	@Override
	public String execute(String ID) {
		// TODO Auto-generated method stub
		Receiver = new MessageReceiver();
		return Receiver.readGroupMessage(ID);
		
	}

}
