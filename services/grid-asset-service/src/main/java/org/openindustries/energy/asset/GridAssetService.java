package org.openindustries.energy.asset;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class GridAssetService {

    @Inject
    GridAssetRepository repository;

    public GridAsset create(CreateGridAssetRequest request) {
        return repository.save(GridAsset.create(request.name(), request.assetType(), request.regionCode()));
    }

    public GridAsset get(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new DomainException(404, "GridAsset not found: " + id));
    }

    public List<GridAsset> list() {
        return repository.findAll();
    }

    public GridAsset maintain(UUID id) {
        GridAsset entity = get(id);
        if ("RETIRED".equals(entity.status)) {
            throw new DomainException(409, "A retired gridasset is terminal");
        }
        if ("MAINTENANCE".equals(entity.status)) {
            throw new DomainException(409, "GridAsset already MAINTENANCE");
        }
        entity.status = "MAINTENANCE";
        return repository.save(entity);
    }

    public GridAsset retire(UUID id) {
        GridAsset entity = get(id);
        if ("RETIRED".equals(entity.status)) {
            throw new DomainException(409, "A retired gridasset is terminal");
        }
        if ("RETIRED".equals(entity.status)) {
            throw new DomainException(409, "GridAsset already RETIRED");
        }
        entity.status = "RETIRED";
        return repository.save(entity);
    }
}
