package org.openindustries.energy.grid;

import jakarta.enterprise.context.ApplicationScoped;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/** Outbound port; default in-memory adapter, production adapter is JPA on this service's own database. */
@ApplicationScoped
public class GridRuleRepository {
    private final ConcurrentHashMap<UUID, GridRule> store = new ConcurrentHashMap<>();

    public GridRule save(GridRule rule) {
        store.put(rule.id, rule);
        return rule;
    }

    public Optional<GridRule> findById(UUID id) {
        return Optional.ofNullable(store.get(id));
    }

    public List<GridRule> findAll() {
        return List.copyOf(store.values());
    }

    /** Active rules for a scope, highest priority (lowest number) first. */
    public List<GridRule> activeFor(String zoneType) {
        return store.values().stream()
                .filter(r -> r.zoneType.equals(zoneType) && "ACTIVE".equals(r.status))
                .sorted(Comparator.comparingInt(r -> r.priority))
                .toList();
    }
}
