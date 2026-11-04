package org.openindustries.energy.dispatch;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class DispatchService {

    @Inject
    DispatchRepository repository;

    public DispatchCapacity open(OpenDispatchCapacityRequest request) {
        return repository.saveAccount(DispatchCapacity.open(request.assetId(), request.capacityMw()));
    }

    public DispatchCapacity get(UUID id) {
        return repository.findAccount(id)
                .orElseThrow(() -> new DomainException(404, "DispatchCapacity not found: " + id));
    }

    /**
     * Draws from the account. Invariant: the balance/capacity never goes negative —
     * an over-drawing operation is rejected (422) and nothing is recorded.
     */
    public DispatchOrder consume(UUID accountId, CreateDispatchOrderRequest request) {
        DispatchCapacity account = get(accountId);
        if (!"ACTIVE".equals(account.status)) {
            throw new DomainException(409, "DispatchCapacity is not ACTIVE");
        }
        if (request.mw() > account.capacityMw) {
            throw new DomainException(422, "Insufficient capacity: requested " + request.mw()
                    + " exceeds available " + account.capacityMw);
        }
        account.capacityMw -= request.mw();
        repository.saveAccount(account);
        return repository.saveRecord(DispatchOrder.of(accountId, request.sourceType(), request.mw(), account.capacityMw));
    }

    public List<DispatchOrder> recordsOf(UUID accountId) {
        get(accountId);
        return repository.recordsOf(accountId);
    }
}
