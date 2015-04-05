package com.FCI.SWE.ModelServices;

public class ReadPersonalMessageCommand implements ICommand {
	NotificationsController Controller;
	@Override
	public String execute(String ID) {
		// TODO Auto-generated method stub
		Controller = new NotificationsController();
		return Controller.readPersonalMessage(ID);
		
	}

}
