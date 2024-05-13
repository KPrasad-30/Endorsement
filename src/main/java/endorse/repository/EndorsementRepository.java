package endorse.repository;

import endorse.dto.Endorsement;
import endorse.dto.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface EndorsementRepository extends JpaRepository<Endorsement, Long>{

//    List<Endorsement> findAll();
}
