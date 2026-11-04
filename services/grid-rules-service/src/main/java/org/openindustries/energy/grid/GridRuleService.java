package org.openindustries.energy.grid;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class GridRuleService {

    @Inject
    GridRuleRepository repository;

    public GridRule create(CreateGridRuleRequest request) {
        return repository.save(GridRule.create(request.name(), request.zoneType(),
                request.maxLoadMw(), request.maxOutageMinutes(), request.priority()));
    }

    public GridRule get(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new DomainException(404, "GridRule not found: " + id));
    }

    public List<GridRule> list() {
        return repository.findAll();
    }

    public GridRule activate(UUID id) {
        GridRule rule = get(id);
        rule.status = "ACTIVE";
        return repository.save(rule);
    }

    public Evaluation evaluate(EvaluateRequest request) {
        var candidates = repository.activeFor(request.zoneType());
        if (candidates.isEmpty()) {
            return new Evaluation("DENIED", null,
                    "No active rule for " + request.zoneType());
        }
        for (GridRule rule : candidates) {
            if (rule.maxOutageMinutes <= request.requiredMaxOutageMinutes()
                    && rule.maxLoadMw >= request.requiredMaxLoadMw()) {
                return new Evaluation("ALLOWED", rule.id,
                        "Rule '" + rule.name + "' covers the request");
            }
        }
        return new Evaluation("DEGRADED", candidates.get(0).id,
                "Active rules exist for " + request.zoneType() + " but none meets the requirement");
    }
}
