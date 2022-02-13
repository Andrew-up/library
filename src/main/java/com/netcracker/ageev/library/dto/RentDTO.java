package com.netcracker.ageev.library.dto;

import lombok.Data;

@Data
public class RentDTO {
    private Integer id;
    private String dateIssue;
    private String dateReturn;
    private Long bookId;
    private Long employeeId;
    private Integer priceId;
    private Long userId;

    //name
    private String employeeName;
    private String bookName;
    private String priceName;
    private String userName;

}
