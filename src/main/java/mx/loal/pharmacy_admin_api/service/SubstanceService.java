package mx.loal.pharmacy_admin_api.service;

import mx.loal.pharmacy_admin_api.payload.Pagination;
import mx.loal.pharmacy_admin_api.payload.SubstanceDto;

import java.util.List;

public interface SubstanceService {

    Pagination<SubstanceDto> findAll(int page, int size);

    List<SubstanceDto> search(String query);

    SubstanceDto findById(Long substanceId);

    SubstanceDto save(SubstanceDto substance);
    SubstanceDto update(Long substanceId, SubstanceDto substance);

}
