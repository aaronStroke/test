package mx.loal.pharmacy_admin_api.service;

import mx.loal.pharmacy_admin_api.payload.LaboratoryDto;
import mx.loal.pharmacy_admin_api.payload.Pagination;

import java.util.List;

public interface LaboratoryService {

    Pagination<LaboratoryDto> findAll(int page, int size, String query);

    List<LaboratoryDto> search(String query);

    LaboratoryDto findById(Long id);

    LaboratoryDto save(LaboratoryDto laboratory);
    LaboratoryDto update(Long id, LaboratoryDto laboratory);

    Boolean delete(Long laboratoryId);

}
