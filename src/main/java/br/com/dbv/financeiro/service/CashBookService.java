package br.com.dbv.financeiro.service;

import br.com.dbv.financeiro.dto.CashBookDTO;
import br.com.dbv.financeiro.enums.BookTypeEnum;
import br.com.dbv.financeiro.enums.ErrorBusinessEnum;
import br.com.dbv.financeiro.exception.BusinessException;
import br.com.dbv.financeiro.model.CashBook;
import br.com.dbv.financeiro.repository.CashBookRepository;
import br.com.dbv.financeiro.service.mapper.CashBookMapper;
import io.micrometer.core.instrument.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CashBookService {

    @Autowired
    private CashBookRepository repository;

    @Autowired
    private ClubBankService clubService;

    @Autowired
    private EventService eventService;

    private final CashBookMapper mapper = new CashBookMapper();

    public CashBook getById(Long id) {
        var optionalCashbook = repository.findById(id);
        if (optionalCashbook.isEmpty()) throw new BusinessException(ErrorBusinessEnum.CASH_BOOK_NOT_FOUND);
        return optionalCashbook.get();
    }

    public CashBookDTO update(Long id, CashBookDTO request) throws BusinessException {

        var cashBook = getById(id);
        var club = cashBook.getClub();

        if (request.getType() != null) {
            if (request.getType() == BookTypeEnum.INPUT) {
                if (cashBook.getType() == request.getType()) {
                    if (request.getValue() != null && !Double.valueOf(request.getValue()).equals(cashBook.getValue())) {
                        clubService.subtract(club.getId(), cashBook.getValue());
                        cashBook.setValue(Double.valueOf(request.getValue()));
                        clubService.deposit(club.getId(), Double.valueOf(request.getValue()));
                    }
                } else {
                    cashBook.setType(request.getType());
                    if (request.getValue() != null) {
                        clubService.deposit(club.getId(), cashBook.getValue());
                        cashBook.setValue(Double.valueOf(request.getValue()));
                        clubService.deposit(club.getId(), Double.valueOf(request.getValue()));
                    }
                }
            } else if (request.getType() == BookTypeEnum.OUTPUT) {
                if (cashBook.getType() == request.getType()) {
                    if (request.getValue() != null && !Double.valueOf(request.getValue()).equals(cashBook.getValue())) {
                        clubService.deposit(club.getId(), cashBook.getValue());
                        cashBook.setValue(Double.valueOf(request.getValue()));
                        clubService.subtract(club.getId(), Double.valueOf(request.getValue()));
                    }
                } else {
                    cashBook.setType(request.getType());
                    if (request.getValue() != null) {
                        clubService.subtract(club.getId(), cashBook.getValue());
                        cashBook.setValue(Double.valueOf(request.getValue()));
                        clubService.subtract(club.getId(), Double.valueOf(request.getValue()));
                    }
                }
            }
        } else if (request.getValue() != null && !Double.valueOf(request.getValue()).equals(cashBook.getValue())) {
            if (cashBook.getType() == BookTypeEnum.INPUT) {
                clubService.subtract(club.getId(), cashBook.getValue());
                cashBook.setValue(Double.valueOf(request.getValue()));
                clubService.deposit(club.getId(), Double.valueOf(request.getValue()));
            } else if (cashBook.getType() == BookTypeEnum.OUTPUT) {
                clubService.deposit(club.getId(), cashBook.getValue());
                cashBook.setValue(Double.valueOf(request.getValue()));
                clubService.subtract(club.getId(), Double.valueOf(request.getValue()));
            }
        }

        if (!StringUtils.isBlank(request.getDescription())) cashBook.setDescription(request.getDescription());
        if (request.getDate() != null) cashBook.setDate(request.getDate());

        if (request.getEventId() != null) {
            cashBook.setEvent(eventService.getEvent(request.getEventId()));
        } else {
            cashBook.setEvent(null);
        }

        repository.save(cashBook);

        return mapper.toDto(cashBook);
    }

}
