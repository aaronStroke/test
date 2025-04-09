package mx.loal.pharmacy_admin_api.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mx.loal.pharmacy_admin_api.exceptions.NotFoundException;
import mx.loal.pharmacy_admin_api.model.Turn;
import mx.loal.pharmacy_admin_api.payload.TurnDto;
import mx.loal.pharmacy_admin_api.payload.request.ChangeTurnStatus;
import mx.loal.pharmacy_admin_api.repository.TurnRepository;
import mx.loal.pharmacy_admin_api.service.TurnService;
import mx.loal.pharmacy_admin_api.utils.enums.TurnStatus;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class TurnServiceImpl implements TurnService {

    private final TurnRepository turnRepository;

    private final ModelMapper modelMapper;

    private final SimpMessagingTemplate messagingTemplate;


    @Transactional
    @Override
    public TurnDto addTurn(TurnDto turn) {

        var lastSequential = turnRepository.getLastIdentifierNumber(LocalDate.now(), turn.getMedicalConsultType().getIdentifier());

        if (Objects.isNull(lastSequential))
            lastSequential = 0;

        lastSequential += 1;

        var turnCode = turn.getMedicalConsultType().getIdentifier() + String.format("%02d", lastSequential);

        turn.setTurnCode(turnCode);
        turn.setStatus(TurnStatus.PENDING);

        var turnToSave = turnRepository.save(convertToEntity(turn));

        messagingTemplate.convertAndSend("/topic/turns", getAllTurns());

        return convertToDTO(turnToSave);
    }

    @Transactional
    @Override
    public TurnDto updateTurnStatus(Long turnId, ChangeTurnStatus status) {

        var turn = turnRepository.findById(turnId)
                .orElseThrow(() -> new NotFoundException("El turno no se ha encontrado"));

        turn.setStatus(status.status());

        turn = turnRepository.save(turn);



        messagingTemplate.convertAndSend("/topic/turns", getAllTurns());
        messagingTemplate.convertAndSend("/topic/attending-turn", getCurrentAttendingTurn());

        return convertToDTO(turn);
    }

    @Transactional(readOnly = true)
    @Override
    public List<TurnDto> getAllTurns() {

        List<Turn> turns = new ArrayList<>();

        var pendingTurns = turnRepository.findTurnsByDate(LocalDate.now());

        Queue<Turn> highPriorityQueue = new LinkedList<>();
        Queue<Turn> lowPriorityQueue = new LinkedList<>();

        for (Turn t : pendingTurns) {
            if (t.getMedicalConsultType().getPriority().equals(1))
                highPriorityQueue.add(t);
            else
                lowPriorityQueue.add(t);
        }

        var lastTurnsAttended = turnRepository.getLastAttendedTurn(PageRequest.of(0, 1));
        var lastAttendedTurn = lastTurnsAttended.isEmpty() ? null : lastTurnsAttended.get(0);
        var nextTurnIsHighPriority = Objects.isNull(lastAttendedTurn) || lastAttendedTurn.getMedicalConsultType().getPriority() == 2;

        while (!highPriorityQueue.isEmpty() || !lowPriorityQueue.isEmpty()) {
            if (nextTurnIsHighPriority && !highPriorityQueue.isEmpty())
                turns.add(highPriorityQueue.poll());
            else if (!nextTurnIsHighPriority && !lowPriorityQueue.isEmpty())
                turns.add(lowPriorityQueue.poll());
            else if (!highPriorityQueue.isEmpty())
                turns.add(highPriorityQueue.poll());
            else
                turns.add(lowPriorityQueue.poll());

             nextTurnIsHighPriority = !nextTurnIsHighPriority;
        }

        return turns
            .stream()
            .map(this::convertToDTO)
            .toList();
    }

    @Transactional(readOnly = true)
    @Override
    public List<TurnDto> allTurns(Long typeConsultId) {
        List<Turn> turns;

        if(typeConsultId == null){
            turns = turnRepository.findTurnsForId(LocalDate.now(), null);
        }else{
            turns = turnRepository.findTurnsForId(LocalDate.now(), typeConsultId);
        }

        return turns.stream().map(this::convertToDTO).toList();
    }

    @Transactional(readOnly = true)
    @Override
    public List<TurnDto> getHistoryTurns(LocalDate date) {
        return List.of();
    }

    @Transactional(readOnly = true)
    @Override
    public TurnDto getCurrentAttendingTurnNullSafe() {

        var currentTurn = turnRepository.findAttendingTurn(LocalDate.now());

        if (currentTurn.isEmpty())
            throw new NotFoundException("El turno que está en atención no se ha encontrado");

        return convertToDTO(currentTurn.get());
    }

    @Transactional(readOnly = true)
    @Override
    public TurnDto getCurrentAttendingTurn() {
        return turnRepository
            .findAttendingTurn(LocalDate.now())
            .map(this::convertToDTO)
            .orElse(new TurnDto());
    }

    private Turn convertToEntity(TurnDto turnDto) {
        return modelMapper.map(turnDto, Turn.class);
    }

    private TurnDto convertToDTO(Turn turn) {
        return modelMapper.map(turn, TurnDto.class);
    }
}
