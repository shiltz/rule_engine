package za.co.shilton.rules_engine.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class ValidationRule {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String tableName;
    private String columnName;

    @Enumerated(EnumType.STRING)
    private ConstraintType constraintType;

}

