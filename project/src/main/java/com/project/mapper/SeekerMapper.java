package com.project.mapper;

import com.project.dto.SeekerReqDto;
import com.project.model.Seeker;
import jakarta.validation.Valid;

public class SeekerMapper {

    public static Seeker MapDtoToEntity(@Valid SeekerReqDto seekerReqDto) {
        Seeker seeker = new Seeker();
        seeker.setName(seekerReqDto.name());
        seeker.setResumeSummary(seekerReqDto.resumeSummary());
        return seeker;
    }
}
