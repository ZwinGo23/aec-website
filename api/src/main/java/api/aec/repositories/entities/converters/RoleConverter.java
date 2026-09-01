package api.aec.repositories.entities.converters;

import api.aec.domain.models.RoleModel;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class RoleConverter implements AttributeConverter<RoleModel, String> {


    @Override
    public String convertToDatabaseColumn(RoleModel attribute) {
        return attribute == null ? null : attribute.getCode();
    }

    @Override
    public RoleModel convertToEntityAttribute(String dbData) {
        return dbData == null ? null : RoleModel.fromCode(dbData);
    }
}
