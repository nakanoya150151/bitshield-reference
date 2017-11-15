package com.fressets.bitshield.common.exception;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum ApplicationHttpErrors implements HttpErrors {

	// TODO:koike 海外展開することを想定して日本語はmessage_jp.propertiesに書きたい
	INSUFFICIENT_MONEY(HttpStatus.BAD_REQUEST, "振替金額に対してウォレット残高が不足しています。： id={0} balance={1} amount={2} (satoshis)"),
	UNEXPECTED(HttpStatus.INTERNAL_SERVER_ERROR, "想定外のエラーが発生しました。: {0}");

	@Getter(onMethod=@__({@Override}))
	private HttpStatus status;
	@Getter(onMethod=@__({@Override}))
	private String message;
}
