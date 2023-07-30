package za.co.shilton.rules_engine.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import za.co.shilton.rules_engine.entity.RuleSetLenient;
import za.co.shilton.rules_engine.entity.ValidationRuleType;

import java.util.Optional;

@RepositoryRestResource
public interface RuleSetLenientRepository extends JpaRepository<RuleSetLenient, Long> {

    Optional<RuleSetLenient> findByNameEquals(ValidationRuleType name);

}
