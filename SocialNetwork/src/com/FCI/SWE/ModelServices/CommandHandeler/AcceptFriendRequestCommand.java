package com.FCI.SWE.ModelServices.CommandHandeler;

public class AcceptFriendRequestCommand implements ICommand {
	FriendRequestReceiver Receiver;

	/**
	 * This function calls the acceptFriendService
	 * @param ID
	 *            Notification ID
	 * @return String status
	 */
	@Override
	public String execute(String ID) {
		// TODO Auto-generated method stub
		Receiver = new FriendRequestReceiver(ID);
		return Receiver.acceptFriendService();

	}

}
