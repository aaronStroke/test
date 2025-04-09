package mx.loal.pharmacy_admin_api.service.impl;

import lombok.RequiredArgsConstructor;
import mx.loal.pharmacy_admin_api.model.AgeGroup;
import mx.loal.pharmacy_admin_api.payload.AgeGroupDto;
import mx.loal.pharmacy_admin_api.repository.AgeGroupRepository;
import mx.loal.pharmacy_admin_api.service.AgeGroupService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AgeGroupServiceImpl implements AgeGroupService {

    private final AgeGroupRepository ageGroupRepository;

    private final ModelMapper modelMapper;

    @Override
    public List<AgeGroupDto> getAllAgeGroups() {
        return ageGroupRepository
            .findAll()
            .stream()
            .map(this::convertToDTO)
            .toList();
    }

    private AgeGroup convertToEntity(AgeGroupDto ageGroupDto) {
        return modelMapper.map(ageGroupDto, AgeGroup.class);
    }

    private AgeGroupDto convertToDTO(AgeGroup ageGroup) {
        return modelMapper.map(ageGroup, AgeGroupDto.class);
    }
}
