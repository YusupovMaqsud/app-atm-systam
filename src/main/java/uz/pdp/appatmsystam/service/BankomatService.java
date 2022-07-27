package uz.pdp.appatmsystam.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.pdp.appatmsystam.entity.*;
import uz.pdp.appatmsystam.enums.CardTypeEnum;
import uz.pdp.appatmsystam.enums.MoneyTypeEnum;
import uz.pdp.appatmsystam.enums.RoleEnum;
import uz.pdp.appatmsystam.payload.*;
import uz.pdp.appatmsystam.repository.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class BankomatService {
    @Autowired
    BankomatRepository bankomatRepository;
    @Autowired
    BankRepository bankRepository;
    @Autowired
    CardRepository cardRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    JavaMailSender javaMailSender;
    @Autowired
    MoneyRepository moneyRepository;


    public List<Bankomat> getAll() {
        return bankomatRepository.findAll();
    }


    public Bankomat getBankomat(Long id) {
        Optional<Bankomat> bankomatOptional = bankomatRepository.findById(id);
        return bankomatOptional.get();
    }



    public ApiResponse add(BankomatDto bankomatDto) {
        Optional<Bank> optionalBank = bankRepository.findById(Long.valueOf(bankomatDto.getBankId()));
        if (!optionalBank.isPresent())
            return new ApiResponse("Bank mavjud emas", false);


        Bankomat bankomat = new Bankomat();
        bankomat.setBank(optionalBank.get());
        bankomat.setAddress(bankomatDto.getAddress());
        bankomat.setCardType(CardTypeEnum.valueOf(bankomatDto.getCardType()));

        List<Money> moneyList = new ArrayList<>();
        for (Money monies : bankomatDto.getMoneys()) {
            Money money = new Money();
            money.setMoneyTypeEnum(MoneyTypeEnum.valueOf(String.valueOf(monies.getMoneyTypeEnum())));
            money.setQuantity(monies.getQuantity());
            money.setSumma((double) (monies.getMoneyTypeEnum().getValue() * monies.getQuantity()));

            moneyList.add(money);
        }

        if (bankomat.getMaxAmount() < getSum(moneyList))
            return new ApiResponse("Siz kiritgan summa judayam ko'p", false);

        bankomat.setMoneys(moneyList);
        bankomat.setBalance(getSum(moneyList));
        bankomatRepository.save(bankomat);

        return new ApiResponse("Added", true);

    }






    public ApiResponse edit(BankomatDto bankomatDto, Long id) {
        Optional<Bankomat> bankomatOptional = bankomatRepository.findById(id);
        if (!bankomatOptional.isPresent())
            return new ApiResponse("Bankomat mavjud emas",false);

        Bankomat bankomat = bankomatOptional.get();

        bankomat.setAddress(bankomatDto.getAddress());
        bankomat.setCardType(CardTypeEnum.valueOf(bankomatDto.getCardType()));

        List<Money> moneyList = bankomat.getMoneys();
        for (Money monies : bankomatDto.getMoneys()) {
            for (Money money : moneyList) {
                Optional<Money> optionalMoney = moneyRepository.findById(money.getId());
                if (!optionalMoney.isPresent())
                    return new ApiResponse("Money mavjud emas", false);

                Money money1 = optionalMoney.get();
                money1.setMoneyTypeEnum(MoneyTypeEnum.valueOf(String.valueOf(monies.getMoneyTypeEnum())));
                money1.setQuantity(monies.getQuantity());
                money1.setSumma((double) (monies.getMoneyTypeEnum().getValue() * monies.getQuantity()));

                moneyList.add(money1);
            }

        }
            if (bankomat.getMaxAmount() < getSum(moneyList))
                return new ApiResponse("Siz kiritgan summa judayam ko'p",false);

            bankomat.setMoneys(moneyList);
            bankomat.setBalance(getSum(moneyList));
            bankomatRepository.save(bankomat);

            return new ApiResponse("edited",true);

    }


    public Double getSum(List<Money> banknotList) {
        double summa = 0;
        for (Money banknot : banknotList) {
            summa += banknot.getSumma();
        }
        return summa;
    }


}
