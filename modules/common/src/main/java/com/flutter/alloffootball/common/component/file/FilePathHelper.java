package com.flutter.alloffootball.common.component.file;

public interface FilePathHelper {

    String getOriginalPath(String fileName, FileType type);
    String getThumbnailPath(String fileName, FileType type);
}
