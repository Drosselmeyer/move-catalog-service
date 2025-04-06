create database movie_catalog_db;

use movie_catalog_db;

CREATE TABLE movies (
                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        name VARCHAR(255) NOT NULL,
                        release_year INT,
                        synopsis TEXT,
                        image_url VARCHAR(255),
                        category VARCHAR(100),
                        created_by VARCHAR(255),
                        created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                        updated_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Ratings Table
CREATE TABLE ratings (
                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                         movie_id BIGINT NOT NULL,
                         user_email VARCHAR(255) NOT NULL,
                         score INT CHECK (score >= 1 AND score <= 10),
                         created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                         updated_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                         FOREIGN KEY (movie_id) REFERENCES movies(id)
);


CREATE TABLE IF NOT EXISTS user (
                                    id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                    email VARCHAR(255),
    password VARCHAR(255),
    role ENUM('USER', 'ADMIN')
    );

CREATE TABLE IF NOT EXISTS movie_categories (
                                                Movie_id BIGINT NOT NULL,
                                                categories VARCHAR(255),
    FOREIGN KEY (Movie_id) REFERENCES movie(id)
    );

-- Insert Test Movies Data
INSERT INTO movies (name, release_year, synopsis, image_url, category, created_by) VALUES
                                                                                       ('The Shawshank Redemption', 1994, 'Two imprisoned men bond over a number of years, finding solace and eventual redemption through acts of common decency.', 'https://example.com/shawshank.jpg', 'Drama', 'admin'),
                                                                                       ('The Godfather', 1972, 'The aging patriarch of an organized crime dynasty transfers control of his clandestine empire to his reluctant son.', 'https://example.com/godfather.jpg', 'Crime', 'admin'),
                                                                                       ('The Dark Knight', 2008, 'When the menace known as The Joker emerges from his mysterious past, he wreaks havoc and chaos on the people of Gotham.', 'https://example.com/darkknight.jpg', 'Action', 'admin'),
                                                                                       ('Pulp Fiction', 1994, 'The lives of two mob hitmen, a boxer, a gangster\'s wife, and a pair of diner bandits intertwine in four tales of violence and redemption.', 'https://example.com/pulpfiction.jpg', 'Crime', 'user1'),
('Inception', 2010, 'A thief who enters the dreams of others to steal secrets from their subconscious is given the inverse task of planting an idea into the mind of a CEO.', 'https://example.com/inception.jpg', 'Sci-Fi', 'user2'),
('The Matrix', 1999, 'A computer hacker learns from mysterious rebels about the true nature of his reality and his role in the war against its controllers.', 'https://example.com/matrix.jpg', 'Sci-Fi', 'admin'),
('Interstellar', 2014, 'A team of explorers travel through a wormhole in space in an attempt to ensure humanity\'s survival.', 'https://example.com/interstellar.jpg', 'Sci-Fi', 'admin'),
                                                                                       ('Fight Club', 1999, 'An insomniac office worker and a soap salesman build a global organization to help vent male aggression.', 'https://example.com/fightclub.jpg', 'Drama', 'user1');

-- Insert Test Ratings Data
INSERT INTO ratings (movie_id, user_email, score) VALUES
                                                      (1, 'user1@example.com', 9),
                                                      (2, 'user2@example.com', 10),
                                                      (3, 'user1@example.com', 8),
                                                      (4, 'user3@example.com', 7),
                                                      (5, 'user2@example.com', 9),
                                                      (6, 'user1@example.com', 10),
                                                      (7, 'user2@example.com', 8),
                                                      (8, 'user1@example.com', 9);

-- Insert Test Users Data with Hashed Passwords
INSERT INTO users (username, password, role, email) VALUES
                                                        ('admin', '$2a$10$u0XzVhT4eE4kmjk3qg9eK.1qAt5gGcET8FEcQsVRRzjfzQf1VhQ8S', 'ADMIN', 'admin@example.com'),  -- Pass adminpassword
                                                        ('user1', '$2a$10$kbkTpqTjfVQXWzQw6vnMwX9lb9L6o3Eny6Qodx.BtP6FyQu8gUE36', 'USER', 'user1@example.com'), -- Pass user1password
                                                        ('user2', '$2a$10$wTjC5umKw0yJ8hfMCgNgyJ4ZdVR3vnoLv6sO11KN2z9J9D.C3Q2V.', 'USER', 'user2@example.com'), -- Pass user2password
                                                        ('user3', '$2a$10$pt97TY1tXrNmATlCrH/cdExCjmQ1zthGVZEKi9X/vfksxEZeblYiq', 'USER', 'user3@example.com'); -- Pass user3password