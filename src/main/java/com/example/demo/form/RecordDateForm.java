package com.example.demo.form;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class RecordDateForm {
	
	@DateTimeFormat(pattern="yyyy/MM/dd")
	@NotNull(message="yyyy/MM/ddの形で入力して下さい")
	private LocalDate recordDate;
	
}
