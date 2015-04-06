package com.FCI.SWE.ModelServices.CommandHandeler;

public class ReadPersonalMessageCommand implements ICommand {
	MessageReceiver Receiver;
	@Override
	public String execute(String ID) {
		// TODO Auto-generated method stub
		Receiver = new MessageReceiver();
		return Receiver.readPersonalMessage(ID);
		
	}

}
