package com.FCI.SWE.ModelServices.CommandHandeler;

public class AcceptFriendRequestCommand implements ICommand {
	FriendRequestReceiver receiver;

	/**
	 * This function calls the acceptFriendService
	 * @param ID
	 *            Notification ID
	 * @return String status
	 */
	@Override
	public String execute(String ID) {
		// TODO Auto-generated method stub
		receiver = new FriendRequestReceiver(ID);
		return receiver.acceptFriendService();

	}

}
