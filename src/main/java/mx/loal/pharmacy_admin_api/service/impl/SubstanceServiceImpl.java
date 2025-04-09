package mx.loal.pharmacy_admin_api.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mx.loal.pharmacy_admin_api.exceptions.DuplicateKeyException;
import mx.loal.pharmacy_admin_api.exceptions.NoContentException;
import mx.loal.pharmacy_admin_api.model.Substance;
import mx.loal.pharmacy_admin_api.payload.Pagination;
import mx.loal.pharmacy_admin_api.payload.SubstanceDto;
import mx.loal.pharmacy_admin_api.repository.SubstanceRepository;
import mx.loal.pharmacy_admin_api.service.SubstanceService;
import mx.loal.pharmacy_admin_api.utils.constants.ExceptionMessageConstants;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class SubstanceServiceImpl implements SubstanceService {

    private final SubstanceRepository substanceRepository;

    private final ModelMapper modelMapper;

    @Override
    @Transactional(readOnly = true)
    public Pagination<SubstanceDto> findAll(int page, int size) {

        var result = substanceRepository.findAllByOrderByNameAsc(PageRequest.of(page, size));

        if (result.isEmpty())
            throw new NoContentException(ExceptionMessageConstants.SUBSTANCE_NOT_FOUND);

        var substances = result
            .stream()
            .map(this::convertToDTO)
            .toList();

        return Pagination
            .<SubstanceDto>builder()
            .empty(result.isEmpty())
            .first(result.isFirst())
            .last(result.isLast())
            .last(result.isLast())
            .number(result.getNumber())
            .numberOfElements(result.getNumberOfElements())
            .size(result.getSize())
            .totalElements(result.getTotalElements())
            .totalPages(result.getTotalPages())
            .content(substances)
            .build();
    }

    @Override
    public List<SubstanceDto> search(String query) {

        var result = substanceRepository.searchSubstances(query);

        if (result.isEmpty())
            throw new NoContentException(ExceptionMessageConstants.SUBSTANCE_NOT_FOUND);

        return result
            .stream()
            .map(this::convertToDTO)
            .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public SubstanceDto findById(Long substanceId) {
        var substance = substanceRepository.findById(substanceId)
                .orElseThrow(() -> new NoContentException(ExceptionMessageConstants.SUBSTANCE_NOT_FOUND));
        return convertToDTO(substance);
    }

    @Override
    @Transactional
    public SubstanceDto save(SubstanceDto substance) {

        var exists = substanceRepository.existsByNameIgnoreCase(substance.getName());

        if (exists)
            throw new DuplicateKeyException(ExceptionMessageConstants.SUBSTANCE_ALREADY_EXISTS);

        var newSubstance = convertToEntity(substance);

        return convertToDTO(substanceRepository.save(newSubstance));
    }

    @Override
    @Transactional
    public SubstanceDto update(Long substanceId, SubstanceDto substance) {

        var existentSubstance = substanceRepository.findById(substanceId)
                .orElseThrow(() -> new NoContentException(ExceptionMessageConstants.SUBSTANCE_NOT_FOUND));

        if (!existentSubstance.getId().equals(substance.getId()))
            throw new NoContentException(ExceptionMessageConstants.SUBSTANCE_UPDATE_CONFLICT);

        existentSubstance.setName(substance.getName());
        existentSubstance.setActive(substance.getActive());

        substanceRepository.save(existentSubstance);

        return substance;
    }

    private Substance convertToEntity(SubstanceDto substanceDto) {
        return modelMapper.map(substanceDto, Substance.class);
    }

    private SubstanceDto convertToDTO(Substance substance) {
        return modelMapper.map(substance, SubstanceDto.class);
    }
}
