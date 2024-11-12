package com.flutter.alloffootball.admin.service;

import com.flutter.alloffootball.admin.dto.field.*;
import com.flutter.alloffootball.admin.dto.match.*;
import com.flutter.alloffootball.admin.dto.user.RequestSearchUser;
import com.flutter.alloffootball.admin.dto.user.ResponseSearchUser;
import com.flutter.alloffootball.admin.dto.user.ResponseUserOrder;
import com.flutter.alloffootball.admin.dto.user.ResponseViewUser;
import com.flutter.alloffootball.admin.repository.AdminPageRepository;
import com.flutter.alloffootball.admin.repository.AdminRepository;
import com.flutter.alloffootball.common.component.file.FileService;
import com.flutter.alloffootball.common.domain.field.Address;
import com.flutter.alloffootball.common.domain.field.Field;
import com.flutter.alloffootball.common.domain.field.FieldData;
import com.flutter.alloffootball.common.domain.match.Match;
import com.flutter.alloffootball.common.domain.user.User;
import com.flutter.alloffootball.common.enums.MatchStatus;
import com.flutter.alloffootball.common.exception.*;
import com.flutter.alloffootball.common.jparepository.JpaFieldRepository;
import com.flutter.alloffootball.common.jparepository.JpaMatchRepository;
import com.flutter.alloffootball.common.jparepository.JpaOrderRepository;
import com.flutter.alloffootball.common.jparepository.JpaUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AdminServiceImpl implements AdminService {

    private final FileService fileService;
    private final JpaFieldRepository jpaFieldRepository;
    private final JpaOrderRepository jpaOrderRepository;

    private final JpaMatchRepository jpaMatchRepository;
    private final JpaUserRepository jpaUserRepository;


    @Override
    public void saveField(RequestSaveFieldForm form) {
        System.out.println("form = " + form);
        Field saveField = Field.builder()
            .title(form.getTitle())
            .description(form.getDescription())
            .address(Address.builder()
                .address(form.getAddress())
                .region(form.getRegion())
                .link(form.getLink())
                .build())
            .fieldData(FieldData.builder()
                .size(form.getSize())
                .parking(form.getParking())
                .shower(form.getShower())
                .toilet(form.getToilet())
                .build())
            .build();

        jpaFieldRepository.save(saveField);

        fileService.saveFieldImages(form.getImages(), saveField);

    }

    @Override
    public ResponseViewField findByIdViewField(long fieldId) {
        Field field = fieldFindById(fieldId);
        return new ResponseViewField(field);
    }

    @Override
    public ResponseViewMatch findByIdViewMatch(long matchId) {
        Match match = matchFindById(matchId);
        List<ResponseViewMatchUser> userList = jpaOrderRepository.findAllByMatchAndCancelDateIsNull(match)
            .stream().map(ResponseViewMatchUser::new)
            .sorted(Comparator.comparingLong(ResponseViewMatchUser::getUserId))
            .toList();
        return new ResponseViewMatch(match, userList);
    }

    @Override
    public ResponseViewUser findByIdViewUser(long userId) {
        User user = userFindById(userId);
        return new ResponseViewUser(user);
    }


    @Override
    public ResponseEditField getEditFieldForm(Long fieldId) {
        Field field = fieldFindById(fieldId);
        return new ResponseEditField(field);
    }

    @Override
    public void patchEditField(Long fieldId, ResponseEditField editField) {
        Field field = fieldFindById(fieldId);

        String[] deleteStoreImages = editField.getDeleteImages().split(",");
        fileService.deleteImages(deleteStoreImages);
        fileService.saveFieldImages(editField.getImages(), field);

        field.setTitle(editField.getTitle());
        field.setDescription(editField.getDescription());

        Address address = field.getAddress();
        address.setLink(editField.getLink());
        address.setRegion(editField.getRegion());
        address.setAddress(editField.getAddress());
        System.out.println("address = " + address);

        FieldData fieldData = field.getFieldData();
        fieldData.setParking(editField.getParking());
        fieldData.setShower(editField.getShower());
        fieldData.setToilet(editField.getToilet());
        fieldData.setSize(editField.getSize());

    }

    @Override
    public ResponseFieldSimpInfo findByIdFieldSimpInfo(long fieldId) {
        Field field = fieldFindById(fieldId);
        return new ResponseFieldSimpInfo(field);
    }

    @Override
    public long createMatch(long fieldId, RequestSaveMatchForm form) {
        Field field = fieldFindById(fieldId);
        Match saveMatch = Match.builder()
            .field(field)
            .matchDate(LocalDateTime.of(form.getMatchDate(), form.getMatchHour()))
            .matchTime(form.getMatchTime())
            .matchSex(form.getSex())
            .matchCount(form.getMatchCount())
            .personCount(form.getMatchPerson())
            .price(form.getPrice())
            .matchStatus(MatchStatus.OPEN)
            .build();
        return jpaMatchRepository.save(saveMatch).getId();
    }

    Field fieldFindById(long fieldId) {
        return jpaFieldRepository.findById(fieldId)
            .orElseThrow(() -> new FieldException(FieldError.FIELD_NOT_EXISTS));
    }

    Match matchFindById(long matchId) {
        return jpaMatchRepository.findById(matchId)
            .orElseThrow(() -> new MatchException(MatchError.MATCH_NOT_EXISTS));
    }

    User userFindById(long userId) {
        return jpaUserRepository.findById(userId)
            .orElseThrow(() -> new UserException(UserError.USER_NOT_EXISTS));
    }
}
