package uz.pdp.appatmsystam.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appatmsystam.entity.Bankomat;
import uz.pdp.appatmsystam.payload.*;
import uz.pdp.appatmsystam.repository.BankomatRepository;
import uz.pdp.appatmsystam.service.BankomatService;

import java.util.Optional;

@RestController
@RequestMapping("/api/bankomat")
public class BankomatController {
    @Autowired
    BankomatService bankomatService;


    @PreAuthorize("hasAnyAuthority('BANKOMAT_CRUD', 'READ_BANKOMAT')")
    @GetMapping
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok().body(bankomatService.getAll());
    }



    @PreAuthorize("hasAnyAuthority('BANKOMAT_CRUD', 'READ_BANKOMAT')")
    @GetMapping("/{id}")
    public ResponseEntity<?> getBankomat(@PathVariable Long id) {
        Bankomat bankomat = bankomatService.getBankomat(id);
        return ResponseEntity.ok().body(bankomat);
    }



    @PreAuthorize("hasAnyAuthority('BANKOMAT_CRUD', 'ADD_BANKOMAT')")
    @PostMapping
    public ResponseEntity<?> add(@RequestBody BankomatDto bankomatDto) {
        ApiResponse apiResponse = bankomatService.add(bankomatDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 400).body(apiResponse);
    }



    @PreAuthorize("hasAnyAuthority('BANKOMAT_CRUD', 'ADD_BANKOMAT')")
    @PutMapping("/{id}")
    public ResponseEntity<?> edit(@RequestBody BankomatDto bankomatDto, @PathVariable Integer id) {
        ApiResponse apiResponse = bankomatService.edit(bankomatDto, id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 400).body(apiResponse);
    }

}
