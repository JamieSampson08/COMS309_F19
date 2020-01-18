package eventXpert.controllers;

import eventXpert.entities.Event;
import eventXpert.errors.BadRequestException;
import eventXpert.errors.ResourceNotFoundException;
import eventXpert.repositories.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

/**
 * This is a controller for urls who begin with /events
 */
@RestController
@RequestMapping(path = "/events")
public class EventController {
	/**
	 * An EventRepository that is autogenerated by Spring.
	 * eventRepository can be used to make CRUD operations to the database
	 */
	@Autowired
	private EventRepository eventRepository;
	
	/**
	 * Gets all events in database
	 *
	 * @return list of all events
	 */
	@GetMapping(path = "")
	public @ResponseBody
	Iterable<Event> getAllEvents(@RequestParam(required = false) String name, @RequestParam(required = false) String sort) {
		if (name != null) {
			if (sort != null) {
				return eventRepository.findByNameAndSort(name, new Sort(Sort.Direction.ASC, sort));
			}
			return eventRepository.findByName(name);
		}
		if (sort != null) {
			return eventRepository.findAll(new Sort(Sort.Direction.ASC, sort));
		}
		return eventRepository.findAll();
	}
	
	/**
	 * Adds a user to the database
	 *
	 * @param event to add to database
	 *              Example JSON:
	 *              {
	 *              "name": "name",
	 *              "location": "location",
	 *              "description": "description",
	 *              "startDateTime": "2000-10-23T01:30:00",
	 *              "endDateTime": "2000-10-23T01:45:00"
	 *              }
	 * @return event that was created
	 */
	@PostMapping(path = "")
	public Event addEvent(@RequestBody Event event) {
		validateData(event);
		return eventRepository.save(event);
	}
	
	/**
	 * Saves modified event data
	 *
	 * @param modifiedEvent the new event to be saved in the database
	 * @param eventId       the id of the event to update
	 * @return updated event that was saved in the database
	 */
	@PutMapping(path = "/{eventId}")
	public Event saveEvent(@RequestBody Event modifiedEvent, @PathVariable("eventId") Integer eventId) {
		Event event = eventRepository.findById(eventId).get();
		validateData(modifiedEvent);
		
		event.setDescription(modifiedEvent.getDescription());
		event.setEndDateTime(modifiedEvent.getEndDateTime());
		event.setStartDateTime(modifiedEvent.getStartDateTime());
		event.setLocation(modifiedEvent.getLocation());
		event.setName(modifiedEvent.getName());
		
		return eventRepository.save(event);
	}
	
	/**
	 * Returns an event based on its id
	 *
	 * @param id event id to search for in the database
	 * @return event with the given id
	 */
	@GetMapping(path = "/{id}")
	public @ResponseBody
	Event getEventById(@PathVariable("id") Integer id) {
		if (!eventRepository.existsById(id)) {
			throw new ResourceNotFoundException();
		}
		return eventRepository.findById(id).get();
	}
	
	/**
	 * Validate passed in event data and throws an error if the data is invalid
	 *
	 * @param event to have fields validated
	 */
	private void validateData(Event event) {
		if (event.getStartDateTime() == null || event.getEndDateTime() == null) {
			throw new BadRequestException("You must insert a start and end time");
		} else if (event.getStartDateTime().after(event.getEndDateTime())) {
			throw new BadRequestException("Your start time can not be after your end time");
		} else if (event.getName() == null || event.getName().length() > 100) {
			throw new BadRequestException("You must have a name with a length of less than 100 characters");
		} else if (event.getLocation() == null || event.getLocation().length() > 100) {
			throw new BadRequestException("You must have a location with a length of less than 100 characters");
		} else if (event.getDescription() == null || event.getDescription().length() > 1000) {
			throw new BadRequestException("You must have a description with a length of less than 1000 characters");
		}
	}
}
