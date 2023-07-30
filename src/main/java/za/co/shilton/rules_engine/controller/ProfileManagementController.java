package za.co.shilton.rules_engine.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import za.co.shilton.rules_engine.dto.response.ValidationRuleDto;
import za.co.shilton.rules_engine.entity.ValidationRule;
import za.co.shilton.rules_engine.entity.ValidationRuleType;
import za.co.shilton.rules_engine.repository.ProfileRepository;
import za.co.shilton.rules_engine.service.RuleEngine;

import java.lang.reflect.InvocationTargetException;
import java.util.Set;

@RestController
@RequestMapping("/profile/validate")
@RequiredArgsConstructor
public class ProfileManagementController {

    private final RuleEngine ruleEngine;
    private final ProfileRepository profileRepository;

    @GetMapping
    public Set<ValidationRuleDto> getValidation(@RequestParam ValidationRuleType name) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        var profile = profileRepository.findById(1L).get();
        return ruleEngine.validate(profile, name);
    }


}
