package mx.loal.pharmacy_admin_api.service;

import mx.loal.pharmacy_admin_api.model.AgeGroup;
import mx.loal.pharmacy_admin_api.payload.AgeGroupDto;

import java.util.List;

public interface AgeGroupService {

    List<AgeGroupDto> getAllAgeGroups();

}
