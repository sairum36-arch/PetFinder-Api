package com.PetFinder.PetFinder.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatisticsSumDto {
    private long totalUsers;
    private long totalPets;
    private long activeIncidents;
}
