package com.flutter.alloffootball.admin.controller;

import com.flutter.alloffootball.admin.dto.notice.RequestEditNoticeForm;
import com.flutter.alloffootball.admin.dto.notice.RequestSaveNoticeForm;
import com.flutter.alloffootball.admin.dto.notice.ResponseNoticeView;
import com.flutter.alloffootball.admin.service.AdminNoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/notice")
public class AdminNoticeController {

    private final AdminNoticeService noticeService;

    @GetMapping
    public String notice() {
        return "admin_notice";
    }
    @GetMapping("/post")
    public String post(Model model) {
        model.addAttribute("form", new RequestSaveNoticeForm());
        return "admin_notice_add";
    }
    @PostMapping("/post")
    public String post(@Validated @ModelAttribute("form") final RequestSaveNoticeForm form,
                       BindingResult bindingResult) {
        if (bindingResult.hasErrors()) return "admin_notice_add";

        noticeService.saveNotice(form);
        return "redirect:/admin/notice";
    }

    @GetMapping("/{noticeId}/edit")
    public String edit(@PathVariable("noticeId") Long noticeId, Model model) {
        RequestEditNoticeForm form = noticeService.getEditForm(noticeId);
        model.addAttribute("form", form);

        return "admin_notice_edit";
    }
    /**
     * PATCH 수정
     */
    @PatchMapping("/{noticeId}")
    public String patch(@PathVariable("noticeId") Long noticeId,
                             RedirectAttributes redirectAttributes,
                             @ModelAttribute("form") RequestEditNoticeForm form) {
        try {
            noticeService.editNotice(noticeId, form);
            return "redirect:/admin/notice/" + noticeId;
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("noticeId", noticeId);
            redirectAttributes.addFlashAttribute("form", form);
            return "admin_field_edit";
        }
    }

    @DeleteMapping("/{noticeId}")
    public String delete(@PathVariable("noticeId") Long noticeId) {
        noticeService.deleteNotice(noticeId);
        return "redirect:/admin/notice";
    }

    @GetMapping("/{noticeId}")
    public String notice(@PathVariable("noticeId") final Long noticeId, Model model) {

        ResponseNoticeView notice = noticeService.findByNoticeId(noticeId);
        model.addAttribute("notice", notice);

        return "admin_notice_view";
    }
}
