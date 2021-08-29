package com.example.demo.service;

import org.springframework.stereotype.Service;

import com.example.demo.form.CalcDataForm;

@Service
public class AutoNicknameService {
	
	public CalcDataForm autoNickname(CalcDataForm form) {
		
		int year = form.getYear();
		int month = form.getMonth();
		int day = form.getDay();
		
		String strYear = String.format("%1$+d", year) + "Y";
		String strMonth = String.format("%1$+d", month) + "M";
		String strDay = String.format("%1$+d", day) + "D";
		
		String[] list = new String[3];
		list[0] = strYear;
		list[1] = strMonth;
		list[2] = strDay;
		
		String nickname = "";
		for (String data: list) {
			if (!(data.equals("+0Y") || data.equals("+0M") || data.equals("+0D"))) {
				nickname = nickname + data;
			}
		}
		
		form.setNickname(nickname);
		
		return form;
	}
	
}
