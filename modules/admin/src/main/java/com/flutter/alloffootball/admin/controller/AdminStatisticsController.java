package com.flutter.alloffootball.admin.controller;

import com.flutter.alloffootball.admin.dto.statistics.RequestDateRange;
import com.flutter.alloffootball.common.batch.dto.ResponseRegionStatistics;
import com.flutter.alloffootball.common.batch.service.MatchStatisticsService;
import com.flutter.alloffootball.common.batch.service.RegionStatisticsService;
import com.flutter.alloffootball.common.batch.service.UserStatisticsService;
import com.flutter.alloffootball.common.dto.Response;
import com.flutter.alloffootball.common.enums.region.Region;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminStatisticsController {

    private final MatchStatisticsService matchStatisticsService;
    private final UserStatisticsService userStatisticsService;
    private final RegionStatisticsService regionStatisticsService;

    @GetMapping
    public String statistics(Model model) {
        model.addAttribute("matchStats", matchStatisticsService.getMatchStatisticsEntity());
        model.addAttribute("userStats", userStatisticsService.getUserStatistics(LocalDate.now()));
        return "admin_main";
    }

    @ResponseBody
    @GetMapping("/statistics/region")
    public ResponseEntity<Response> getRegionStatistics(@ModelAttribute RequestDateRange dateRange) {
        List<ResponseRegionStatistics> result = regionStatisticsService.getRegionStatisticsRangeClosed(dateRange.getStartDate(), dateRange.getEndDate());
        return Response.ok(result);
    }
}
