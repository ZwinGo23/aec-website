package api.aec.repositories.entities.converters;

import api.aec.domain.models.GenderModel;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class GenderConverter implements AttributeConverter<GenderModel, String> {

    @Override
    public String convertToDatabaseColumn(GenderModel attribute) {
        return attribute == null ? null : attribute.getCode();
    }

    @Override
    public GenderModel convertToEntityAttribute(String dbData) {
        return dbData == null ? null : GenderModel.fromCode(dbData);
    }
}
