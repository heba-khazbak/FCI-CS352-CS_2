package com.FCI.SWE.ModelServices.CommandHandeler;

public interface ICommand {
	
	/**
	 * This function should be implemented by derived classes
	 * @param ID
	 *            Notification ID
	 * @return String status
	 */
	public String execute(String ID);

}
