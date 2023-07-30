package za.co.shilton.rules_engine.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import za.co.shilton.rules_engine.entity.RuleSetStrict;
import za.co.shilton.rules_engine.entity.ValidationRuleType;

import java.util.Optional;

@RepositoryRestResource
public interface RuleSetStrictRepository extends JpaRepository<RuleSetStrict, Long> {

    Optional<RuleSetStrict> findByNameEquals(ValidationRuleType name);

}
