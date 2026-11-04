package org.openindustries.energy.dispatch;

import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/** Outbound port; default in-memory adapter, production adapter is JPA on this service's own database. */
@ApplicationScoped
public class DispatchRepository {
    private final ConcurrentHashMap<UUID, DispatchCapacity> accounts = new ConcurrentHashMap<>();
    private final CopyOnWriteArrayList<DispatchOrder> records = new CopyOnWriteArrayList<>();

    public DispatchCapacity saveAccount(DispatchCapacity account) {
        accounts.put(account.id, account);
        return account;
    }

    public Optional<DispatchCapacity> findAccount(UUID id) {
        return Optional.ofNullable(accounts.get(id));
    }

    public DispatchOrder saveRecord(DispatchOrder record) {
        records.add(record);
        return record;
    }

    public List<DispatchOrder> recordsOf(UUID accountId) {
        return records.stream().filter(r -> r.accountId.equals(accountId)).toList();
    }
}
