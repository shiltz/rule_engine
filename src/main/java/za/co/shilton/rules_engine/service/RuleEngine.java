package za.co.shilton.rules_engine.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.text.CaseUtils;
import org.springframework.stereotype.Service;
import za.co.shilton.rules_engine.dto.response.ValidationRuleDto;
import za.co.shilton.rules_engine.entity.ConstraintType;
import za.co.shilton.rules_engine.entity.Profile;
import za.co.shilton.rules_engine.entity.ValidationRule;
import za.co.shilton.rules_engine.entity.ValidationRuleType;
import za.co.shilton.rules_engine.repository.RuleSetLenientRepository;
import za.co.shilton.rules_engine.repository.RuleSetStrictRepository;

import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class RuleEngine {

    private final RuleSetStrictRepository ruleSetStrictRepository;
    private final RuleSetLenientRepository ruleSetLenientRepository;

    public Set<ValidationRuleDto> validate(Profile profile, ValidationRuleType name) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {

        var ruleSet = getValidationRules(name);

        var validationErrors = getValidationErrors(profile, ruleSet);

        return mapValidationErrors(validationErrors);
    }

    private static Set<ValidationRuleDto> mapValidationErrors(Set<ValidationRule> validationErrors) {
        return validationErrors.stream().map(validationRule -> {
            return ValidationRuleDto.builder()
                    .constraintType(validationRule.getConstraintType())
                    .columnName(validationRule.getColumnName())
                    .tableName(validationRule.getTableName()).build();
        }).collect(Collectors.toSet());
    }

    private static HashSet<ValidationRule> getValidationErrors(Profile profile, Set<ValidationRule> ruleSet) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        char c = '_';
        var validationErrors = new HashSet<ValidationRule>();
        for (var rule : ruleSet) {
            var tableName = CaseUtils.toCamelCase(rule.getTableName(), false, c);
            var columnName = CaseUtils.toCamelCase(rule.getColumnName(), false, c);

            if (ConstraintType.REQUIRED.equals(rule.getConstraintType())) {
                if (profile.getClass().getSimpleName().equalsIgnoreCase(tableName)) {
                    if (BeanUtils.getProperty(profile, columnName) == null) {
                        validationErrors.add(rule);
                    }
                } else {
                    if (BeanUtils.getProperty(profile, tableName) == null || BeanUtils.getProperty(profile, tableName + "." + columnName) == null) {
                        validationErrors.add(rule);
                    }
                }

            } else {
                log.info("Constraint rule not implemented");
            }
        }
        return validationErrors;
    }

    private Set<ValidationRule> getValidationRules(ValidationRuleType name) {
        Set<ValidationRule> ruleSet = new HashSet<>();

        if (ValidationRuleType.STRICT.equals(name)) {
            ruleSet = ruleSetStrictRepository.findByNameEquals(name).get().getRuleSet();
        } else if (ValidationRuleType.LENIENT.equals(name)) {
            ruleSet = ruleSetLenientRepository.findByNameEquals(name).get().getRuleSet();
        }
        return ruleSet;
    }

}
