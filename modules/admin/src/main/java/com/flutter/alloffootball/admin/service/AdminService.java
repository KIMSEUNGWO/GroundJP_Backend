package com.flutter.alloffootball.admin.service;

import com.flutter.alloffootball.admin.dto.field.*;
import com.flutter.alloffootball.admin.dto.match.*;
import com.flutter.alloffootball.admin.dto.user.ResponseViewUser;

public interface AdminService {

    void saveField(RequestSaveFieldForm saveFieldForm);

    ResponseViewField findByIdViewField(long fieldId);
    ResponseViewMatch findByIdViewMatch(long matchId);

    ResponseEditField getEditFieldForm(Long fieldId);

    void patchEditField(Long fieldId, ResponseEditField editField);

    ResponseFieldSimpInfo findByIdFieldSimpInfo(long fieldId);

    long createMatch(long fieldId, RequestSaveMatchForm form);

    ResponseViewUser findByIdViewUser(long userId);

}
