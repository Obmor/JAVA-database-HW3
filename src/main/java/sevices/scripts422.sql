CREATE TABLE Person (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    age INT NOT NULL,
    has_license BOOLEAN NOT NULL
);

CREATE TABLE Car (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    brand VARCHAR(255) NOT NULL,
    model VARCHAR(255) NOT NULL,
    price INT(16) NOT NULL
);


CREATE TABLE Person_Car (
    person_id BIGINT NOT NULL,
    car_id BIGINT NOT NULL,
    PRIMARY KEY (person_id, car_id),
    FOREIGN KEY (person_id) REFERENCES Person(id),
    FOREIGN KEY (car_id) REFERENCES Car(id)
);


INSERT INTO Person (name, age, has_license) VALUES ('Harry', 21, true);
INSERT INTO Person (name, age, has_license) VALUES ('Draco', 22, false);


INSERT INTO Car (brand, model, price) VALUES ('Tank', 'T34', 2000000);
INSERT INTO Car (brand, model, price) VALUES ('Bicycle', 'Aist', 2000);


INSERT INTO Person_Car (person_id, car_id) VALUES (1, 1);
INSERT INTO Person_Car (person_id, car_id) VALUES (1, 2);