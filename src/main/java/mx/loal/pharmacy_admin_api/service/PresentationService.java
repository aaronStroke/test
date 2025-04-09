package mx.loal.pharmacy_admin_api.service;

import mx.loal.pharmacy_admin_api.payload.PresentationDto;

import java.util.List;

public interface PresentationService {

    List<PresentationDto> findAll();

}
