 -- Keeps track of users and account totals
CREATE TABLE IF NOT EXISTS users (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    username TEXT NOT NULL,
    age INTEGER NOT NULL,
    savings REAL DEFAULT 0.0,
    checking REAL DEFAULT 0.0,
    goals REAL DEFAULT 0.0,
    goalName TEXT DEFAULT '',
    goalCost REAL DEFAULT 0.0
);

-- This table keeps track of transacitons
CREATE TABLE IF NOT EXISTS transactions (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    userId INTEGER NOT NULL,
    accountType TEXT NOT NULL,
    amount REAL NOT NULL,
    transactionType TEXT NOT NULL,
    description TEXT CHECK(LENGTH(description) <= 20),
    date TEXT NOT NULL,
    FOREIGN KEY(userId) REFERENCES users(id)
);


