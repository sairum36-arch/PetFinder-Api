package com.PetFinder.PetFinder.dto.websocket;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LocationUpdateMessage {
    private Long petId;
    private double latitude;
    private double longitude;
}
