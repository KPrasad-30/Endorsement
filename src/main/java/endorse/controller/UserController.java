package endorse.controller;

import endorse.dto.Endorsement;
import endorse.dto.Skill;
import endorse.dto.User;
import endorse.exception.InvalidIdsException;
import endorse.repository.EndorsementRepository;
import endorse.util.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import endorse.service.UserService;
import endorse.repository.UserRepository;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EndorsementRepository endorsementRepository;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        userService.registerUser(user);
        return new ResponseEntity<>("User registered successfully", HttpStatus.CREATED);
    }

    @PostMapping("/postEndorsement")
    public ResponseEntity<String> postEndorsement(@RequestBody Endorsement requestDTO) {
        try {
            Endorsement endorsement = userService.postEndorsement(requestDTO);
            int adjustedScore = endorsement.getAdjustedScore();
            String reason = endorsement.getReason();
            return ResponseEntity.ok("Adjusted endorsement score: " + adjustedScore + ". Reason: " + reason);
        } catch (InvalidIdsException e) {
            return new ResponseEntity<>(AppConstants.INVALID_IDS, HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping("/getEndorsement")
    public ResponseEntity<String> getEndorsement() {
        // Get endorsements from the service layer
        Map<String, List<String>> endorsements = userService.getEndorsements();

        // Format the endorsements into the desired string format
        StringBuilder responseBuilder = new StringBuilder();
        endorsements.forEach((skill, users) -> {
            responseBuilder.append(skill).append(":\n");
            users.forEach(user -> responseBuilder.append("- ").append(user).append("\n"));
        });

        String response = responseBuilder.toString();
        return ResponseEntity.ok(response);
    }
}