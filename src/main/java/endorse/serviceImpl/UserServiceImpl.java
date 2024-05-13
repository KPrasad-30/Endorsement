package endorse.serviceImpl;

import endorse.dto.Endorsement;
import endorse.dto.Skill;
import endorse.dto.User;
import endorse.exception.InvalidIdsException;
import endorse.repository.EndorsementRepository;
import endorse.util.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import endorse.repository.UserRepository;
import endorse.service.UserService;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EndorsementRepository endorsementRepository;
    @Override
    @Transactional
    public User registerUser(User user) {
        // Save the user to get the generated ID
        User savedUser = userRepository.save(user);

        // Iterate over the skills and associate them with the saved user
        for (Skill skill : user.getSkills()) {
            // Set the user for each skill
            skill.setUser(savedUser);
        }

        // Save the updated user with associated skills
        userRepository.save(savedUser);

        return savedUser;
    }

    @Override
    public double getExperienceForSkill(List<Skill> skills, String skillName) {
        for (Skill skill : skills) {
            if (skill.getName().equalsIgnoreCase(skillName)) {
                return skill.getExperience();
            }
        }
        return 0; // Return 0 if skill not found
    }
    @Override
    public Map<String, List<String>> getEndorsements() {
        // Fetch endorsements from the database
        List<Endorsement> endorsements = endorsementRepository.findAll();

        // Group endorsements by skill
        Map<String, List<String>> endorsementsMap = new HashMap<>();
        endorsements.forEach(endorsement -> {
            String skill = endorsement.getSkill();
            String revieweeUserName = endorsement.getReviewee().getName(); // Retrieve the name of the reviewee user
            String adjustmentReason = getAdjustmentReason(endorsement);
            String formattedEndorsement = String.format("%s - %d (%d) - %s",
                    revieweeUserName,
                    endorsement.getActualScore(),
                    endorsement.getAdjustedScore(),
                    adjustmentReason); // Include the adjustment reason here
            endorsementsMap.computeIfAbsent(skill, k -> new ArrayList<>()).add(formattedEndorsement);
        });

        return endorsementsMap;
    }


    @Override
    public Endorsement postEndorsement(Endorsement requestDTO) {
        User reviewee = userRepository.findById(requestDTO.getReviewee().getId()).orElse(null);
        User reviewer = userRepository.findById(requestDTO.getReviewer().getId()).orElse(null);

        if (reviewee == null || reviewer == null) {
            throw new InvalidIdsException(AppConstants.INVALID_IDS);
        }

        // Calculate the adjusted score
        int adjustedScore = calculateAdjustedScore(requestDTO, reviewee, reviewer);

        // Log the adjusted score for debugging
        System.out.println("Adjusted Score (Before Saving): " + adjustedScore);

        // Create the endorsement object
        Endorsement endorsement = new Endorsement();
        endorsement.setReviewee(reviewee);
        endorsement.setReviewer(reviewer);
        endorsement.setSkill(requestDTO.getSkill());
        endorsement.setActualScore(requestDTO.getActualScore());
        endorsement.setCoworker(requestDTO.getCoworker());
        endorsement.setAdjustedScore(adjustedScore);
        endorsement.setReason(getAdjustmentReason(endorsement)); // Pass the endorsement object here

        // Save the endorsement object
        Endorsement savedEndorsement = endorsementRepository.save(endorsement);

        // Log the saved endorsement for debugging
        System.out.println("Saved Endorsement: " + savedEndorsement);

        return savedEndorsement;
    }


    private int calculateAdjustedScore(Endorsement requestDTO, User reviewee, User reviewer) {
        // Extract skills and experience from user profiles
        List<Skill> revieweeSkills = reviewee.getSkills();
        List<Skill> reviewerSkills = reviewer.getSkills();
        double revieweeExperience = getExperienceForSkill(revieweeSkills, requestDTO.getSkill());
        double reviewerExperience = getExperienceForSkill(reviewerSkills, requestDTO.getSkill());

        int actualScore = requestDTO.getActualScore(); // Store the actual score for logging

        int adjustedScore = actualScore; // Start with the actual score

        // Log the reviewer and reviewee experience for debugging
        System.out.println("Reviewer Experience: " + reviewerExperience);
        System.out.println("Reviewee Experience: " + revieweeExperience);

        // Adjust the score based on the conditions
        if (requestDTO.getCoworker() != 1) {
            if (reviewerExperience <= 0) {
                // Reviewer has no experience in the skill being endorsed, normalize more
                adjustedScore = (int) Math.round(actualScore * 0.5); // Adjust the score more significantly
            } else if (reviewerExperience < revieweeExperience) {
                // Reviewer has less experience, normalize
                adjustedScore = (int) Math.round(actualScore * 0.8); // Adjust the score as needed
            }
            // Otherwise, keep the score same
        }

        // Log the calculated adjusted score for debugging
        System.out.println("Actual Score: " + actualScore);
        System.out.println("Adjusted Score: " + adjustedScore);

        return adjustedScore;
    }

    private String getAdjustmentReason(Endorsement endorsement) {
        double revieweeExperience = getExperienceForSkill(endorsement.getReviewee().getSkills(), endorsement.getSkill());
        double reviewerExperience = getExperienceForSkill(endorsement.getReviewer().getSkills(), endorsement.getSkill());

        if (endorsement.getCoworker() == 1) {
            return AppConstants.CO_WORKER;
        }

        if (reviewerExperience <= 0) {
            return AppConstants.NO_SKILL_EXPERIENCE;
        } else if (reviewerExperience < revieweeExperience) {
            return AppConstants.LESS_EXPERIENCE;
        } else {
            return AppConstants.MORE_EXPERIENCE;
        }
    }



}
