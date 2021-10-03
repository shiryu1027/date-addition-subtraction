INSERT INTO users (mail_address, username, password, role)
VALUES ('user@gmail.com', 'ユーザー', 'password', 'ROLE_GENERAL');

INSERT INTO users (mail_address, username, password, role)
VALUES ('user2@gmail.com', 'ユーザー2', 'password', 'ROLE_GENERAL');

INSERT INTO date_formulas (date_formula_code, year, month, day, explanation, user_id)
VALUES ('+1Y', 1, 0, 0, '一年後', 1);

INSERT INTO date_formulas (date_formula_code, year, month, day, explanation, user_id)
VALUES ('+3Y+4M', 3, 4, 0, '3年と4か月後', 1);

INSERT INTO date_formulas (date_formula_code, year, month, day, explanation, user_id)
VALUES ('-3Y-4M', -3, -4, 0, '3年と4か月前', 2);