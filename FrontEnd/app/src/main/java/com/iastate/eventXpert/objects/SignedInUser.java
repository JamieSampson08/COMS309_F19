package com.iastate.eventXpert.objects;

public class SignedInUser {
	private User user;

	private static SignedInUser signedInUser;

	private SignedInUser() {
		user = null;
		signedInUser = null;
	}

	public static SignedInUser getSignedInUser() {
		if (signedInUser == null) {
			signedInUser = new SignedInUser();
		}

		return signedInUser;
	}

	public void setUser(User u) {
		user = u;
	}

	public String getEmail() {
		return user != null ? user.getEmail() : "";
	}

	public String getName() {
		return user != null ? user.getFirstName() + " " + user.getLastName() : "EventXpert";
	}
}
