package uz.pdp.appatmsystam.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appatmsystam.entity.Bankomat;
import uz.pdp.appatmsystam.entity.Card;
import uz.pdp.appatmsystam.payload.ApiResponse;
import uz.pdp.appatmsystam.payload.CardDto;
import uz.pdp.appatmsystam.payload.LoginDto;
import uz.pdp.appatmsystam.payload.RegisterDto;
import uz.pdp.appatmsystam.service.AuthService;
import uz.pdp.appatmsystam.service.CardService;

import java.util.Optional;

@RestController
@RequestMapping("/api/card")
public class CardController {
    @Autowired
    CardService cardService;

    @PreAuthorize("hasAnyAuthority('CARD_CRUD','READ_CARD')")
    @GetMapping
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok().body(cardService.getAll());
    }


    @PreAuthorize("hasAnyAuthority('CARD_CRUD','READ_CARD')")
    @GetMapping("/{id}")
    public ResponseEntity<?> getBankomat(@PathVariable Long id) {
        Card card = cardService.getCard(id);
        return ResponseEntity.ok().body(card);
    }



    @PreAuthorize("hasAnyAuthority('CARD_CRUD','DELETE_CARD')")
    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        ApiResponse apiResponse = cardService.deleteCard(id);
        return ResponseEntity.ok().body("Deleted");
    }



    @PreAuthorize("hasAnyAuthority('CARD_CRUD','ADD_CARD')")
    @PostMapping
    public ResponseEntity<?> add(@RequestBody CardDto cardDto) {
        ApiResponse apiResponse = cardService.add(cardDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 400).body(apiResponse);
    }


}
