package com.flutter.alloffootball.admin.controller;

import com.flutter.alloffootball.admin.dto.field.ResponseFieldSimpInfo;
import com.flutter.alloffootball.admin.dto.field.ResponseViewField;
import com.flutter.alloffootball.admin.dto.match.RequestSaveMatchForm;
import com.flutter.alloffootball.admin.dto.match.ResponseViewMatch;
import com.flutter.alloffootball.admin.service.AdminService;
import com.flutter.alloffootball.common.enums.MatchStatus;
import com.flutter.alloffootball.common.enums.SexType;
import com.flutter.alloffootball.common.enums.region.Region;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/match")
public class AdminMatchController {

    private final AdminService adminService;

    @GetMapping
    public String match(Model model, Locale locale) {
        model.addAttribute("region", Region.sortedValues(locale));
        model.addAttribute("sex", SexType.values());
        model.addAttribute("state", MatchStatus.values());
        return "admin_match";
    }

    @GetMapping("/field/{fieldId}")
    public String addMatchGet(@PathVariable("fieldId") long fieldId, Model model) {
        ResponseFieldSimpInfo fieldInfo = adminService.findByIdFieldSimpInfo(fieldId);
        model.addAttribute("fieldInfo", fieldInfo);
        model.addAttribute("saveMatchForm", new RequestSaveMatchForm());
        return "admin_match_add";
    }
    @PostMapping("/field/{fieldId}")
    public String addMatchPost(@PathVariable("fieldId") long fieldId, @ModelAttribute RequestSaveMatchForm form, Model model) {
        System.out.println("form = " + form);
        long matchId = adminService.createMatch(fieldId, form);
        return String.format("redirect:/admin/match/%d", matchId);
    }

    /**
     * 경기 정보 조회
     */
    @GetMapping("/{matchId}")
    public String matchViewPage(@PathVariable("matchId") long matchId, Model model) {
        ResponseViewMatch viewMatch = adminService.findByIdViewMatch(matchId);
        model.addAttribute("match", viewMatch);
        return "admin_match_view";
    }
}
