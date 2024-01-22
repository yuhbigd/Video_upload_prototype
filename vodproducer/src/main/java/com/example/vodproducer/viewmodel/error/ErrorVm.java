package com.example.vodproducer.viewmodel.error;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErrorVm {
    private String statusCode;
    private String detail;
    private String title;
    private List<String> fieldErrors;
}
