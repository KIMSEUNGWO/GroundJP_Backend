package com.flutter.alloffootball.admin.controller;

import com.flutter.alloffootball.admin.dto.field.*;
import com.flutter.alloffootball.admin.dto.match.RequestSaveMatchForm;
import com.flutter.alloffootball.admin.service.AdminService;
import com.flutter.alloffootball.common.enums.region.Region;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Locale;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/field")
public class AdminFieldController {

    private final AdminService adminService;

    @GetMapping
    public String field(Model model, Locale locale) {
        model.addAttribute("region", Region.sortedValues(locale));
        return "admin_field";
    }

    /**
     * 구장 정보 조회
     */
    @GetMapping("/{fieldId}")
    public String fieldViewPage(@PathVariable("fieldId") long fieldId, Model model) {
        ResponseViewField viewField = adminService.findByIdViewField(fieldId);
        model.addAttribute("field", viewField);
        return "admin_field_view";
    }

    /**
     * 구장 정보 수정
     */
    @GetMapping("/{fieldId}/edit")
    public String fieldEdit(@PathVariable("fieldId") Long fieldId, Model model) {
        ResponseEditField form = adminService.getEditFieldForm(fieldId);

        model.addAttribute("options", new FieldOption());
        model.addAttribute("fieldId", fieldId);
        model.addAttribute("editField", form);
        return "admin_field_edit";
    }

    /**
     * GET 구장 등록 데이터
     */
    @GetMapping("/post")
    public String getFieldAdd(Model model) {
        model.addAttribute("options", new FieldOption());
        model.addAttribute("saveFieldForm", new RequestSaveFieldForm());
        return "admin_field_add";
    }


    /**
     * PATCH 구장 수정
     */
    @PatchMapping("/{fieldId}")
    public String fieldPatch(@PathVariable("fieldId") Long fieldId,
                             RedirectAttributes redirectAttributes,
                             @ModelAttribute("editField") ResponseEditField editField) {
        System.out.println("editField = " + editField);
        try {
            adminService.patchEditField(fieldId, editField);
            return "redirect:/admin/field/" + fieldId;
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("options", new FieldOption());
            redirectAttributes.addFlashAttribute("fieldId", fieldId);
            redirectAttributes.addFlashAttribute("editField", editField);
            return "admin_field_edit";
        }
    }


    /**
     * POST 구장 등록
     */
    @PostMapping("/post")
    public String postFieldAdd(@Validated @ModelAttribute("saveFieldForm") RequestSaveFieldForm saveFieldForm,
                               BindingResult bindingResult) {
        if (bindingResult.hasErrors()) return "admin_field_add";

        adminService.saveField(saveFieldForm);
        return "redirect:/admin/field";
    }

}
