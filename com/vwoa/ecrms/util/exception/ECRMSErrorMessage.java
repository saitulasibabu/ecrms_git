/**
 * 
 */
package com.vwoa.ecrms.util.exception;

/**
 * @author Aparna_Deshmukh01
 * 
 */
public final class ECRMSErrorMessage {

	public static enum Constants {
		ERR_LOGIN(999,	"err.login.failure"),
		UNKNOWN_ERROR(1, "err.unable.save"),
		ERR_SELECT_DATA(2,"err.unable.retrieve"),
		ERR_INSERT_DATA(3,"err.unable.save"),
		ERR_UPDATE_DATA(4,"err.unable.update"),
		ERR_VERIFY_DATA(5,"err.unable.verify"),
		ERR_EXPORT_DATA(6,"err.unable.export"),
		ERR_EMAIL_DATA(7,"err.unable.email"), 
		ERR_APPROVE_DATA(8,"err.unable.approve"), 
		ERR_STALL_DATA(9,"err.unable.stall"),
		ERR_REVOKE_DATA(10,"err.unable.revokCerti"),
		ERR_MULTIPLE_REVOKE_DATA(11,"error.multipleRevoke")
		;

		private final int status;
		private final String description;

		Constants(int aStatus, String desc) {
			this.status = aStatus;
			this.description = desc;
		}

		public int status() {
			return this.status;
		}

		public String description() {
			return this.description;
		}

	}

}
