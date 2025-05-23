CREATE TABLE IF NOT EXISTS cards (
                       id INTEGER PRIMARY KEY,
                       question TEXT NOT NULL,
                       answer TEXT NOT NULL,
                       tag TEXT,
                       category VARCHAR(20),
                       updated_at TIMESTAMP,
                       last_answered_at TIMESTAMP,
                       created_at TIMESTAMP
);

CREATE TABLE IF NOT EXISTS quizz (
                       id INTEGER PRIMARY KEY,
                       status VARCHAR(20),
                       user_id UUID NOT NULL,
                       date TIMESTAMP,
                       started_at TIMESTAMP,
                       completed_at TIMESTAMP
);

CREATE TABLE IF NOT EXISTS quizz_cards (
                             quizz_id INTEGER,
                             cards_id INTEGER,
                             PRIMARY KEY (quizz_id, cards_id),
                             FOREIGN KEY (quizz_id) REFERENCES quizz(id) ON DELETE CASCADE,
                             FOREIGN KEY (cards_id) REFERENCES cards(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS quizz_answers (
                               quizz_id INTEGER NOT NULL,
                               card_id INTEGER NOT NULL,
                               is_correct BOOLEAN,
                               PRIMARY KEY (quizz_id, card_id),
                               FOREIGN KEY (quizz_id) REFERENCES quizz(id) ON DELETE CASCADE
);
