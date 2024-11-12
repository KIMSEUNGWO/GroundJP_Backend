package com.flutter.alloffootball.admin.dto.field;

import com.flutter.alloffootball.common.enums.field.Parking;
import com.flutter.alloffootball.common.enums.field.Shower;
import com.flutter.alloffootball.common.enums.field.Toilet;
import com.flutter.alloffootball.common.enums.region.Region;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class RequestSaveFieldForm {

    private List<MultipartFile> images = new ArrayList<>();

    @NotNull
    private String title;
    @NotNull
    private String description;
    @NotNull
    private Region region;
    @NotNull
    private String address;

    @NotNull
    private int size;

    @NotNull
    private Parking parking;
    @NotNull
    private Toilet toilet;
    @NotNull
    private Shower shower;

    private String link;

}
