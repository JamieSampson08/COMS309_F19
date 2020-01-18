package com.iastate.eventXpert.objects;

/**
 * Represents the relationship between a user registered for an event
 */
public class Registrant {
	private User associatedUser;
	private UserEvent associatedUserEvent;

	/**
	 * Builds a relationship between user and event
	 *
	 * @param associatedUser      user for the event
	 * @param associatedUserEvent event for the user
	 */
	public Registrant(User associatedUser, UserEvent associatedUserEvent) {
		this.associatedUser = associatedUser;
		this.associatedUserEvent = associatedUserEvent;
	}

	/**
	 * Returns User of registrant relationship
	 *
	 * @return User user
	 */
	public User getAssociatedUser() {
		return associatedUser;
	}

	/**
	 * Returns Event of registrant relationship
	 *
	 * @return Event event
	 */
	public UserEvent getAssociatedUserEvent() {
		return associatedUserEvent;
	}
}
