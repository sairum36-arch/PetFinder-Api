package com.PetFinder.PetFinder.dto.websocket;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LocationUpdateDto {
    private double latitude;
    private double longitude;

}
