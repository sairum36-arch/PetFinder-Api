package com.PetFinder.PetFinder.dto.locationHistory;

import com.PetFinder.PetFinder.dto.CoordinateDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LocationPoint {
    private CoordinateDto location;
    private LocalDateTime timestamp;

}
