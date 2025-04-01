CREATE TABLE users (
    id            INT PRIMARY KEY AUTO_INCREMENT,
    user_email    VARCHAR(255) UNIQUE NOT NULL,
    user_pw       VARCHAR(255) NOT NULL,
    user_name     VARCHAR(100) NOT NULL,
    user_phone    VARCHAR(20),
    user_image    VARCHAR(255) -- image url
);

CREATE TABLE items (
    id                 INT PRIMARY KEY AUTO_INCREMENT,
    seller_id          INT NOT NULL,
    item_name          VARCHAR(255) NOT NULL,
    starting_price     DECIMAL(20,2) NOT NULL,
    item_description   TEXT,
    item_image         VARCHAR(255),
    auction_status     ENUM('ACTIVE', 'ENDED') NOT NULL DEFAULT 'ACTIVE',
    end_time           TIMESTAMP NOT NULL,
    FOREIGN KEY (seller_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE bids (
    id           INT PRIMARY KEY AUTO_INCREMENT,
    item_id      INT NOT NULL,
    bidder_id    INT NOT NULL,
    bid_price    DECIMAL(20,2) NOT NULL,
    bid_time     TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (item_id) REFERENCES items(id) ON DELETE CASCADE,
    FOREIGN KEY (bidder_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE payments (
    id              INT PRIMARY KEY AUTO_INCREMENT,
    winner_id       INT NOT NULL,
    item_id         INT NOT NULL,
    final_price     DECIMAL(20,2) NOT NULL,
    payment_time    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    payment_status  ENUM('PENDING', 'COMPLETED', 'FAILED') NOT NULL DEFAULT 'PENDING',
    FOREIGN KEY (winner_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (item_id) REFERENCES items(id) ON DELETE CASCADE
);

CREATE TABLE notifications (
    id              INT PRIMARY KEY AUTO_INCREMENT,
    user_id         INT NOT NULL,
    type            VARCHAR(50) NOT NULL,
    message         TEXT NOT NULL,
    notified_at     TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    is_read         BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);
