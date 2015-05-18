package com.FCI.SWE.ModelServices.CommandHandeler;

public class ReadPersonalMessageCommand implements ICommand {
	MessageReceiver receiver;
	/**
	 * This function calls the readPersonalMessage Service
	 * @param ID
	 *            Notification ID
	 * @return String status
	 */
	@Override
	public String execute(String ID) {
		// TODO Auto-generated method stub
		receiver = new MessageReceiver();
		return receiver.readPersonalMessage(ID);
		
	}

}
