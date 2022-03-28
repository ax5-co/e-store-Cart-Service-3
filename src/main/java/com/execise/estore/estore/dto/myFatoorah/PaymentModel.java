package com.execise.estore.estore.dto.myFatoorah;

import javax.validation.constraints.NotNull;

import com.execise.estore.estore.enums.MyFatoorahApiType;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;


@Getter
public class PaymentModel {

//	@ValueOfEnum(enumClass = MyFatoorahApiType.class)
//	private String myFatoorahApiType;

	@JsonProperty("MyFatoorahApi")
	@NotNull(message = "myFatoorahApiType must be type from enum myFatoorahApiType")
	private MyFatoorahApiType myFatoorahApiType;

	public void setMyFatoorahApiType(Object typeInput) {
		for(MyFatoorahApiType typeEnum: MyFatoorahApiType.values()) 
			if(typeEnum.name().equalsIgnoreCase(typeInput.toString())) 
				this.myFatoorahApiType = MyFatoorahApiType.valueOf(typeInput.toString());
	}
	
}
