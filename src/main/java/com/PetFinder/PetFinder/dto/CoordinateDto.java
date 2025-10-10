package com.PetFinder.PetFinder.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CoordinateDto {
    private double longitude;
    private double latitude;
}
