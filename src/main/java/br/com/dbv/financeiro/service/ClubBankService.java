package br.com.dbv.financeiro.service;

import br.com.dbv.financeiro.enums.ErrorBusinessEnum;
import br.com.dbv.financeiro.exception.BusinessException;
import br.com.dbv.financeiro.repository.ClubRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClubBankService {

    @Autowired
    private ClubRepository repository;

    public void deposit(Long id, Double value) throws BusinessException {

        var optionalClub = repository.findById(id);
        if (optionalClub.isEmpty()) throw new BusinessException(ErrorBusinessEnum.CASH_BOOK_NOT_FOUND);

        var club = optionalClub.get();

        club.setBank(club.getBank() + value);

    }

    public void subtract(Long id, Double value) throws BusinessException {

        var optionalClub = repository.findById(id);
        if (optionalClub.isEmpty()) throw new BusinessException(ErrorBusinessEnum.CASH_BOOK_NOT_FOUND);

        var club = optionalClub.get();

        club.setBank(club.getBank() - value);
    }

}
