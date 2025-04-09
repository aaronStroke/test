package mx.loal.pharmacy_admin_api.service;

import mx.loal.pharmacy_admin_api.payload.MedicalConsultDto;

public interface MedicalConsultService {

    MedicalConsultDto save(Long customerId, MedicalConsultDto medicalConsultDto);

}
