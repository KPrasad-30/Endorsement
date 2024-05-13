package endorse.service;
import endorse.dto.Endorsement;
import endorse.dto.User;
import endorse.dto.Skill;

import java.util.List;
import java.util.Map;

public interface UserService {
       User registerUser(User user);
       double getExperienceForSkill(List<Skill> skills, String skillName);
       Map<String, List<String>> getEndorsements();
       Endorsement postEndorsement(Endorsement requestDTO);

       }
