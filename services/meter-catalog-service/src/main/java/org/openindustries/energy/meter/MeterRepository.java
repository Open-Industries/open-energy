package org.openindustries.energy.meter;

import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/** Outbound port; default in-memory adapter, production adapter is JPA on this service's own database. */
@ApplicationScoped
public class MeterRepository {
    private final ConcurrentHashMap<UUID, Meter> store = new ConcurrentHashMap<>();

    public Meter save(Meter entity) {
        store.put(entity.id, entity);
        return entity;
    }

    public Optional<Meter> findById(UUID id) {
        return Optional.ofNullable(store.get(id));
    }

    public List<Meter> findAll() {
        return List.copyOf(store.values());
    }
}
