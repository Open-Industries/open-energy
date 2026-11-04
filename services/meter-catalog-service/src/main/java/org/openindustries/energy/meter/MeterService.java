package org.openindustries.energy.meter;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class MeterService {

    @Inject
    MeterRepository repository;

    public Meter create(CreateMeterRequest request) {
        return repository.save(Meter.create(request.name(), request.meterType(), request.readingsPerDay()));
    }

    public Meter get(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new DomainException(404, "Meter not found: " + id));
    }

    public List<Meter> list() {
        return repository.findAll();
    }

    public Meter activate(UUID id) {
        Meter entity = get(id);
        if (!"INSTALLED".equals(entity.status)) {
            throw new DomainException(409, "Only a INSTALLED meter can transition via activate");
        }
        entity.status = "ACTIVE";
        return repository.save(entity);
    }
}
