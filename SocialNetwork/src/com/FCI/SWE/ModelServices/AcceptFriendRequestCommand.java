package com.FCI.SWE.ModelServices;

public class AcceptFriendRequestCommand implements ICommand {
	FriendRequestReceiver Receiver;
	@Override
	public String execute(String ID) {
		// TODO Auto-generated method stub
		Receiver = new FriendRequestReceiver(ID);
		return Receiver.acceptFriendService();
		
	}

}
