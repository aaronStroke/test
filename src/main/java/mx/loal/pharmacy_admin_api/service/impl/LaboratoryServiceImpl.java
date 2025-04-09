package mx.loal.pharmacy_admin_api.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mx.loal.pharmacy_admin_api.exceptions.DuplicateKeyException;
import mx.loal.pharmacy_admin_api.exceptions.NoContentException;
import mx.loal.pharmacy_admin_api.exceptions.NotFoundException;
import mx.loal.pharmacy_admin_api.exceptions.RequestException;
import mx.loal.pharmacy_admin_api.model.Laboratory;
import mx.loal.pharmacy_admin_api.payload.LaboratoryDto;
import mx.loal.pharmacy_admin_api.payload.Pagination;
import mx.loal.pharmacy_admin_api.repository.LaboratoryRepository;
import mx.loal.pharmacy_admin_api.service.LaboratoryService;
import mx.loal.pharmacy_admin_api.utils.constants.ExceptionMessageConstants;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@Service
public class LaboratoryServiceImpl implements LaboratoryService {

    private final LaboratoryRepository laboratoryRepository;

    private final ModelMapper modelMapper;

    @Override
    @Transactional(readOnly = true)
    public Pagination<LaboratoryDto> findAll(int page, int size, String query) {

        Page<Laboratory> result;

        if (Objects.isNull(query))
            result = laboratoryRepository.findAllByOrderByNameAsc(PageRequest.of(page, size));
        else
            result = laboratoryRepository.findAllByNameIgnoreCaseOrderByNameAsc(PageRequest.of(page, size), query);

        if (result.isEmpty())
            throw new NoContentException(ExceptionMessageConstants.LABORATORIES_NOT_FOUND);

        var laboratories = result
            .stream()
            .map(this::convertToDTO)
            .toList();

        return Pagination
            .<LaboratoryDto>builder()
            .empty(result.isEmpty())
            .first(result.isFirst())
            .last(result.isLast())
            .last(result.isLast())
            .number(result.getNumber())
            .numberOfElements(result.getNumberOfElements())
            .size(result.getSize())
            .totalElements(result.getTotalElements())
            .totalPages(result.getTotalPages())
            .content(laboratories)
            .build();
    }

    @Override
    public List<LaboratoryDto> search(String query) {

        var result = laboratoryRepository.searchLaboratories(query);

        if (result.isEmpty())
            throw new NoContentException(ExceptionMessageConstants.LABORATORIES_NOT_FOUND);

        return result
            .stream()
            .map(this::convertToDTO)
            .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public LaboratoryDto findById(Long id) {
        var laboratory = laboratoryRepository.findById(id)
                .orElseThrow(() -> new NoContentException(ExceptionMessageConstants.LABORATORY_NOT_FOUND));
        return convertToDTO(laboratory);
    }

    @Override
    @Transactional
    public LaboratoryDto save(LaboratoryDto laboratory) {

        var exists = laboratoryRepository.existsByNameIgnoreCase(laboratory.getName());

        if (exists)
            throw new DuplicateKeyException(ExceptionMessageConstants.LABORATORY_ALREADY_EXISTS);

        laboratory.setActive(true);

        var newLaboratory = convertToEntity(laboratory);

        return convertToDTO(laboratoryRepository.save(newLaboratory));
    }

    @Override
    @Transactional
    public LaboratoryDto update(Long id, LaboratoryDto laboratory) {

        var existentLaboratory = laboratoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ExceptionMessageConstants.LABORATORY_NOT_FOUND));

        if (!existentLaboratory.getId().equals(laboratory.getId()))
            throw new RequestException(ExceptionMessageConstants.LABORATORY_UPDATE_CONFLICT);

        existentLaboratory.setName(laboratory.getName());
        existentLaboratory.setActive(laboratory.getActive());

        laboratoryRepository.save(existentLaboratory);

        return laboratory;
    }

    @Override
    public Boolean delete(Long laboratoryId) {

        var existentLaboratory = laboratoryRepository.findById(laboratoryId)
                .orElseThrow(() -> new NotFoundException(ExceptionMessageConstants.LABORATORY_NOT_FOUND));

        existentLaboratory.setActive(false);

        laboratoryRepository.save(existentLaboratory);

        return true;
    }

    private Laboratory convertToEntity(LaboratoryDto laboratoryDto) {
        return modelMapper.map(laboratoryDto, Laboratory.class);
    }

    private LaboratoryDto convertToDTO(Laboratory laboratory) {
        return modelMapper.map(laboratory, LaboratoryDto.class);
    }
}
