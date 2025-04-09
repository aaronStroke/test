package mx.loal.pharmacy_admin_api.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mx.loal.pharmacy_admin_api.exceptions.NoContentException;
import mx.loal.pharmacy_admin_api.exceptions.NotFoundException;
import mx.loal.pharmacy_admin_api.exceptions.RequestException;
import mx.loal.pharmacy_admin_api.model.CashRegister;
import mx.loal.pharmacy_admin_api.payload.CashRegisterDto;
import mx.loal.pharmacy_admin_api.payload.request.OpenCashRegister;
import mx.loal.pharmacy_admin_api.repository.CashRegisterRepository;
import mx.loal.pharmacy_admin_api.repository.UserRepository;
import mx.loal.pharmacy_admin_api.service.CashRegisterService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Slf4j
@RequiredArgsConstructor
@Service
public class CashRegisterServiceImpl implements CashRegisterService {

    private final CashRegisterRepository cashRegisterRepository;

    private final UserRepository userRepository;

    private final ModelMapper modelMapper;

    @Transactional
    @Override
    public CashRegisterDto openCashRegister(OpenCashRegister openCashRegister) {

        var cashRegisterIsOpen = cashRegisterRepository.findCurrentCashRegister(LocalDate.now());

        if (cashRegisterIsOpen.isPresent())
            throw new RequestException("Ya existe un registro de caja para la fecha actual");

        var user = userRepository.findById(openCashRegister.userId())
            .orElseThrow(() -> new NotFoundException("El usuario no existe"));

        var cashRegister = CashRegister
            .builder()
            .openingDate(LocalDateTime.now())
            .initialAmount(openCashRegister.initialAmount())
            .finalAmount(openCashRegister.initialAmount())
            .user(user)
            .build();

        return convertToDTO(cashRegisterRepository.save(cashRegister));
    }

    @Transactional(readOnly = true)
    @Override
    public CashRegisterDto getCurrentCashRegister() {

        var currentCashRegister = cashRegisterRepository.findCurrentCashRegister(LocalDate.now())
                .orElseThrow(() -> new NoContentException("No existe un registro de caja para la fecha actual"));

        return convertToDTO(currentCashRegister);
    }

    private CashRegister convertToEntity(CashRegisterDto cashRegisterDto) {
        return modelMapper.map(cashRegisterDto, CashRegister.class);
    }

    private CashRegisterDto convertToDTO(CashRegister cashRegister) {
        return modelMapper.map(cashRegister, CashRegisterDto.class);
    }
}
