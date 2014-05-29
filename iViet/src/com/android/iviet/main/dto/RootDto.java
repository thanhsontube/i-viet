package com.android.iviet.main.dto;

public class RootDto {
	private NewestDto newestDto;
	private FeaturedDto featuredDto;
	private YoursDto yoursDto;
	
	public RootDto() {
	    super();
    }
	public RootDto(NewestDto newestDto, FeaturedDto featuredDto, YoursDto yoursDto) {
	    super();
	    this.newestDto = newestDto;
	    this.featuredDto = featuredDto;
	    this.yoursDto = yoursDto;
    }
	public NewestDto getNewestDto() {
		return newestDto;
	}
	public void setNewestDto(NewestDto newestDto) {
		this.newestDto = newestDto;
	}
	public FeaturedDto getFeaturedDto() {
		return featuredDto;
	}
	public void setFeaturedDto(FeaturedDto featuredDto) {
		this.featuredDto = featuredDto;
	}
	public YoursDto getYoursDto() {
		return yoursDto;
	}
	public void setYoursDto(YoursDto yoursDto) {
		this.yoursDto = yoursDto;
	}
	
	
}
