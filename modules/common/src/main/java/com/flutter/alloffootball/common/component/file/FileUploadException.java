package com.flutter.alloffootball.common.component.file;

import lombok.Getter;

@Getter
public class FileUploadException extends RuntimeException {

    private final FileCode fileCode;

    public FileUploadException(FileCode fileCode) {
        this.fileCode = fileCode;
    }
}
