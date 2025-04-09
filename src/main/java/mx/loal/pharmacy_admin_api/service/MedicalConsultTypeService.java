package mx.loal.pharmacy_admin_api.service;

import mx.loal.pharmacy_admin_api.payload.MedicalConsultTypeDto;
import mx.loal.pharmacy_admin_api.payload.Pagination;

import java.util.List;

public interface MedicalConsultTypeService {

    Pagination<MedicalConsultTypeDto> findAll(int page, int size);

    List<MedicalConsultTypeDto> getMedicalConsultTypes();

    MedicalConsultTypeDto findById(Long medicalConsultTypeId);

    MedicalConsultTypeDto save(MedicalConsultTypeDto medicalConsultType);
    MedicalConsultTypeDto update(Long medicalConsultTypeId, MedicalConsultTypeDto medicalConsultType);

}
