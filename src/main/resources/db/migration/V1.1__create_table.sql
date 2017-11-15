SET SESSION FOREIGN_KEY_CHECKS=0;

/* Drop Tables */

DROP TABLE IF EXISTS address;
DROP TABLE IF EXISTS customer_audit_log;




/* Create Tables */

CREATE TABLE address
(
	id bigint unsigned NOT NULL AUTO_INCREMENT,
	wallet_id bigint unsigned,
	path varchar(128),
	input_user varchar(20),
	update_user varchar(20),
	input_date_time datetime,
	update_date_time datetime,
	version int unsigned,
	is_deleted boolean,
	PRIMARY KEY (id)
);


CREATE TABLE customer_audit_log
(
	id bigint unsigned NOT NULL AUTO_INCREMENT,
	request_id varchar(128),
	login_id varchar(40),
	event_date_time datetime,
	source_ip_address varchar(128),
	is_success boolean,
	operation_name varchar(1024),
	module_name varchar(64),
	request_body varchar(2048),
	PRIMARY KEY (id),
	UNIQUE (id)
);



