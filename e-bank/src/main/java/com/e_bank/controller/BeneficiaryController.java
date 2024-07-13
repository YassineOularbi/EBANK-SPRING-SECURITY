package com.e_bank.controller;

import com.e_bank.dto.BeneficiaryDto;
import com.e_bank.exception.BeneficiaryNotFoundException;
import com.e_bank.exception.DatabaseEmptyException;
import com.e_bank.service.BeneficiaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Contrôleur REST pour gérer les opérations liées aux bénéficiaires.
 */
@RestController
@RequestMapping("/api/beneficiary")
@RequiredArgsConstructor
public class BeneficiaryController {

    private final BeneficiaryService beneficiaryService;

    /**
     * Récupère tous les bénéficiaires associés à un compte par l'identifiant du compte.
     *
     * @param id l'identifiant du compte.
     * @return la liste des bénéficiaires associés au compte ou une erreur 404 si la base de données est vide.
     */
    @GetMapping("/get-all-beneficiary-by-account/{id}")
    public ResponseEntity<?> getAllByAccount(@PathVariable("id") String id) {
        try {
            var beneficiaries = beneficiaryService.getAllByAccount(Long.valueOf(id));
            return ResponseEntity.ok(beneficiaries);
        } catch (DatabaseEmptyException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    /**
     * Récupère un bénéficiaire par son identifiant.
     *
     * @param id l'identifiant du bénéficiaire.
     * @return le bénéficiaire correspondant ou une erreur 404 si le bénéficiaire n'est pas trouvé.
     */
    @GetMapping("/get-by-id/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") String id) {
        try {
            var beneficiary = beneficiaryService.getById(Long.valueOf(id));
            return ResponseEntity.ok(beneficiary);
        } catch (BeneficiaryNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    /**
     * Ajoute un nouveau bénéficiaire.
     *
     * @param beneficiaryDto les détails du bénéficiaire à ajouter.
     * @return le bénéficiaire ajouté avec un statut 201 (créé).
     */
    @PostMapping("/add")
    public ResponseEntity<BeneficiaryDto> save(@RequestBody BeneficiaryDto beneficiaryDto) {
        var savedBeneficiary = beneficiaryService.save(beneficiaryDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedBeneficiary);
    }

    /**
     * Met à jour un bénéficiaire existant par son identifiant.
     *
     * @param beneficiaryDto les détails mis à jour du bénéficiaire.
     * @param id l'identifiant du bénéficiaire à mettre à jour.
     * @return le bénéficiaire mis à jour ou une erreur 404 si le bénéficiaire n'est pas trouvé.
     */
    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@RequestBody BeneficiaryDto beneficiaryDto, @PathVariable("id") String id) {
        try {
            var updatedBeneficiary = beneficiaryService.update(beneficiaryDto, Long.valueOf(id));
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(updatedBeneficiary);
        } catch (BeneficiaryNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    /**
     * Supprime un bénéficiaire par son identifiant.
     *
     * @param id l'identifiant du bénéficiaire à supprimer.
     * @return le bénéficiaire supprimé ou une erreur 404 si le bénéficiaire n'est pas trouvé.
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") String id) {
        try {
            var deletedBeneficiary = beneficiaryService.delete(Long.valueOf(id));
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(deletedBeneficiary);
        } catch (BeneficiaryNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
