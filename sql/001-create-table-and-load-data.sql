DROP TABLE IF EXISTS sweets;
CREATE TABLE sweets (
 id int unsigned AUTO_INCREMENT,
 name VARCHAR(255) NOT NULL,
 company VARCHAR(255),
 price int,
 prefecture VARCHAR(255),
 PRIMARY KEY(id)
);
INSERT INTO sweets (name, company, price, prefecture) VALUES ('博多通りもん','明月堂', 720, '福岡県');
INSERT INTO sweets (name, company, price, prefecture) VALUES ('萩の月', '菓匠三全', 1500, '宮城県');
INSERT INTO sweets (name, company, price, prefecture) VALUES ('白い恋人', '石屋製菓', 1036, '北海道');
INSERT INTO sweets (name, company, price, prefecture) VALUES ('東京ばな奈', '東京ばな奈ワールド', 1198, '東京都');
