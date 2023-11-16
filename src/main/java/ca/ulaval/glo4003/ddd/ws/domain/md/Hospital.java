package ca.ulaval.glo4003.ddd.ws.domain.md;

import java.util.UUID;

public record Hospital(UUID hospitalId, String hospitalName, double nonResidencyChargingRate) {

}
