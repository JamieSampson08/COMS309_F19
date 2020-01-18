package com.iastate.eventXpert.interfaces;

import com.iastate.eventXpert.objects.User;

public interface UserView {
	void setMasteryText(String masteryText);

	void setMasteryImage(String imageName);

	void updateUser(User user);

	void enableEditing();

	void disableEditing(boolean showEditButton);
}
