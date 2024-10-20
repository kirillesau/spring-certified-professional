package de.kirill.restapispringboot;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import static org.springframework.data.domain.Sort.Direction.ASC;

@RestController
@RequestMapping("/cashcards")
public class CashCardController {

    private final CashCardRepository cashCardRepository;

    public CashCardController(CashCardRepository cashCardRepository) {
        this.cashCardRepository = cashCardRepository;
    }

    @GetMapping
    private ResponseEntity<List<CashCard>> findAll(Pageable pageable, Principal principal) {
        Page<CashCard> page = cashCardRepository.findByOwner(principal.getName(),
                PageRequest.of(pageable.getPageNumber(),
                        pageable.getPageSize(),
                        pageable.getSortOr(Sort.by(ASC, "amount")))
        );
        return ResponseEntity.ok(page.getContent());
    }

    @GetMapping("/{requestedId}")
    private ResponseEntity<CashCard> findById(@PathVariable Long requestedId, Principal principal) {
        Optional<CashCard> cashCard = findCashCard(requestedId, principal);
        return cashCard.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    private ResponseEntity<Void> create(@RequestBody CashCard cashCard, UriComponentsBuilder ucb, Principal principal) {
        CashCard savedCashCard = cashCardRepository.save(new CashCard(null, cashCard.amount(), principal.getName()));
        return ResponseEntity.created(ucb.path("cashcards/{id}").buildAndExpand(savedCashCard.id()).toUri()).build();
    }

    @PutMapping("/{requestedId}")
    private ResponseEntity<Void> update(@PathVariable Long requestedId, @RequestBody CashCard cashCard, Principal principal) {
        Optional<CashCard> existingCashCard = findCashCard(requestedId, principal);
        if (existingCashCard.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        cashCardRepository.save(new CashCard(requestedId, cashCard.amount(), principal.getName()));
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{requestedId}")
    private ResponseEntity<Void> delete(@PathVariable Long requestedId, Principal principal) {
        if (cashCardRepository.existsByIdAndOwner(requestedId, principal.getName())) {
            cashCardRepository.deleteById(requestedId);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    private Optional<CashCard> findCashCard(Long requestedId, Principal principal) {
        return cashCardRepository.findByIdAndOwner(requestedId, principal.getName());
    }
}
