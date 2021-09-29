CREATE TABLE date_formulas (
	date_formula_id INT AUTO_INCREMENT,
	date_formula_code VARCHAR(30) NOT NULL,
	year INT NOT NULL,
	month INT NOT NULL,
	day INT NOT NULL,
	explanation VARCHAR(50),
	user_id INT NOT NULL,
	PRIMARY KEY (date_formula_id)
	FOREIGN KEY fk_user_id(user_id) REFERENCES users(user_id)
);

CREATE TABLE users (
	user_id INT AUTO_INCREMENT,
	mail_address VARCHAR(50) NOT NULL,
	username VARCHAR(40) NOT NULL,
	password VARCHAR(200) NOT NULL,
	role VARCHAR(20) NOT NULL,
	PRIMARY KEY (user_id)
);