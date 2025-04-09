package mx.loal.pharmacy_admin_api.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mx.loal.pharmacy_admin_api.exceptions.DuplicateKeyException;
import mx.loal.pharmacy_admin_api.exceptions.NoContentException;
import mx.loal.pharmacy_admin_api.exceptions.RequestException;
import mx.loal.pharmacy_admin_api.model.MedicalConsultType;
import mx.loal.pharmacy_admin_api.payload.MedicalConsultTypeDto;
import mx.loal.pharmacy_admin_api.payload.Pagination;
import mx.loal.pharmacy_admin_api.repository.MedicalConsultTypeRepository;
import mx.loal.pharmacy_admin_api.service.MedicalConsultTypeService;
import mx.loal.pharmacy_admin_api.utils.constants.ExceptionMessageConstants;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class MedicalConsultTypeServiceImpl implements MedicalConsultTypeService {

    private final MedicalConsultTypeRepository medicalConsultTypeRepository;

    private final ModelMapper modelMapper;

    @Override
    @Transactional(readOnly = true)
    public Pagination<MedicalConsultTypeDto> findAll(int page, int size) {

        var result = medicalConsultTypeRepository.findAllByOrderByNameAsc(PageRequest.of(page, size));

        if (result.isEmpty())
            throw new NoContentException(ExceptionMessageConstants.MEDICAL_CONSULT_TYPE_NOT_FOUND);

        var medicalConsultTypes = result
            .stream()
            .map(this::convertToDTO)
            .toList();

        return Pagination
            .<MedicalConsultTypeDto>builder()
            .empty(result.isEmpty())
            .first(result.isFirst())
            .last(result.isLast())
            .last(result.isLast())
            .number(result.getNumber())
            .numberOfElements(result.getNumberOfElements())
            .size(result.getSize())
            .totalElements(result.getTotalElements())
            .totalPages(result.getTotalPages())
            .content(medicalConsultTypes)
            .build();
    }

    @Transactional(readOnly = true)
    @Override
    public List<MedicalConsultTypeDto> getMedicalConsultTypes() {
        return medicalConsultTypeRepository
            .findAllByOrderByNameAsc()
            .stream()
            .map(this::convertToDTO)
            .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public MedicalConsultTypeDto findById(Long medicalConsultTypeId) {
        return medicalConsultTypeRepository
            .findById(medicalConsultTypeId)
            .map(this::convertToDTO)
            .orElseThrow(() -> new NoContentException(ExceptionMessageConstants.MEDICAL_CONSULT_TYPE_NOT_FOUND));
    }

    @Override
    @Transactional
    public MedicalConsultTypeDto save(MedicalConsultTypeDto medicalConsultType) {

        var exists = medicalConsultTypeRepository.existsByNameIgnoreCase(medicalConsultType.getName());

        if (exists)
            throw new DuplicateKeyException(ExceptionMessageConstants.MEDICAL_CONSULT_TYPE_ALREADY_EXISTS);

        var newMedicalConsultType = convertToEntity(medicalConsultType);

        return convertToDTO(medicalConsultTypeRepository.save(newMedicalConsultType));
    }

    @Override
    @Transactional
    public MedicalConsultTypeDto update(Long medicalConsultTypeId, MedicalConsultTypeDto medicalConsultType) {

        var existentMedicalConsultType = medicalConsultTypeRepository.findById(medicalConsultTypeId)
                .orElseThrow(() -> new NoContentException(ExceptionMessageConstants.MEDICAL_CONSULT_TYPE_NOT_FOUND));

        if (!existentMedicalConsultType.getId().equals(medicalConsultType.getId()))
            throw new RequestException(ExceptionMessageConstants.MEDICAL_CONSULT_TYPE_UPDATE_CONFLICT);

        existentMedicalConsultType.setCost(medicalConsultType.getCost());
        existentMedicalConsultType.setDescription(medicalConsultType.getDescription());
        existentMedicalConsultType.setIdentifier(medicalConsultType.getIdentifier());
        existentMedicalConsultType.setName(medicalConsultType.getName());
        existentMedicalConsultType.setPriority(medicalConsultType.getPriority());

        medicalConsultTypeRepository.save(existentMedicalConsultType);

        return medicalConsultType;
    }

    private MedicalConsultType convertToEntity(MedicalConsultTypeDto medicalConsultTypeDto) {
        return modelMapper.map(medicalConsultTypeDto, MedicalConsultType.class);
    }

    private MedicalConsultTypeDto convertToDTO(MedicalConsultType medicalConsultType) {
        return modelMapper.map(medicalConsultType, MedicalConsultTypeDto.class);
    }
}
