package org.openindustries.energy.asset;

import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Outbound port (hexagonal architecture). Default adapter is in-memory so the reference
 * implementation runs anywhere; the production adapter is JPA against this service's own
 * database (database-per-service, ADR-0003).
 */
@ApplicationScoped
public class GridAssetRepository {
    private final ConcurrentHashMap<UUID, GridAsset> store = new ConcurrentHashMap<>();

    public GridAsset save(GridAsset entity) {
        store.put(entity.id, entity);
        return entity;
    }

    public Optional<GridAsset> findById(UUID id) {
        return Optional.ofNullable(store.get(id));
    }

    public List<GridAsset> findAll() {
        return List.copyOf(store.values());
    }
}
