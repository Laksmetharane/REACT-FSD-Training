package com.rent_db.dto;


public record CustomerRespDto(
        int id,
        String name,
        String mob_no,
        String email,
        int dl_no,
        int user_id,
        String user_name,
        int admin_id,
        String admin_name
) {
}
