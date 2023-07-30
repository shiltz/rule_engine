package za.co.shilton.rules_engine.dto.response;

import lombok.Builder;
import lombok.Data;
import za.co.shilton.rules_engine.entity.ConstraintType;

@Data
@Builder
public class ValidationRuleDto {

    private ConstraintType constraintType;
    private String tableName;
    private String columnName;

}
