package eventXpert.controllers;

import eventXpert.entities.User;
import eventXpert.errors.ResourceNotFoundException;
import eventXpert.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import eventXpert.storage.StorageService;

@RestController
@RequestMapping(path = "/files")
public class FileUploadController {
	private final StorageService storageService;
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	public FileUploadController(StorageService storageService) {
		this.storageService = storageService;
	}
	
	@PostMapping(path = "/{userId}")
	public User handleFileUpload(@PathVariable("userId") Integer userId, @RequestParam("file") MultipartFile file) {
		if (!userRepository.existsById(userId)) {
			throw new ResourceNotFoundException();
		}
		User user = userRepository.findById(userId).get();
		user.setProfileFileName(file.getResource().getFilename());
		
		storageService.store(file);
		
		return userRepository.save(user);
	}
}
