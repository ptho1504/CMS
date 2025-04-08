package com.microservices.upload_service.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class FileResponse {
    String filename;
    String url;
}
