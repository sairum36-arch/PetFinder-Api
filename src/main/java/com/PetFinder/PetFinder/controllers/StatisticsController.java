package com.PetFinder.PetFinder.controllers;

import com.PetFinder.PetFinder.dto.StatisticsSumDto;
import com.PetFinder.PetFinder.service.StatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/statistics")
@RequiredArgsConstructor
public class StatisticsController {
    private final StatisticsService statisticsService;

    @GetMapping("/summary")
    public StatisticsSumDto getSumm(){
        return statisticsService.getSumm();
    }
}
