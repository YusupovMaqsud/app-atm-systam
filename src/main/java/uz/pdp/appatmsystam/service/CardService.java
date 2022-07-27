package uz.pdp.appatmsystam.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.pdp.appatmsystam.entity.Bank;
import uz.pdp.appatmsystam.entity.Bankomat;
import uz.pdp.appatmsystam.entity.Card;
import uz.pdp.appatmsystam.entity.User;
import uz.pdp.appatmsystam.enums.CardTypeEnum;
import uz.pdp.appatmsystam.enums.PermissionEnum;
import uz.pdp.appatmsystam.enums.RoleEnum;
import uz.pdp.appatmsystam.payload.ApiResponse;
import uz.pdp.appatmsystam.payload.CardDto;
import uz.pdp.appatmsystam.repository.*;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CardService {
    @Autowired
    CardRepository cardRepository;
    @Autowired
    BankRepository bankRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    PasswordEncoder passwordEncoder;


    public List<Card> getAll() {
        return cardRepository.findAll();
    }


    public Card getCard(Long id) {
        Optional<Card> optionalCard = cardRepository.findById(id);
        return optionalCard.get();
    }



    public ApiResponse deleteCard(Long id) {
        cardRepository.deleteById(id);
        return new ApiResponse("Deleted",true);
    }

    public ApiResponse add(CardDto cardDto) {
        Optional<Bank> optionalBank = bankRepository.findById(Long.valueOf(cardDto.getBankId()));
        if (!optionalBank.isPresent()){
            return new ApiResponse("Bunday Bank mavjud emas",false);
        }

        if (!(cardDto.getPassword().length() == 4))
            return new ApiResponse("Parol uzunligi 4 xonali bo'lishi kerak",false);

        Card card = new Card();
        card.setBank(optionalBank.get());
        card.setExpiredDate(Date.valueOf(LocalDate.now().plusYears(5)));
        card.setCvv(randomCode(100, 999));
        card.setFirstName(cardDto.getFirstName());
        card.setLastName(cardDto.getLastName());
        card.setPassword(passwordEncoder.encode(cardDto.getPassword()));
        card.setCardType(CardTypeEnum.valueOf(cardDto.getCardType()));
        String code16size = randomCode(1000000000000000l, 9999999999999999l);

        for (Card card1 : cardRepository.findAll()) {
            if (!card1.getNumber().equals(code16size)) {
                card.setNumber(code16size);
            } else {
                card.setNumber(randomCode(1000000000000000l, 9999999999999999l));
            }
        }
        cardRepository.save(card);
        return new ApiResponse("Added",true);
    }


    public String randomCode(long max, long min) {
        long b = (long) Math.floor(Math.random() * (max - min + 1) + min);
        return String.valueOf(b);
    }

}
