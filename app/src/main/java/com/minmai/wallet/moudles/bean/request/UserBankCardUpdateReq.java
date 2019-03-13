package com.minmai.wallet.moudles.bean.request;

public class UserBankCardUpdateReq {

	private String userId; // 用户id
	private String carNumber;// 银行卡号
//	@NotNull(message = "银行名称不能为空")
	private String openBank;// 开户银行名称
	private String phone;// 预留手机号
	private String areaCode; // 区域id
	private String photo; // 银行卡照片
	private String bankId;// 银行卡id
	private String isDefault;// 是否默认使用
	
	private String oldDebitCardId; // 老的信用卡ID

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getCarNumber() {
		return carNumber;
	}

	public void setCarNumber(String carNumber) {
		this.carNumber = carNumber;
	}

	public String getOpenBank() {
		return openBank;
	}

	public void setOpenBank(String openBank) {
		this.openBank = openBank;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getBankId() {
		return bankId;
	}

	public void setBankId(String bankId) {
		this.bankId = bankId;
	}

	public String getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(String isDefault) {
		this.isDefault = isDefault;
	}

	public String getOldDebitCardId() {
		return oldDebitCardId;
	}

	public void setOldDebitCardId(String oldDebitCardId) {
		this.oldDebitCardId = oldDebitCardId;
	}
	
	



}
