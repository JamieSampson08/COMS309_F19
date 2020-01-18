package eventXpert.controllers;

import eventXpert.entities.Mastery;
import eventXpert.errors.ResourceNotFoundException;
import eventXpert.repositories.MasteryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/masteries")
public class MasteryController {
	@Autowired
	private MasteryRepository masteryRepository;
	
	@GetMapping(path = "")
	public @ResponseBody
	Iterable<Mastery> getAllMasteries(@RequestParam(required = false) String points) {
		if (points != null) {
			return masteryRepository.findMasteryByPoints(Integer.parseInt(points));
		}
		
		return masteryRepository.findAll();
	}
	
	@GetMapping(path = "/{masteryId}")
	public Mastery getMastery(@PathVariable("masteryId") Integer id) {
		if (!masteryRepository.existsById(id)) {
			throw new ResourceNotFoundException();
		}
		
		return masteryRepository.findById(id).get();
	}
}
