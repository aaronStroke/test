package mx.loal.pharmacy_admin_api.service.impl;

import lombok.RequiredArgsConstructor;
import mx.loal.pharmacy_admin_api.model.Presentation;
import mx.loal.pharmacy_admin_api.payload.PresentationDto;
import mx.loal.pharmacy_admin_api.repository.PresentationRepository;
import mx.loal.pharmacy_admin_api.service.PresentationService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PresentationServiceImpl implements PresentationService {

    private final PresentationRepository presentationRepository;

    private final ModelMapper modelMapper;

    @Override
    public List<PresentationDto> findAll() {
        return presentationRepository
            .findAll()
            .stream()
            .map(this::convertToDTO)
            .toList();
    }

    private Presentation convertToEntity(PresentationDto presentationDto) {
        return modelMapper.map(presentationDto, Presentation.class);
    }

    private PresentationDto convertToDTO(Presentation presentation) {
        return modelMapper.map(presentation, PresentationDto.class);
    }
}
