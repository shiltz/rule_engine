package za.co.shilton.rules_engine.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import za.co.shilton.rules_engine.entity.Address;
import za.co.shilton.rules_engine.entity.ConstraintType;
import za.co.shilton.rules_engine.entity.Profile;
import za.co.shilton.rules_engine.entity.RuleSetStrict;
import za.co.shilton.rules_engine.entity.ValidationRule;
import za.co.shilton.rules_engine.entity.ValidationRuleType;
import za.co.shilton.rules_engine.repository.RuleSetStrictRepository;

import java.lang.reflect.InvocationTargetException;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@Import({RuleEngine.class})
class RuleEngineTest {

    @Autowired
    private RuleEngine ruleEngine;

    @MockBean
    private RuleSetStrictRepository ruleSetStrictRepository;

    @Test
    void validate_ValidModel() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Profile profile = new Profile();
        profile.setId(1L);
        profile.setSurname("Naicker");
        profile.setName("Shilton");
        Address physicalAddress = new Address();
        physicalAddress.setAddressLine("1 Lone Street");
        profile.setAddress(physicalAddress);

        ValidationRule validationRule1 = new ValidationRule();
        validationRule1.setColumnName("surname");
        validationRule1.setConstraintType(ConstraintType.R);
        validationRule1.setTableName("profile");

        ValidationRule validationRule2 = new ValidationRule();
        validationRule2.setColumnName("name");
        validationRule2.setConstraintType(ConstraintType.R);
        validationRule2.setTableName("profile");

        ValidationRule validationRule3 = new ValidationRule();
        validationRule3.setColumnName("address_line");
        validationRule3.setConstraintType(ConstraintType.R);
        validationRule3.setTableName("address");

        RuleSetStrict ruleSetStrict = new RuleSetStrict();
        ruleSetStrict.setName(ValidationRuleType.STRICT);
        ruleSetStrict.setRuleSet(Set.of(validationRule1, validationRule2, validationRule3));
        when(ruleSetStrictRepository.findByNameEquals(ValidationRuleType.STRICT)).thenReturn(
                Optional.of(ruleSetStrict)
        );

        var r = ruleEngine.validate(profile, ValidationRuleType.STRICT);
        assertEquals(0, r.size());
    }

    @Test
    void validate_Missing_Name() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Profile profile = new Profile();
        profile.setId(1L);
        profile.setSurname("Naicker");
        Address physicalAddress = new Address();
        physicalAddress.setAddressLine("1 Lone Street");
        profile.setAddress(physicalAddress);

        ValidationRule validationRule1 = new ValidationRule();
        validationRule1.setColumnName("surname");
        validationRule1.setConstraintType(ConstraintType.R);
        validationRule1.setTableName("profile");

        ValidationRule validationRule2 = new ValidationRule();
        validationRule2.setColumnName("name");
        validationRule2.setConstraintType(ConstraintType.R);
        validationRule2.setTableName("profile");

        ValidationRule validationRule3 = new ValidationRule();
        validationRule3.setColumnName("address_line");
        validationRule3.setConstraintType(ConstraintType.R);
        validationRule3.setTableName("address");

        RuleSetStrict ruleSetStrict = new RuleSetStrict();
        ruleSetStrict.setName(ValidationRuleType.STRICT);
        ruleSetStrict.setRuleSet(Set.of(validationRule1, validationRule2, validationRule3));
        when(ruleSetStrictRepository.findByNameEquals(ValidationRuleType.STRICT)).thenReturn(
                Optional.of(ruleSetStrict)
        );

        var r = ruleEngine.validate(profile, ValidationRuleType.STRICT);
        assertEquals(1, r.size());
    }
}