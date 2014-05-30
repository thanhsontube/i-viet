package com.android.iviet.user;

public class ProfileDto {
	int drawerId;

	
	public ProfileDto() {
	    super();
	    // TODO Auto-generated constructor stub
    }

	public ProfileDto(int drawerId) {
	    super();
	    this.drawerId = drawerId;
    }

	public int getDrawerId() {
		return drawerId;
	}

	public void setDrawerId(int drawerId) {
		this.drawerId = drawerId;
	}
	
}
