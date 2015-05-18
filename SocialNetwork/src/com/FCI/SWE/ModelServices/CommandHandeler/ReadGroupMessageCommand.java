package com.FCI.SWE.ModelServices.CommandHandeler;

public class ReadGroupMessageCommand implements ICommand {
	MessageReceiver receiver;
	/**
	 * This function calls the readGroupMessage Service
	 * @param ID
	 *            Notification ID
	 * @return String status
	 */
	@Override
	public String execute(String ID) {
		// TODO Auto-generated method stub
		receiver = new MessageReceiver();
		return receiver.readGroupMessage(ID);
		
	}

}
