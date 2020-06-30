package noticeiro.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Datas {
	String minDate = "0";
	String maxDate = "0";
	String minTime = "0";
	String maxTime = "0";
	
	public Datas (@JsonProperty("minDate") String minDate, @JsonProperty("maxDate") String maxDate,
					@JsonProperty("minTime") String minTime, @JsonProperty("maxTime") String maxTime) {
		System.out.println("0"  + maxDate + "O");
		
		if(minDate.length() != 0)
			this.minDate = minDate.trim();
		if(!minDate.isBlank())
			this.maxDate = maxDate.trim();
		if(minTime.length() != 0) 
			this.minTime = minTime.trim();
		if(maxTime.length() != 0)
			this.maxTime = maxTime.trim();
		
	}
	
	public String getMinDate() {
		return minDate;
	}

	public String getMaxDate() {
		return maxDate;
	}

	public String getMinTime() {
		return minTime;
	}

	public String getMaxTime() {
		return maxTime;
	}
	
}
